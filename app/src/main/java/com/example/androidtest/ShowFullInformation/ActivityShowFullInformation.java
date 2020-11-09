package com.example.androidtest.ShowFullInformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.androidtest.GlobalsVariable;
import com.example.androidtest.R;
import com.example.androidtest.UserList.ActivityUserList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityShowFullInformation extends AppCompatActivity {

    TextView Profile_Name, Profile_Age, Profile_bday, Profile_Email, Profile_MNumber, Profile_Address, Profile_CPerson, Profile_CPersonNumber;
    FloatingActionButton Profile_fab_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_information);
        SetWidget();
    }

    void SetWidget(){
        Profile_Name = findViewById(R.id.Profile_Name);
        Profile_Age = findViewById(R.id.Profile_Age);
        Profile_bday = findViewById(R.id.Profile_bday);
        Profile_Email = findViewById(R.id.Profile_Email);
        Profile_MNumber = findViewById(R.id.Profile_MNumber);
        Profile_Address = findViewById(R.id.Profile_Address);
        Profile_CPerson = findViewById(R.id.Profile_CPerson);
        Profile_CPersonNumber = findViewById(R.id.Profile_CPersonNumber);


        Profile_fab_back = findViewById(R.id.Profile_fab_back);

        GetIntentData();
    }

    void GetIntentData(){
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Profile_Name.setText(extras.getString("fname") + " " + extras.getString("lname"));
            Profile_Age.setText(extras.getString("age"));
            Profile_bday.setText(GlobalsVariable.DateConvert(extras.getString("bday")));
            Profile_Email.setText(extras.getString("email"));
            Profile_MNumber.setText(extras.getString("mnumber"));
            Profile_Address.setText(extras.getString("address"));
            Profile_CPerson.setText(extras.getString("cperson"));
            Profile_CPersonNumber.setText(extras.getString("cpersonnumber"));
        }

        OnClickFunction();
    }

    void OnClickFunction(){
        Profile_fab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityShowFullInformation.this, ActivityUserList.class);
        startActivity(i);
        ActivityShowFullInformation.this.finish();
    }
}