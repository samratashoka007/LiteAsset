package com.websquareit.daasset;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.websquareit.daasset.adapter.DataAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewOneActivity extends AppCompatActivity implements View.OnClickListener {

    /*
     * this is the url to our webservice
     * make sure you are using the ip instead of localhost
     * it will not work if you are using localhost
     * */
    public static final String URL_SAVE_NAME = "http://lightassets.websquareit.com/index.php/welcome/new_insert_data";

    //database helper object
    private DatabaseHelper db;

    //View objects
    private Button buttonSave,buttonNext,buttonExport;
    private EditText editSiteName,editAssetNumber,editLocation,editPropertyLevel,editComment;
    private ListView listViewNames;

    //List to store all the names
    private List<DataModel> names;

    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;

    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCAST = "websquareit.datasaved";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;

    //adapterobject for list view
    private DataAdapter nameAdapter;
    Spinner spnrBatten,spnrEmergancy,spnrExitSign;
    String[] BattenData=new String[]{
            "Select","EM\tShortBody\tWireguard Batten -Fluro\n",
            "EM\tShortBody\tBare Batten\n","EM\tShortBody\tWeatheproof\n",
            "EM\tShortBody\tDiffused Batten\n","EM\tLong Body\tWireguard Batten -Fluro\n",
            "EM\tLong Body\tBare Batten\n","EM\tLong Body\tWeatheproof Battern\n",
            "EM\tLong Body\tDiffused Batten\n"};
    String[] EmergancyLights=new String[]{"Select","EM\tTwin Lamp EM Light\n","EM\tOyster Light\n","EM\tSquare Light\n","EM\tSpitfire LED - Recessed Mount\n","EM\tSpitfire LED - Surface Mount\n"};
    String[] ExitSignData=new  String[]{"Select",
            "EX\tQuick Fit - Ceiling Mount\n","EX\tEconomy - Ceiling Mount Wedge\n","EX\tEconomy - Wall Mount Box\n","EX\tBlade Type - Ceiling Mount - Left Arrow\n","EX\tBlade Type - Surface Mount - Left Arrow\n","EX\tBlade Type - Ceiling Mount - Right Arrow\n","EX\tBlade Type - Surface Mount - Right Arrow\n","EX\tBlade Type - Recessed Mount – Running Man\n","EX\tBlade Type - Surface Mount – Running Man\n","EX\tWeatherproof - Ceiling Mount - Right Arrow\n","EX\tWeatherproof - Ceiling Mount - Left Arrow\n","EX\tDS - Jumbo - Ceiling Mount\n","EX\tSS - Jumbo - Ceiling Mount\n","EX\tDS - Jumbo - Wall Mount\n","EX\tSS - Jumbo - Wall Mount\n"};
    String selectBaten,selectEmr,selectExitSign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_one);
        registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //initializing views and objects
        db = new DatabaseHelper(this);
        names = new ArrayList<>();

        buttonSave = (Button) findViewById(R.id.new1_saveBtn);
        buttonNext = (Button) findViewById(R.id.new1_nextBtn);
        buttonExport = (Button) findViewById(R.id.new1_endBtn);
        editSiteName = (EditText) findViewById(R.id.edit_new_sitename);
        editAssetNumber=findViewById(R.id.edit_new_assetNo);
        editLocation=findViewById(R.id.edt_new_location);
        editPropertyLevel=findViewById(R.id.edt_new_propertyType);
        editComment=findViewById(R.id.edt_new_cmnt);

        spnrBatten=findViewById(R.id.new1_spn_batn);
        spnrEmergancy=findViewById(R.id.new1_spn_emr);
        spnrExitSign=findViewById(R.id.new1_spn_es);

        spnrBatten.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,BattenData));
        spnrBatten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectBaten=(String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnrEmergancy.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,EmergancyLights));
        spnrEmergancy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectEmr=(String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnrExitSign.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,ExitSignData));
        spnrExitSign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectExitSign=(String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        listViewNames = (ListView) findViewById(R.id.listViewNames);

        //adding click listener to button
        buttonSave.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonExport.setOnClickListener(this);

        //calling the method to load all the stored names
        loadNames();

        //the broadcast receiver to update sync status
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //loading the names again
                loadNames();
            }
        };

        //registering the broadcast receiver to update sync status
        registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /*
     * this method will
     * load the names from the database
     * with updated sync status
     * */
    private void loadNames() {
        names.clear();
        Cursor cursor = db.getNames();
        if (cursor.moveToFirst()) {
            do {
                DataModel name = new DataModel(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.ASSET_NUMBER)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.BATTEN)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMERGENCY)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXITSIGN)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOCATION)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROPERTY_LEVEL)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COMMENTS)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS))
                );
                names.add(name);
            } while (cursor.moveToNext());
        }

        nameAdapter = new DataAdapter(this, R.layout.name, names);
        listViewNames.setAdapter(nameAdapter);
    }

    /*
     * this method will simply refresh the list
     * */
    private void refreshList() {
        nameAdapter.notifyDataSetChanged();
    }

    /*
     * this method is saving the name to ther server
     * */
    private void saveNameToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Name...");
        progressDialog.show();

        final String name = editSiteName.getText().toString().trim();
        final String asset = editAssetNumber.getText().toString().trim();
        final String baten = spnrBatten.getSelectedItem().toString();
        final String emer = spnrEmergancy.getSelectedItem().toString();
        final String exit = spnrExitSign.getSelectedItem().toString();
        final String location = editLocation.getText().toString().trim();
        final String property = editPropertyLevel.getText().toString().trim();
        final String cmnt = editComment.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //if there is a success
                                //storing the name to sqlite with status synced

                                saveNameToLocalStorage(name,asset,baten,emer,exit,location,property, cmnt,NAME_SYNCED_WITH_SERVER);
                                SharedPreferences.Editor prefsEditor = getSharedPreferences("NEXTACTIVITY1", 0).edit();
                                Gson gson = new Gson();
                                List<DataModel> textList = new ArrayList<>();
                                textList.addAll(names);
                                prefsEditor.putString("NextData1", gson.toJson((Object) textList));
                                prefsEditor.apply();
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                saveNameToLocalStorage(name,asset,baten,emer,exit,location,property,cmnt, NAME_NOT_SYNCED_WITH_SERVER);
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //on error storing the name to sqlite with status unsynced
                        Toast.makeText(getApplicationContext(),"The data saved in local database",Toast.LENGTH_SHORT).show();

                        saveNameToLocalStorage(name,asset,baten,emer,exit,location,property,cmnt,  NAME_NOT_SYNCED_WITH_SERVER);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("SITES", name);

                params.put("ASSET_NUMBER", asset);
                params.put("BATTENS", baten);
                params.put("EXIT_SIGNS", exit);
                params.put("EMERGENCY_LIGHTS",emer);
                params.put("LOCATION", location);

                params.put("PROPERTY_LEVEL", property);
                params.put("OTHERS_COMMENTS", cmnt);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void nextNameToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Name...");
        progressDialog.show();

        final String name = editSiteName.getText().toString().trim();
        final String asset = editAssetNumber.getText().toString().trim();
        final String baten = spnrBatten.getSelectedItem().toString();
        final String emer = spnrEmergancy.getSelectedItem().toString();
        final String exit = spnrExitSign.getSelectedItem().toString();
        final String location = editLocation.getText().toString().trim();
        final String property = editPropertyLevel.getText().toString().trim();
        final String cmnt = editComment.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {

                                //if there is a success
                                //storing the name to sqlite with status synced
                                saveNameToLocalStorage(name,asset,baten,emer,exit,location,property, cmnt,NAME_SYNCED_WITH_SERVER);
/*
                                SharedPreferences sharedPreferences = getSharedPreferences("NEXTACTIVITY", MODE_PRIVATE);
*//*

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();
                                List<DataModel> mExampleList=new ArrayList<>();
                                String wholeData = gson.toJson(mExampleList);
                                editor.putString("task list", wholeData);
                                editor.apply();
*/


                                SharedPreferences.Editor prefsEditor = getSharedPreferences("NEXTACTIVITY", 0).edit();
                                Gson gson = new Gson();
                                List<DataModel> textList = new ArrayList<>();
                                textList.addAll(names);
                                prefsEditor.putString("NextData", gson.toJson((Object) textList));
                                prefsEditor.apply();
                                Intent nextIntent=new Intent(getApplicationContext(),NextActivity.class);
                                startActivity(nextIntent);



                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                saveNameToLocalStorage(name,asset,baten,emer,exit,location,property,cmnt, NAME_NOT_SYNCED_WITH_SERVER);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //on error storing the name to sqlite with status unsynced
                        saveNameToLocalStorage(name,asset,baten,emer,exit,location,property,cmnt,  NAME_NOT_SYNCED_WITH_SERVER);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("SITES", name);
                params.put("ASSET_NUMBER", asset);
                params.put("BATTENS", baten);
                params.put("EXIT_SIGNS", exit);
                params.put("EMERGENCY_LIGHTS",emer);
                params.put("LOCATION", location);
                params.put("PROPERTY_LEVEL", property);
                params.put("OTHERS_COMMENTS", cmnt);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    //saving the name to local storage
    private void saveNameToLocalStorage(String name, String name1,String name2,String name3,String name4,String name5,String name6,String name7,int status) {
        editSiteName.setText("");
        editAssetNumber.setText("");
        editLocation.setText("");
        editComment.setText("");
        editPropertyLevel.setText("");
        spnrBatten.setSelection(0);
        spnrEmergancy.setSelection(0);
        spnrExitSign.setSelection(0);
        db.addNewName(name,name1,name2,name3,name4,name5,name6,name7, status);
        DataModel n = new DataModel(name,name1,name2,name3,name4,name5,name6,name7, status);
        names.add(n);
        refreshList();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.new1_saveBtn:
            saveNameToServer();
            break;

            case R.id.new1_nextBtn:
                nextNameToServer();
                break;

            case  R.id.new1_endBtn:
                Intent intent=new Intent(getApplicationContext(),WebViewActivity.class);
                startActivity(intent);
                break;
        }


        //saveNameToServer();
    }
}