package com.example.appointmentscheduling.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointmentscheduling.MainHomeActivity;
import com.example.appointmentscheduling.R;
import com.example.appointmentscheduling.superAdmin.SuperAdminDraw;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentBookAppointment extends AppCompatActivity {
    static String uname;
    Spinner process,college,dateFinal,slot;
    Button bookBtn,viewApp;

    List<String> processList = new ArrayList<>();
    List<String> collegeList = new ArrayList<>();
    List<String> dateList = new ArrayList<>();
    List<String> schId = new ArrayList<>();
    Set<String> clgId = new HashSet<>();

    static String studCourse = new String();
    static String processSelected = new String();
    static String collegeSelected = new String();
    static String clgIdFinal = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_book_appointment);

        Bundle extra = getIntent().getExtras();
        uname = extra.getString("username");
        Log.d("uname", uname);

        process = findViewById(R.id.book_app_process);
        college = findViewById(R.id.book_app_clg);
        dateFinal = findViewById(R.id.book_app_date);
        slot = findViewById(R.id.book_app_slot);
        bookBtn = findViewById(R.id.book_app_btn);
        viewApp = findViewById(R.id.view_app_btn);


        DatabaseReference df = FirebaseDatabase.getInstance().getReference();

        df.child("Student")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(snap.child("AppId").getValue().toString().equals(uname)){
                                studCourse = snap.child("Stream").getValue().toString();
                                Log.d("course", studCourse);

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
                            if(snap.child("Course").getValue().toString().equals(studCourse)){
                                processList.add(snap.child("Process").getValue().toString());
                                schId.add(snap.child("ScheduleID").getValue().toString());

                                ArrayAdapter<String> array = new ArrayAdapter<>(StudentBookAppointment.this, android.R.layout.simple_spinner_dropdown_item,processList);
                                process.setAdapter(array);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

        showCollege();


        process.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                processSelected = process.getSelectedItem().toString();
                Log.d("process", processSelected);
                dateList.removeAll(dateList);


                df.child("Schedule")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                for(DataSnapshot snap : snapshot.getChildren()){
                                    if(snap.child("Course").getValue().toString().equals(studCourse) && snap.child("Process").getValue().toString().equals(processSelected)){
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

                                        ArrayAdapter<String> array = new ArrayAdapter<>(StudentBookAppointment.this, android.R.layout.simple_spinner_dropdown_item,dateList);
                                        dateFinal.setAdapter(array);
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

        college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                collegeSelected = college.getSelectedItem().toString();

                df.child("Admin")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for(DataSnapshot snap : snapshot.getChildren()){
                                    if(snap.child("ClgName").getValue().toString().equals(collegeSelected)){
                                        clgIdFinal = snap.child("ClgId").getValue().toString();
                                        Log.d("clgid", clgIdFinal);
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



        viewApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentBookAppointment.this,ViewAppointment.class);
                intent.putExtra("username",uname);
                startActivity(intent);
            }
        });






        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentBookAppointment.this);
                builder.setMessage("Are your sure want to book ? ");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        df.child("Admin")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snap : snapshot.getChildren()) {
                                            String clgnm = snap.child("ClgName").getValue().toString();
                                            Log.d("clgnm", clgnm);

                                            if (clgnm.equals(college.getSelectedItem().toString())) {
                                                String clgId = snap.child("ClgId").getValue().toString();
                                                Log.d("clgid", clgId);

                                                df.child("Appointment")
                                                        .child(clgId)
                                                        .child(studCourse)
                                                        .child(process.getSelectedItem().toString())
                                                        .child(dateFinal.getSelectedItem().toString())
                                                        .child(slot.getSelectedItem().toString())
                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                if(Integer.parseInt(snapshot.getValue().toString()) >=1 && Integer.parseInt(snapshot.getValue().toString()) <=10){
                                                                    int val = Integer.parseInt(snapshot.getValue().toString()) - 1;

                                                                    String value2 = String.valueOf(val);
                                                                    snapshot.getRef().setValue(value2);
                                                                    bookApp();

                                                                }else{
                                                                    Toast.makeText(StudentBookAppointment.this,"Slots full",Toast.LENGTH_LONG).show();
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.student_action_logout:
                Intent i = new Intent(StudentBookAppointment.this, MainHomeActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void bookApp(){
        DatabaseReference df = FirebaseDatabase.getInstance().getReference();

        df.child("Schedule")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            String cour = snap.child("Course").getValue().toString();
                            String proc = snap.child("Process").getValue().toString();

                            df.child("Student")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            for(DataSnapshot snapStud : snapshot.getChildren()) {
                                                if (snapStud.child("AppId").getValue().toString().equals(uname)) {
                                                    String course = snapStud.child("Stream").getValue().toString();

                                                    if (course.equals(cour) && process.getSelectedItem().toString().equals(proc)) {
                                                        String schId = snap.child("ScheduleID").getValue().toString();

                                                        df.child("Request")
                                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                                        if(snapshot.exists()){
                                                                            for(DataSnapshot snap : snapshot.getChildren()){
                                                                                if(snap.child("ScheduleId").getValue().toString().equals(schId) && snap.child("ClgId").getValue().toString().equals(clgIdFinal)){
                                                                                    String reqId = snap.child("RequestId").getValue().toString();

                                                                                    df.child("Student")
                                                                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                @Override
                                                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                                                    for(DataSnapshot snap : snapshot.getChildren()){
                                                                                                        if(snap.child("AppId").getValue().toString().equals(uname)){

                                                                                                            if(process.getSelectedItem().toString().equals("FC")){
                                                                                                                snap.getRef().child("FC").setValue("Booked");
                                                                                                                snap.getRef().child("FCDate").setValue(dateFinal.getSelectedItem().toString());
                                                                                                                snap.getRef().child("FCSlot").setValue(slot.getSelectedItem().toString());
                                                                                                                snap.getRef().child("RequestId").setValue(reqId);
                                                                                                                break;
                                                                                                            }

                                                                                                            if(process.getSelectedItem().toString().equals("ARC")){
                                                                                                                snap.getRef().child("ARC").setValue("Booked");
                                                                                                                snap.getRef().child("ARCDate").setValue(dateFinal.getSelectedItem().toString());
                                                                                                                snap.getRef().child("ARCSlot").setValue(slot.getSelectedItem().toString());
                                                                                                                snap.getRef().child("RequestId").setValue(reqId);
                                                                                                                break;
                                                                                                            }
                                                                                                        }
                                                                                                    }

                                                                                                }

                                                                                                @Override
                                                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                                                }
                                                                                            });
                                                                                }

                                                                            }
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                                                    }
                                                                });
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void showCollege(){
        college = findViewById(R.id.book_app_clg);

        DatabaseReference df = FirebaseDatabase.getInstance().getReference();


        clgId.removeAll(clgId);
        schId.removeAll(schId);
        collegeList.removeAll(collegeList);


        df.child("Schedule")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(snap.child("Course").getValue().toString().equals(studCourse)){

                                schId.add(snap.child("ScheduleID").getValue().toString());


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
                            for(String id : schId){
                                if(snap.child("Status").getValue().toString().equals("Granted") && snap.child("ScheduleId").getValue().toString().equals(id)){
                                    clgId.add(snap.child("ClgId").getValue().toString());
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


        df.child("Admin")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()){
                            for(String id : clgId){
                                if(snap.child("ClgId").getValue().toString().equals(id)){
                                    String clg = snap.child("ClgName").getValue().toString();
                                    Log.d("clg", clg);
                                    //String clgName[] = clg.split(",",2);
                                    collegeList.add(clg);

                                    ArrayAdapter<String> array1 = new ArrayAdapter<>(StudentBookAppointment.this, android.R.layout.simple_spinner_dropdown_item,collegeList);
                                    college.setAdapter(array1);
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }
}