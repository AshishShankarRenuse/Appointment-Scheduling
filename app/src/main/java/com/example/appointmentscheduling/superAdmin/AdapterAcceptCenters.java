package com.example.appointmentscheduling.superAdmin;

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

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterAcceptCenters extends RecyclerView.Adapter<AdapterAcceptCenters.MyViewHolder> {

    Context context;
    Fragment fragment;
    List<ModelAcceptCenters> notesList;

    public AdapterAcceptCenters(Context context, Fragment fragment, List<ModelAcceptCenters> notesList) {
        this.context = context;
        this.fragment = fragment;
        this.notesList = notesList;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterAcceptCenters.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accepted_centers_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterAcceptCenters.MyViewHolder holder, int position) {
        holder.clgId.setText(notesList.get(position).getClgId());
        holder.clgName.setText(notesList.get(position).getClgName());
        holder.clgAddr.setText(notesList.get(position).getClgAddr());
        holder.contact.setText(notesList.get(position).getContact());
        holder.course.setText(notesList.get(position).getCourse());
        holder.process.setText(notesList.get(position).getProcess());

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView clgId,clgName,clgAddr,contact,course,process;
        RelativeLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            clgId = itemView.findViewById(R.id.accepted_centers_instId);
            clgName = itemView.findViewById(R.id.accepted_centers_clgName);
            clgAddr = itemView.findViewById(R.id.accepted_centers_clgAddr);
            contact = itemView.findViewById(R.id.accepted_centers_contact);
            course = itemView.findViewById(R.id.accepted_centers_course);
            process = itemView.findViewById(R.id.accepted_centers_process);
            layout = itemView.findViewById(R.id.accepted_centers_recycler);
        }
    }

}

