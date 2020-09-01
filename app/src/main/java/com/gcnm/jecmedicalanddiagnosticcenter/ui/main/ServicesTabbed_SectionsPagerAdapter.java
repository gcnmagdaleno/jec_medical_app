package com.gcnm.jecmedicalanddiagnosticcenter.ui.main;

import android.content.Context;

import com.gcnm.jecmedicalanddiagnosticcenter.R;
import com.gcnm.jecmedicalanddiagnosticcenter.Services_TabOne;
import com.gcnm.jecmedicalanddiagnosticcenter.Services_TabTwo;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
@SuppressWarnings("ALL")
public class ServicesTabbed_SectionsPagerAdapter extends FragmentPagerAdapter {

  @StringRes
  public static final int[] TAB_TITLES = new int[]{R.string.strServicesTab1, R.string.strServicesTab2};
  public final Context mContext;

  public ServicesTabbed_SectionsPagerAdapter(Context context, FragmentManager fm) {
    super(fm);
    mContext = context;
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        Services_TabOne tab1 = new Services_TabOne();

        return tab1;
      case 1:
        Services_TabTwo tab2 = new Services_TabTwo();


        return tab2;

    }
    return null;
  }

  @Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    return mContext.getResources().getString(TAB_TITLES[position]);
  }

  @Override
  public int getCount() {
    // Show 2 total pages.
    return 2;
  }
}