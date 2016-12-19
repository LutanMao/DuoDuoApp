package com.mao.duoduo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import com.mao.duoduo.R;
import com.mao.duoduo.widget.SlidingMenu;

/**
 * Created by Mao on 2016/11/3.
 */
public class HomeActivity extends AppCompatActivity {

    private SlidingMenu mMenu;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void toggleMenu(View view) {
        mMenu.toggle();
    }

    private void initView() {
        setContentView(R.layout.activity_home);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final DrawerArrowDrawable indicator = new DrawerArrowDrawable(this);
        indicator.setColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(indicator);

        mMenu = (SlidingMenu) findViewById(R.id.id_menu);

    }

}
