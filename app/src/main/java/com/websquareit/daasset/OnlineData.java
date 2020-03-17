package com.websquareit.daasset;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.websquareit.daasset.adapter.ListViewAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OnlineData extends AppCompatActivity {
    ArrayList<DataModel> CustomSubjectNamesList;
    Button Export;
    Button ExportCSV;
    String FinalJSonObject;
    String HTTP_URL = "http://lightassets.websquareit.com/index.php/welcome/fetch_data";
    Button button;
    StringBuilder data = new StringBuilder();
    EditText editText;
    ListView listView;
    ListViewAdapter listViewAdapter;
    ProgressBar progressBar;
    DataModel subject;

    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;

        public ParseJSonDataClass(Context context2) {
            this.context = context2;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... arg0) {
            String str = ",";
            try {
                if (OnlineData.this.FinalJSonObject != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(OnlineData.this.FinalJSonObject);
                        OnlineData.this.CustomSubjectNamesList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            OnlineData.this.subject = new DataModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            OnlineData.this.subject.id = jsonObject.getString(DatabaseHelper.COLUMN_ID);
                            OnlineData.this.subject.siteName = jsonObject.getString(DatabaseHelper.COLUMN_NAME);
                            OnlineData.this.subject.Date = jsonObject.getString(DatabaseHelper.DATETIME);
                            OnlineData.this.subject.assetNo = jsonObject.getString(DatabaseHelper.ASSET_NUMBER);
                            OnlineData.this.subject.twin = jsonObject.getString(DatabaseHelper.TWIN_SPITFIRE);
                            OnlineData.this.subject.eronefour = jsonObject.getString(DatabaseHelper.EMERGENCY_LIGHTS_T5_2X14W);
                            OnlineData.this.subject.eroneeight = jsonObject.getString(DatabaseHelper.EMERGENCY_LIGHTS_T8_2X18W);
                            OnlineData.this.subject.ertwoeight = jsonObject.getString(DatabaseHelper.EMERGENCY_LIGHTS_T5_2X28W);
                            OnlineData.this.subject.erthreesix = jsonObject.getString(DatabaseHelper.EMERGENCY_LIGHTS_T8_2X36W);
                            OnlineData.this.subject.quickfit = jsonObject.getString(DatabaseHelper.EXIT_QUICK_FIT);
                            OnlineData.this.subject.boxtype = jsonObject.getString(DatabaseHelper.EXIT_BOX_TYPE);
                            OnlineData.this.subject.bladetype = jsonObject.getString(DatabaseHelper.EXIT_BLADE_TYPE);
                            OnlineData.this.subject.swingtype = jsonObject.getString(DatabaseHelper.EXIT_SWINGBLADE_TYPE);
                            OnlineData.this.subject.weatherlevel = jsonObject.getString(DatabaseHelper.EXIT_WEATHERPROOF);
                            OnlineData.this.subject.property = jsonObject.getString(DatabaseHelper.PROPERTY_LEVEL);
                            OnlineData.this.subject.cmnt = jsonObject.getString(DatabaseHelper.COMMENTS);
                            OnlineData.this.subject.jumbo = jsonObject.getString(DatabaseHelper.EXIT_JUMBO);
                            OnlineData.this.subject.location = jsonObject.getString(DatabaseHelper.LOCATION);
                            String str2 = OnlineData.this.subject.siteName;
                            OnlineData.this.CustomSubjectNamesList.add(OnlineData.this.subject);
                            OnlineData.this.data.append("Id,SiteName,Date,AssetNumber,Twin/Spitfire,EmergancyLightsT5_2cross14,EmergancyLightsT5_2cross18'EmergancyLightsT5_2cross28,EmergancyLightsT5_2cross36,ExitQuickFit,EcitBoxType,ExitSwingBladeLevel,ExitWeatherProof,PropertyLevel,Comments,ExitJumbo,Location");
                            StringBuilder sb = OnlineData.this.data;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("\n");
                            sb2.append(OnlineData.this.subject.getId());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getSiteName());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getAssetNo());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getTwin());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getEronefour());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getEroneeight());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getErtwoeight());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getErthreesix());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getQuickfit());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getBoxtype());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getBladetype());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getSwingtype());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getWeatherlevel());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getProperty());
                            sb2.append("'");
                            sb2.append(OnlineData.this.subject.getCmnt());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getJumbo());
                            sb2.append(str);
                            sb2.append(OnlineData.this.subject.getLocation());
                            sb.append(sb2.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            OnlineData onlineData = OnlineData.this;
            onlineData.listViewAdapter = new ListViewAdapter(onlineData, R.layout.listview_items, onlineData.CustomSubjectNamesList);
            OnlineData.this.listView.setAdapter(OnlineData.this.listViewAdapter);
            OnlineData.this.progressBar.setVisibility(View.GONE);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_online_data);
        this.listView = (ListView) findViewById(R.id.listView1);
        this.Export = (Button) findViewById(R.id.buttonexportxls);
        this.ExportCSV = (Button) findViewById(R.id.buttonexportcsv);
        this.progressBar = (ProgressBar) findViewById(R.id.ProgressBar1);
        this.editText = (EditText) findViewById(R.id.edittext1);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        this.editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {
                OnlineData.this.listViewAdapter.getFilter().filter(stringVar.toString());
            }
        });
        this.Export.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
              //  OnlineData.this.Excel();
            }
        });
        this.ExportCSV.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
           //     OnlineData.this.ExportCSVData();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        DisplayListView();
        super.onResume();
    }

    public void DisplayListView() {
        this.progressBar.setVisibility(View.VISIBLE);
        Volley.newRequestQueue(this).add(new StringRequest(this.HTTP_URL, new Listener<String>() {
            public void onResponse(String response) {
                OnlineData onlineData = OnlineData.this;
                onlineData.FinalJSonObject = response;
                new ParseJSonDataClass(onlineData).execute(new Void[0]);
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OnlineData.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }));
    }

    /* access modifiers changed from: private */
   /* public void Excel() {
        WriteException e;
        IOException e2;
        Exception e3;
        JSONException e4;
        String str = DatabaseHelper.PROPERTY_LEVEL;
        String str2 = DatabaseHelper.LOCATION;
        String str3 = DatabaseHelper.EXIT_JUMBO;
        String str4 = DatabaseHelper.EXIT_WEATHERPROOF;
        String str5 = DatabaseHelper.EXIT_SWINGBLADE_TYPE;
        String str6 = DatabaseHelper.EXIT_BLADE_TYPE;
        String str7 = DatabaseHelper.EXIT_BOX_TYPE;
        String str8 = DatabaseHelper.EXIT_QUICK_FIT;
        String str9 = DatabaseHelper.EMERGENCY_LIGHTS_T8_2X36W;
        String str10 = DatabaseHelper.EMERGENCY_LIGHTS_T5_2X28W;
        String str11 = DatabaseHelper.EMERGENCY_LIGHTS_T8_2X18W;
        String str12 = DatabaseHelper.EMERGENCY_LIGHTS_T5_2X14W;
        String str13 = DatabaseHelper.TWIN_SPITFIRE;
        File sdCard = Environment.getExternalStorageDirectory();
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        String xlFIle = "onlineData.xls";
        String str14 = str;
        String str15 = str2;
        StringBuilder sb = new StringBuilder();
        String str16 = str3;
        sb.append(sdCard.getAbsolutePath());
        sb.append("/LIGHTASSET");
        File directory = new File(sb.toString());
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {
            File file = new File(directory, xlFIle);
            WorkbookSettings wbSettings = new WorkbookSettings();
            String str17 = xlFIle;

            try {
                wbSettings.setLocale(new Locale("en", "EN"));
                WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
                WritableSheet sheet = workbook.createSheet("userList", 0);
                File file8 = file;
                WorkbookSettings workbookSettings = wbSettings;
                sheet.addCell(new Label(0, 0, "Id"));
                WritableWorkbook workbook2 = workbook;
                sheet.addCell(new Label(1, 0, "SiteName"));
                sheet.addCell(new Label(2, 0, "AssetNumber"));
                sheet.addCell(new Label(3, 0, str13));
                sheet.addCell(new Label(4, 0, str12));
                sheet.addCell(new Label(5, 0, str11));
                sheet.addCell(new Label(6, 0, str10));
                sheet.addCell(new Label(7, 0, str9));
                sheet.addCell(new Label(8, 0, str8));
                sheet.addCell(new Label(9, 0, str7));
                sheet.addCell(new Label(10, 0, str6));
                sheet.addCell(new Label(11, 0, str5));
                sheet.addCell(new Label(12, 0, str4));
                String str18 = str16;
                sheet.addCell(new Label(13, 0, str18));
                String str19 = str18;
                String str20 = str15;
                sheet.addCell(new Label(14, 0, str20));
                String str21 = str20;
                String str22 = str14;
                sheet.addCell(new Label(15, 0, str22));
                String str23 = str22;
                sheet.addCell(new Label(16, 0, DatabaseHelper.COMMENTS));
                sheet.addCell(new Label(17, 0, DatabaseHelper.DATETIME));
                OnlineData onlineData = this;
                onlineData.subject = new DataModel();
                try {
                    if (onlineData.FinalJSonObject != null) {
                        try {
                            JSONArray jsonArray = new JSONArray(onlineData.FinalJSonObject);
                            try {
                                onlineData.CustomSubjectNamesList = new ArrayList<>();
                                int i = 0;
                                while (i < jsonArray.length()) {
                                    onlineData.subject = new DataModel();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    JSONArray jsonArray2 = jsonArray;
                                    try {
                                        WritableSheet sheet2 = sheet;
                                        try {
                                            onlineData.subject.f41id = jsonObject.getString(DatabaseHelper.COLUMN_ID);
                                            onlineData.subject.siteName = jsonObject.getString(DatabaseHelper.COLUMN_NAME);
                                            onlineData.subject.Date = jsonObject.getString(DatabaseHelper.DATETIME);
                                            onlineData.subject.assetNo = jsonObject.getString(DatabaseHelper.ASSET_NUMBER);
                                            onlineData.subject.twin = jsonObject.getString(str13);
                                            onlineData.subject.eronefour = jsonObject.getString(str12);
                                            onlineData.subject.eroneeight = jsonObject.getString(str11);
                                            onlineData.subject.ertwoeight = jsonObject.getString(str10);
                                            onlineData.subject.erthreesix = jsonObject.getString(str9);
                                            onlineData.subject.quickfit = jsonObject.getString(str8);
                                            onlineData.subject.boxtype = jsonObject.getString(str7);
                                            onlineData.subject.bladetype = jsonObject.getString(str6);
                                            onlineData.subject.swingtype = jsonObject.getString(str5);
                                            onlineData.subject.weatherlevel = jsonObject.getString(str4);
                                            String str24 = str23;
                                            int i2 = i;
                                            onlineData.subject.property = jsonObject.getString(str24);
                                            onlineData.subject.cmnt = jsonObject.getString(DatabaseHelper.COMMENTS);
                                            String str25 = str19;
                                            String str26 = str24;
                                            onlineData.subject.jumbo = jsonObject.getString(str25);
                                            String str27 = str21;
                                            String str28 = str25;
                                            onlineData.subject.location = jsonObject.getString(str27);
                                            onlineData.CustomSubjectNamesList.add(onlineData.subject);
                                            String tempNumber = jsonObject.getString(DatabaseHelper.COLUMN_ID);
                                            String tempSiteName = jsonObject.getString(DatabaseHelper.COLUMN_NAME);
                                            String tempAssetNumber = jsonObject.getString(DatabaseHelper.ASSET_NUMBER);
                                            String tempTwin = jsonObject.getString(str13);
                                            String temoer2cross14 = jsonObject.getString(str12);
                                            String temper2cross18 = jsonObject.getString(str11);
                                            String temper2cross28 = jsonObject.getString(str10);
                                            String temper2cross36 = jsonObject.getString(str9);
                                            String tempExitQuick = jsonObject.getString(str8);
                                            String tempBox = jsonObject.getString(str7);
                                            String tempBlad = jsonObject.getString(str6);
                                            String tempSwingBlad = jsonObject.getString(str5);
                                            String tempExitWeather = jsonObject.getString(str4);
                                            String str29 = str4;
                                            String str30 = str28;
                                            String tempJumbo = jsonObject.getString(str30);
                                            String tempLocation = jsonObject.getString(str27);
                                            str21 = str27;
                                            String str31 = str26;
                                            String tempProperty = jsonObject.getString(str31);
                                            String str32 = str31;
                                            String tempCmnt = jsonObject.getString(DatabaseHelper.COMMENTS);
                                            String str33 = str30;
                                            String tempDate = jsonObject.getString(DatabaseHelper.DATETIME);
                                            JSONObject jSONObject = jsonObject;
                                            int j = i2 + 1;
                                            String str34 = str5;
                                            String str35 = str6;
                                            WritableSheet sheet3 = sheet2;
                                            try {
                                                sheet3.addCell(new Label(0, j, tempNumber));
                                                String str36 = tempNumber;
                                                sheet3.addCell(new Label(1, j, tempSiteName));
                                                sheet3.addCell(new Label(2, j, tempAssetNumber));
                                                String str37 = tempAssetNumber;
                                                String tempAssetNumber2 = tempTwin;
                                                sheet3.addCell(new Label(3, j, tempAssetNumber2));
                                                String str38 = tempAssetNumber2;
                                                String temoer2cross142 = temoer2cross14;
                                                sheet3.addCell(new Label(4, j, temoer2cross142));
                                                String str39 = temoer2cross142;
                                                String temper2cross182 = temper2cross18;
                                                sheet3.addCell(new Label(5, j, temper2cross182));
                                                String str40 = temper2cross182;
                                                String temper2cross183 = temper2cross28;
                                                sheet3.addCell(new Label(6, j, temper2cross183));
                                                String str41 = temper2cross183;
                                                String temper2cross362 = temper2cross36;
                                                sheet3.addCell(new Label(7, j, temper2cross362));
                                                String str42 = temper2cross362;
                                                String temper2cross363 = tempExitQuick;
                                                sheet3.addCell(new Label(8, j, temper2cross363));
                                                String str43 = temper2cross363;
                                                String tempBox2 = tempBox;
                                                sheet3.addCell(new Label(9, j, tempBox2));
                                                String str44 = tempBox2;
                                                String tempBox3 = tempBlad;
                                                sheet3.addCell(new Label(10, j, tempBox3));
                                                String str45 = tempBox3;
                                                String tempSwingBlad2 = tempSwingBlad;
                                                sheet3.addCell(new Label(11, j, tempSwingBlad2));
                                                String str46 = tempSwingBlad2;
                                                String tempSwingBlad3 = tempExitWeather;
                                                sheet3.addCell(new Label(12, j, tempSwingBlad3));
                                                String str47 = tempSwingBlad3;
                                                String tempJumbo2 = tempJumbo;
                                                sheet3.addCell(new Label(13, j, tempJumbo2));
                                                String str48 = tempJumbo2;
                                                String tempJumbo3 = tempLocation;
                                                sheet3.addCell(new Label(14, j, tempJumbo3));
                                                String str49 = tempJumbo3;
                                                sheet3.addCell(new Label(15, j, tempProperty));
                                                sheet3.addCell(new Label(16, j, tempCmnt));
                                                sheet3.addCell(new Label(17, j, tempDate));
                                                i = i2 + 1;
                                                onlineData = this;
                                                sheet = sheet3;
                                                str23 = str32;
                                                jsonArray = jsonArray2;
                                                str4 = str29;
                                                str19 = str33;
                                                str5 = str34;
                                                str6 = str35;
                                            }
                                        } catch (JSONException e9) {
                                            WritableSheet writableSheet = sheet2;
                                            e4 = e9;
                                            e4.printStackTrace();
                                            workbook2.write();
                                            workbook2.close();
                                            Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_LONG).show();
                                        } catch (Exception e10) {
                                            WritableSheet writableSheet2 = sheet2;
                                            e3 = e10;
                                            e3.printStackTrace();
                                            workbook2.write();
                                            workbook2.close();
                                            Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e11) {
                                        WritableSheet writableSheet3 = sheet;
                                        e4 = e11;
                                        e4.printStackTrace();
                                        workbook2.write();
                                        workbook2.close();
                                        Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_LONG).show();
                                    }
                                }
                                WritableSheet writableSheet4 = sheet;
                                int i3 = i;
                            } catch (JSONException e12) {
                                JSONArray jSONArray = jsonArray;
                                WritableSheet writableSheet5 = sheet;
                                e4 = e12;
                                e4.printStackTrace();
                                workbook2.write();
                                workbook2.close();
                                Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e13) {
                            WritableSheet writableSheet6 = sheet;
                            e4 = e13;
                            e4.printStackTrace();
                            workbook2.write();
                            workbook2.close();
                            Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e14) {
                    WritableSheet writableSheet7 = sheet;
                    e3 = e14;
                    e3.printStackTrace();
                    workbook2.write();
                    workbook2.close();
                    Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_LONG).show();
                }
                workbook2.write();
                workbook2.close();
                Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_LONG).show();
            } catch (WriteException e15) {
                e = e15;
                e.printStackTrace();
            } catch (IOException e16) {
                e2 = e16;
                e2.printStackTrace();
            }
        } catch (WriteException e17) {
            String str50 = xlFIle;
            File file9 = directory;
            File file10 = sdCard;
            e = e17;
            e.printStackTrace();
        } catch (IOException e18) {
            String str51 = xlFIle;
            File file11 = directory;
            File file12 = sdCard;
            e2 = e18;
            e2.printStackTrace();
        }
    }*/

    /* access modifiers changed from: private */
/*    public void ExportCSVData() {
        String str = "onlineData.csv";
        File sdCard = Environment.getExternalStorageDirectory();
        StringBuilder sb = new StringBuilder();
        sb.append(sdCard.getAbsolutePath());
        sb.append("/LIGHTASSET");
        File directory = new File(sb.toString());
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {
            FileOutputStream out = openFileOutput(str, 0);
            out.write(this.data.toString().getBytes());
            out.close();
            Uri path = FileProvider.getUriForFile(getApplicationContext(), "com.example.myapplication.fileprovider", new File(getFilesDir(), str));
            Intent fileIntent = new Intent("android.intent.action.SEND");
            fileIntent.setType("text/csv");
            fileIntent.putExtra("android.intent.extra.SUBJECT", "Data");
            fileIntent.addFlags(1);
            fileIntent.putExtra("android.intent.extra.STREAM", path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
