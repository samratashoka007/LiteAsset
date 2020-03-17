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
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SaveActivity extends AppCompatActivity implements OnClickListener {
    public static final String DATA_SAVED_BROADCAST = "websquareit.datasaved";
    public static final String HttpUrl = "http://lightassets.websquareit.com/index.php/welcome/new_insert_data";
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    private String[] BladeTypeData;
    private String[] BoxData;
    private String[] JumboData;
    private String[] Quickdata;
    private String[] SwingBladData;
    private String[] Twindata,BattenData,EmergancyLights,ExitSignData;
    private String[] WeatherData;
    String assetNumberHolder;
    private BroadcastReceiver broadcastReceiver;
    String cmntHolder;

    /* renamed from: db */
    private DatabaseHelper databaseHelper;
    private String[] emergancy2cross14;
    private String[] emergancy2cross18;
    private String[] emergancy2cross28;
    private String[] emergancy2cross36;
    private Button endBtn;
    private ListView listViewNames;
    String locationHolder;
    private DataAdapter nameAdapter;
    /* access modifiers changed from: private */
    public List<DataModel> names;
    private Button nextBtn;
    ProgressDialog progressDialog;
    String propertyLevelHolder;
    RequestQueue requestQueue;
    private Button saveBtn;
    EditText saveassetNumber;
    EditText savecommnets;
    EditText savelocation;
    EditText savepropertyLevel;
    TextView savesitename;
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
    private Spinner spnTwin,spnBatten,spnEmr,spnExitSign;
    String spnTwinHolder,spnBattenHolder,spnEmrHolder,spnExitSignHolder;
    private Spinner spnWeather;
    String spnWeatherHolder;
    private Spinner spn_er2cross14;
    private Spinner spn_er2cross18;
    private Spinner spn_er2cross28;
    private Spinner spn_er2cross36;

    public SaveActivity() {
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
        setContentView(R.layout.activity_save);
        registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

      /*  List<DataModel> userSites = (List) new Gson().fromJson(getSharedPreferences("AppName", 0).getString("key", null), new TypeToken<Collection<DataModel>>() {
        }.getType());*/
        Gson gson=new Gson();
      /*  List<DataModel> userSites=
                gson.fromJson(getIntent().getStringExtra("key"),new TypeToken<ArrayList<DataModel>>(){}.getType());
        SharedPreferences sharedPreferences = getSharedPreferences("quick", 0);
   */

        /*List<DataModel> userSites = (List) new Gson().fromJson(getSharedPreferences("key", 0).
                getString("data", null), new TypeToken<Collection<DataModel>>() {
        }.getType());*/
        List<DataModel> userSites = (List) new Gson().fromJson(getSharedPreferences("NEXTACTIVITY1", 0).getString("NextData1", null), new TypeToken<Collection<DataModel>>() {
        }.getType());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    //    this.spnTwin = (Spinner) findViewById(R.id.save_spn_twin);
        this.spnBatten=(Spinner)findViewById(R.id.save_spn_batn);
        this.spnEmr=(Spinner)findViewById(R.id.save_spn_emr);
        this.spnExitSign=(Spinner)findViewById(R.id.save_spn_es);
        this.saveBtn = (Button) findViewById(R.id.save_saveBtn);
        this.nextBtn = (Button) findViewById(R.id.save_nextBtn);
        this.endBtn = (Button) findViewById(R.id.save_endBtn);
        this.savesitename = (TextView) findViewById(R.id.edit_save_sitename);
        this.saveassetNumber = (EditText) findViewById(R.id.edit_save_assetNo);
        this.savepropertyLevel = (EditText) findViewById(R.id.edt_save_propertyType);
        this.savelocation = (EditText) findViewById(R.id.edt_save_location);
        this.savecommnets = (EditText) findViewById(R.id.edt_save_cmnt);
        this.progressDialog = new ProgressDialog(this);
/*        this.spn_er2cross14 = (Spinner) findViewById(R.id.save_spn_er2cross14);
        this.spn_er2cross18 = (Spinner) findViewById(R.id.save_spn_er2cross18);
        this.spn_er2cross28 = (Spinner) findViewById(R.id.save_spn_er2cross28);
        this.spn_er2cross36 = (Spinner) findViewById(R.id.save_spn_er2cross36);
        this.spnQuick = (Spinner) findViewById(R.id.save_spn_quick);
        this.spnBoxtype = (Spinner) findViewById(R.id.save_spn_boxType);
        this.spnJumbo = (Spinner) findViewById(R.id.save_spn_Jumbo);
        this.spnWeather = (Spinner) findViewById(R.id.save_spn_Weather);
        this.spnBlad = (Spinner) findViewById(R.id.save_spn_Bladtype);
        this.spnSwingBlad = (Spinner) findViewById(R.id.save_spn_swing);*/
        this.listViewNames = (ListView) findViewById(R.id.listViewNames);
        this.databaseHelper = new DatabaseHelper(this);
        this.names = new ArrayList();
        this.progressDialog = new ProgressDialog(this);
        if (userSites == null) {
            String str = "";
            this.savesitename.setText(str);
            this.saveassetNumber.setText(str);
            this.savepropertyLevel.setText(str);
            this.savelocation.setText(str);
            this.savecommnets.setText(str);
        } else {
            for (DataModel dataModel : userSites) {
                this.savesitename.setText(dataModel.getSiteName());
            //    this.saveassetNumber.setText(dataModel.getAssetNo());
             //   this.savepropertyLevel.setText(dataModel.getProperty());
              //  this.savelocation.setText(dataModel.getLocation());
               // this.savecommnets.setText(dataModel.getCmnt());
            }
        }
        this.spnBatten.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,this.BattenData));
        this.spnBatten.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spnBattenHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnEmr.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,this.EmergancyLights));
        this.spnEmr.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spnEmrHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnExitSign.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,this.ExitSignData));
        this.spnExitSign.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spnExitSignHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
      /*  this.spnTwin.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.Twindata));
        this.spnTwin.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spnTwinHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spn_er2cross14.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.emergancy2cross14));
        this.spn_er2cross14.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SaveActivity.this.spnErFourteen = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spn_er2cross18.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.emergancy2cross18));
        this.spn_er2cross18.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SaveActivity.this.spnErEighteen = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spn_er2cross28.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.emergancy2cross28));
        this.spn_er2cross28.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SaveActivity.this.spnErTwoEight = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spn_er2cross36.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.emergancy2cross36));
        this.spn_er2cross36.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SaveActivity.this.spnErThreeSix = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnQuick.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.Quickdata));
        this.spnQuick.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SaveActivity.this.spnQuickHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnBoxtype.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.BoxData));
        this.spnBoxtype.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SaveActivity.this.spnBoxtypeHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnJumbo.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.JumboData));
        this.spnJumbo.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SaveActivity.this.spnJumboHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnWeather.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.WeatherData));
        this.spnWeather.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SaveActivity.this.spnWeatherHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnBlad.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.BladeTypeData));
        this.spnBlad.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SaveActivity.this.spnBladHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spnSwingBlad.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.SwingBladData));
        this.spnSwingBlad.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SaveActivity.this.spnSwingBladHolder = (String) parent.getItemAtPosition(position);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/
        loadNames();
        this.broadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                SaveActivity.this.loadNames();
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));
        this.saveBtn.setOnClickListener(this);
        this.nextBtn.setOnClickListener(this);
        this.endBtn.setOnClickListener(this);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /* access modifiers changed from: private */
    private void loadNames() {
        names.clear();
        Cursor cursor = databaseHelper.getNames();
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
        int id = v.getId();
        if (id == R.id.save_endBtn) {
            startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
        } else if (id == R.id.save_saveBtn) {
            insertData();
        }
    }

    public void insertData() {
        this.progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        this.progressDialog.show();
        GetValueFromEditText();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //if there is a success
                                //storing the name to sqlite with status synced
                                saveNameToLocalStorage(siteNameHolder,assetNumberHolder,spnBattenHolder,spnEmrHolder,spnExitSignHolder,locationHolder,propertyLevelHolder,cmntHolder, NAME_SYNCED_WITH_SERVER);
                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                saveNameToLocalStorage(siteNameHolder,assetNumberHolder,spnBattenHolder,spnEmrHolder,spnExitSignHolder,locationHolder,propertyLevelHolder,cmntHolder, NAME_NOT_SYNCED_WITH_SERVER);
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
                        saveNameToLocalStorage(siteNameHolder,assetNumberHolder,spnBattenHolder,spnEmrHolder,spnExitSignHolder,locationHolder,propertyLevelHolder,cmntHolder, NAME_NOT_SYNCED_WITH_SERVER);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(DatabaseHelper.COLUMN_NAME, SaveActivity.this.siteNameHolder);
                params.put(DatabaseHelper.ASSET_NUMBER, SaveActivity.this.assetNumberHolder);
                params.put(DatabaseHelper.BATTEN, spnBattenHolder);
                params.put(DatabaseHelper.EMERGENCY,spnEmrHolder);
                params.put(DatabaseHelper.EXITSIGN, spnExitSignHolder);

                params.put(DatabaseHelper.LOCATION, SaveActivity.this.locationHolder);
                params.put(DatabaseHelper.PROPERTY_LEVEL, SaveActivity.this.propertyLevelHolder);
                params.put(DatabaseHelper.COMMENTS, SaveActivity.this.cmntHolder);
                return params;


            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void GetValueFromEditText() {
        this.siteNameHolder = this.savesitename.getText().toString().trim();
        this.assetNumberHolder = this.saveassetNumber.getText().toString().trim();
        this.locationHolder = this.savelocation.getText().toString().trim();
        this.propertyLevelHolder = this.savepropertyLevel.getText().toString().trim();
        this.cmntHolder = this.savecommnets.getText().toString().trim();
    }

    /* access modifiers changed from: private */
    public void saveNameToLocalStorage(String siteNameHolder2, String assetNumberHolder2, String batten, String emergancy, String exitsign,String locationHolder2, String propertyLevelHolder2, String comment, int status) {

       // editTextName.setText("");
        databaseHelper.addNewName(siteNameHolder2,assetNumberHolder2,batten,emergancy,exitsign,locationHolder2,propertyLevelHolder2,cmntHolder, status);
        DataModel n = new DataModel(siteNameHolder2,assetNumberHolder2,batten,emergancy,exitsign,locationHolder2,propertyLevelHolder2,cmntHolder, status);
        names.add(n);
        refreshList();
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
}
