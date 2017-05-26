package com.matthijs.philipshue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.matthijs.philipshue.ColorPickerActivity;
import com.matthijs.philipshue.GroupSettingsActivity;
import com.matthijs.philipshue.HueController.HueController;
import com.matthijs.philipshue.Model.Group;
import com.matthijs.philipshue.Model.Light;
import com.matthijs.philipshue.Model.State;

/**
 * Created by Matthijs Overboom on 25-5-17.
 */

public class LightSettingsActivity extends AppCompatActivity implements Button.OnClickListener {
    private Light light;
    private static final int PICK_COLOR = 111;
    HueController hueController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        hueController = HueController.create(getBaseContext());

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        light = (Light)getIntent().getSerializableExtra("LIGHT");
        light.setState((State) getIntent().getSerializableExtra("LIGHTSTATE"));
        TextView label = (TextView)findViewById(R.id.lightNameAct);
        label.setText(light.getName());

        Button changeColor = (Button)findViewById(R.id.change_color_button);
        changeColor.setOnClickListener(this);

        Switch onOffSwitch = (Switch)findViewById(R.id.lightState_act);
        onOffSwitch.setChecked(light.getState().on);
        onOffSwitch.setOnCheckedChangeListener(new OnOfSwitchListener());

        Switch colorLoopSwitch = (Switch)findViewById(R.id.colorLoopSwitch);
        colorLoopSwitch.setChecked(light.getState().effect.equals("colorloop"));
        colorLoopSwitch.setOnCheckedChangeListener(new ColorLoopListener());
    }

    @Override
    public void onClick(View view) {
        Intent colorPicker = new Intent(getApplicationContext(), ColorPickerActivity.class);
        colorPicker.putExtra("LIGHT", light);
        colorPicker.putExtra("COLOR_X", light.getState().x);
        colorPicker.putExtra("COLOR_Y", light.getState().y);
        startActivityForResult(colorPicker, PICK_COLOR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_COLOR && resultCode == RESULT_OK) {
            Log.d("PhilipHue", "Picked ColorX: " + data.getDoubleExtra("COLOR_X", 0.0));
            Log.d("PhilipHue", "Picked ColorY: " + data.getDoubleExtra("COLOR_Y", 0.0));
            light.getState().x = data.getDoubleExtra("COLOR_X", 0.0);
            light.getState().y = data.getDoubleExtra("COLOR_Y", 0.0);
            hueController.controlLight(light);
        }
    }

    private class OnOfSwitchListener implements Switch.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            light.getState().on = b;
            hueController.controlLight(light);
        }
    }

    private class ColorLoopListener implements Switch.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            light.getState().effect = b ? "colorloop":"none";
            hueController.controlLight(light);
        }
    }
}