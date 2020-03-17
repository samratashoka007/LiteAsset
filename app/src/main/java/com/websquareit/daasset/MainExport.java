package com.websquareit.daasset;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainExport extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;
    Button offlineData;
    Button onlinedata;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_export);
        /*if (!checkPermission()) {
            openActivity();
        } else if (checkPermission()) {
            requestPermissionAndContinue();
        } else {
            openActivity();
        }*/
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        this.offlineData = (Button) findViewById(R.id.offline);
        this.onlinedata = (Button) findViewById(R.id.online);
        this.offlineData.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainExport.this.startActivity(new Intent(MainExport.this.getApplicationContext(), OfflineData.class));
            }
        });
        this.onlinedata.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               /* MainExport.this.startActivity(new Intent(MainExport.this.getApplicationContext(), OnlineData.class));*/

                Intent WebView=new Intent(getApplicationContext(),WebViewActivity.class);
                startActivity(WebView);
            }
        });
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 || ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0) ? false : true;
    }

    private void requestPermissionAndContinue() {
        String str = "android.permission.WRITE_EXTERNAL_STORAGE";
        if (ContextCompat.checkSelfPermission(this, str) != 0) {
            String str2 = "android.permission.READ_EXTERNAL_STORAGE";
            if (ContextCompat.checkSelfPermission(this, str2) != 0) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, str) || !ActivityCompat.shouldShowRequestPermissionRationale(this, str2)) {
                    ActivityCompat.requestPermissions(this, new String[]{str, str2}, 200);
                    return;
                }
                Builder alertBuilder = new Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle((CharSequence) getString(R.string.permission_necessary));
                alertBuilder.setMessage((int) R.string.storage_permission_is_encessary_to_wrote_event);
                alertBuilder.setPositiveButton("OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainExport.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 200);
                    }
                });
                alertBuilder.create().show();
                Log.e("", "permission denied, show dialog");
                return;
            }
        }
        openActivity();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != 200) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } else if (permissions.length <= 0 || grantResults.length <= 0) {
            finish();
        } else {
            boolean flag = true;
            for (int i : grantResults) {
                if (i != 0) {
                    flag = false;
                }
            }
            if (flag) {
                openActivity();
            } else {
                finish();
            }
        }
    }

    private void openActivity() {
    }

        public boolean onSupportNavigateUp() {
            onBackPressed();
            return true;
        }
}
