package com.gcnm.jecmedicalanddiagnosticcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class Services_ChooseAService extends AppCompatActivity {

  List<String> childList;
  Map<String, List<String>> parentListItems;
  ExpandableListView expandableListView;

  List<String> parentList = new ArrayList<>();

  String _PE = "[FREE PHYSICAL EXAMINATION AND FIT TO WORK CERTIFICATE]";
  String _FASTING = "(W/ 6-8 HOURS FASTING) - [FREE PHYSICAL EXAMINATION]";

  {
    parentList.add("Pre-Employment Packages \n" + _PE);
    parentList.add("X-Ray Pricelist");
    parentList.add("Other Packages \n" + _FASTING);
    parentList.add("Vaccines for Immunization");
  }


  String[] preEmploymentPackages = {
      "Package A *PHP 300* \n" +
          "RT. URINALYSIS, FECALYSIS, CHEST X-RAY\n" + _PE,

      "Package B *PHP 400* \n" +
          "COMPLETE BLOOD COUNT (CBC), RT. URINALYSIS, FECALYSIS, CHEST X-RAY\n" + _PE,

      "Package C *PHP 600* \n" +
          "COMPLETE BLOOD COUNT (CBC), BLOOD TYPING / RH TYPING, URINALYSIS, FECALYSIS, CHEST X-RAY\n" + _PE,

      "Package D *PHP 900* \n" +
          "COMPLETE BLOOD COUNT (CBC), HEPATITIS B (SCREENING), HEPATITIS A (SCREENING), URINALYSIS, FECALYSIS, CHEST X-RAY\n" + _PE,

      "Package E *PHP 650* \n" +
          "COMPLETE BLOOD COUNT (CBC), HEPATITIS B (SCREENING), URINALYSIS, FECALYSIS, CHEST X-RAY\n" + _PE,

      "Package F *PHP 700* \n" +
          "COMPLETE BLOOD COUNT (CBC), HEPATITIS A (SCREENING), URINALYSIS, FECALYSIS, CHEST X-RAY\n" + _PE
  };

  String[] xrayPricelist = {
      "[CHEST] CHEST PA *PHP 300*", "[CHEST] CHEST AP *PHP 300*", "[CHEST] CHEST PEDIA AP/LAT *PHP 500*", "[CHEST] CHEST APICOLORDOTIC *PHP 400*",
      "[PELVIC] PELVIC AP *PHP 400*", "[PELVIC] PELVIC (FROGLEG) *PHP 400*",
      "[SPINE] CERVICAL AP/LAT *PHP 600*", "[SPINE] THORATIC AP/LAT *PHP 500*", "[SPINE] LUMBAR AP/LAT *PHP 600*",
      "[SKULL] SKULL AP/LAT *PHP 600*", "[SKULL] SKULL (WATER) AP/LAT *PHP 400*",
      "[EXTREMITIES] HAND AP/OBLIQUE *PHP 450*", "[EXTREMITIES] ULNAR DEVIATION *PHP 550*", "[EXTREMITIES] RADIAL DEVIATION *PHP 550*",
      "[EXTREMITIES] WRIST PA/LATERAL *PHP 450*", "[EXTREMITIES] FOREARM AP/LAT *PHP 550*", "[EXTREMITIES] ELBOW AP/LAT *PHP 550*",
      "[EXTREMITIES] ARM AP/LAT *PHP 450*", "[EXTREMITIES] SHOULDER AP *PHP 400*", "[EXTREMITIES] SCAPULAR Y *PHP 450*", "[EXTREMITIES] CLAVICLE AP *PHP 400*",
      "[EXTREMITIES] FOOT AP/OBLIQUE *PHP 550*", "[EXTREMITIES] ANKLE AP/LAT *PHP 550*", "[EXTREMITIES] LEG AP/LAT *PHP 550*",
      "[EXTREMITIES] KNEE AP/LAT *PHP 550*", "[EXTREMITIES] THIGH AP/LAT *PHP 550*",
      "[UPRIGHT/SUPINE] SCOUT ABDOMEN *PHP 700*"
  };

  String[] otherPackages = {
      "DIABETIC PACKAGE *PHP 1600*\n" +
          "COMPLETE BLOOD COUNT (CBC), RT. URINALYSIS, URINE MICRAL, FBS (FASTING BLOOD SUGAR), LIPID PROFILE, BUN, CREATININE\n" + _FASTING,

      "LIVER FUNCTION PACKAGE *PHP 1700*\n" +
          "SGOT, SGPT, ALKALINE PHOSPHATASE, BILIRUBIN (TB, B1, B2), TOTAL PROTEIN A/G RATIO\n" + _FASTING,

      "THYROID FUNCTION PACKAGE *PHP 2500*\n" +
          "T3, T4, FT3, FT4, TSH\n" + _FASTING,

      "HEPATITIS PACKAGE *PHP 1600*\n" +
          "HEPATITIS BsAg (SCREENING), ANTI-HBS, ANTI-HAV IgG, ANTI-HAV IgM, ANTI-HCV\n" + _FASTING,

      "RENAL FUNCTION PACKAGE *PHP 1800*\n" +
          "COMPLETE BLOOD COUNT (CBC), BUN, CREATININE, URINALYSIS, URINE MICRAL, ELECTROLYTES (NA, K, CL), TOTAL PROTEIN A/G RATIO\n" + _FASTING,

      "HYPERTENSION PACKAGE *PHP 2100*\n" +
          "COMPLETE BLOOD COUNT (CBC), URINALYSIS, HBA1C, FBS, URIC ACID, BUN, CREATININE, LIPID PROFILE, SGOT, SGPT, ELECTROLYTES (NA, K, CL), " +
          "ECG W/ READING\n" + _FASTING,
  };

  String[] immunizationVaccines = {
      "BCG\nPEDIA - *PHP 700*",
      "CHICKEN FOX - [VARILRIX/VZ VAX]\nPEDIA - *PHP 1800*\nADULT - *PHP 2500*",
      "IPV\nPEDIA - *PHP 500*",
      "DPT/OPV - [EURO DPT/POLIO SABIN]\nPEDIA - *PHP 300*",
      "DPT, HEPATITIS B - [TRITANRIX]\nPEDIA - *PHP 700*",
      "DPT, OPV, HEPATITIS B - [5-IN-1]\nPEDIA - *PHP 800*",
      "DPT, IPV, HIB - [PENTACHIB/PENTAXIM]\nPEDIA - *PHP 1800*",
      "DPT, OPV, HEPATITIS A/B, HIB - [6-IN-1]\nPEDIA - *PHP 1500*",
      "DPT, OPV, HEPATITIS B, HIB - [INFRANRIX]\nPEDIA - *PHP 2500*",
      "FLU VACCINE - [VAXIGRIP]\nPEDIA - *PHP 1500*\nADULT *PHP2000*",
      "ANTI CERVICAL CANCER VACCINE, HPV - [GARDASIL PROMO RATE]\nPEDIA - *PHP 2500/DOSE*\nADULT - *PHP 6500/DOSE*",
      "HEPATITIS B - [HEPAVAX GENE GENEVAC-B]\nPEDIA - *PHP 500*\nADULT - *PHP 700*",
      "HEPATITIS A - [HAVRIX]\nPEDIA - *PHP 1200*\nADULT - *PHP 1500*",
      "HEPATITIS A + HEPATITIS B - [TWINRIX]\nPEDIA - *PHP 1300*\nADULT - *PHP 1800*",
      "HIB - [HIBERIX]\nPEDIA - *PHP 1000*\nADULT - *PHP 1300*",
      "MEASLES - [ROUVAX]\nPEDIA - *PHP 500*\nADULT - *PHP 700*",
      "MEASLES, MUMPS, RUBELLA (MMR) - [PRIORIX PROMO RATE]\nPEDIA - *PHP 1000*\nADULT - *PHP 1200*",
      "MENINGO VACCINE- [MENINGGOCOCCAL]\nPEDIA - *PHP 1500*\nADULT - *PHP 1800*",
      "PNEUMOCOCCAL - [PNEUOMO 23 PROMO RATE]\nPEDIA - *PHP 1200*\nADULT - *PHP 1500*",
      "PNEUMOCOCCAL CONJUGATE - [PREVANAR PROMO RATE]\nPEDIA - *PHP 2000*\nADULT - *PHP 3500*",
      "IPD (PNEUOMOCOCCAL)\nPEDIA - *PHP 2800*",
      "PPD - [EURO PPD]\nPEDIA - *PHP 500*",
      "ROTAVIRUS VACCINE - [ROTATEQ PROMO RATE]\nPEDIA - *PHP 2500*",
      "TYPHOID - [TYPHERI/TYPHIMV]\nPEDIA - *PHP 1200*\nADULT - *PHP 1500*",
      "ANTI-RABIES VACCINE - [ERORAB]\nPEDIA - *PHP 750*\nADULT - *PHP 750*",
      "JAPANESE ENCEPHALITIS - [IMOJEV]\nPEDIA - *PHP 1800*\nADULT - *PHP 2000*",
      "ERIG\nPEDIA - *PHP 2000*\nADULT - *PHP 4000*",


  };

  String[] defaultServices = {
      "JECMedical"
  };

  SharedPreferences sharedPreference;
  SharedPreferences.Editor sharedEditor;

  TextView txtServicesTitle;

  @SuppressLint("CommitPrefEdits")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_services__choose_a_service);

    txtServicesTitle = findViewById(R.id.txtServicesTitle);

    sharedPreference = getSharedPreferences("__savedData", MODE_PRIVATE);
    sharedEditor = sharedPreference.edit();


    //////////////////////////ExpandableListView
    parentListItems = new LinkedHashMap<>();

    for (String HoldItem : parentList) {
      if (HoldItem.equals("Pre-Employment Packages \n[FREE PHYSICAL EXAMINATION AND FIT TO WORK CERTIFICATE]")) {
        loadChild(preEmploymentPackages);
      } else if (HoldItem.equals("X-Ray Pricelist")) {
        loadChild(xrayPricelist);
      } else if (HoldItem.equals("Other Packages \n" + _FASTING)) {
        loadChild(otherPackages);
      } else if (HoldItem.equals("Vaccines for Immunization")) {
        loadChild(immunizationVaccines);
      } else {
        loadChild(defaultServices);
      }
      parentListItems.put(HoldItem, childList);

    }

    expandableListView = findViewById(R.id.expandableSeviceView);
    final ExpandableListAdapter expandableListAdapter = new ServicesList_Adapter(this, parentList, parentListItems);
    expandableListView.setAdapter(expandableListAdapter);
    expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
      @SuppressLint("SetTextI18n")
      @Override
      public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int ii, long l) {


        String selected = (String) expandableListAdapter.getChild(i, ii);
        long intSelected = expandableListAdapter.getChildId(i, ii);
        txtServicesTitle.setText("Selected: " + selected + "\nintSelected: " + intSelected);
        txtServicesTitle.setText(txtServicesTitle.getText() + " Package A Selected");
        sharedEditor.putString("selectedService", selected);
        sharedEditor.apply();
        sharedEditor.commit();
        startActivity(new Intent(Services_ChooseAService.this, BookingScheduling_Activity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        Services_ChooseAService.this.finish();

//        if (selected.matches("(.*)Package A(.*)")) {
//
//
//        }else{
//          txtServicesTitle.setText(txtServicesTitle.getText() + " Iba ang pinili");
//        }

        return true;
      }
    });
  }

  public void loadChild(String[] parentElementName) {
    childList = new ArrayList<>();
    Collections.addAll(childList, parentElementName);
  }
}