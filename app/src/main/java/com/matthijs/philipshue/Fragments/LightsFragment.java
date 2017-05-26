package com.matthijs.philipshue.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.matthijs.philipshue.Adapters.GroupAdapter;
import com.matthijs.philipshue.Adapters.LightAdapter;
import com.matthijs.philipshue.GroupSettingsActivity;
import com.matthijs.philipshue.HueController.HueController;
import com.matthijs.philipshue.LightSettingsActivity;
import com.matthijs.philipshue.Model.Group;
import com.matthijs.philipshue.Model.Light;
import com.matthijs.philipshue.R;

import java.util.ArrayList;

/**
 * Created by Matthijs Overboom on 28-5-16.
 */
public class LightsFragment extends ListFragment implements AdapterView.OnItemClickListener {
    LightAdapter lightAdapter;
    LayoutInflater layoutInflater;
    HueController hueController;
    ArrayList<Light> lightArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hueController = HueController.create(getContext());
        this.layoutInflater = inflater;
        return inflater.inflate(R.layout.fragment_lights, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lightAdapter = new LightAdapter(layoutInflater, lightArrayList);
        hueController.setAdapter(lightAdapter);
        hueController.getLights(lightArrayList);

        setListAdapter(lightAdapter);

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent lightSettingsIntent = new Intent(getContext(), LightSettingsActivity.class);
        Light light = lightArrayList.get(i);
        lightSettingsIntent.putExtra("LIGHT", light);
        lightSettingsIntent.putExtra("LIGHTSTATE", light.getState());
        startActivity(lightSettingsIntent);
    }
}
