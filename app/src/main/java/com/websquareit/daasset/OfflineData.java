package com.websquareit.daasset;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.websquareit.daasset.adapter.OfflineAdapter;

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

public class OfflineData extends Activity {
    ArrayList<DataModel> DataModelList = new ArrayList<>();
    ArrayList<String> ID_ArrayList = new ArrayList<>();
    ListView LISTVIEW;

    OfflineAdapter ListAdapter;
    ArrayList<String> NAME_ArrayList = new ArrayList<>();
    ArrayList<String> PHONE_NUMBER_ArrayList = new ArrayList<>();
    SQLiteDatabase SQLITEDATABASE;
    DatabaseHelper SQLITEHELPER;
    ArrayList<String> SUBJECT_ArrayList = new ArrayList<>();
    ArrayList<String> SampleArrayList = new ArrayList<>();
    String[] array;
    Cursor cursor;
    StringBuilder data = new StringBuilder();
    EditText editText;
    Button exportCSV;
    Button exportXls;
    SearchView mySearchView = null;
    SearchView searchView;
    DataModel student;
    Toolbar toolbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_main_layout);
        this.toolbar = (Toolbar) findViewById(R.id.oflineexporttool);
        this.toolbar.setTitle((int) R.string.app_name);
        this.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        this.toolbar.setNavigationIcon((int) R.drawable.back);
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OfflineData offlineData = OfflineData.this;
                offlineData.startActivity(new Intent(offlineData.getApplicationContext(), HomeActivity.class));
            }
        });
        this.LISTVIEW = (ListView) findViewById(R.id.listViewNames);
        this.editText = (EditText) findViewById(R.id.edittext1);
        this.exportXls = (Button) findViewById(R.id.buttonexportxls);
        this.exportCSV = (Button) findViewById(R.id.buttonexportcsv);
        this.SQLITEHELPER = new DatabaseHelper(this);
        this.editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {
                OfflineData.this.ListAdapter.getFilter().filter(stringVar.toString());
            }
        });
        this.exportXls.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OfflineData.this.Excel();
            }
        });
        this.exportCSV.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OfflineData.this.ExportCSVData();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        ShowSQLiteDBdata();
        super.onResume();
    }

    private void ShowSQLiteDBdata() {
        this.SQLITEDATABASE = this.SQLITEHELPER.getWritableDatabase();
        this.cursor = this.SQLITEDATABASE.rawQuery("SELECT * FROM SiteDetails", null);
        this.ID_ArrayList.clear();
        this.NAME_ArrayList.clear();
        this.PHONE_NUMBER_ArrayList.clear();
        this.SUBJECT_ArrayList.clear();
        this.DataModelList = new ArrayList<>();
        if (this.cursor.moveToFirst()) {
            do {
                /*Cursor cursor2 = this.cursor;
                String tempNumber = cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String str = tempNumber;
                Cursor cursor3 = this.cursor;
                String string = cursor3.getString(cursor3.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                Cursor cursor4 = this.cursor;
                String string2 = cursor4.getString(cursor4.getColumnIndex(DatabaseHelper.ASSET_NUMBER));
                Cursor cursor5 = this.cursor;
                String string3 = cursor5.getString(cursor5.getColumnIndex(DatabaseHelper.TWIN_SPITFIRE));
                Cursor cursor6 = this.cursor;
                String string4 = cursor6.getString(cursor6.getColumnIndex(DatabaseHelper.EMERGENCY_LIGHTS_T5_2X14W));
                Cursor cursor7 = this.cursor;
                String string5 = cursor7.getString(cursor7.getColumnIndex(DatabaseHelper.EMERGENCY_LIGHTS_T8_2X18W));
                Cursor cursor8 = this.cursor;
                String string6 = cursor8.getString(cursor8.getColumnIndex(DatabaseHelper.EMERGENCY_LIGHTS_T5_2X28W));
                Cursor cursor9 = this.cursor;
                String string7 = cursor9.getString(cursor9.getColumnIndex(DatabaseHelper.EMERGENCY_LIGHTS_T8_2X36W));
                Cursor cursor10 = this.cursor;
                String string8 = cursor10.getString(cursor10.getColumnIndex(DatabaseHelper.EXIT_QUICK_FIT));
                Cursor cursor11 = this.cursor;
                String string9 = cursor11.getString(cursor11.getColumnIndex(DatabaseHelper.EXIT_BOX_TYPE));
                Cursor cursor12 = this.cursor;
                String string10 = cursor12.getString(cursor12.getColumnIndex(DatabaseHelper.EXIT_BLADE_TYPE));
                Cursor cursor13 = this.cursor;
                String string11 = cursor13.getString(cursor13.getColumnIndex(DatabaseHelper.EXIT_SWINGBLADE_TYPE));
                Cursor cursor14 = this.cursor;
                String str2 = tempNumber;
                String tempExitWeather = cursor14.getString(cursor14.getColumnIndex(DatabaseHelper.EXIT_WEATHERPROOF));
                String str3 = tempExitWeather;
                Cursor cursor15 = this.cursor;
                String str4 = tempExitWeather;
                String tempJumbo = cursor15.getString(cursor15.getColumnIndex(DatabaseHelper.EXIT_JUMBO));
                String str5 = tempJumbo;
                Cursor cursor16 = this.cursor;
                String str6 = tempJumbo;
                String tempLocation = cursor16.getString(cursor16.getColumnIndex(DatabaseHelper.LOCATION));
                String str7 = tempLocation;
                Cursor cursor17 = this.cursor;
                String str8 = tempLocation;
                String tempProperty = cursor17.getString(cursor17.getColumnIndex(DatabaseHelper.PROPERTY_LEVEL));
                String str9 = tempProperty;
                Cursor cursor18 = this.cursor;
                String str10 = tempProperty;*/
                String tempNumber=(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                String tempSiteName=(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
                String tempassetno=(cursor.getString(cursor.getColumnIndex(DatabaseHelper.ASSET_NUMBER)));
                String tempBatten=(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BATTEN)));
                String tempEmergancy=(cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMERGENCY)));
                String tempExitSign=(cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXITSIGN)));
                String tempProperty=(cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROPERTY_LEVEL)));
                String tempCmment=(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COMMENTS)));
                String tempLocation=(cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOCATION)));



                //    DataModel dataModel = r3;
                DataModel dataModel2 = new DataModel(tempNumber,tempSiteName,tempassetno,
                        tempBatten, tempEmergancy,tempExitSign, tempProperty, tempCmment, tempLocation);
                this.student = dataModel2;
                this.DataModelList.add(this.student);
                this.data.append("Id,SiteName,Date,AssetNumber ,Twin/Spitfire,EmergancyLightsT5_2cross14,EmergancyLightsT5_2cross18'EmergancyLightsT5_2cross28,EmergancyLightsT5_2cross36,ExitQuickFit,EcitBoxType,ExitSwingBladeLevel,ExitWeatherProof,PropertyLevel,Comments,ExitJumbo,Location");
                StringBuilder sb = this.data;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("\n");
                sb2.append(this.student.getId());
                String str11 = ",";
                sb2.append(str11);
                sb2.append(this.student.getSiteName());
                sb2.append(str11);
                sb2.append(this.student.getAssetNo());
                sb2.append(str11);
                sb2.append(this.student.getTwin());
                sb2.append(str11);
                sb2.append(this.student.getEronefour());
                sb2.append(str11);
                sb2.append(this.student.getEroneeight());
                sb2.append(str11);
                sb2.append(this.student.getErtwoeight());
                sb2.append(str11);
                sb2.append(this.student.getErthreesix());
                sb2.append(str11);
                sb2.append(this.student.getQuickfit());
                sb2.append(str11);
                sb2.append(this.student.getBoxtype());
                sb2.append(str11);
                sb2.append(this.student.getBladetype());
                sb2.append(str11);
                sb2.append(this.student.getSwingtype());
                sb2.append(str11);
                sb2.append(this.student.getWeatherlevel());
                sb2.append(str11);
                sb2.append(this.student.getProperty());
                sb2.append(str11);
                sb2.append(this.student.getCmnt());
                sb2.append(str11);
                sb2.append(this.student.getJumbo());
                sb2.append(str11);
                sb2.append(this.student.getLocation());
                sb.append(sb2.toString());
            } while (this.cursor.moveToNext());
        }
        this.ListAdapter = new OfflineAdapter(this, R.layout.offlinelayout, this.DataModelList);
        this.LISTVIEW.setAdapter(this.ListAdapter);
        this.cursor.close();
    }

    private void Excel(){
        File sdCard = Environment.getExternalStorageDirectory();

        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "offlineData.xls";

        File directory = new File (sdCard.getAbsolutePath() + "/LIGHTASSET");
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {

            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("userList", 0);
            // column and row
            sheet.addCell(new Label(0, 0, "Id"));
            sheet.addCell(new Label(1, 0, "SiteName"));
            sheet.addCell(new Label(2, 0, "AssetNumber"));
            sheet.addCell(new Label(3, 0, "TWIN_SPITFIRE"));
            sheet.addCell(new Label(4, 0, "EMERGENCY_LIGHTS_T5_2X14W"));
            sheet.addCell(new Label(5, 0, "EMERGENCY_LIGHTS_T8_2X18W"));
            sheet.addCell(new Label(6, 0, "EMERGENCY_LIGHTS_T5_2X28W"));
            sheet.addCell(new Label(7, 0, "EMERGENCY_LIGHTS_T8_2X36W"));
            sheet.addCell(new Label(8, 0, "EXIT_QUICK_FIT"));
            sheet.addCell(new Label(9, 0, "EXIT_BOX_TYPE"));
            sheet.addCell(new Label(10, 0, "EXIT_BLADE_TYPE"));
            sheet.addCell(new Label(11, 0, "EXIT_SWINGBLADE_TYPE"));
            sheet.addCell(new Label(12, 0, "EXIT_WEATHERPROOF"));
            sheet.addCell(new Label(13, 0, "EXIT_JUMBO"));
            sheet.addCell(new Label(14, 0, "LOCATION"));
            sheet.addCell(new Label(15, 0, "PROPERTY_LEVEL"));
            sheet.addCell(new Label(16, 0, "COMMENTS"));

            cursor = SQLITEDATABASE.rawQuery("SELECT * FROM SiteDetails", null);

            if (cursor.moveToFirst()) {
                do {
                    String tempNumber = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));

                    String tempSiteName= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                    String tempAssetNumber= cursor.getString(cursor.getColumnIndex(DatabaseHelper.ASSET_NUMBER));
                    String tempTwin= cursor.getString(cursor.getColumnIndex(DatabaseHelper.TWIN_SPITFIRE));
                    String temoer2cross14= cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMERGENCY_LIGHTS_T5_2X14W));
                    String temper2cross18= cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMERGENCY_LIGHTS_T8_2X18W));
                    String temper2cross28= cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMERGENCY_LIGHTS_T5_2X28W));
                    String temper2cross36= cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMERGENCY_LIGHTS_T8_2X36W));
                    String tempExitQuick= cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXIT_QUICK_FIT));
                    String tempBox= cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXIT_BOX_TYPE));
                    String tempBlad= cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXIT_BLADE_TYPE));
                    String tempSwingBlad= cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXIT_SWINGBLADE_TYPE));
                    String tempExitWeather= cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXIT_WEATHERPROOF));
                    String tempJumbo= cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXIT_JUMBO));
                    String tempLocation= cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOCATION));
                    String tempProperty= cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROPERTY_LEVEL));
                    String tempCmnt= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COMMENTS));

                    int i = cursor.getPosition() + 1;
                    sheet.addCell(new Label(0, i, tempNumber));
                    sheet.addCell(new Label(1, i, tempSiteName));
                    sheet.addCell(new Label(2, i, tempAssetNumber));
                    sheet.addCell(new Label(3, i, tempTwin));
                    sheet.addCell(new Label(4, i, temoer2cross14));
                    sheet.addCell(new Label(5, i, temper2cross18));
                    sheet.addCell(new Label(6, i, temper2cross28));
                    sheet.addCell(new Label(7, i, temper2cross36));
                    sheet.addCell(new Label(8, i, tempExitQuick));
                    sheet.addCell(new Label(9, i, tempBox));
                    sheet.addCell(new Label(10, i, tempBlad));
                    sheet.addCell(new Label(11, i, tempSwingBlad));
                    sheet.addCell(new Label(12, i, tempExitWeather));
                    sheet.addCell(new Label(13, i, tempJumbo));
                    sheet.addCell(new Label(14, i, tempLocation));
                    sheet.addCell(new Label(15, i, tempProperty));
                    sheet.addCell(new Label(16, i, tempCmnt));
                } while (cursor.moveToNext());
            }

            //closing cursor
            cursor.close();
            workbook.write();
            workbook.close();
            Toast.makeText(getApplication(),
                    "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
        }  catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void ExportCSVData() {
        File sdCard = Environment.getExternalStorageDirectory();




        File directory = new File (sdCard.getAbsolutePath() + "/LIGHTASSET");
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try{
            //saving the file into device
            FileOutputStream out = openFileOutput("offlineData.csv", Context.MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();

            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "offlineData.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.myapplication.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
}
