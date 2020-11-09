package com.example.androidtest.UserList;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.androidtest.GlobalsVariable;
import com.example.androidtest.R;
import com.example.androidtest.ShowFullInformation.ActivityShowFullInformation;
import com.example.androidtest.UserList.Database.SQLiteDB;
import com.example.androidtest.UserList.Model.HolderInfo;
import com.example.androidtest.UserList.Presenter.Userlist_Presenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ActivityUserList extends AppCompatActivity implements Userlist_Contract.View {

    Activity activity;
    SQLiteDB SQLiteDB;
    DatabaseReference reff;

    FloatingActionButton UserList_fab_add;
    SwipeRefreshLayout UserList_SwiRef;
    EditText UserList_Search;
    ListView UserList_List_of_info;

    AlertDialog AlertAddNew;
    InformationAdapter InformationAdapter;

    Userlist_Contract.UserList_AddNew userList_addNew;

    String a = null, b = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        activity = ActivityUserList.this;
        GlobalsVariable.holderInfo = new ArrayList<>();
        SetWidget();
    }

    void SetWidget(){
        UserList_SwiRef = findViewById(R.id.UserList_SwiRef);
        UserList_fab_add = findViewById(R.id.UserList_fab_add);
        UserList_Search = findViewById(R.id.UserList_Search);
        UserList_List_of_info = findViewById(R.id.UserList_List_of_info);
        OnClickFunction();
    }

    void OnClickFunction(){

        UserList_fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsWifiOn()){
                    AddNewInformation();
                }else{
                    GlobalsVariable.AlertMessage(activity,"NO INTERNET","Please make sure you are connected to internet before adding new information.","GOT IT");
                }
            }
        });

        UserList_SwiRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (IsWifiOn()) {
                    ChckCaches();
                    UserList_SwiRef.setRefreshing(false);
                }else{
                    GlobalsVariable.AlertMessage(activity,"NO INTERNET","Please open your wifi or mobile data.","GOT IT");
                    UserList_SwiRef.setRefreshing(false);
                }
            }
        });

        UserList_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(GlobalsVariable.holderInfo.size() > 0){
                    InformationAdapter.filter(s.toString());
                    InformationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(GlobalsVariable.holderInfo.size() > 0){
                    InformationAdapter.filter(s.toString());
                    InformationAdapter.notifyDataSetChanged();
                }
            }
        });

        UserList_List_of_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Intent i = new Intent(activity, ActivityShowFullInformation.class);
                i.putExtra("fname", GlobalsVariable.holderInfo.get(position).getFname());
                i.putExtra("lname", GlobalsVariable.holderInfo.get(position).getLname());
                i.putExtra("bday", GlobalsVariable.holderInfo.get(position).getBday());
                i.putExtra("age", GlobalsVariable.holderInfo.get(position).getAge());
                i.putExtra("email", GlobalsVariable.holderInfo.get(position).getEmail());
                i.putExtra("mnumber", GlobalsVariable.holderInfo.get(position).getMnumber());
                i.putExtra("address", GlobalsVariable.holderInfo.get(position).getAddress());
                i.putExtra("cperson", GlobalsVariable.holderInfo.get(position).getCperson());
                i.putExtra("cpersonnumber", GlobalsVariable.holderInfo.get(position).getCpersonnumber());
                startActivity(i);
                activity.finish();
            }
        });

        ChckCaches();
    }

    void ChckCaches(){
        SQLiteDB = new SQLiteDB(activity,"",null,1);
        if(SQLiteDB.get_info_count() > 0 ){
            SQLiteDB.List_Info();
//            InformationAdapter = new InformationAdapter(activity, R.layout.item_person, GlobalsVariable.holderInfo);
            InformationAdapter = new InformationAdapter(activity, R.layout.item_person, GlobalsVariable.holderInfo);
            UserList_List_of_info.setAdapter(InformationAdapter);
        }else{
            if (IsWifiOn()) {
                reff = FirebaseDatabase.getInstance().getReference().child("Information");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot newsnapshot : snapshot.getChildren()){
                            SQLiteDB.insertInfo(
                                    newsnapshot.child("fname").getValue().toString(),
                                    newsnapshot.child("lname").getValue().toString(),
                                    newsnapshot.child("bday").getValue().toString(),
                                    newsnapshot.child("age").getValue().toString(),
                                    newsnapshot.child("email").getValue().toString(),
                                    newsnapshot.child("mnumber").getValue().toString(),
                                    newsnapshot.child("address").getValue().toString(),
                                    newsnapshot.child("cperson").getValue().toString(),
                                    newsnapshot.child("cpersonnumber").getValue().toString());
                        }
                      if(SQLiteDB.get_info_count() > 0){
                          SQLiteDB.List_Info();
                          InformationAdapter = new InformationAdapter(activity, R.layout.item_person, GlobalsVariable.holderInfo);
                          UserList_List_of_info.setAdapter(InformationAdapter);
                      }else{
                          GlobalsVariable.AlertMessage(activity,"NO RECORD","Please add new information.","GOT IT");
                      }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }else{
                GlobalsVariable.AlertMessage(activity,"NO INTERNET","Please open your wifi or mobile data.","GOT IT");
            }
        }
    }

    public boolean IsWifiOn() {
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
        return (is3g || isWifi);
    }

    void AddNewInformation(){
        LayoutInflater li = LayoutInflater.from(activity);
        final View promptsView = li.inflate(R.layout.dialog_add_new_information, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setView(promptsView);
        AlertAddNew = alertDialogBuilder.create();
        AlertAddNew.setCancelable(true);

        EditText AddNew_fname = promptsView.findViewById(R.id.AddNew_fname);
        EditText AddNew_lname = promptsView.findViewById(R.id.AddNew_lname);
        TextView AddNew_bday = promptsView.findViewById(R.id.AddNew_bday);
        EditText AddNew_email = promptsView.findViewById(R.id.AddNew_email);
        EditText AddNew_mnumber = promptsView.findViewById(R.id.AddNew_mnumber);
        EditText AddNew_address = promptsView.findViewById(R.id.AddNew_address);
        EditText AddNew_cperson = promptsView.findViewById(R.id.AddNew_cperson);
        EditText AddNew_cpersonnumber = promptsView.findViewById(R.id.AddNew_cpersonnumber);
        Button btn_save = promptsView.findViewById(R.id.btn_save);
        Button btn_cancel = promptsView.findViewById(R.id.btn_cancel);

        AddNew_bday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDate(AddNew_bday);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertAddNew.dismiss();
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(AddNew_fname.getText().toString().length() == 0 || AddNew_lname.getText().toString().length() == 0 || AddNew_bday.getText().toString().length() == 0 ||
                      AddNew_email.getText().toString().length() == 0 || AddNew_mnumber.getText().toString().length() == 0 || AddNew_cperson.getText().toString().length() == 0 ||
                      AddNew_cpersonnumber.getText().toString().length() == 0 || AddNew_address.getText().toString().length() == 0){
                  GlobalsVariable.AlertMessage(activity,"MISSING INFO","Please complete all information before saving.","GOT IT");
              }else{
                  String[] Seperated_Date = GlobalsVariable.DateconvertReverse(AddNew_bday.getText().toString()).split("-");

                  userList_addNew = new Userlist_Presenter(ActivityUserList.this);
                  userList_addNew.AddNew(AddNew_fname.getText().toString(), AddNew_lname.getText().toString(), AddNew_bday.getText().toString(), AddNew_email.getText().toString(), AddNew_mnumber.getText().toString(), AddNew_address.getText().toString(), AddNew_cperson.getText().toString(), AddNew_cpersonnumber.getText().toString(), Seperated_Date, activity);
              }
            }
        });

        AlertAddNew.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        AlertAddNew.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        AlertAddNew.show();
    }

    void ShowDate(TextView txt){
        final int mYear, mMonth, mDay;
        final int[] mMonth1 = new int[1];
        final int[] mDay1 = new int[1];
        final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mMonth1[0] = mMonth;
                mDay1[0] = mDay;

                if((monthOfYear + 1) < 10){
                    mMonth1[0] = Integer.parseInt(String.valueOf("0" + monthOfYear));
                    a = "0" + String.valueOf(monthOfYear + 1);
                }else{
                    a = String.valueOf((monthOfYear + 1));
                }

                if(dayOfMonth < 10){
                    b = "0" + String.valueOf(dayOfMonth);
                }else{
                    b = String.valueOf(dayOfMonth);
                }
                txt.setText(GlobalsVariable.DateConvert(String.valueOf( year + "-" +  a + "-" + b)));
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    private class InformationAdapter extends ArrayAdapter<HolderInfo> {
        ArrayList<HolderInfo> arrayList;
        ArrayList<HolderInfo> tmpLog = new ArrayList<>();

        InformationAdapter(Context scheduleActivity, int textViewResourceId, ArrayList<HolderInfo> incList) {
            super(scheduleActivity, textViewResourceId, incList);
            tmpLog = new ArrayList<>();
            arrayList = new ArrayList<>();

            tmpLog.addAll(incList);
            arrayList.addAll(incList);
        }

        private class ViewHolder {
            TextView name, contact;
        }

        @Override
        public int getCount() { return GlobalsVariable.holderInfo.size(); }

        @Override
        public HolderInfo getItem(int i) { return GlobalsVariable.holderInfo.get(i); }

        @Override
        public long getItemId(int i) { return i; }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.item_person, null);
                holder = new ViewHolder();
                holder.name = convertView.findViewById(R.id.name);
                holder.contact = convertView.findViewById(R.id.contact);
                convertView.setTag(holder);
            } else { holder = (ViewHolder) convertView.getTag(); }

            HolderInfo info = tmpLog.get(position);
            holder.name.setText(info.getFname() + " " + info.getLname());
            holder.contact.setText(info.getMnumber());
            return convertView;
        }

        public void filter(String charText){
            charText = charText.toLowerCase(Locale.getDefault());
            GlobalsVariable.holderInfo.clear();
            if (charText.length()==0){
                GlobalsVariable.holderInfo.addAll(tmpLog);
            } else {
                for (HolderInfo HolderInfo : arrayList){
                    if (HolderInfo.getFname().toLowerCase(Locale.getDefault()).contains(charText)){
                        GlobalsVariable.holderInfo.add(HolderInfo);
                    }else if (HolderInfo.getLname().toLowerCase(Locale.getDefault()).contains(charText)){
                        GlobalsVariable.holderInfo.add(HolderInfo);
                    }
                }
            }
        }
    }

    @Override
    public void onSuccess(String message) {
        GlobalsVariable.AlertMessage(activity,"FIRE BASE", message,"GOT IT");
        InformationAdapter = new InformationAdapter(activity, R.layout.item_person, GlobalsVariable.holderInfo);
        UserList_List_of_info.setAdapter(InformationAdapter);
        AlertAddNew.dismiss();
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onBackPressed() {
        GlobalsVariable.AlertClose(activity, "CONFIRM Exit","Are you sure you want to exit?","EXIT","CANCEL");
    }
}