package com.example.fpt_vietbao;
import java.util.ArrayList;
import java.util.HashMap;

import android.R.color;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TinNongAcitivity extends Fragment {
	// khai bao
	ArrayList<String> arr = new ArrayList<String>();
	ArrayList<String> arrLink = new ArrayList<String>();
	ArrayAdapter<String> adapter = null;
	SQLiteDatabase database;
	ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
	SimpleAdapter adapter2;
	String tieude = "tieude";
	String noidung = "noidung";
	String thoigian = "thoigian";
	String stt = "sott";
	ListView hienthi;
	TextView de;
	String check = " ";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_tinnong, container,false);
		createOrOpentDB();
		hienthi = (ListView) rootView.findViewById(R.id.lvtinnong);
		String[] from = { tieude, noidung, thoigian ,stt};
		int[] to = { R.id.tinvan, R.id.noidung, R.id.thoigian, R.id.txtStt };
		redDataofTable();
		adapter2 = new SimpleAdapter(getActivity(), array,R.layout.custom_tinnong, from, to);
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
			getActivity().setTheme(android.R.style.Theme_DeviceDefault);
		} else {
			getActivity().setTheme(android.R.style.Theme_Light);
		}
				
		// link
		hienthi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				final Object[] arrL = arrLink.toArray();
				Intent intent = new Intent(getActivity(), HienThiWeb.class);
				intent.putExtra("linkweb", arrL[arg2].toString());
				startActivity(intent);
				
			}
		});
		return rootView;
	}
	// khoi tao data va mo data
	public void createOrOpentDB() {
		database = getActivity().openOrCreateDatabase("QLWEB",android.content.Context.MODE_PRIVATE, null);
	}

	public void redDataofTable() {
		hienthi.clearFocus();
		array.clear();
		arrLink.clear();
		Cursor c = database.query("tbWeb", null, null, null, null, null, null);
		c.moveToFirst();
		int i=0;
		while (c.isAfterLast() == false) {
			HashMap<String, String> temp = new HashMap<String, String>();
			i++;
			temp.put(tieude, c.getString(0));
			temp.put(noidung, c.getString(1));
			temp.put(thoigian, c.getString(3));
			temp.put(stt, i+"");
			array.add(temp);
			arrLink.add(c.getString(2));
			c.moveToNext();
		}
		c.close();
	}
}
