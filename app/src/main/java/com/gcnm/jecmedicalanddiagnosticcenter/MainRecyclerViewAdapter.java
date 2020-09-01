package com.gcnm.jecmedicalanddiagnosticcenter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {

  public int[] arr;

  public MainRecyclerViewAdapter(int[] arr) {
    this.arr = arr;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_recycler, parent, false);
    return new MyViewHolder(view);
  }


  @SuppressLint("SetTextI18n")
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.imageView.setImageResource(arr[position]);
    holder.textView.setText("Wao: " + position);
  }

  @Override
  public int getItemCount() {
    return arr.length;
  }

  public static class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView textView;
    CardView linearLayout;

    MyViewHolder(@NonNull View itemView) {
      super(itemView);
      linearLayout = itemView.findViewById(R.id.layout_cardView);
      linearLayout.setBackgroundColor(Color.WHITE);
      linearLayout.setRadius(100);
      imageView = itemView.findViewById(R.id.id_mainRecyclerImage);
      textView = itemView.findViewById(R.id.id_mainRecyclerText);
      textView.setTextColor(Color.argb(255, 24, 24, 24));
    }
  }
}
