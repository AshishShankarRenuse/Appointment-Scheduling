package com.example.appointmentscheduling.admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appointmentscheduling.R;
import com.example.appointmentscheduling.student.StudentBookAppointment;
import com.example.appointmentscheduling.superAdmin.ViewSchedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FCDayFragment extends Fragment {

    static String usernameFinal,collegeIdFinal;


    private Spinner processSpin,courseSpin,dateSpin,slot;
    Button viewBtn;

    static String processSelected = new String();

    List<String> schId = new ArrayList<>();
    List<String> processList = new ArrayList<>();
    List<String> courseList = new ArrayList<>();
    List<String> dateList = new ArrayList<>();




    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fc_day_fragment, container, false);

        processSpin = view.findViewById(R.id.ad_select_process);
        courseSpin = view.findViewById(R.id.ad_select_courses);
        dateSpin = view.findViewById(R.id.ad_schedule_date);
        slot = view.findViewById(R.id.ad_select_time);
        viewBtn = view.findViewById(R.id.view_applicants_btn);

        ViewApplicant.username = usernameFinal;

        DatabaseReference df = FirebaseDatabase.getInstance().getReference();

        df.child("Admin")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(snap.child("Username").getValue().toString().equals(usernameFinal)){
                                collegeIdFinal = snap.child("ClgId").getValue().toString();
                                Log.d("collegeidfinal", collegeIdFinal);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

        df.child("Request")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(snap.child("ClgId").getValue().toString().equals(collegeIdFinal) && snap.child("Status").getValue().toString().equals("Granted")){
                                schId.add(snap.child("ScheduleId").getValue().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

        df.child("Schedule")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){

                            for(String id : schId){
                                if(snap.child("ScheduleID").getValue().toString().equals(id)){
                                    processList.add(snap.child("Process").getValue().toString());
                                    courseList.add(snap.child("Course").getValue().toString());

                                    ArrayAdapter<String> array = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,processList);
                                    processSpin.setAdapter(array);

                                    ArrayAdapter<String> array1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,courseList);
                                    courseSpin.setAdapter(array1);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


        processSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                processSelected = processSpin.getSelectedItem().toString();
                courseList.removeAll(courseList);
                dateList.removeAll(dateList);



                df.child("Schedule")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for(DataSnapshot snap : snapshot.getChildren()){

                                    for(String id : schId){
                                        if(snap.child("ScheduleID").getValue().toString().equals(id)){

                                            courseList.add(snap.child("Course").getValue().toString());

                                            ArrayAdapter<String> array1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,courseList);
                                            courseSpin.setAdapter(array1);
                                        }
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });


                df.child("Schedule")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                for(DataSnapshot snap : snapshot.getChildren()){
                                    if(snap.child("Course").getValue().toString().equals(courseSpin.getSelectedItem().toString()) && snap.child("Process").getValue().toString().equals(processSelected)){
                                        String startDate = snap.child("StartDate").getValue().toString();
                                        String endDate = snap.child("EndDate").getValue().toString();

                                        String[] firstDate = startDate.split("-",2);
                                        int dateStart = Integer.parseInt(firstDate[0]);

                                        String[] lastDate = endDate.split("-",2);
                                        int dateEnd = Integer.parseInt(lastDate[0]);

                                        int allDate[] = new int[(dateEnd-dateStart)+1];

                                        for (int i = dateStart,j = 0;i <= dateEnd;i++,j++){
                                            allDate[j] = i;
                                        }

                                        String[] datesFinal = new String[allDate.length];

                                        for(int i=0;i < datesFinal.length;i++){
                                            datesFinal[i] = allDate[i] +"-0"+ firstDate[1];
                                        }


                                        for(int i=0;i<datesFinal.length;i++){
                                            dateList.add(datesFinal[i]);
                                        }

                                        ArrayAdapter<String> array = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,dateList);
                                        dateSpin.setAdapter(array);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewApplicant.process = processSpin.getSelectedItem().toString();
                ViewApplicant.course = courseSpin.getSelectedItem().toString();
                ViewApplicant.date = dateSpin.getSelectedItem().toString();
                ViewApplicant.slot = slot.getSelectedItem().toString();



                Fragment fragment = new ViewApplicant();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.admin_nav_host_fragment_content_main_home,fragment);
                ft.commit();
            }
        });

        return view;
    }

    public void setUsername(String usernameFinal){
        this.usernameFinal = usernameFinal;
    }

}
