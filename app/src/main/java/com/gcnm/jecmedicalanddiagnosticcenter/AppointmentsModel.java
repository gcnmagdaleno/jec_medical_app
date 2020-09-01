package com.gcnm.jecmedicalanddiagnosticcenter;

import androidx.annotation.Keep;

@Keep
public class AppointmentsModel {

  public String datesubmitted;
  public String fullname;
  public String gender;
  public String selectedappointmentdate;
  public String selectedappointmenttime;
  public String selectedservice;
  public String timesubmitted;


  @Keep
  public AppointmentsModel() {
  }

  @Keep
  public AppointmentsModel(String DateSubmitted, String fullname, String gender, String selectedappointmentdate, String selectedappointmenttime, String selectedservice, String timesubmitted) {
    this.datesubmitted = DateSubmitted;
    this.fullname = fullname;
    this.gender = gender;
    this.selectedappointmentdate = selectedappointmentdate;
    this.selectedappointmenttime = selectedappointmenttime;
    this.selectedservice = selectedservice;
    this.timesubmitted = timesubmitted;
  }

  public String getDatesubmitted() {
    return datesubmitted;
  }

  public AppointmentsModel setDateSubmitted(String dateSubmitted) {
    dateSubmitted = dateSubmitted;
    return this;
  }

  public String getFullname() {
    return fullname;
  }

  public AppointmentsModel setFullname(String fullName) {
    fullname = fullName;
    return this;
  }

  public String getGender() {
    return gender;
  }

  public AppointmentsModel setGender(String gender) {
    gender = gender;
    return this;
  }

  public String getSelectedappointmentdate() {
    return selectedappointmentdate;
  }

  public AppointmentsModel setSelectedappointmentdate(String selectedappointmentdate) {
    selectedappointmentdate = selectedappointmentdate;
    return this;
  }

  public String getSelectedappointmenttime() {
    return selectedappointmenttime;
  }

  public AppointmentsModel setSelectedappointmenttime(String selectedappointmenttime) {
    selectedappointmenttime = selectedappointmenttime;
    return this;
  }

  public String getSelectedservice() {
    return selectedservice;
  }

  public AppointmentsModel setSelectedservice(String selectedservice) {
    selectedservice = selectedservice;
    return this;
  }

  public String getTimesubmitted() {
    return timesubmitted;
  }

  public AppointmentsModel setTimesubmitted(String timesubmitted) {
    timesubmitted = timesubmitted;
    return this;
  }
}
