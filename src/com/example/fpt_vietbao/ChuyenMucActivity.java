package com.example.fpt_vietbao;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

public class ChuyenMucActivity extends Fragment {
	ImageView batdongsang;
	ImageView bongda;
	ImageView giaoduc;
	ImageView khcn;
	ImageView khtn;
	ImageView kinhte;
	ImageView phapluat;
	ImageView suckhoe;
	ImageView thegioianh;
	ImageView xahoi;
	ImageView vanhoa;
	ImageView thegioi;
	ScrollView scrollView;
	SQLiteDatabase database;
	String checkTheme = " ";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_chuyenmuc,container, false);
		// them linkvieo
		createOrOpentDB();
		batdongsang = (ImageView) rootView.findViewById(R.id.imbatdongsan);
		bongda = (ImageView) rootView.findViewById(R.id.imbongda);
		giaoduc = (ImageView) rootView.findViewById(R.id.imgiaoduc);
		khcn = (ImageView) rootView.findViewById(R.id.imkhoahoccongnhe);
		khtn = (ImageView) rootView.findViewById(R.id.imkhoahoctunhien);
		kinhte = (ImageView) rootView.findViewById(R.id.imkinhte);
		phapluat = (ImageView) rootView.findViewById(R.id.imphapluat);
		suckhoe = (ImageView) rootView.findViewById(R.id.imsuckhoe);
		thegioianh = (ImageView) rootView.findViewById(R.id.imthegioianh);
		xahoi = (ImageView) rootView.findViewById(R.id.imxahoi);
		vanhoa = (ImageView) rootView.findViewById(R.id.imvanhoa);
		thegioi = (ImageView)rootView.findViewById(R.id.imthegioi);
		scrollView =(ScrollView)rootView.findViewById(R.id.chuyenmuc);
		// check theme
				Cursor c3 = database.query("tbTheme", null, null, null, null, null,null);
				c3.moveToFirst();
				while (c3.isAfterLast() == false) {
					checkTheme = c3.getString(0);
					c3.moveToNext();
				}
				c3.close();
			    // doi theme
				if (checkTheme.equals("bandem")) {
					scrollView.setBackgroundColor(Color.BLACK);
				} else {
					scrollView.setBackgroundColor(Color.WHITE);
				}
		// hanh dong nut
		batdongsang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NDBatDonSanActivity.class);
				startActivity(intent);
			}
		});
		bongda.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NDBongDaActivity.class);
				startActivity(intent);
			}
		});
		giaoduc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NDGiaoDucActivity.class);
				startActivity(intent);
			}
		});
		khcn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NDKHCNActivity.class);
				startActivity(intent);
			}
		});
		khtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NDKHTNActivity.class);
				startActivity(intent);
			}
		});
		kinhte.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NDKinhTeActivity.class);
				startActivity(intent);
			}
		});
		phapluat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NDPhapLuatActivity.class);
				startActivity(intent);
			}
		});
		suckhoe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NDSucKhoeActivity.class);
				startActivity(intent);
			}
		});
		thegioianh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NDTheGioiAnhActivity.class);
				startActivity(intent);
			}
		});
		vanhoa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NDVanHoaActivity.class);
				startActivity(intent);
			}
		});
		xahoi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NDXaHoiActivity.class);
				startActivity(intent);
			}
		});
		thegioi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NDTheGioi.class);
				startActivity(intent);
			}
		});
		return rootView;
	}
	// khoi tao data
	public void createOrOpentDB() {
		database = getActivity().openOrCreateDatabase("QLWEB",android.content.Context.MODE_PRIVATE, null);
	}
}
