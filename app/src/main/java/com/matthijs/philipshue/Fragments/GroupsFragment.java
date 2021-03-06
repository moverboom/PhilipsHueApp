package com.matthijs.philipshue.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.matthijs.philipshue.Adapters.GroupAdapter;
import com.matthijs.philipshue.GroupSettingsActivity;
import com.matthijs.philipshue.HueController.HueController;
import com.matthijs.philipshue.Model.Group;
import com.matthijs.philipshue.R;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by Matthijs Overboom on 28-5-16.
 */
public class GroupsFragment extends ListFragment implements AdapterView.OnItemClickListener {
    GroupAdapter groupAdapter;
    LayoutInflater layoutInflater;
    HueController hueController;
    ArrayList<Group> groupArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hueController = HueController.create(getContext());
        this.layoutInflater = inflater;
        return inflater.inflate(R.layout.fragment_groups, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        groupAdapter = new GroupAdapter(layoutInflater, groupArrayList);
        hueController.setAdapter(groupAdapter);
        hueController.getGroups(groupArrayList);

        setListAdapter(groupAdapter);

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent groupSettingsIntent = new Intent(getContext(), GroupSettingsActivity.class);
        Group group = groupArrayList.get(i);
        groupSettingsIntent.putExtra("GROUP", group);
        groupSettingsIntent.putExtra("GROUPSTATE", group.getState());
        startActivity(groupSettingsIntent);
    }
}

