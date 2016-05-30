package com.matthijs.philipshue.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.matthijs.philipshue.Model.Group;
import com.matthijs.philipshue.R;

import java.util.ArrayList;

/**
 * Created by Matthijs Overboom on 28-5-16.
 */
public class GroupAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Group> groupArrayList;

    public GroupAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Group> groupArrayList) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.groupArrayList = groupArrayList;
    }

    @Override
    public int getCount() {
        return groupArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return groupArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder = new GroupViewHolder();

        if(view == null || view.getTag() == null) {
            view = layoutInflater.inflate(R.layout.group_list, null);

            groupViewHolder.name = (TextView)view.findViewById(R.id.groupName);

            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder)view.getTag();
        }

        Group group = groupArrayList.get(i);
        groupViewHolder.name.setText(group.getName());
        view.findViewById(R.id.loading_icon).setVisibility(View.GONE);
        return view;
    }

    private class GroupViewHolder {
        protected TextView name;
    }
}
