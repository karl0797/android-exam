package com.example.androidtest;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.androidtest.UserList.Model.HolderInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GlobalsVariable {
    public static ArrayList<HolderInfo> holderInfo;

    public static void AlertMessage(Activity activity, String title, String message, String button){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setTitle(title);
        builder1.setCancelable(true);
        builder1.setMessage(message);

        builder1.setPositiveButton(
                button,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static void AlertClose(Activity activity, String title, String message, String button1, String button2){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setTitle(title);
        builder1.setCancelable(true);
        builder1.setMessage(message);

        builder1.setPositiveButton(
                button1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        activity.finish();
                        System.exit(0);
                    }
                });

        builder1.setNegativeButton(
                button2,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static String DateConvert(String Strdate) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(Strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String myFormat2 = "MMM. d, yyyy";
        DateFormat formatMDY = new SimpleDateFormat(myFormat2, Locale.US);

        return formatMDY.format(date);
    }

    public static String DateconvertReverse(String Strdate) {
        DateFormat format = new SimpleDateFormat("MMM. d, yyyy");
        Date date = null;
        try {
            date = format.parse(Strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String myFormat2 = "yyyy-MM-dd";
        DateFormat formatMDY = new SimpleDateFormat(myFormat2, Locale.US);

        return formatMDY.format(date);
    }

    public static String GetAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}
