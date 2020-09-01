package com.gcnm.jecmedicalanddiagnosticcenter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BookingScheduling_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
  public TextView btnAppointmentDate, getBtnAppointmentTime, txtFN, txtMN, txtLN, btnSubmitBooking, txtBookError, txtContactNumber, txtAddress, btnHome, btnViewAppointments;
  public int _appointmentDay, _appointmentMonth, _appointmentYear, _appointmentHour, _appointmentMinute;
  public int intServiceSelectedIndex = 0;
  public RadioButton rbMale;
  public FirebaseFirestore firebaseFirestore;
  public DocumentReference documentReference;
  public Map<String, Object> firebaseAppointments;
  public String[] arrayServices = {};
  public String selectedAppointmentDate, _selectedAppointmentDateFinal, selectedAppointmentTime, strFullName;
  public Spinner spinnerServices;
  public Calendar _currentDate;
  public SharedPreferences sharedPreference;
  public SharedPreferences.Editor sharedEditor;


  ////////////////////////////////////////////ExpandableListView


  @SuppressLint({"SetTextI18n", "Assert", "CommitPrefEdits"})
  @Override
  protected void onCreate(Bundle savedInstanceState) {
//    try {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_booking_scheduling_);


    sharedPreference = getSharedPreferences("__savedData", MODE_PRIVATE);
    sharedEditor = sharedPreference.edit();


    btnAppointmentDate = findViewById(R.id.btnAppointmentDate);
    getBtnAppointmentTime = findViewById(R.id.btnAppointmentTime);
    btnHome = findViewById(R.id.btnBookBackToHome);
    btnViewAppointments = findViewById(R.id.btnBookViewAppointments);

    btnHome.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(BookingScheduling_Activity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        BookingScheduling_Activity.this.finish();
      }
    });

    btnViewAppointments.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(BookingScheduling_Activity.this, ViewAppointments.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        BookingScheduling_Activity.this.finish();
      }
    });


    txtFN = findViewById(R.id.txtFN);
    txtLN = findViewById(R.id.txtLN);
    txtMN = findViewById(R.id.txtMN);
    txtContactNumber = findViewById(R.id.txtCNumber);
    txtAddress = findViewById(R.id.txtAddress);
    rbMale = findViewById(R.id.rbMale);
    txtBookError = findViewById(R.id.txtBookError);

    _currentDate = Calendar.getInstance();
    _appointmentDay = _currentDate.get(Calendar.DAY_OF_MONTH);
    _appointmentMonth = _currentDate.get(Calendar.MONTH);
    _appointmentYear = _currentDate.get(Calendar.YEAR);

    btnAppointmentDate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(BookingScheduling_Activity.this, new DatePickerDialog.OnDateSetListener() {
          @Override
          public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month = month + 1;

            int selectedMonth = month;
            int selectedDay = dayOfMonth;
            int selectedYear = year;
            int currentMonth = _appointmentMonth + 1;
            int currentDay = _appointmentDay;
            int currentYear = _appointmentYear;
            boolean isLessYear = false;
            boolean isLessMonth = false;

            if (selectedMonth < currentMonth) {
              selectedMonth = currentMonth;
              isLessMonth = true;
            }

            if (selectedYear < currentYear) {
              selectedYear = currentYear;
              isLessYear = true;
            }

            if (isLessYear && (selectedMonth < currentMonth)) {
              selectedMonth = currentMonth;
              isLessMonth = true;
            }

            if ((isLessMonth) && isLessYear && (selectedDay < currentDay))
              selectedDay = currentDay;

            if (isLessMonth && (selectedDay < currentDay)) selectedDay = currentDay;

            if (isLessYear && (selectedDay < currentDay)) selectedDay = currentDay;

            if (!isLessMonth && (selectedDay < currentDay)) selectedDay = currentDay;

            if (!isLessYear && (selectedDay < currentDay)) selectedDay = currentDay;

            if (isLessMonth && !isLessYear) selectedMonth = currentMonth;

            selectedAppointmentDate = selectedMonth + "-" + selectedDay + "-" + selectedYear;

            btnAppointmentDate.setText("The following is the adjusted selected date of appointment.\n");
            btnAppointmentDate.setText(btnAppointmentDate.getText().toString() + selectedAppointmentDate + "\n" + isLessMonth + " - " + isLessYear);
          }
        }, _appointmentYear, _appointmentMonth, _appointmentDay);
        Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
        datePickerDialog.show();
      }
    });

    getBtnAppointmentTime.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        _appointmentHour = c.get(Calendar.HOUR_OF_DAY);
        _appointmentMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(BookingScheduling_Activity.this,
            new TimePickerDialog.OnTimeSetListener() {

              @Override
              public void onTimeSet(TimePicker view, int hourOfDay,
                                    int minute) {

                String time1 = hourOfDay + ":" + minute;
                @SuppressLint("SimpleDateFormat") SimpleDateFormat _selectedAppointmentDateFormat1 = new SimpleDateFormat("h:mm");
                @SuppressLint("SimpleDateFormat") SimpleDateFormat _selectedAppointmentDateFormat2 = new SimpleDateFormat("h:mm:ss a");
                Date _selectedAppointmentTime = null;
                try {
                  _selectedAppointmentTime = (_selectedAppointmentDateFormat1.parse(time1));
                } catch (ParseException e) {

                  _errorHandler(e.toString(), Color.rgb(241, 128, 128), Color.rgb(36, 36, 36), 5000);


                }

                assert _selectedAppointmentTime != null;
                selectedAppointmentTime = _selectedAppointmentDateFormat2.format(_selectedAppointmentTime);

//                  selectedAppointmentTime = hourOfDay + ":" + minute;

//                  _currentTime2 = new SimpleDateFormat("h:mm:ss a", ;
                getBtnAppointmentTime.setText("The time selected for the appointment is:\n" + selectedAppointmentTime);
              }
            }, _appointmentHour, _appointmentMinute, false);
        timePickerDialog.show();
      }
    });

    spinnerServices = findViewById(R.id.spinnerServices);
    spinnerServices.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        ((TextView) spinnerServices.getSelectedView()).setTextColor(Color.WHITE);
      }
    });

    String _freePhysical = "FREE PHYSICAL EXAMINATION AND FIT TO WORK CERTIFICATE\n";
    String _freePhysical2 = "FREE PHYSICAL EXAMINATION\n";

    arrayServices = new String[]{
        "Pre-Employment Package A (PHP 250.00) \nRT. Urinalysis, Fecalysis, Chest X-Ray\n" + _freePhysical,
        "Pre-Employment Package B (PHP 350.00) \nComplete Blood Count (CBC), RT. Urinalysis, Fecalysis, Chest X-Ray\n" + _freePhysical,
        "Pre-Employment Package C (PHP 550.00) \nComplete Blood Count (CBC), Blood Typing/RH Typing, Urinalysis, Fecalysis, Chest X-Ray\n" + _freePhysical,
        "Pre-Employment Package D (PHP 850.00) \nComplete Blood Count (CBC), Hepatitis B (Screening), Hepatitis A (Screening), Urinalysis, Fecalysis, Chest X-Ray\n" + _freePhysical,
        "Pre-Employment Package E (PHP 600.00) \nComplete Blood Count (CBC), Hepatitis B (Screening), Urinalysis, Fecalysis, Chest X-Ray\n" + _freePhysical,
        "Pre-Employment Package F (PHP 650.00) \nComplete Blood Count (CBC), Hepatitis A (Screening), Urinalysis, Fecalysis, Chest X-Ray\n" + _freePhysical,

        "Executive Check-Up Package A (PHP 900.00) \nComplete Blood Count (CBC), RT. Urinalysis, Fecalysis, Chest X-Ray" +
            "FBS (Fasting Blood Sugar), Cholesterol, Triglycerides, ECG with Reading\n" + _freePhysical2,
        "Executive Check-Up Package B (PHP 1000.00) \nComplete Blood Count (CBC), RT. Urinalysis, Fecalysis, Chest X-Ray" +
            "FBS (Fasting Blood Sugar), Cholesterol, Triglycerides, Uric Acid, Creatinine,  ECG with Reading\n" + _freePhysical2,
        "Executive Check-Up Package C (PHP 1800.00) \nComplete Blood Count (CBC), RT. Urinalysis, Fecalysis, Chest X-Ray" +
            "FBS (Fasting Blood Sugar), Lipid Profile, Uric Acid, BUN, Creatinine, SGOT, SGPT, ECG with Reading\n" + _freePhysical2,
    };

    ArrayAdapter<String> adapterServices = new ArrayAdapter<String>(BookingScheduling_Activity.this, android.R.layout.simple_spinner_item, arrayServices);

    adapterServices.setDropDownViewResource(R.layout.custom_spinnertext);
    spinnerServices.setAdapter(adapterServices);
    spinnerServices.setOnItemSelectedListener(BookingScheduling_Activity.this);
