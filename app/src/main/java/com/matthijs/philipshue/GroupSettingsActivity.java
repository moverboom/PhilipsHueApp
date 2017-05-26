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

import com.matthijs.philipshue.HueController.HueController;
import com.matthijs.philipshue.Model.Group;
import com.matthijs.philipshue.Model.State;

/**
 * Created by Matthijs Overboom on 28-5-16.
 */
public class GroupSettingsActivity extends AppCompatActivity implements Button.OnClickListener {
    private Group group;
    private static final int PICK_COLOR = 111;
    HueController hueController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        hueController = HueController.create(getBaseContext());

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        group = (Group)getIntent().getSerializableExtra("GROUP");
        group.setState((State) getIntent().getSerializableExtra("GROUPSTATE"));
        TextView label = (TextView)findViewById(R.id.groupNameAct);
        label.setText(group.getName());

        Button changeColor = (Button)findViewById(R.id.change_color_button);
        changeColor.setOnClickListener(this);

        Switch onOffSwitch = (Switch)findViewById(R.id.groupState_act);
        onOffSwitch.setChecked(group.getState().on);
        onOffSwitch.setOnCheckedChangeListener(new OnOfSwitchListener());

        Switch colorLoopSwitch = (Switch)findViewById(R.id.colorLoopSwitch);
        colorLoopSwitch.setChecked(group.getState().effect.equals("colorloop"));
        colorLoopSwitch.setOnCheckedChangeListener(new ColorLoopListener());
    }

    @Override
    public void onClick(View view) {
        Intent colorPicker = new Intent(getApplicationContext(), ColorPickerActivity.class);
        colorPicker.putExtra("GROUP", group);
        startActivityForResult(colorPicker, PICK_COLOR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_COLOR && resultCode == RESULT_OK) {
            group.getState().x = data.getDoubleExtra("COLOR_X", 0.0);
            group.getState().y = data.getDoubleExtra("COLOR_Y", 0.0);
            hueController.controlGroup(group);
        }
    }

    private class OnOfSwitchListener implements Switch.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            group.getState().on = b;
            hueController.controlGroup(group);
        }
    }

    private class ColorLoopListener implements Switch.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            group.getState().effect = b ? "colorloop":"none";
            hueController.controlGroup(group);
        }
    }
}
