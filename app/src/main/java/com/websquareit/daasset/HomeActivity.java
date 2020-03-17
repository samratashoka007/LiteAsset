package com.websquareit.daasset;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnClickListener {
    Button btn_export;
    Button btn_export1;
    Button btn_new;
    Button btn_save;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new LongOperation().execute("");
        setContentView((int) R.layout.activity_home);
        this.btn_new = (Button) findViewById(R.id.home_new);
        this.btn_save = (Button) findViewById(R.id.home_save);
        this.btn_export = (Button) findViewById(R.id.home_export);
        this.btn_export1 = (Button) findViewById(R.id.home_export1);
        this.btn_new.setOnClickListener(this);
        this.btn_save.setOnClickListener(this);
        this.btn_export1.setOnClickListener(this);
        this.btn_export.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_export /*2131296369*/:
                startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
                break;
            case R.id.home_new /*2131296371*/:
                startActivity(new Intent(getApplicationContext(), NewOneActivity.class));
                break;
            case R.id.home_save /*2131296372*/:
                startActivity(new Intent(getApplicationContext(), SaveActivity.class));
                break;

        }
    }
    @Override
    protected void onStart() {

        super.onStart();

        //Handler handler = new Handler();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Write whatever to want to do after delay specified (1 sec)
                Log.d("Handler", "Running Handler");
            }
        }, 1000);

    }
   /* private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            for(int i=0;i<5;i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.output);
            //txt.setText("Executed"); // txt.setText(result);
            //might want to change "executed" for the returned string passed into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }*/

    public void onBackPressed() {
        finish();
    }
}
