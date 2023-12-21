package com.example.appointmentscheduling.superAdmin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointmentscheduling.R;
import com.example.appointmentscheduling.admin.ModelApplyFC;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterVS extends RecyclerView.Adapter<AdapterVS.MyViewHolder>{

    Context context;
    /*Activity activity;*/
    Fragment fragment;
    List<ModelVS> notesList;

    public AdapterVS(Context context, Fragment fragment, List<ModelVS> notesList) {
        this.context = context;
        this.fragment = fragment;
        this.notesList = notesList;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterVS.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_schedule_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterVS.MyViewHolder holder, int position) {
        holder.schId.setText(notesList.get(position).getId());
        holder.startdate.setText(notesList.get(position).getStart_date());
        holder.enddate.setText(notesList.get(position).getEnd_date());
        holder.process.setText(notesList.get(position).getProcess());
        holder.course.setText(notesList.get(position).getCourse());

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView startdate,enddate,process,course,schId;
        RelativeLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            startdate = itemView.findViewById(R.id.vs_startdate);
            enddate = itemView.findViewById(R.id.vs_enddate);
            process = itemView.findViewById(R.id.vs_process);
            course = itemView.findViewById(R.id.vs_course);
            schId = itemView.findViewById(R.id.vs_id);
            layout = itemView.findViewById(R.id.vs_note_layout);
        }
    }
}
