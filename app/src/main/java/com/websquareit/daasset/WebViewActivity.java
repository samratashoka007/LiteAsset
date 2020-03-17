package com.websquareit.daasset;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    String url = "http://lightassets.websquareit.com/";
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

          if (!checkPermission()) {
            openActivity();
        } else if (checkPermission()) {
            requestPermissionAndContinue();
        } else {
            openActivity();
        }
        //Runtime External storage permission for saving download files
    /*    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);
            }
        }*/



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
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle((CharSequence) getString(R.string.permission_necessary));
                alertBuilder.setMessage((int) R.string.storage_permission_is_encessary_to_wrote_event);
                alertBuilder.setPositiveButton("OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(WebViewActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 200);
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

        WebView view = (WebView) findViewById(R.id.webView);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(WebViewActivity.this);
        progressDialog.setMessage("Loading...Please Wait...");
        progressDialog.show();

        view.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progressDialog.dismiss();
            }

        });
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        view.setDownloadListener(new DownloadListener()
        {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimeType,
                                        long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));
                request.setMimeType(mimeType);
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading File...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
                                url, contentDisposition, mimeType));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
            }});
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}