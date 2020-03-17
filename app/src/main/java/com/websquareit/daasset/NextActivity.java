package com.websquareit.daasset;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.websquareit.daasset.adapter.DataAdapter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class NextActivity extends AppCompatActivity implements OnClickListener {
    public static final String DATA_SAVED_BROADCAST = "websquareit.lightassets";
    public static final String HttpUrl = "http://lightassets.websquareit.com/index.php/welcome/new_insert_data";
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    private String[] BladeTypeData;
    private String[] BoxData;
    private String[] JumboData;
    private String[] Quickdata;
    private String[] SwingBladData;
    /* access modifiers changed from: private */
    private String[] Twindata,BattenData,EmergancyLights,ExitSignData;
    private String[] WeatherData;
    EditText assetNumber;
    String assetNumberHolder;
    private BroadcastReceiver broadcastReceiver;
    String cmntHolder;
    EditText commnets;

    /* renamed from: db */
    private DatabaseHelper f44db;
    private String[] emergancy2cross14;
    private String[] emergancy2cross18;
    private String[] emergancy2cross28;
    private String[] emergancy2cross36;
    private Button endBtn;
    private ListView listViewNames;
    EditText location;
    String locationHolder;

    private DataAdapter nameAdapter;
    /* access modifiers changed from: private */
    public List<DataModel> names;
    private Button nextBtn;
    ProgressDialog progressDialog;
    EditText propertyLevel;
    String propertyLevelHolder;
    RequestQueue requestQueue;
    private Button saveBtn;
    EditText siteName;
    String siteNameHolder;
    private Spinner spnBlad;
    String spnBladHolder;
    private Spinner spnBoxtype;
    String spnBoxtypeHolder;
    String spnErEighteen;
    String spnErFourteen;
    String spnErThreeSix;
    String spnErTwoEight;
    private Spinner spnJumbo;
    String spnJumboHolder;
    private Spinner spnQuick;
    String spnQuickHolder;
    private Spinner spnSwingBlad;
    String spnSwingBladHolder;
    /* access modifiers changed from: private */
    private Spinner spnTwin,spnBatten,spnEmr,spnExitSign;
    String spnTwinHolder,spnBattenHolder,spnEmrHolder,spnExitSignHolder;
    private Spinner spnWeather;
    String spnWeatherHolder;
    private Spinner spn_er2cross14;
    private Spinner spn_er2cross18;
    private Spinner spn_er2cross28;
    private Spinner spn_er2cross36;
    String selectBaten,selectEmr,selectExitSign;

    public NextActivity() {
        String str = "Select";
        this.Twindata = new String[]{str, "EM\tTwin Spotlight\t2x10W\n", "EM\tSpitfire LED\tRecessed Mount\n", "EM\tSpitfire LED\tSurface Mount\n"};
        this.Quickdata = new String[]{str, "EX\tSS\tQuick Fit\tWall\n", "EX\tSS\tQuick Fit\tCeiling\n", "EX\tDS\tQuick Fit\tWall\n", "EX\tDS\tQuick Fit\tCeiling\n"};
        this.BoxData = new String[]{str, "EX\tSS\tBox Type\tWall\n", "EX\tSS\tBox Type\tCeiling\n", "EX\tDS\tBox Type\tWall\n", "EX\tDS\tBox Type\tCeiling\n"};
        this.JumboData = new String[]{str, "EX\tSS\tJumbo\tWall\n", "EX\tSS\tJumbo\tCeiling\n", "EX\tDS\tJumbo\tWall\n", "EX\tDS\tJumbo\tCeiling\n"};
        this.WeatherData = new String[]{str, "EX\tSS\tWeatherproof\tWall\n", "EX\tSS\tWeatherproof\tCeiling\n", "EX\tDS\tWeatherproof\tWall\n", "EX\tDS\tWeatherproof\tCeiling\n"};
        this.BladeTypeData = new String[]{str, "EX\tSS\tBlade Type\tWall\n", "EX\tSS\tBlade Type\tCeiling\n", "EX\tDS\tBlade Type\tWall\n", "EX\tDS\tBlade Type\tCeiling\n"};
        this.SwingBladData = new String[]{str, "EX\tSS\tSwingblade Type\tWall\n", "EX\tSS\tSwingblade Type\tCeiling\n", "EX\tDS\tSwingblade Type\tWall\n", "EX\tDS\tSwingblade Type\tCeiling\n"};
        this.emergancy2cross14 = new String[]{str, "EM\tT5\t2x14W\tBare Batten\n", "EM\tT5\t2x14W\tWeatherproof Batten\n", "EM\tT5\t2x14W\tWireguard Batten\n", "EM\tT5\t2x14W\tDiffused Batten\n"};
        this.emergancy2cross28 = new String[]{str, "EM\tT5\t2x28W\tBare Batten\n", "EM\tT5\t2x28W\tWeatherproof Batten\n", "EM\tT5\t2x28W\tWireguard Batten\n", "EM\tT5\t2x28W\tDiffused Batten\n"};
        this.emergancy2cross18 = new String[]{str, "EM\tT8\t2x18W\tBare Batten\n", "EM\tT8\t2x18W\tWeatherproof Batten\n", "EM\tT8\t2x18W\tWireguard Batten\n", "EM\tT8\t2x18W\tDiffused Batten\n"};
        this.emergancy2cross36 = new String[]{str, "EM\tT8\t2x36W\tBare Batten\n", "EM\tT8\t2x36W\tWeatherproof Batten\n", "EM\tT8\t2x36W\tWireguard Batten\n", "EM\tT8\t2x36W\tDiffused Batten\n"};
        this.BattenData=new String[]{str,"EM\tShortBody\tWireguard Batten -Fluro\n","EM\tShortBody\tBare Batten\n","EM\tShortBody\tWeatheproof\n","EM\tShortBody\tDiffused Batten\n","EM\tLong Body\tWireguard Batten -Fluro\n","EM\tLong Body\tBare Batten\n","EM\tLong Body\tWeatheproof Battern\n","EM\tLong Body\tDiffused Batten\n"};
        this.EmergancyLights=new  String[]{str,"EM\tTwin Lamp EM Light\n","EM\tOyster Light\n","EM\tSquare Light\n","EM\tSpitfire LED - Recessed Mount\n","EM\tSpitfire LED - Surface Mount\n"};
        this.ExitSignData=new  String[]{str,
                "EX\tQuick Fit - Ceiling Mount\n","EX\tEconomy - Ceiling Mount Wedge\n","EX\tEconomy - Wall Mount Box\n","EX\tBlade Type - Ceiling Mount - Left Arrow\n","EX\tBlade Type - Surface Mount - Left Arrow\n","EX\tBlade Type - Ceiling Mount - Right Arrow\n","EX\tBlade Type - Surface Mount - Right Arrow\n","EX\tBlade Type - Recessed Mount – Running Man\n","EX\tBlade Type - Surface Mount – Running Man\n","EX\tWeatherproof - Ceiling Mount - Right Arrow\n","EX\tWeatherproof - Ceiling Mount - Left Arrow\n","EX\tDS - Jumbo - Ceiling Mount\n","EX\tSS - Jumbo - Ceiling Mount\n","EX\tDS - Jumbo - Wall Mount\n","EX\tSS - Jumbo - Wall Mount\n"};

    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_next);
        registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        this.spnTwin = (Spinner) findViewById(R.id.next_spn_twin);
        this.spnBatten=(Spinner)findViewById(R.id.nxt_spn_batn);
        this.spnEmr=(Spinner)findViewById(R.id.nxt_spn_emr);
        this.spnExitSign=(Spinner)findViewById(R.id.nxt_spn_es);
        this.saveBtn = (Button) findViewById(R.id.next_saveBtn);
        this.nextBtn = (Button) findViewById(R.id.next_nextBtn);
        this.endBtn = (Button) findViewById(R.id.next_endBtn);
        this.siteName = (EditText) findViewById(R.id.edit_next_sitename);
        this.assetNumber = (EditText) findViewById(R.id.edit_next_assetNo);
        this.propertyLevel = (EditText) findViewById(R.id.edt_next_propertyType);
        this.location = (EditText) findViewById(R.id.edt_next_location);
        this.commnets = (EditText) findViewById(R.id.edt_next_cmnt);
        this.listViewNames = (ListView) findViewById(R.id.listViewNames);
        this.f44db = new DatabaseHelper(this);
        this.names = new ArrayList();
        this.progressDialog = new ProgressDialog(this);
        List<DataModel> userSites = (List) new Gson().fromJson(getSharedPreferences("NEXTACTIVITY", 0).getString("NextData", null), new TypeToken<Collection<DataModel>>() {
        }.getType());



        if (userSites == null) {
            String str = "";
            this.siteName.setText(str);
            this.assetNumber.setText(str);
            this.propertyLevel.setText(str);
            this.location.setText(str);
            this.commnets.setText(str);
        } else {
            for (DataModel dm : userSites) {
                this.siteName.setText(dm.getSiteName());
            }
        }
        spnBatten.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,BattenData));
        spnBatten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectBaten=(String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnEmr.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,EmergancyLights));
        spnEmr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectEmr=(String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnExitSign.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,ExitSignData));
        spnExitSign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectExitSign=(String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.spnTwin.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.Twindata));
        this.spnTwin.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NextActivity.this.spnTwinHolder = (String) parent.getItemAtPosition(position);
              //  Editor edit = NextActivity.this.getSharedPreferences("quick", 0).edit();
              //  edit.putString("spinnerSelection", NextActivity.this.Twindata[NextActivity.this.spnTwin.getSelectedItemPosition()]);
              //  edit.apply();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spn_er2cross14 = (Spinner) findViewById(R.id.next_spn_er2cross14);
        this.spn_er2cross14.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.emergancy2cross14));
        this.spn_er2cross14.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NextActivity.this.spnErFourteen = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spn_er2cross18 = (Spinner) findViewById(R.id.next_spn_er2cross18);
        this.spn_er2cross18.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.emergancy2cross18));
        this.spn_er2cross18.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NextActivity.this.spnErEighteen = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spn_er2cross28 = (Spinner) findViewById(R.id.next_spn_er2cross28);
        this.spn_er2cross28.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.emergancy2cross28));
        this.spn_er2cross28.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NextActivity.this.spnErTwoEight = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spn_er2cross36 = (Spinner) findViewById(R.id.next_spn_er2cross36);
        this.spn_er2cross36.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.emergancy2cross36));
        this.spn_er2cross36.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NextActivity.this.spnErThreeSix = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnQuick = (Spinner) findViewById(R.id.next_spn_quick);
        this.spnQuick.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.Quickdata));
        this.spnQuick.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NextActivity.this.spnQuickHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnBoxtype = (Spinner) findViewById(R.id.next_spn_boxType);
        this.spnBoxtype.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.BoxData));
        this.spnBoxtype.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NextActivity.this.spnBoxtypeHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnJumbo = (Spinner) findViewById(R.id.next_spn_Jumbo);
        this.spnJumbo.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.JumboData));
        this.spnJumbo.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NextActivity.this.spnJumboHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnWeather = (Spinner) findViewById(R.id.next_spn_Weather);
        this.spnWeather.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.WeatherData));
        this.spnWeather.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NextActivity.this.spnWeatherHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnBlad = (Spinner) findViewById(R.id.next_spn_Bladtype);
        this.spnBlad.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.BladeTypeData));
        this.spnBlad.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NextActivity.this.spnBladHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnSwingBlad = (Spinner) findViewById(R.id.next_spn_swing);
        this.spnSwingBlad.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, this.SwingBladData));
        this.spnSwingBlad.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NextActivity.this.spnSwingBladHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        loadNames();
        this.broadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                NextActivity.this.loadNames();
            }
        };
        registerReceiver(this.broadcastReceiver, new IntentFilter("websquareit.lightassets"));
        this.saveBtn.setOnClickListener(this);
        this.nextBtn.setOnClickListener(this);
        this.endBtn.setOnClickListener(this);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /* access modifiers changed from: private */
 /*   public void loadNames() {
        this.names.clear();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        Cursor cursor = this.f44db.getNames();
        if (cursor.moveToFirst()) {
            while (true) {
                SimpleDateFormat sdf2 = sdf;
                DataModel dataModel = new DataModel(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.ASSET_NUMBER)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.TWIN_SPITFIRE)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMERGENCY_LIGHTS_T5_2X14W)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMERGENCY_LIGHTS_T8_2X18W)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMERGENCY_LIGHTS_T5_2X28W)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMERGENCY_LIGHTS_T8_2X36W)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXIT_QUICK_FIT)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXIT_BOX_TYPE)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXIT_BLADE_TYPE)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXIT_SWINGBLADE_TYPE)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXIT_WEATHERPROOF)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXIT_JUMBO)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOCATION)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROPERTY_LEVEL)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.COMMENTS)), cursor.getInt(cursor.getColumnIndex("status")));
                this.names.add(dataModel);
                if (!cursor.moveToNext()) {
                    break;
                }
                sdf = sdf2;
            }
        }
        this.nameAdapter = new DataAdapter(this, R.layout.name, this.names);
        this.listViewNames.setAdapter(this.nameAdapter);
    }*/
    private void loadNames() {
        names.clear();
        Cursor cursor = f44db.getNames();
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

    private void refreshList() {
        this.nameAdapter.notifyDataSetChanged();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_endBtn /*2131296407*/:
                startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
                break;
            case R.id.next_nextBtn /*2131296408*/:
                nextNameToServer();
                break;
            case R.id.next_saveBtn /*2131296409*/:
                saveNameToServer();
                break;
            default:
                break;
        }
    }

    private void saveNameToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Name...");
        progressDialog.show();

        final String name = siteName.getText().toString().trim();
        final String asset = assetNumber.getText().toString().trim();
        final String baten = spnBatten.getSelectedItem().toString();
        final String emer = spnEmr.getSelectedItem().toString();
        final String exit = spnExitSign.getSelectedItem().toString();
        final String location1 = location.getText().toString().trim();
        final String property = propertyLevel.getText().toString().trim();
        final String cmnt = commnets.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NewOneActivity.URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //if there is a success
                                //storing the name to sqlite with status synced
                                saveNameToLocalStorage(name,asset,baten,emer,exit,location1,property, cmnt,NAME_SYNCED_WITH_SERVER);
                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                saveNameToLocalStorage(name,asset,baten,emer,exit,location1,property,cmnt, NAME_NOT_SYNCED_WITH_SERVER);
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
                        saveNameToLocalStorage(name,asset,baten,emer,exit,location1,property,cmnt,  NAME_NOT_SYNCED_WITH_SERVER);
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
                params.put("LOCATION", location1);

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

        final String name = siteName.getText().toString().trim();
        final String asset = assetNumber.getText().toString().trim();
        final String baten = spnBatten.getSelectedItem().toString();
        final String emer = spnEmr.getSelectedItem().toString();
        final String exit = spnExitSign.getSelectedItem().toString();
        final String location1 = location.getText().toString().trim();
        final String property = propertyLevel.getText().toString().trim();
        final String cmnt = commnets.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, NewOneActivity.URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {

                                //if there is a success
                                //storing the name to sqlite with status synced
                                saveNameToLocalStorage(name,asset,baten,emer,exit,location1,property, cmnt,NAME_SYNCED_WITH_SERVER);
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
                                saveNameToLocalStorage(name,asset,baten,emer,exit,location1,property,cmnt, NAME_NOT_SYNCED_WITH_SERVER);
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
                        saveNameToLocalStorage(name,asset,baten,emer,exit,location1,property,cmnt,  NAME_NOT_SYNCED_WITH_SERVER);
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
                params.put("LOCATION", location1);

                params.put("PROPERTY_LEVEL", property);
                params.put("OTHERS_COMMENTS", cmnt);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void GetValueFromEditText() {
        this.siteNameHolder = this.siteName.getText().toString().trim();
        this.assetNumberHolder = this.assetNumber.getText().toString().trim();
        this.locationHolder = this.location.getText().toString().trim();
        this.propertyLevelHolder = this.propertyLevel.getText().toString().trim();
        this.cmntHolder = this.commnets.getText().toString().trim();
    }

    private void GetValueFromNextEditText() {
        this.siteNameHolder = this.siteName.getText().toString().trim();
        this.assetNumberHolder = this.assetNumber.getText().toString().trim();
        this.locationHolder = this.location.getText().toString().trim();
        this.propertyLevelHolder = this.propertyLevel.getText().toString().trim();
        this.cmntHolder = this.commnets.getText().toString().trim();
    }

    public void saveNameToLocalStorage(String siteNameHolder2, String assetNumberHolder2, String batten, String emergancy, String exitsign,String locationHolder2, String propertyLevelHolder2, String comment, int status) {

        // editTextName.setText("");
        f44db.addNewName(siteNameHolder2,assetNumberHolder2,batten,emergancy,exitsign,locationHolder2,propertyLevelHolder2,cmntHolder, status);
        DataModel n = new DataModel(siteNameHolder2,assetNumberHolder2,batten,emergancy,exitsign,locationHolder2,propertyLevelHolder2,cmntHolder, status);
        names.add(n);
        refreshList();
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
}
