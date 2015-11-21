package com.example.fpt_vietbao;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private ListView listView;
	private TextView vietnam, tienganh, noidung, thietlap, thongtin, dieukhoan, phienban, banngay, bandem, sanpham;
	private String check = " ";
	private SQLiteDatabase database;
	private ArrayAdapter<String> adapter = null;
	// Tab titles
	private String[] tabs = { "Tin nóng", "Chuyên mục", "Tin nổi bật", "Videos" };
	private String[] tabsE = { "Hot news", "Categories", "Popular News",
			"Videos" };
	private String[] tabs2 = { "Giáo dục", "Bóng đá", "Văn hóa", "Tin ảnh" };
	private String[] tabs2E = { "Education", "Soccer ball", "Cultural",
			"Photos" };
	// text
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// khai bao
		viewPager = (ViewPager) findViewById(R.id.pager);
		vietnam = (TextView) findViewById(R.id.tvTiengViet);
		tienganh = (TextView) findViewById(R.id.tvTiengAnh);
		noidung = (TextView) findViewById(R.id.tvNoiDung);
		thietlap = (TextView) findViewById(R.id.tvThietLap);
		dieukhoan = (TextView) findViewById(R.id.tvDieuKhoan);
		thongtin = (TextView) findViewById(R.id.tvThongTin);
		phienban = (TextView) findViewById(R.id.tvPhienBan);
		bandem = (TextView) findViewById(R.id.tvBanDem);
		banngay = (TextView) findViewById(R.id.tvBanngay);
		sanpham = (TextView) findViewById(R.id.tvSanPham);
		// modata
		createOrOpentDB();
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setBackgroundDrawable(getWallpaper());
		
		// khai bao va hien thi list
		listView = (ListView) findViewById(R.id.lvMenuNoiDung);
		// ngon ngu
		vietnam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues values = new ContentValues();
				values.put("ngonngu", "tiengviet");
				database.insert("tbNgonNgu", null, values);
				Toast.makeText(getBaseContext(), "Thiết lập tiếng việt",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		tienganh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues values = new ContentValues();
				values.put("ngonngu", "tienganh");
				database.insert("tbNgonNgu", null, values);
				Toast.makeText(getBaseContext(), "Setting English",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		// che đo ban ngay ban dem
		banngay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues values = new ContentValues();
				values.put("theme", "banngay");
				database.insert("tbTheme", null, values);
				Toast.makeText(getBaseContext(), "Chế độ ban ngày",Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		bandem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues values = new ContentValues();
				values.put("theme", "bandem");
				database.insert("tbTheme", null, values);
				Toast.makeText(getBaseContext(), "Chế độ ban đêm",Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		// luu ngon ngu vao dÃ¢t
		Cursor c = database.query("tbNgonNgu", null, null, null, null, null,
				null);
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			check = c.getString(0);
			c.moveToNext();
		}
		c.close();
		// Adding Tabs
		if (check.equals("tiengviet")) {
			for (String tab_name : tabs) {actionBar.addTab(actionBar.newTab().setText(tab_name)
						.setTabListener(this));
			}
			// thay doi list
			adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1, tabs2);
			listView.setAdapter(adapter);
			// thiet lap text
			noidung.setText("Nội dung");
			thietlap.setText("Thiết lập");
			phienban.setText("Phiên bản");
			dieukhoan.setText("Điều khoản");
			thongtin.setText("Thông tin");
			bandem.setText("Chế độ ban đêm");
			banngay.setText("Chế độ ban ngày");
			sanpham.setText("Sản phẩm");
		} else {
			for (String tab_name : tabsE) {
				actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
			}
			// thay doi list
			adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1, tabs2E);
			listView.setAdapter(adapter);
			noidung.setText("Content");
			thietlap.setText("Set up");
			phienban.setText("Version");
			dieukhoan.setText("Negotiable");
			thongtin.setText("About");
			bandem.setText("Night mode");
			banngay.setText("Daytime mode");
			sanpham.setText("Product");
		}
		
		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}
		});
		// hanh dong nut
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					Intent intent1 = new Intent(getBaseContext(),
							NDGiaoDucActivity.class);
					startActivity(intent1);
					break;
				case 1:
					Intent intent2 = new Intent(getBaseContext(),
							NDBongDaActivity.class);
					startActivity(intent2);
					break;
				case 2:
					Intent intent3 = new Intent(getBaseContext(),
							NDVanHoaActivity.class);
					startActivity(intent3);
					break;
				case 3:
					Intent intent4 = new Intent(getBaseContext(),
							NDTheGioiAnhActivity.class);
					startActivity(intent4);
					break;
				default:
					break;
				}
			}

		});
		// hanh dong thong tin
		thongtin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(),
						AboutActivity.class);
				startActivity(intent);
			}
		});
		phienban.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(),
						PhienBanActivity.class);
				startActivity(intent);
			}
		});
	}
	
						
	
	// Thong bao tin moi
	

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.action_share);
		ShareActionProvider myShareActionProvider = (ShareActionProvider) item
				.getActionProvider();
		Intent myIntent = new Intent();
		myIntent.setAction(Intent.ACTION_SEND);
		myIntent.putExtra(Intent.EXTRA_TEXT, "FPT VietBao rất tuyệt vời");
		myIntent.setType("text/plain");
		myShareActionProvider.setShareIntent(myIntent);
		return true;
	}

	// khoi tao data va mo data
	public void createOrOpentDB() {
		database = openOrCreateDatabase("QLWEB", MODE_APPEND, null);
	}

	
}