//    text.contains("Java8")
    btnSubmitBooking = findViewById(R.id.btnSubmitBooking);
    btnSubmitBooking.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          firebaseDatabase();
          saveToFirebaseDatabase();
        } catch (ParseException e) {
//            Toast.makeText(getApplicationContext(), "The error(s) encountered: " + e.toString(), Toast.LENGTH_LONG).show();
          Log.d("gianLogcat1", e.toString());
          _errorHandler("All fields are required\nBawal ang blankong sagot sa form na ito.\n" + e.toString(), Color.rgb(241, 128, 128), Color.rgb(36, 36, 36), 7000);


        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), "The error(s) encountered: " + e.toString(), Toast.LENGTH_LONG).show();
          Log.d("gianLogcat2", e.toString());
          _errorHandler("All fields are required\nBawal ang blankong sagot sa form na ito.\n" + e.toString(), Color.rgb(241, 128, 128), Color.rgb(36, 36, 36), 7000);

        } catch (Error e) {
          Log.d("gianLogcat3", e.toString());
//            Toast.makeText(getApplicationContext(), "The error(s) encountered: " + e.toString(), Toast.LENGTH_LONG).show();
          _errorHandler("All fields are required\nBawal ang blankong sagot sa form na ito.\n" + e.toString(), Color.rgb(241, 128, 128), Color.rgb(36, 36, 36), 7000);

        }

   /*     String strValidation1 = PublicCodeAccess.current_date(new Date()) + "_" + PublicCodeAccess.android_id(getApplicationContext());
        if (strValidation1.matches(PublicCodeAccess.current_date(new Date()))) {
          _errorHandler(strValidation1 + " matches with " + PublicCodeAccess.current_date(new Date()), Color.rgb(241, 128, 128), Color.rgb(36, 36, 36), 5000);
        } else {
          _errorHandler(strValidation1 + " does not match with " + PublicCodeAccess.current_date(new Date()), Color.rgb(241, 128, 128), Color.rgb(36, 36, 36), 5000);
        }*/
      }
    });

    hideViews();

