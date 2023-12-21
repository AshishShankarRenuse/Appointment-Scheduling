package com.example.appointmentscheduling.superAdmin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appointmentscheduling.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class CreateSchedule extends Fragment implements View.OnClickListener {

    private TextView dateFirst, dateSecond;
    private int mYear, mMonth, mDay;
    private Spinner spinnerCourses,spinnerProcess;
    Button createButton;
    static int idCount = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.super_admin_create_schedule, container, false);
        initView(view);


        createButton = view.findViewById(R.id.create_schedule_btn);
        dateFirst = view.findViewById(R.id.dateFirst);
        dateSecond = view.findViewById(R.id.dateSecond);

        spinnerCourses = view.findViewById(R.id.spinnerCourses);
        spinnerProcess = view.findViewById(R.id.spinnerProcess);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(dateFirst.getText().toString()) && !TextUtils.isEmpty(dateSecond.getText().toString())){

                    HashMap<String,Object> map = new HashMap<>();
                    map.put("ScheduleID",String.valueOf(++idCount));
                    map.put("Course",spinnerCourses.getSelectedItem().toString());
                    map.put("Process",spinnerProcess.getSelectedItem().toString());
                    map.put("StartDate",dateFirst.getText().toString());
                    map.put("EndDate",dateSecond.getText().toString());

                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Schedule")
                            .child(String.valueOf(idCount))
                            .setValue(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setCancelable(true);
                                    builder.setMessage("Schedule Created Successfully !!!");
                                    builder.setNegativeButton("View Schedule", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dateFirst.setText("");
                                            dateSecond.setText("");

                                            Fragment fragment = new ViewSchedule();
                                            FragmentManager fm = getFragmentManager();
                                            FragmentTransaction ft = fm.beginTransaction();
                                            ft.replace(R.id.sup_nav_host_fragment_content_main_home,fragment);
                                            ft.commit();

                                            dialog.cancel();
                                        }
                                    });
                                    builder.show();
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });


                }

            }
        });
        return view;
    }

    private void initView(View view) {
        dateFirst = view.findViewById(R.id.dateFirst);
        dateSecond = view.findViewById(R.id.dateSecond);

        spinnerCourses = view.findViewById(R.id.spinnerCourses);
        spinnerProcess = view.findViewById(R.id.spinnerProcess);

        dateFirst.setOnClickListener(this);
        dateSecond.setOnClickListener(this);

        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(requireContext(), "Please select course", Toast.LENGTH_LONG).show();
                    return;
                }
                String selectedItem = (String) spinnerCourses.getSelectedItem();
                Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerProcess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(requireContext(), "Please select Process", Toast.LENGTH_LONG).show();
                    return;
                }
                String selectedItem = (String) spinnerProcess.getSelectedItem();
                Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateFirst:
                showDatePickerDialog(dateFirst);
                break;
            case R.id.dateSecond:
                showDatePickerDialog(dateSecond);
                break;
        }
    }

    private void showDatePickerDialog(TextView textView) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        textView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
