package com.gcnm.jecmedicalanddiagnosticcenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsViewHolder> {

  ViewAppointments viewAppointments;
  ArrayList<AppointmentsModel> userArrayList;
  View view;

  public AppointmentsAdapter(ViewAppointments viewAppointments, ArrayList<AppointmentsModel> userArrayList) {
    this.userArrayList = userArrayList;
    this.viewAppointments = viewAppointments;
  }

  @NonNull
  @Override
  public AppointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_appointment_list, parent, false);
    return new AppointmentsViewHolder(view);
  }


  @Override
  public void onBindViewHolder(@NonNull AppointmentsViewHolder holder, int position) {
    holder._TimeSubmitted.setText(userArrayList.get(position).timesubmitted);
    holder._SelectedService.setText(userArrayList.get(position).getSelectedservice());
    holder._SelectedAppointmentTime.setText(userArrayList.get(position).getSelectedappointmenttime());
    holder._SelectedAppointmentDate.setText(userArrayList.get(position).getSelectedappointmentdate());
    holder._Gender.setText(userArrayList.get(position).getGender());
    holder._FullName.setText(userArrayList.get(position).getFullname());
    holder._DateSubmitted.setText(userArrayList.get(position).getDatesubmitted());
  }

  @Override
  public int getItemCount() {
    return userArrayList.size();
  }
}
