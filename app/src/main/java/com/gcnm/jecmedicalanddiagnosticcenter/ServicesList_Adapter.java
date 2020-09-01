package com.gcnm.jecmedicalanddiagnosticcenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

class ServicesList_Adapter extends BaseExpandableListAdapter {

  Activity context;
  Map<String, List<String>> parentListItems;
  List<String> item;
  Object listView;

  public ServicesList_Adapter(Activity context, List<String> item, Map<String, List<String>> parentListItems) {
    this.context = context;
    this.parentListItems = parentListItems;
    this.item = item;
  }


  //Parent

  @Override
  public int getGroupCount() {
    return item.size();
  }

  @Override
  public Object getGroup(int i) {
    return item.get(i);
  }


  @Override
  public long getGroupId(int i) {
    return i;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @SuppressLint("InflateParams")
  @Override
  public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
    String parentName = (String) getGroup(i);
    if (view == null) {
      LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      assert layoutInflater != null;
      view = layoutInflater.inflate(R.layout.services_parent_list, null);
    }

    TextView item = view.findViewById(R.id.txtServicesParent);
    item.setText(parentName);

    return view;
  }

  //Child

  @Override
  public long getChildId(int i, int i1) {
    return i;
  }

  @Override
  public Object getChild(int i, int i1) {
    return Objects.requireNonNull(parentListItems.get(item.get(i))).get(i1);
  }

  @SuppressLint("InflateParams")
  @Override
  public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
    String childName = (String) getChild(i, i1);
    LayoutInflater inflater = context.getLayoutInflater();

    if (view == null) {
      view = inflater.inflate(R.layout.services_child_list, null);
    }

    TextView item = view.findViewById(R.id.txtServicesChild);
    item.setText(childName);

    return view;
  }

  @Override
  public boolean isChildSelectable(int i, int i1) {
    return true;
  }

  @Override
  public int getChildrenCount(int i) {
    return Objects.requireNonNull(parentListItems.get(item.get(i))).size();
  }
}
