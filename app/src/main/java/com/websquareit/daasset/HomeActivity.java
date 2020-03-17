package com.websquareit.daasset;

import android.content.Intent;
import android.os.Bundle;
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

    public void onBackPressed() {
        finish();
    }
}
