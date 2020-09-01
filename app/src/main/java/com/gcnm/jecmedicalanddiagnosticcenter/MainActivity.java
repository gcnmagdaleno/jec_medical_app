package com.gcnm.jecmedicalanddiagnosticcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
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
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CustomDialog_AdminLogin.adminDialogListener {

  public DrawerLayout drawerLayout;
  public NavigationView navigationView;
  public ImageView imgMenu;
  public RecyclerView recyclerView;
  public RecyclerView.LayoutManager gridLayoutManager;
  public MainRecyclerViewAdapter mainRecyclerViewAdapter;
  public TextView txtAdmin, txtAdminUN, txtAdminPW;


  public SharedPreferences sharedPreference;
  public SharedPreferences.Editor sharedEditor;

  public BiometricManager biometricManager;


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


  int[] arr = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,};

  @SuppressLint("CommitPrefEdits")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


    sharedPreference = getSharedPreferences("__savedData", MODE_PRIVATE);
    sharedEditor = sharedPreference.edit();

    biometricManager = androidx.biometric.BiometricManager.from(getApplicationContext());

    if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
      Toast.makeText(this, "Device supports fingerprint authentication ", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(this, "Device does not support fingerprint authentication ", Toast.LENGTH_SHORT).show();
    }

    Toast.makeText(this, "Last Activity: " + sharedPreference.getString("strLastActivity", ""), Toast.LENGTH_SHORT).show();

    drawerLayout = findViewById(R.id.id_drawerLayout);

    recyclerView = findViewById(R.id.id_recyclerView);
    gridLayoutManager = new GridLayoutManager(this, 2);
    recyclerView.setLayoutManager(gridLayoutManager);
    mainRecyclerViewAdapter = new MainRecyclerViewAdapter(arr);
    recyclerView.setAdapter(mainRecyclerViewAdapter);
    recyclerView.setHasFixedSize(true);

    navigationView = findViewById(R.id.id_navView);
    navigationView.setNavigationItemSelectedListener(this);

    imgMenu = findViewById(R.id.id_imgMenu);
    imgMenu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        drawerLayout.openDrawer(GravityCompat.START);
      }
    });

    txtAdmin = findViewById(R.id.txtAdmin);
    txtAdmin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openAdminDialog();
      }
    });

    txtAdminUN = findViewById(R.id.txtAdminUN);
    txtAdminPW = findViewById(R.id.txtAdminPW);


    userArrayList = new ArrayList<>();
    txtTitle = findViewById(R.id.txtAppointmentTitle);

    firebaseFirestore = FirebaseFirestore.getInstance();


    refAppointment = firebaseFirestore.collection("Admins");


  }

  public void getAdminCredentials(final String username1, final String password1) {
    query = refAppointment.whereEqualTo("usernamepassword", username1 + password1);
    query.addSnapshotListener(new EventListener<QuerySnapshot>() {
      @Override
      public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

        assert queryDocumentSnapshots != null;
        List<DocumentSnapshot> snapshotsList = queryDocumentSnapshots.getDocuments();
        for (DocumentSnapshot snapshot : snapshotsList) {

          if (username1.equalsIgnoreCase(snapshot.getString("username")) && password1.equalsIgnoreCase(snapshot.getString("password"))) {
            new Timer().schedule(new TimerTask() {
              @Override
              public void run() {
                Looper.prepare();
                startActivity(new Intent(MainActivity.this, ViewAppointments.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                MainActivity.this.finish();

              }
            }, 500);

          } else {
            Toast.makeText(getApplicationContext(), "not logged in", Toast.LENGTH_LONG).show();

            new Timer().schedule(new TimerTask() {
              @Override
              public void run() {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "not logged in", Toast.LENGTH_LONG).show();


              }
            }, 500);
          }


        }


      }
    });
  }

  @Override
  public void applyData(final String username, final String password) {
//    txtAdminUN.setText(username);
//    txtAdminPW.setText(password);


//    getAdminCredentials(username, password);

    query = refAppointment.whereEqualTo("usernamepassword", username + password);
    query.addSnapshotListener(new EventListener<QuerySnapshot>() {
      @Override
      public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

        assert queryDocumentSnapshots != null;
        List<DocumentSnapshot> snapshotsList = queryDocumentSnapshots.getDocuments();
        for (DocumentSnapshot snapshot : snapshotsList) {

          if (username.equalsIgnoreCase(snapshot.getString("username")) && password.equalsIgnoreCase(snapshot.getString("password"))) {
            new Timer().schedule(new TimerTask() {
              @Override
              public void run() {
                Looper.prepare();
                startActivity(new Intent(MainActivity.this, ViewAppointments.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                MainActivity.this.finish();
              }
            }, 500);

          }


        }


      }

    });


  }

  public void openAdminDialog() {
    CustomDialog_AdminLogin customDialog_adminLogin = new CustomDialog_AdminLogin();
    customDialog_adminLogin.show(getSupportFragmentManager(), "Admin Dialog");
  }


  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
      MainActivity.this.finish();
    }
  }


  public void __saveData(String strLastActivity) {
    sharedEditor.putString("strLastActivity", strLastActivity);
    sharedEditor.apply();
    sharedEditor.commit();
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    __saveData("MainActivity");
    switch (item.getItemId()) {
      case R.id.nav_service:
        Toast.makeText(this, R.string.strNavService, Toast.LENGTH_LONG).show();
//        startActivity(new Intent(this, Services_Tabbed.class));

        startActivity(new Intent(MainActivity.this, Services_Tabbed.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        MainActivity.this.finish();

        break;

      case R.id.nav_book:
        Toast.makeText(this, R.string.strNavBook, Toast.LENGTH_LONG).show();
//        startActivity(new Intent(this, Services_Tabbed.class));

        startActivity(new Intent(MainActivity.this, BookingScheduling_Activity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        MainActivity.this.finish();

        break;

    }

    return true;
  }

  @Override
  protected void onUserLeaveHint() {

    __saveData("MainActivity");
    super.onUserLeaveHint();
  }

  @Override
  protected void onPause() {

    __saveData("MainActivity");
    super.onPause();
  }


//  public void getData(String username, String password, String ) {
////    txtAdminUN.setText(username);
////    txtAdminPW.setText(password);
//    Toast.makeText(this, "Username: " + username + "\n Password: " + password, Toast.LENGTH_SHORT).show();
//
//    sharedEditor.putString("strAdminUN", username);
//    sharedEditor.putString("strAdminPW", password);
//    sharedEditor.apply();
//    sharedEditor.commit();
//  }

}