//    } catch (Exception e) {
//      Toast.makeText(getApplicationContext(), "The error(s) encountered: " + e.toString(), Toast.LENGTH_LONG).show();
//      Log.d("gianLogcat", e.toString());
//    }


  }


  public void hideViews() {
//    btnBirthday.setVisibility(View.GONE);
//    spinnerbranches.setVisibility(View.GONE);
  }

  public String _currentDate2, _currentTime2, selectedGender, selectedService;

  @SuppressLint("SetTextI18n")
  public void firebaseDatabase() throws ParseException {
    try {


      firebaseFirestore = FirebaseFirestore.getInstance();

//    documentReference = firebaseFirestore.document(_selectedAppointmentDateFinal).collection(PublicCodeAccess.android_id(getApplicationContext()));

//    firebaseFirestore.document("appointments").collection(_selectedAppointmentDateFinal).document(PublicCodeAccess.android_id(getApplicationContext()));

      _currentDate2 = new SimpleDateFormat("EEE - MMM dd, yyyy", Locale.getDefault()).format(new Date());
      _currentTime2 = new SimpleDateFormat("h:mm:ss a", Locale.getDefault()).format(new Date());


      @SuppressLint("SimpleDateFormat") SimpleDateFormat _selectedAppointmentDateFormat1 = new SimpleDateFormat("MM-dd-yyyy");
      @SuppressLint("SimpleDateFormat") SimpleDateFormat _selectedAppointmentDateFormat2 = new SimpleDateFormat("EEE - MMM dd, yyyy");


      Date _selectedAppointmentDate = (_selectedAppointmentDateFormat1.parse(selectedAppointmentDate));
      assert _selectedAppointmentDate != null;
      if ((_selectedAppointmentDate.getTime() == 0) || (String.valueOf(_selectedAppointmentDate.getTime()) == null) || (_selectedAppointmentDateFormat1.toString().length() == 0) || (_selectedAppointmentDateFormat1 == null) || (_selectedAppointmentDateFormat2.toString().length() == 0) || (_selectedAppointmentDateFormat2 == null)) {

      }
      _selectedAppointmentDateFinal = _selectedAppointmentDateFormat2.format(_selectedAppointmentDate);
      sharedEditor.putString("strSelectedAppointmentDate", _selectedAppointmentDateFinal);
      Log.d("gianLogcat", _selectedAppointmentDateFinal);
      sharedEditor.commit();
      sharedEditor.apply();

      strFullName = txtLN.getText().toString().trim() + ", " + txtFN.getText().toString().trim() + " " + txtMN.getText().toString().trim();
      selectedGender = rbMale.isChecked() ? "Male" : "Female";
      selectedService = arrayServices[intServiceSelectedIndex];

      documentReference = firebaseFirestore.collection("Appointments").document(Objects.requireNonNull(sharedPreference.getString("strSelectedAppointmentDate", "")) + "_" + PublicCodeAccess.android_id(BookingScheduling_Activity.this));
      firebaseAppointments = new HashMap<>();
    } catch (Exception e) {
      _errorHandler("All fields are required\nBawal ang blankong sagot sa form na ito.\n" + e.toString(), Color.rgb(241, 128, 128), Color.rgb(36, 36, 36), 7000);
    }

  }


  public void saveToFirebaseDatabase() {
    _errorHandler(_formValidation(), Color.rgb(241, 128, 128), Color.rgb(36, 36, 36), 7000);


//    Toast.makeText(getApplicationContext(),,Toast.LENGTH_LONG).show();

    if (_formValidation() == null || _formValidation().length() == 0 || _formValidation().isEmpty() || _formValidation().equals("")) {
      firebaseAppointments.put("datesubmitted", sharedPreference.getString("strSelectedAppointmentDate", ""));
      firebaseAppointments.put("timesubmitted", _currentTime2);
      firebaseAppointments.put("fullname", strFullName);
      firebaseAppointments.put("gender", selectedGender);
      firebaseAppointments.put("selectedservice", sharedPreference.getString("selectedService", ""));
      firebaseAppointments.put("selectedappointmentdate", _selectedAppointmentDateFinal);
      firebaseAppointments.put("selectedappointmenttime", selectedAppointmentTime);
      firebaseAppointments.put("contactnumber", txtContactNumber.getText().toString());
      firebaseAppointments.put("address", txtAddress.getText().toString());
      firebaseAppointments.put("formvalidation", _formValidation());

      documentReference.set(firebaseAppointments).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
//        Toast.makeText(getApplicationContext(), "SUBMITTED/NAISUMITE NA", Toast.LENGTH_LONG).show();

          documentReference.addSnapshotListener(BookingScheduling_Activity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

              _errorHandler("The appointment for " + strFullName + " is submitted.", Color.rgb(174, 213, 129), Color.rgb(36, 36, 36), 5000);
            }
          });
        }
      });
      documentReference.set(firebaseAppointments).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

          _errorHandler("There is an error Submitting...", Color.rgb(241, 128, 128), Color.rgb(36, 36, 36), 3000);
        }
      });
      documentReference.set(firebaseAppointments).addOnCanceledListener(new OnCanceledListener() {
        @Override
        public void onCanceled() {

          _errorHandler("There is an error Submitting...", Color.rgb(241, 128, 128), Color.rgb(36, 36, 36), 3000);

        }
      });
    } else {
      _errorHandler("All fields are required\nBawal ang blankong sagot sa form na ito.\n" + _formValidation(), Color.rgb(241, 128, 128), Color.rgb(36, 36, 36), 7000);
    }


