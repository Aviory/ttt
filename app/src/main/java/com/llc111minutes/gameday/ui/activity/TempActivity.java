package com.llc111minutes.gameday.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.ui.fragment.CropFragment;

/**
 * Created by NewUser on 12/9/16.
 */

public class TempActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new CropFragment(), CropFragment.class.getCanonicalName())
                .commitAllowingStateLoss();
    }
}
