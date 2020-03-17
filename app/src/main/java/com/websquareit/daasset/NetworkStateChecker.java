package com.websquareit.daasset;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkStateChecker extends BroadcastReceiver {

    //context and database helper object
    private Context context;
    private DatabaseHelper db;


    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        db = new DatabaseHelper(context);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //if there is a network
        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                //getting all the unsynced names
                Cursor cursor = db.getUnsyncedNames();
                if (cursor.moveToFirst()) {
                    do {
                        //calling the method to save the unsynced name to MySQL
                        saveName(
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.ASSET_NUMBER)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.BATTEN)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMERGENCY)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.EXITSIGN)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOCATION)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROPERTY_LEVEL)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COMMENTS))
                        );
                    } while (cursor.moveToNext());
                }
            }
        }


    }

    /*
     * method taking two arguments
     * name that is to be saved and id of the name from SQLite
     * if the name is successfully sent
     * we will update the status as synced in SQLite
     * */
    private void saveName(final int id ,final String name,final String asset,final String baten,
                          final String exit,final  String emer,final  String location,final String property,
                          final String cmnt) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, NewOneActivity.URL_SAVE_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //updating the status in sqlite
                                db.updateNameStatus(id, NewOneActivity.NAME_SYNCED_WITH_SERVER);

                                //sending the broadcast to refresh the list
                                context.sendBroadcast(new Intent(NewOneActivity.DATA_SAVED_BROADCAST));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


}
