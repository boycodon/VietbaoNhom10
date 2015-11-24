package com.example.fpt_vietbao;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("NewApi")
public class HienThiWeb extends Activity {
	// khai bao
	WebView webView;
	SQLiteDatabase database;
	ProgressDialog progressDialog;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hienthiweb);
		// doc tao data
		createOrOpentDB();
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(getWallpaper());
		// luu ngon ngu vao dÃ¢t
				String check = " ";
				Cursor c = database.query("tbNgonNgu", null, null, null, null, null,
						null);
				c.moveToFirst();
				while (c.isAfterLast() == false) {
					check = c.getString(0);
					c.moveToNext();
				}
				c.close();
				if (check.equals("tiengviet")) {
					getActionBar().setTitle("Quay lại");
				} else {
					getActionBar().setTitle("Back");
				}
		getActionBar().setIcon(R.drawable.iconback);
		// doc data
		Intent data = getIntent();
		String url = data.getStringExtra("linkweb");
		webView = (WebView) findViewById(R.id.hienthiWeb);
		WebSettings webSettings = webView.getSettings();
		webSettings.setBuiltInZoomControls(true);
		// hanh dong thong bao
		progressDialog = new ProgressDialog(HienThiWeb.this);
		progressDialog.setMessage("Loading ...");
		progressDialog.show();
		// hien thi web
		webView.loadUrl(url);
		webView.requestFocus();
		data.clone();
	}
	public void createOrOpentDB() {
		database = openOrCreateDatabase("QLWEB",MODE_PRIVATE, null);
	}
}