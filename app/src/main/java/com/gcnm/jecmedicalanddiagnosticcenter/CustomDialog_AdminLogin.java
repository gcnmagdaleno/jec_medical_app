package com.gcnm.jecmedicalanddiagnosticcenter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CustomDialog_AdminLogin extends AppCompatDialogFragment {

  public TextView txtAdminUN, txtAdminPW;

  public adminDialogListener adminDialog_Listener;

  @SuppressLint("UseRequireInsteadOfGet")
  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder((getActivity()));

    LayoutInflater inflater;
    inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

    @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.customdialog_adminlogin, null);

    txtAdminUN = view.findViewById(R.id.txtAdminUN);
    txtAdminPW = view.findViewById(R.id.txtAdminPW);


    builder.setView(view)
        .setTitle(("Admin Login"))
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
        })
        .setPositiveButton("Login", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            adminDialog_Listener.applyData(txtAdminUN.getText().toString(), txtAdminPW.getText().toString());

          }
        });
    AlertDialog dialog = builder.create();
    dialog.show();

    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.argb(255, 239, 83, 80));
    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.argb(255, 66, 165, 245));

    return dialog;
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    try {
      adminDialog_Listener = (adminDialogListener) context;
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString() + "\n Must Implement adminDialog_Listener");
    }
  }

  public interface adminDialogListener {
    void applyData(String username, String password);
  }
}
