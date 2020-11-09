package com.example.androidtest.UserList.Presenter;

import android.app.Activity;

import com.example.androidtest.GlobalsVariable;
import com.example.androidtest.UserList.Database.SQLiteDB;
import com.example.androidtest.UserList.Model.Information;
import com.example.androidtest.UserList.Userlist_Contract;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Userlist_Presenter implements Userlist_Contract.UserList_AddNew {

    Userlist_Contract.View view;


    public Userlist_Presenter(Userlist_Contract.View view){
        this.view = view;
    }

    @Override
    public void AddNew(String AddNew_fname, String AddNew_lname, String AddNew_bday, String AddNew_email, String AddNew_mnumber, String AddNew_address, String AddNew_cperson, String AddNew_cpersonnumber, String[] Seperated_Date, Activity activity) {
        SQLiteDB SQLiteDB = new SQLiteDB(activity,"",null,1);
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Information");
        Information information  = new Information();

        information.setFname(AddNew_fname);
        information.setLname(AddNew_lname);
        information.setBday(GlobalsVariable.DateconvertReverse(AddNew_bday));
        information.setAge(GlobalsVariable.GetAge(Integer.parseInt(Seperated_Date[0]),Integer.parseInt(Seperated_Date[1]),Integer.parseInt(Seperated_Date[2])));
        information.setEmail(AddNew_email);
        information.setMnumber(AddNew_mnumber);
        information.setAddress(AddNew_address);
        information.setCperson(AddNew_cperson);
        information.setCpersonnumber(AddNew_cpersonnumber);
        reff.push().setValue(information);

        SQLiteDB.insertInfo(
                AddNew_fname,
                AddNew_lname,
                GlobalsVariable.DateconvertReverse(AddNew_bday),
                GlobalsVariable.GetAge(Integer.parseInt(Seperated_Date[0]),Integer.parseInt(Seperated_Date[1]),Integer.parseInt(Seperated_Date[2])),
                AddNew_email,
                AddNew_mnumber,
                AddNew_address,
                AddNew_cperson,
                AddNew_cpersonnumber);

        SQLiteDB.List_Info();

        view.onSuccess("Success : Adding Information");
    }

}
