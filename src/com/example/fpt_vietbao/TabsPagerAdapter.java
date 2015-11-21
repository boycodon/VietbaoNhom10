package com.example.fpt_vietbao;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.fpt_vietbao.ChuyenMucActivity;
import com.example.fpt_vietbao.TinNoiBatActivity;
import com.example.fpt_vietbao.TinNongAcitivity;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new TinNongAcitivity();
		case 1:
			// Games fragment activity
			return new ChuyenMucActivity();
		case 2:
			// Movies fragment activity
			return new TinNoiBatActivity();
		case 3:
			// Movies fragment activity
			return new VideoActivity();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
