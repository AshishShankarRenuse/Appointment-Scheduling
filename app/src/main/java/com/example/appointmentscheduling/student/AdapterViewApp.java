package com.example.appointmentscheduling.student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointmentscheduling.R;
import com.example.appointmentscheduling.superAdmin.AdapterReqAdmin;
import com.example.appointmentscheduling.superAdmin.ModelReqAdmin;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterViewApp extends RecyclerView.Adapter<AdapterViewApp.MyViewHolder> {


    List<ModelViewApp> notesList;

    public AdapterViewApp( List<ModelViewApp> notesList) {

        this.notesList = notesList;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterViewApp.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_appointment_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterViewApp.MyViewHolder holder, int position) {
        holder.process.setText(notesList.get(position).getProcess());
        holder.college.setText(notesList.get(position).getCollege());
        holder.date.setText(notesList.get(position).getDate());
        holder.slot.setText(notesList.get(position).getSlot());
        holder.status.setText(notesList.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView process,college,date,slot,status;
        RelativeLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            process = itemView.findViewById(R.id.view_appointment_process);
            college = itemView.findViewById(R.id.view_appointment_college);
            date = itemView.findViewById(R.id.view_appointment_date);
            slot = itemView.findViewById(R.id.view_appointment_slot);
            status = itemView.findViewById(R.id.view_appointment_status);

            layout = itemView.findViewById(R.id.view_appointment_recycler);
        }
    }

}