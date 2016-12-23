package com.mao.duoduo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import cn.bmob.v3.BmobUser;
import com.bumptech.glide.Glide;
import com.mao.duoduo.R;
import com.mao.duoduo.adapter.HomePagerAdapter;
import com.mao.duoduo.bean.User;
import com.mao.duoduo.fragment.Text1Fragment;
import com.mao.duoduo.fragment.Text2Fragment;
import com.mao.duoduo.fragment.Text3Fragment;
import com.mao.duoduo.fragment.Text4Fragment;
import com.mao.duoduo.presenter.HomePresenter;
import com.mao.duoduo.utils.MaoLog;
import com.mao.duoduo.widget.CircleImageView;
import com.mao.duoduo.widget.PullToZoomListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mao on 2016/11/3.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, IHomeView {

    private static final String TAG = "HomeActivity";

    private HomePresenter mHomePresenter;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private PullToZoomListView mListView;
    private CircleImageView mCivHeader;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter mArrayAdapter;

    private ViewPager mViewPager;
    private HomePagerAdapter mHomePagerAdapter;
    private List<Fragment> mFragmentList;
    private RadioGroup mRadioGroup;
    private RadioButton mRbText1;
    private RadioButton mRbText2;
    private RadioButton mRbText3;
    private RadioButton mRbText4;

    private String[] lvs = {"List Item 01", "List Item 02", "List Item 03", "List Item 04"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomePresenter = new HomePresenter(this);
        initView();
        initData();
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

    @Override
    protected void initView() {
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("ToolBar");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setBackgroundColor(Color.parseColor("#3F51B5"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mListView = (PullToZoomListView) findViewById(R.id.lv_left_menu);

        // 显示放大图片的ListView
        Glide.with(this).load(BmobUser.getCurrentUser(User.class).getAvatar()).into(mListView.getHeaderView());
        mListView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        // 侧边栏头像
        mCivHeader = (CircleImageView) findViewById(R.id.civ_header);
        mCivHeader.setOnClickListener(this);
        // 主体内容
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mHomePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mHomePagerAdapter);
        mViewPager.setCurrentItem(0);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MaoLog.i(TAG, "onPageSelected : position = " + position);
                mViewPager.setCurrentItem(position);
                switch (position) {
                    case 0:
                        mRbText1.setChecked(true);
                        break;
                    case 1:
                        mRbText2.setChecked(true);
                        break;
                    case 2:
                        mRbText3.setChecked(true);
                        break;
                    case 3:
                        mRbText4.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 主界面底部
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_bottom);
        mRadioGroup.setOnCheckedChangeListener(this);
        mRbText1 = (RadioButton) findViewById(R.id.rb_text1);
        mRbText2 = (RadioButton) findViewById(R.id.rb_text2);
        mRbText3 = (RadioButton) findViewById(R.id.rb_text3);
        mRbText4 = (RadioButton) findViewById(R.id.rb_text4);
        mRbText1.setChecked(true);

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

    private void initData() {
        // Picasso框架
//        Picasso.with(this).load(BmobUser.getCurrentUser(User.class).getAvatar()).into(mCivHeader);
        // Glide框架
        Glide.with(this).load(BmobUser.getCurrentUser(User.class).getAvatar()).into(mCivHeader);

        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new Text1Fragment());
        mFragmentList.add(new Text2Fragment());
        mFragmentList.add(new Text3Fragment());
        mFragmentList.add(new Text4Fragment());
        mHomePagerAdapter.setFragments(mFragmentList);
        mHomePagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.rb_text1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.rb_text2:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.rb_text3:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.rb_text4:
                mViewPager.setCurrentItem(3);
                break;
        }
    }
}
