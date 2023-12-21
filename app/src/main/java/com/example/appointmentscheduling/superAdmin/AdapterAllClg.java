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

public class AdapterAllClg extends RecyclerView.Adapter<AdapterAllClg.MyViewHolder> {

    Context context;
    /*Activity activity;*/
    Fragment fragment;
    List<ModelAllClg> notesList;

    public AdapterAllClg(Context context, Fragment fragment, List<ModelAllClg> notesList) {
        this.context = context;
        this.fragment = fragment;
        this.notesList = notesList;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterAllClg.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_institute_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterAllClg.MyViewHolder holder, int position) {
        holder.clgId.setText(notesList.get(position).getClgId());
        holder.clgName.setText(notesList.get(position).getClgName());
        holder.clgAddr.setText(notesList.get(position).getClgAddr());

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView clgId,clgName,clgAddr;
        RelativeLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            clgId = itemView.findViewById(R.id.view_inst_id);
            clgName = itemView.findViewById(R.id.view_inst_name);
            clgAddr = itemView.findViewById(R.id.view_inst_address);
            layout = itemView.findViewById(R.id.view_inst_recycler);
        }
    }

}
