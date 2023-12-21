package com.example.appointmentscheduling.admin;

import android.app.AlertDialog;
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

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterMyFC extends RecyclerView.Adapter<AdapterMyFC.MyViewHolder>{

    Context context;
    static String username;
    Fragment fragment;
    List<ModelMyFC> notesList;

    public AdapterMyFC(Context context, Fragment fragment, List<ModelMyFC> notesList) {
        this.context = context;
        this.fragment = fragment;
        this.notesList = notesList;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterMyFC.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_my_applied_fc_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterMyFC.MyViewHolder holder, int position) {
        holder.schId.setText(notesList.get(position).getId());
        holder.startdate.setText(notesList.get(position).getStart_date());
        holder.enddate.setText(notesList.get(position).getEnd_date());
        holder.process.setText(notesList.get(position).getProcess());
        holder.course.setText(notesList.get(position).getCourse());
        holder.status.setText(notesList.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView startdate,enddate,process,course,schId,status;
        RelativeLayout layout;
        Button btnApplyFC;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            startdate = itemView.findViewById(R.id.my_fc_startDate);
            enddate = itemView.findViewById(R.id.my_fc_endDate);
            process = itemView.findViewById(R.id.my_fc_process);
            course = itemView.findViewById(R.id.my_fc_course);
            schId = itemView.findViewById(R.id.my_fc_requestId);
            status = itemView.findViewById(R.id.my_fc_status);
            layout = itemView.findViewById(R.id.my_applied_fc_recycler);
        }
    }
}
