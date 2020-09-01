package com.gcnm.jecmedicalanddiagnosticcenter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class AppointmentsViewHolder extends RecyclerView.ViewHolder {
  public TextView _DateSubmitted;
  public TextView _FullName;
  public TextView _Gender;
  public TextView _SelectedAppointmentDate;
  public TextView _SelectedAppointmentTime;
  public TextView _SelectedService;
  public TextView _TimeSubmitted;

  public AppointmentsViewHolder(View itemView) {
    super(itemView);
    _DateSubmitted = itemView.findViewById(R.id.txtDateSubmitted);
    _FullName = itemView.findViewById(R.id.txtFullName);
    _Gender = itemView.findViewById(R.id.txtGender);
    _SelectedAppointmentTime = itemView.findViewById(R.id.txtAppointmentTime);
    _SelectedAppointmentDate = itemView.findViewById(R.id.txtAppointmentDate);
    _SelectedService = itemView.findViewById(R.id.txtSelectedService);
    _TimeSubmitted = itemView.findViewById(R.id.txtTimeSubmitted);
  }
}