//firebaseAppointments.put();
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }


  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    intServiceSelectedIndex = position;
//    String strBranchSelected = parent.getItemAtPosition(position).toString();
    _errorHandler("The selected service is " + sharedPreference.getString("selectedService", ""), Color.rgb(128, 128, 241), Color.rgb(36, 36, 36), 60000);


    String strComparisonString = "This string contains branch".toUpperCase();
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {

  }


//  @Override
//  public void onConfigurationChanged(Configuration newConfig) {
//    super.onConfigurationChanged(newConfig);
//
//    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//      setContentView(R.layout.landscapeView);
//
//    } else {
//      setContentView(R.layout.portraitView);
//    }

  public String _formValidation() {

    String txtError = "";

    if (txtAddress.getText().toString().trim().length() == 0) {
      txtError = "Address | ";
    }

    if (txtContactNumber.getText().toString().trim().length() == 0) {
      txtError += "Contact Number | ";
    }

    if (txtMN.getText().toString().trim().length() == 0) {
      txtError += "Middle Name | ";
    }

    if (txtFN.getText().toString().trim().length() == 0) {
      txtError += "First Name | ";
    }

    if (txtLN.getText().toString().trim().length() == 0) {
      txtError += "Last Name |";
    }

//    if (selectedService.trim().length() == 0) {
//      txtError += "Service |";
//    }

    if (selectedGender.trim().length() == 0) {
      txtError += "Gender |";
    }

    if (selectedAppointmentDate.trim().length() == 0) {
      txtError += "Appointment Date |";
    }

    if (selectedAppointmentTime.trim().length() == 0) {
      txtError += "Appointment Time |";
    }

    return txtError;
  }

  @SuppressLint("SetTextI18n")
  public void _errorHandler(String error, int bgcolor, int fgcolor, int duration) {
//    error.printStackTrace();


    txtBookError.setVisibility(View.VISIBLE);
    txtBookError.setBackgroundColor(bgcolor);
    txtBookError.setTextColor(fgcolor);

    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
    AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
    txtBookError.startAnimation(fadeIn);
    txtBookError.startAnimation(fadeOut);
    fadeIn.setDuration(3000);
    fadeIn.setFillAfter(true);
    fadeOut.setDuration(800);
    fadeOut.setFillAfter(true);
    fadeOut.setStartOffset((duration - 500) + fadeIn.getStartOffset());

//      Toast.makeText(getApplicationContext(), "firebaseDatabase error:  " + e.toString(), Toast.LENGTH_LONG).show();

    if (!txtBookError.getText().toString().trim().equals(txtBookError.getText().toString().trim())) {
      txtBookError.setText(txtBookError.getText().toString().trim() + " " + error);
    } else {
      txtBookError.setText(" " + error);
    }


    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        Looper.prepare();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
          @Override
          public void run() {
            txtBookError.setVisibility(View.GONE);
          }
        });
      }
    }, duration);
  }


}
