package com.bignerdranch.android.newcrimeintent;

import android.support.v4.app.Fragment;

/**
 * Created by VIT_HOME on 2016-08-18.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
