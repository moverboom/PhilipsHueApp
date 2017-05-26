package com.matthijs.philipshue.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.matthijs.philipshue.Model.Light;
import com.matthijs.philipshue.R;

import java.util.ArrayList;

/**
 * Created by Matthijs Overboom on 25-5-17.
 */

public class LightAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<Light> lightArrayList;

    public LightAdapter(LayoutInflater layoutInflater, ArrayList<Light> lightArrayList) {
        this.layoutInflater = layoutInflater;
        this.lightArrayList = lightArrayList;
    }

    @Override
    public int getCount() {
        return lightArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return lightArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LightViewHolder lightViewHolder = new LightViewHolder();

        if (view == null || view.getTag() == null) {
            view = layoutInflater.inflate(R.layout.light_list, null);

            lightViewHolder.name = (TextView) view.findViewById(R.id.lightName);

            view.setTag(lightViewHolder);
        } else {
            lightViewHolder = (LightViewHolder) view.getTag();
        }

        Light light = lightArrayList.get(i);
        lightViewHolder.name.setText(light.getName());
        view.findViewById(R.id.loading_icon).setVisibility(View.GONE);
        return view;
    }

    private class LightViewHolder {
        protected TextView name;
    }
}