package com.websquareit.daasset;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.websquareit.daasset.adapter.DataAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  NewActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String URL_SAVE_NAME = "http://lightassets.websquareit.com/index.php/welcome/new_insert_data";

    //database helper object
    private DatabaseHelper db;

    //View objects
    private Button buttonSave;
    private EditText editTextName,assetnumber,location,propertylevel,cmnt;
    private ListView listViewNames;

    //List to store all the names
    private List<DataModel> names;
    ProgressDialog progressDialog;
    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;

    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCAST = "websquareit.daasset";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;

    //adapterobject for list view
    private DataAdapter nameAdapter;
    String[] BattenData=new String[]{
            "Select","EM\tShortBody\tWireguard Batten -Fluro\n",
            "EM\tShortBody\tBare Batten\n","EM\tShortBody\tWeatheproof\n",
            "EM\tShortBody\tDiffused Batten\n","EM\tLong Body\tWireguard Batten -Fluro\n",
            "EM\tLong Body\tBare Batten\n","EM\tLong Body\tWeatheproof Battern\n",
            "EM\tLong Body\tDiffused Batten\n"};
    String[] EmergancyLights=new String[]{"Select","EM\tTwin Lamp EM Light\n","EM\tOyster Light\n","EM\tSquare Light\n","EM\tSpitfire LED - Recessed Mount\n","EM\tSpitfire LED - Surface Mount\n"};
    String[] ExitSignData=new  String[]{"Select",
            "EX\tQuick Fit - Ceiling Mount\n","EX\tEconomy - Ceiling Mount Wedge\n","EX\tEconomy - Wall Mount Box\n","EX\tBlade Type - Ceiling Mount - Left Arrow\n","EX\tBlade Type - Surface Mount - Left Arrow\n","EX\tBlade Type - Ceiling Mount - Right Arrow\n","EX\tBlade Type - Surface Mount - Right Arrow\n","EX\tBlade Type - Recessed Mount – Running Man\n","EX\tBlade Type - Surface Mount – Running Man\n","EX\tWeatherproof - Ceiling Mount - Right Arrow\n","EX\tWeatherproof - Ceiling Mount - Left Arrow\n","EX\tDS - Jumbo - Ceiling Mount\n","EX\tSS - Jumbo - Ceiling Mount\n","EX\tDS - Jumbo - Wall Mount\n","EX\tSS - Jumbo - Wall Mount\n"};

    Spinner battnspn,emrSpn,exitSignSpnr;
    String selectBaten,selectEmr,selectExitSign;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        db = new DatabaseHelper(this);
        names = new ArrayList<>();

        buttonSave = (Button) findViewById(R.id.new_saveBtn);
        editTextName = (EditText) findViewById(R.id.edit_sitename);
        assetnumber = (EditText) findViewById(R.id.edit_assetNo);
        location = (EditText) findViewById(R.id.edt_location);
        propertylevel = (EditText) findViewById(R.id.edt_propertyType);
        cmnt = (EditText) findViewById(R.id.edt_cmnt);
        editTextName = (EditText) findViewById(R.id.edit_sitename);
        listViewNames = (ListView) findViewById(R.id.listViewNames);
        battnspn=(Spinner)findViewById(R.id.new_spn_batn);
        emrSpn=(Spinner)findViewById(R.id.new_spn_emr);
        exitSignSpnr=(Spinner)findViewById(R.id.new_spn_es);
        progressDialog=new ProgressDialog(this);
        battnspn.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,BattenData));
        battnspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectBaten=(String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         emrSpn.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,EmergancyLights));
         emrSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectEmr=(String)parent.getItemAtPosition(position);
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

         exitSignSpnr.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,ExitSignData));
         exitSignSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectExitSign=(String)parent.getItemAtPosition(position);
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });
        //adding click listener to button
        buttonSave.setOnClickListener(this);

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
        this.progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        this.progressDialog.show();
        progressDialog.show();

        final String name = editTextName.getText().toString().trim();
        final String assetno = editTextName.getText().toString().trim();
        final String propertylevel = editTextName.getText().toString().trim();
        final String location = editTextName.getText().toString().trim();
        final String commnet = editTextName.getText().toString().trim();


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
                                saveNameToLocalStorage(name,assetno,selectBaten,selectEmr,selectExitSign,location,propertylevel,commnet, NAME_SYNCED_WITH_SERVER);
                                Toast.makeText(getApplicationContext(),"message",Toast.LENGTH_SHORT).show();
                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                saveNameToLocalStorage(name,assetno,selectBaten,selectEmr,selectExitSign,location,propertylevel,commnet, NAME_NOT_SYNCED_WITH_SERVER);
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
                        saveNameToLocalStorage(name,assetno,selectBaten,selectEmr,selectExitSign,location,propertylevel,commnet, NAME_NOT_SYNCED_WITH_SERVER);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("SITES", name);
                params.put("ASSET_NUMBER", assetno);
                params.put("BATTENS", selectBaten);
                params.put("EMERGENCY_LIGHTS", selectEmr);
                params.put("EXIT_SIGNS", selectExitSign);
                params.put("LOCATION", location);
                params.put("PROPERTY_LEVEL", propertylevel);
                params.put("OTHERS_COMMENTS", commnet);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    //saving the name to local storage
    private void saveNameToLocalStorage(String name,String assetno,String batten,String emrgncy,String exitsign,String locat,String property,String cmnt, int status) {
        editTextName.setText("");
        db.addNewName(name,assetno,batten,emrgncy,exitsign,locat,property,cmnt, status);
        DataModel n = new DataModel(name,assetno,batten,emrgncy,exitsign,locat,property,cmnt, status);
        names.add(n);
        refreshList();
    }

    @Override
    public void onClick(View view) {
        saveNameToServer();
    }
}