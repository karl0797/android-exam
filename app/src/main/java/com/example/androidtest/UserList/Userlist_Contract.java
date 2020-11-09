package com.example.androidtest.UserList;

import android.app.Activity;

public interface Userlist_Contract {

    interface View{
        void onSuccess(String message);
        void onError(String message);
    }

    interface UserList_AddNew{
        void AddNew(String AddNew_fname, String AddNew_lname, String AddNew_bday, String AddNew_email, String AddNew_mnumber, String AddNew_address, String AddNew_cperson, String AddNew_cpersonnumber, String Seperated_Date[], Activity activity);
    }
}
