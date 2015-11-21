package com.example.fpt_vietbao;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class KhoiDongAcitivity extends Activity {
	// khai bao
	ImageView khoidong;
	SQLiteDatabase database;
	Timer timer;
	TimerTask timerTask;
	final Handler handler = new Handler();
	ArrayList<String> arrTile = new ArrayList<String>();
	ArrayList<String> arrLinkTile = new ArrayList<String>();
	boolean check = true;
	// kiem tra mang
	BroadcastReceiver brNetwork = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();

			if (networkInfo != null && networkInfo.isConnected()) {
				check = true;
			} else {
				Toast.makeText(getBaseContext(), "Không có kết nối dữ liệu",
						Toast.LENGTH_SHORT).show();
				check = false;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_khoidong);
		// khoi tao data
		createOrOpentDB();
		createTable();
		new GetRSS().execute();
		new GetRSS2().execute();
		new GetRSSGD().execute();
		new GetRSSBDS().execute();
		new GetRSSBD().execute();
		new GetRSSKHCN().execute();
		new GetRSSKHTN().execute();
		new GetRSSKT().execute();
		new GetRSSPL().execute();
		new GetRSSSK().execute();
		new GetRSSTGA().execute();
		new GetRSSVH().execute();
		new GetRSSXH().execute();
		new GetRSSTG().execute();
		// them link video
		khoidong = (ImageView) findViewById(R.id.khoidong);
		// check mang
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
		registerReceiver(brNetwork, intentFilter);
		// doc link video
		khoidong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (check) {
					Intent intent = new Intent(getBaseContext(),
							MainActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.in_right, R.anim.out_right);

				} else {
					Toast.makeText(getBaseContext(),"Kiểm tra lại kết nối mạng", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	// load rss tinnoibat
	class GetRSS extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vietnamnet.vn/rss/tin-noi-bat.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("title").item(0)
							.getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("description").item(0)
							.getTextContent();
					String link = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("link").item(0)
							.getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("pubDate").item(0)
							.getTextContent();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbWeb2", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// khoi tao tin nong
	class GetRSS2 extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vietnamnet.vn/rss/moi-nong.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("title").item(0)
							.getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("description").item(0)
							.getTextContent();
					String link = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("link").item(0)
							.getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("pubDate").item(0)
							.getTextContent();
					Log.d("RSS", "Title: " + title);
					Log.d("RSS", "Link: " + link);
					Log.d("RSS", "Noidung: " + noidung.substring(0, 30) + "...");
					Log.d("RSS", "Thá»�i gian: " + pubDate);
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbWeb", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// doc rss giao duc
	class GetRSSGD extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vietnamnet.vn/rss/giao-duc.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("title").item(0)
							.getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("description").item(0)
							.getTextContent();
					String link = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("link").item(0)
							.getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("pubDate").item(0)
							.getTextContent();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbGiaoDuc", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// doc rss bds
	class GetRSSBDS extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vietnamnet.vn/rss/bat-dong-san.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("title").item(0)
							.getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("description").item(0)
							.getTextContent();
					String link = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("link").item(0)
							.getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("pubDate").item(0)
							.getTextContent();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbBatDongSan", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// doc rss bong da
	class GetRSSBD extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vnexpress.net/rss/the-thao.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("title").item(0)
							.getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("description").item(0)
							.getTextContent();
					String link = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("link").item(0)
							.getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("pubDate").item(0)
							.getTextContent();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbBongDa", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// doc rss khoa hoc cong nghe
	class GetRSSKHCN extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vietnamnet.vn/rss/khoa-hoc.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item).getElementsByTagName("title").item(0).getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item).getElementsByTagName("description").item(0).getTextContent();
					String link = ((org.w3c.dom.Element) n1Item).getElementsByTagName("link").item(0).getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item).getElementsByTagName("pubDate").item(0).getTextContent();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbKHCN", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// doc rss khoa hoc tu nhien
	class GetRSSKHTN extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vietnamnet.vn/rss/cong-nghe-thong-tin-vien-thong.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("title").item(0)
							.getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("description").item(0)
							.getTextContent();
					String link = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("link").item(0)
							.getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("pubDate").item(0)
							.getTextContent();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbKHTN", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// doc rss kinh te
	class GetRSSKT extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vietnamnet.vn/rss/kinh-te.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("title").item(0)
							.getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("description").item(0)
							.getTextContent();
					String link = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("link").item(0)
							.getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("pubDate").item(0)
							.getTextContent();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbKinhTe", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// doc rss phap luat
	class GetRSSPL extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vietnamnet.vn/rss/chinh-tri.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("title").item(0)
							.getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("description").item(0)
							.getTextContent();
					String link = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("link").item(0)
							.getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("pubDate").item(0)
							.getTextContent();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbPhapLuat", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// load rss suc khoe
	class GetRSSSK extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vietnamnet.vn/rss/doi-song.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("title").item(0)
							.getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("description").item(0)
							.getTextContent();
					String link = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("link").item(0)
							.getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("pubDate").item(0)
							.getTextContent();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbSucKhoe", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// load rss the gioi anh
	class GetRSSTGA extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vietnamnet.vn/rss/the-gioi-anh.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("title").item(0)
							.getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("description").item(0)
							.getTextContent();
					String link = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("link").item(0)
							.getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("pubDate").item(0)
							.getTextContent();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbTheGioiAnh", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// tai rss van hoa
	class GetRSSVH extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vietnamnet.vn/rss/van-hoa.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("title").item(0)
							.getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("description").item(0)
							.getTextContent();
					String link = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("link").item(0)
							.getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("pubDate").item(0)
							.getTextContent();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbVanHoa", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// tai rss xa hoi
	class GetRSSXH extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vietnamnet.vn/rss/xa-hoi.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("title").item(0)
							.getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("description").item(0)
							.getTextContent();
					String link = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("link").item(0)
							.getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item)
							.getElementsByTagName("pubDate").item(0)
							.getTextContent();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbXaHoi", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// tai rss the gioi
	class GetRSSTG extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				URL url = new URL("http://vietnamnet.vn/rss/quoc-te.rss");
				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(is);
				// doc rss
				Node nRss = doc.getElementsByTagName("rss").item(0);
				// doc channel
				org.w3c.dom.Element eRss = (org.w3c.dom.Element) nRss;
				Node nChannel = eRss.getElementsByTagName("channel").item(0);
				// doc item
				org.w3c.dom.Element eChannel = (org.w3c.dom.Element) nChannel;
				NodeList nItem = eChannel.getElementsByTagName("item");
				for (int i = 0; i < nItem.getLength(); i++) {
					Node n1Item = nItem.item(i);
					String title = ((org.w3c.dom.Element) n1Item).getElementsByTagName("title").item(0).getTextContent();
					String noidung = ((org.w3c.dom.Element) n1Item).getElementsByTagName("description").item(0).getTextContent();
					String link = ((org.w3c.dom.Element) n1Item).getElementsByTagName("link").item(0).getTextContent();
					String pubDate = ((org.w3c.dom.Element) n1Item).getElementsByTagName("pubDate").item(0).getTextContent();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("noidung", noidung.substring(0, 30) + "...");
					values.put("link", link);
					values.put("pubDate", pubDate);
					database.insert("tbTheGioi", null, values);
				}
			} catch (Exception e) {
				Log.e("RSS", "RSSDemoActivity.onClick" + e.getMessage());
			}
			return null;
		}
	}

	// khoi them ngon ngu mac dinh tieng viet
	// khoi tao data va mo data
	public void createOrOpentDB() {
		database = openOrCreateDatabase("QLWEB", MODE_APPEND, null);
	}
	// khoi tao bang
	public void createTable() {
		String sqlWeb2 = "create table if not exists tbWeb2 (title TEXT, noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWeb2);
		String sqlWeb = "create table if not exists tbWeb (title TEXT, noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWeb);
		String sqlWebGD = "create table if not exists tbGiaoDuc (title TEXT, noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWebGD);
		String sqlWebBDS = "create table if not exists tbBatDongSan (title TEXT, noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWebBDS);
		String sqlWebBD = "create table if not exists tbBongDa (title TEXT , noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWebBD);
		String sqlWebKHCN = "create table if not exists tbKHCN (title TEXT, noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWebKHCN);
		String sqlWebKHTN = "create table if not exists tbKHTN (title TEXT, noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWebKHTN);
		String sqlWebKT = "create table if not exists tbKinhTe (title TEXT, noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWebKT);
		String sqlWebPL = "create table if not exists tbPhapLuat (title TEXT, noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWebPL);
		String sqlWebSK = "create table if not exists tbSucKhoe (title TEXT, noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWebSK);
		String sqlWebTGA = "create table if not exists tbTheGioiAnh (title TEXT, noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWebTGA);
		String sqlWebVH = "create table if not exists tbVanHoa (title TEXT, noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWebVH);
		String sqlWebXH = "create table if not exists tbXaHoi (title TEXT, noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWebXH);
		String sqlWebTG = "create table if not exists tbTheGioi (title TEXT, noidung TEXT , link TEXT PRIMARY KEY, pubDate TEXT)";
		database.execSQL(sqlWebTG);
		String sqlNgonngu = "create table if not exists tbNgonNgu (ngonngu TEXT)";
		database.execSQL(sqlNgonngu);
		String sqlTheme = "create table if not exists tbTheme (theme TEXT)";
		database.execSQL(sqlTheme);
		String sqlTaiKhoan = "create table if not exists tbTaiKhoan (tentk TEXT,  matkhau TEXT)";
		database.execSQL(sqlTaiKhoan);

	}

	// doc data
	public void redDataofTable() {
		arrLinkTile.clear();
		arrTile.clear();
		Cursor c = database.query("tbWeb", null, null, null, null, null, null);
		c.moveToFirst();
		while (c.isAfterLast() == false) 
		{
			arrTile.add(c.getString(0));
			arrLinkTile.add(c.getString(2));
			c.moveToNext();
		}
		c.close();
	}

	// hien thi thong bao
	private void Notify(String notificationTitle,CharSequence notificationMessage) {
		// hien thi tin
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher,arrTile.get(0).toString(), System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, HienThiWeb.class);
		notificationIntent.putExtra("linkweb", arrLinkTile.get(0).toString());
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);
		notification.setLatestEventInfo(getBaseContext(), notificationTitle,notificationMessage, pendingIntent);
		notificationManager.notify(9999, notification);
		// tao dung
		Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(500);
	}

	// dem thoi gian chay
	@Override
	protected void onResume() {
		super.onResume();
		startTimer();

	}
	public void startTimer() {
		timer = new Timer();
		initializeTimerTask();
		timer.schedule(timerTask, 120000, 3600000); 
	}
	public void stoptimertask(View v) {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public void initializeTimerTask() {
		timerTask = new TimerTask() {
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						// show the toast
						deleteAll();
						createOrOpentDB();
						createTable();
						new GetRSS().execute();
						new GetRSS2().execute();
						new GetRSSGD().execute();
						new GetRSSBDS().execute();
						new GetRSSBD().execute();
						new GetRSSKHCN().execute();
						new GetRSSKHTN().execute();
						new GetRSSKT().execute();
						new GetRSSPL().execute();
						new GetRSSSK().execute();
						new GetRSSTGA().execute();
						new GetRSSVH().execute();
						new GetRSSXH().execute();
						new GetRSSTG().execute();
						redDataofTable();
						Notify("Việt Báo", arrTile.get(0).toString());
						
					}
				});
			}
		};
	}
	public void deleteAll()
	{
	    database.deleteDatabase(getCacheDir());
	    database.deleteDatabase(getFilesDir());
	}
}
