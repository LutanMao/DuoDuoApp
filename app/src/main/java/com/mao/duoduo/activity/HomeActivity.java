package com.mao.duoduo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.mao.duoduo.R;
import com.mao.duoduo.widget.CircleImageView;

/**
 * Created by Mao on 2016/11/3.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private CircleImageView mCivHeader;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter mArrayAdapter;
    private String[] lvs = {"List Item 01", "List Item 02", "List Item 03", "List Item 04"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.civ_header:
                Intent intent = new Intent(HomeActivity.this, PersonalActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initView() {
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mListView = (ListView) findViewById(R.id.lv_left_menu);
        mCivHeader = (CircleImageView) findViewById(R.id.civ_header);
        mCivHeader.setOnClickListener(this);
        mToolbar.setTitle("ToolBar");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerToggle.syncState();
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lvs);
        mListView.setAdapter(mArrayAdapter);

    }

}
