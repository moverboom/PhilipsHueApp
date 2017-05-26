package com.matthijs.philipshue;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.matthijs.philipshue.ColorPicker.ColorPicker;
import com.matthijs.philipshue.ColorPicker.OpacityBar;
import com.matthijs.philipshue.ColorPicker.SVBar;

import java.util.List;

/**
 * Created by Matthijs Overboom on 28-5-16.
 *
 * @link http://www.developers.meethue.com/documentation/core-concepts#color_gets_more_complicated
 */
public class ColorPickerActivity extends AppCompatActivity implements ColorPicker.OnColorChangedListener {
    private ColorPicker picker;
    private SVBar svBar;
    private OpacityBar opacityBar;
    private Button button;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        picker = (ColorPicker) findViewById(R.id.picker);
        double colorX = getIntent().getDoubleExtra("COLOR_X", 0);
        double colorY = getIntent().getDoubleExtra("COLOR_Y", 0);
        double colorZ = (colorY / colorX) * (1 - colorX - colorY);
        int color = ColorUtils.XYZToColor(colorX, colorY, colorZ);
        picker.setColor(color);

        //svBar = (SVBar) findViewById(R.id.svbar);
        //opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
        button = (Button) findViewById(R.id.button1);
        //text = (TextView) findViewById(R.id.textView1);

        //picker.addSVBar(svBar);
        //picker.addOpacityBar(opacityBar);
        picker.setOnColorChangedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setOldCenterColor(picker.getColor());
                Intent resultIntent = new Intent();
                resultIntent.putExtra("COLOR_X", new Double(String.format("%.4f", convertToXY(picker.getColor())[0])));
                resultIntent.putExtra("COLOR_Y", new Double(String.format("%.4f", convertToXY(picker.getColor())[1])));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    public void onColorChanged(int color) {
        Log.d("PhilipsHue", "New color: " + color);
        double[] xy = convertToXY(color);
        Log.d("PhilipsHue", Double.toString(xy[0]) + ", " + Double.toString(xy[1]));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent resultIntent = new Intent();
                setResult(RESULT_CANCELED, resultIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Converts the returned color as int from the colorwheel to xy values as doubles
     * @param color color as int
     * @return double[]
     */
    private double[] convertToXY(int color) {
        double[] result = new double[2];
        double[] xyzValues = new double[3];
        ColorUtils.colorToXYZ(color, xyzValues);

        result[0] = xyzValues[0] / (xyzValues[0] + xyzValues[1] + xyzValues[2]);
        result[1] = xyzValues[1] / (xyzValues[0] + xyzValues[1] + xyzValues[2]);

        return result;
    }
}
