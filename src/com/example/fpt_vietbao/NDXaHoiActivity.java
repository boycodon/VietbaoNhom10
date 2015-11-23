package com.example.fpt_vietbao;

import java.util.ArrayList;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class NDXaHoiActivity extends Activity {
	// khai bao
	SQLiteDatabase database;
	ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
	ArrayList<String> arrLink = new ArrayList<String>();
	SimpleAdapter adapter2;
	String tieude="tieude";
	String noidung= "noidung";
	String thoigian="thoigian";
	ListView hienthi;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_noidungtin);
	// doc tao data
	createOrOpentDB();
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
				getActionBar().setTitle("Xã Hội");
			} else {
				getActionBar().setTitle("Society");
			}
	getActionBar().setIcon(R.drawable.iconback);
	getActionBar().setBackgroundDrawable(getWallpaper());
	hienthi = (ListView)findViewById(R.id.lvnoidungtin);
	String[] from ={tieude,noidung,thoigian};
	int [] to = {R.id.tinvan,R.id.noidung,R.id.thoigian};
	adapter2 = new SimpleAdapter(NDXaHoiActivity.this,array,R.layout.custom_tinnong,from,to);
	hienthi.setAdapter(adapter2);
	// doc theme
			Cursor c2 = database.query("tbTheme", null, null, null, null, null,null);
			c2.moveToFirst();
			while (c2.isAfterLast() == false) {
				check = c2.getString(0);
				c2.moveToNext();
			}
			c2.close();
			// doi theme
			if (check.equals("bandem")) {
				hienthi.setBackgroundColor(Color.BLACK);
				NDXaHoiActivity.this.setTheme(android.R.style.Theme_DeviceDefault);
			} else {
				getBaseContext().setTheme(android.R.style.Theme_Light);
			}
	redDataofTable();
	hienthi.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			final Object[] arrL = arrLink.toArray();
			Intent intent = new Intent(getBaseContext(), HienThiWeb.class);
			intent.putExtra("linkweb", arrL[arg2].toString());
			startActivity(intent);
		}
	});
}

// khoi tao data va mo data
public void createOrOpentDB() {
	database = openOrCreateDatabase("QLWEB",MODE_PRIVATE, null);
}

	public void redDataofTable() {
		hienthi.clearFocus();
		array.clear();
		arrLink.clear();
		Cursor c = database.query("tbXaHoi", null, null, null, null, null, null);
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put(tieude, c.getString(0));
			temp.put(noidung, c.getString(1));
			temp.put(thoigian, c.getString(3));
			array.add(temp);
			arrLink.add(c.getString(2));
			c.moveToNext();
			adapter2.notifyDataSetChanged();
		}
		c.close();
	}
}

