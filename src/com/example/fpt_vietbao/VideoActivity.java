package com.example.fpt_vietbao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends Fragment {
	// khai bao
	ProgressDialog progressDialog;
	VideoView videoview;
	ListView listView;
	SQLiteDatabase database;
	Spinner spinner;
	// video
	// video
	ArrayList<String> arrayTitle = new ArrayList<String>();
	ArrayList<String> arrayLink = new ArrayList<String>();
	ArrayAdapter<String> adapterList = null;
	ArrayAdapter<String> adapterSprin = null;
	// chay media
	MediaController mediacontroller;
	private Context context = null;
	private TextView danhsach;
	private TextView menuVideo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_video,
				container, false);
		// them linkvieo
		createOrOpentDB();

		// khai bao
		final String loading;
		spinner = (Spinner) rootView.findViewById(R.id.spVideo);
		listView = (ListView) rootView.findViewById(R.id.listVideo);
		danhsach = (TextView) rootView.findViewById(R.id.tvListvideo);
		menuVideo = (TextView) rootView.findViewById(R.id.selectVideo);
		// kiem tra ngon ngu
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
			danhsach.setText("Danh sách video");
			menuVideo.setText("Chuyên mục");
			loading = "Đang tải Video";
			String[] arr2 = { "Thể thao", "Ký sự", "Quân sự", "Pháp luật" };
			adapterSprin = new ArrayAdapter<String>(getActivity(),R.layout.mysprint, arr2);
			spinner.setAdapter(adapterSprin);
		} else {
			danhsach.setText("List videos");
			menuVideo.setText("List");
			loading = "Loading Video";
			String[] arr2 = { "Sprort", "Engineer", "Military", "Bylaw" };
			adapterSprin = new ArrayAdapter<String>(getActivity(),R.layout.mysprint, arr2);
			spinner.setAdapter(adapterSprin);
		}
		adapterList = new ArrayAdapter<String>(getActivity(), R.layout.mylist,
				arrayTitle);
		listView.clearFocus();
		listView.setAdapter(adapterList);
		// check theme
		Cursor c3 = database.query("tbTheme", null, null, null, null, null,
				null);
		c3.moveToFirst();
		String checkTheme = " ";
		while (c3.isAfterLast() == false) {
			checkTheme = c3.getString(0);
			c3.moveToNext();
		}
		c3.close();
		// doi theme
		if (checkTheme.equals("bandem")) {
			spinner.setBackgroundColor(Color.BLACK);
			listView.setBackgroundColor(Color.BLACK);
			danhsach.setTextColor(Color.RED);
		} else {
			spinner.setBackgroundColor(Color.WHITE);
			listView.setBackgroundColor(Color.WHITE);
		}
		// hien thi sprin
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					new json().execute("http://uongdvph03464.890m.com/thethao.json");
					break;
				case 1:
					new json()
							.execute("http://uongdvph03464.890m.com/demo.json");
					break;
				case 2:
					new json()
							.execute("http://uongdvph03464.890m.com/quansu.json");
					break;
				case 3:
					new json()
							.execute("http://uongdvph03464.890m.com/phapluat.json");
					break;
				default:
					break;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// su kien loading
				progressDialog = new ProgressDialog(getActivity());
				progressDialog.setMessage(loading);
				progressDialog.show();
				try {
					context = getActivity();
					videoview = (VideoView) rootView
							.findViewById(R.id.videoview);
					Object[] arrL = arrayLink.toArray();
					String url = arrL[arg2].toString();
					final Uri uri = Uri.parse(url);
					// Tao su kien loa
					
					// hanh dong nut
					mediacontroller = new MediaController(getActivity());
					mediacontroller.setAnchorView(videoview);
					videoview.setMediaController(mediacontroller);
					videoview.setVideoURI(uri);
					videoview.requestFocus();
					videoview.start();
					// nang nghe su kien video
					videoview.setOnPreparedListener(new OnPreparedListener() {

						@Override
						public void onPrepared(MediaPlayer mp) {
							// TODO Auto-generated method stub
							progressDialog.dismiss();
						}
					});
					
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getActivity(), "Error connecting",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		return rootView;
	}

	public void createOrOpentDB() {
		database = getActivity().openOrCreateDatabase("QLWEB",
				android.content.Context.MODE_PRIVATE, null);
	}

	public class json extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			String chuoi = getxmlfromurl(params[0]);
			return chuoi;
		}
			
		@Override
		protected void onPostExecute(String result) {
			try {
				JSONObject root = new JSONObject(result);
				JSONArray arrroot = root.getJSONArray("chuyenmuc");
				arrayLink.clear();
				arrayTitle.clear();
				for (int i = 0; i < arrroot.length(); i++) {
					JSONObject son = arrroot.getJSONObject(i);
					arrayLink.add(son.getString("link"));
					arrayTitle.add(son.getString("title"));
				}
				adapterList.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}

		private String getxmlfromurl(String urlString) {
			String xml = null;
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(urlString);
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				xml = EntityUtils.toString(httpEntity, HTTP.UTF_8);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return xml;
		}
	}
	
}
