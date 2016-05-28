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
 * Created by matthijs on 28-5-16.
 */
public class GroupsFragment extends ListFragment implements AdapterView.OnItemClickListener {
    GroupAdapter groupAdapter;
    LayoutInflater layoutInflater;
    HueController hueController;
    ArrayList<Group> groupArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hueController = new HueController();
        this.layoutInflater = inflater;
        return inflater.inflate(R.layout.fragment_groups, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        groupAdapter = new GroupAdapter(getContext(), layoutInflater, groupArrayList);
        hueController.setAdapter(groupAdapter);
        hueController.getGroups(groupArrayList);

        setListAdapter(groupAdapter);
        groupAdapter.notifyDataSetChanged();

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("PhilipsHue", "Group clicked: " + i);
        Intent groupSettingsIntent = new Intent(getContext(), GroupSettingsActivity.class);
        groupSettingsIntent.putExtra("GROUP", groupArrayList.get(i));
        startActivity(groupSettingsIntent);
    }
}

