package com.gcnm.jecmedicalanddiagnosticcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewAppointments extends AppCompatActivity {
  FirebaseFirestore firebaseFirestore;
  RecyclerView _firestoreList;
  FirestoreRecyclerAdapter adapter;
  FirestoreRecyclerOptions<AppointmentsModel> options;
  View view;
  Query query;
  DatabaseReference dbRef;
  DocumentReference dbRef2;
  CollectionReference refAppointment;
  TextView txtTitle;
  ArrayList<AppointmentsModel> userArrayList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_appointments);

    try {
      userArrayList = new ArrayList<>();
      txtTitle = findViewById(R.id.txtAppointmentTitle);

      firebaseFirestore = FirebaseFirestore.getInstance();
      _firestoreList = findViewById(R.id.recyclerViewAppointments);

      //Query
//    dbRef = FirebaseDatabase.getInstance().getReference();
//    com.google.firebase.database.Query fQuery;
//    fQuery = dbRef.child("issue").orderByChild("").equalTo(0);
//    query = firebaseFirestore.collection("Appointments");
//    dbRef2 = firebaseFirestore.collection("Appointments").document(PublicCodeAccess.current_date(new Date()) + "_" + PublicCodeAccess.android_id(ViewAppointments.this));
//    query = firebaseFirestore.collection("Appointments").whereEqualTo("selectedappointmentdate", PublicCodeAccess.current_date(new Date()));


      _firestoreList.setHasFixedSize(true);
      _firestoreList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

      refAppointment = firebaseFirestore.collection("Appointments");
      query = refAppointment.whereEqualTo("selectedappointmentdate", PublicCodeAccess.current_date(new Date()));
      query.addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
          assert queryDocumentSnapshots != null;
          List<DocumentSnapshot> snapshotsList = queryDocumentSnapshots.getDocuments();
          for (DocumentSnapshot snapshot : snapshotsList) {
            // each document having that particular field based on query

//  public AppointmentsModel(String DateSubmitted, String fullname, String gender, String selectedappointmentdate, String selectedappointmenttime, String selectedservice, String timesubmitted) {

            @SuppressLint("RestrictedApi") AppointmentsModel appointmentsModel = new AppointmentsModel(snapshot.getString("datesubmitted"), snapshot.getString("fullname"), snapshot.getString("gender"), snapshot.getString("selectedappointmentdate"), snapshot.getString("selectedappointmenttime"), snapshot.getString("selectedservice"), snapshot.getString("timesubmitted"));

//        @SuppressLint("RestrictedApi") AppointmentsModel appointmentsModel = new AppointmentsModel(task.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"));

            userArrayList.add(appointmentsModel);


          }
          AppointmentsAdapter appointmentsAdapter = new AppointmentsAdapter(ViewAppointments.this, userArrayList);
          _firestoreList.setAdapter(appointmentsAdapter);

        }
      });


//      dbRef2 = firebaseFirestore.collection("Appointments").document(PublicCodeAccess.current_date(new Date()) + "_" + PublicCodeAccess.android_id(getApplicationContext()));
//      dbRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//        @Override
//        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
////      Log.d("logcatGIAN", document.getId() + " => " + document.getData());
//          requireNonNull(task.getResult()).getString("fullname");
//          @SuppressLint("RestrictedApi") AppointmentsModel appointmentsModel = new AppointmentsModel(requireNonNull(task.getResult()).getString("fullname"), requireNonNull(task.getResult()).getString("fullname"), requireNonNull(task.getResult()).getString("fullname"), requireNonNull(task.getResult()).getString("fullname"), requireNonNull(task.getResult()).getString("fullname"), requireNonNull(task.getResult()).getString("fullname"), requireNonNull(task.getResult()).getString("fullname"));
//
////        @SuppressLint("RestrictedApi") AppointmentsModel appointmentsModel = new AppointmentsModel(task.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"));
//
//          userArrayList.add(appointmentsModel);
//              AppointmentsAdapter appointmentsAdapter = new AppointmentsAdapter(ViewAppointments.this, userArrayList);
//
//          _firestoreList.setAdapter(appointmentsAdapter);
//        }
//
//
//      });

//    query = refAppointment.whereEqualTo("selectedappointmentdate", PublicCodeAccess.current_date(new Date()) + "_" + PublicCodeAccess.android_id(ViewAppointments.this));

//refAppointment.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//  @Override
//  public void onComplete(@NonNull Task<QuerySnapshot> task) {
//    for(DocumentSnapshot querySnapshot: task.getResult()){
////      Log.d("logcatGIAN", document.getId() + " => " + document.getData());
//      @SuppressLint("RestrictedApi") AppointmentsModel appointmentsModel = new AppointmentsModel(querySnapshot.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"),querySnapshot.getString("fullname"));
//
//      userArrayList.add(appointmentsModel);
//
//    }

//
//    AppointmentsAdapter appointmentsAdapter = new AppointmentsAdapter(ViewAppointments.this, userArrayList);
//              _firestoreList.setAdapter(appointmentsAdapter);
//
//  }
//}).addOnFailureListener(new OnFailureListener() {
//  @SuppressLint("SetTextI18n")
//  @Override
//  public void onFailure(@NonNull Exception e) {
//    Log.d("logcatGIAN", "Error getting documents: " + e.toString());
//    txtTitle.setText( "Failure: \n" + e.toString());
//  }
//});


//    .get()
//        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//          @Override
//          public void onComplete(@NonNull Task<QuerySnapshot> task) {
//            if (task.isSuccessful()) {
//              for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                Log.d("logcatGIAN", document.getId() + " => " + document.getData());
//                @SuppressLint("RestrictedApi") AppointmentsModel appointmentsModel = new AppointmentsModel(document.getString("fullname"),document.getString("fullname"),document.getString("fullname"),document.getString("fullname"),document.getString("fullname"),document.getString("fullname"),document.getString("fullname"));
//
//                userArrayList.add(appointmentsModel);
//
//
//
//                adapter.startListening();
//              }
//              AppointmentsAdapter appointmentsAdapter = new AppointmentsAdapter(ViewAppointments.this, userArrayList);
//              _firestoreList.setAdapter(appointmentsAdapter);
//            } else {
//              Log.d("logcatGIAN", "Error getting documents: ", task.getException());
//            }
//          }
//        });
      //RecyclerOptions

    } catch (Exception e) {
      Log.d("logcatGIAN", "Error getting documents: " + e.toString());
      txtTitle.setText(e.toString());
    }
  }


  @Override
  protected void onStop() {
    super.onStop();
//    adapter.stopListening();
  }

  @Override
  protected void onStart() {
    super.onStart();
//    adapter.startListening();
  }


  @Override
  public void onBackPressed() {
    super.onBackPressed();
    startActivity(new Intent(ViewAppointments.this, BookingScheduling_Activity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));
    ViewAppointments.this.finish();

  }

}