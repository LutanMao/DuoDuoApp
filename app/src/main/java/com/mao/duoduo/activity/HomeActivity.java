package com.mao.duoduo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnPageChange;
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
import com.mao.pulltozoomview.PullToZoomScrollViewEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mao on 2016/11/3.
 */
public class HomeActivity extends BaseActivity implements IHomeView {

    private static final String TAG = "HomeActivity";

    private HomePresenter mHomePresenter;

    @BindView(R.id.tb_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawerlayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.scroll_view)
    PullToZoomScrollViewEx mScrollView;
    
    private ListView mListView;
    private CircleImageView mCirHeader;

    @BindView(R.id.vp_content)
    ViewPager mViewPager;
    @BindView(R.id.rg_bottom)
    RadioGroup mRadioGroup;
    @BindView(R.id.rb_text1)
    RadioButton mRbText1;
    @BindView(R.id.rb_text2)
    RadioButton mRbText2;
    @BindView(R.id.rb_text3)
    RadioButton mRbText3;
    @BindView(R.id.rb_text4)
    RadioButton mRbText4;

    private HomePagerAdapter mHomePagerAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter mArrayAdapter;
    private List<Fragment> mFragmentList;


    private String[] lvs = {"List Item 01", "List Item 02", "List Item 03", "List Item 04"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomePresenter = new HomePresenter(this);
        initView();
        initData();
    }

    /**
     * ViewPager滑动过程监听
     */
//    @OnPageChange(value = R.id.vp_content, callback = OnPageChange.Callback.PAGE_SCROLL_STATE_CHANGED)
//    public void onPageChanged() {
//        MaoLog.e(TAG, "PAGE_SCROLL_STATE_CHANGED");
//    }
//
//    @OnPageChange(value = R.id.vp_content, callback = OnPageChange.Callback.PAGE_SELECTED)
//    public void onPageSelected() {
//        MaoLog.e(TAG, "PAGE_SELECTED");
//    }
//
//    @OnPageChange(value = R.id.vp_content, callback = OnPageChange.Callback.PAGE_SCROLLED)
//    public void onPageScrolled() {
//        MaoLog.e(TAG, "PAGE_SCROLLED");
//    }

    @OnPageChange(R.id.vp_content)
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

    @OnCheckedChanged({R.id.rb_text1, R.id.rb_text2, R.id.rb_text3, R.id.rb_text4})
    public void onCheckedChanged(RadioButton radioButton) {
        boolean checked = radioButton.isChecked();
        switch (radioButton.getId()) {
            case R.id.rb_text1:
                if (checked) {
                    mViewPager.setCurrentItem(0);
                }
                break;
            case R.id.rb_text2:
                if (checked) {
                    mViewPager.setCurrentItem(1);
                }
                break;
            case R.id.rb_text3:
                if (checked) {
                    mViewPager.setCurrentItem(2);
                }
                break;
            case R.id.rb_text4:
                if (checked) {
                    mViewPager.setCurrentItem(3);
                }
                break;
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle("ToolBar");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setBackgroundColor(Color.parseColor("#3F51B5"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mHomePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mHomePagerAdapter);

        mViewPager.setCurrentItem(0);
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
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        View headView = LayoutInflater.from(this).inflate(R.layout.profile_head_view, null, false);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.profile_content_view, null, false);

        mListView = ButterKnife.findById(contentView, R.id.lv_content);
        mCirHeader = ButterKnife.findById(headView, R.id.civ_header);

        mCirHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });

        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lvs);
        mListView.setAdapter(mArrayAdapter);

        Glide.with(this).load(BmobUser.getCurrentUser(User.class).getAvatar())
                .into((ImageView) headView.findViewById(R.id.civ_header));
        Glide.with(this).load(BmobUser.getCurrentUser(User.class).getAvatar())
                .into((ImageView) zoomView.findViewById(R.id.iv_zoom));

        mScrollView.setHeaderView(headView);
        mScrollView.setZoomView(zoomView);
        mScrollView.setScrollContentView(contentView);
    }

    private void initData() {
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new Text1Fragment());
        mFragmentList.add(new Text2Fragment());
        mFragmentList.add(new Text3Fragment());
        mFragmentList.add(new Text4Fragment());
        mHomePagerAdapter.setFragments(mFragmentList);
        mHomePagerAdapter.notifyDataSetChanged();
    }

}
