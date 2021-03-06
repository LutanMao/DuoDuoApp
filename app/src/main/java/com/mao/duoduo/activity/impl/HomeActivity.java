package com.mao.duoduo.activity.impl;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import butterknife.*;
import cn.bmob.v3.BmobUser;
import com.baidu.location.*;
import com.bumptech.glide.Glide;
import com.mao.MaoApplication;
import com.mao.base.BaseActivity;
import com.mao.book.activity.BookMainActivity;
import com.mao.diary.activity.impl.NoteMainActivity;
import com.mao.duoduo.R;
import com.mao.duoduo.activity.IHomeView;
import com.mao.duoduo.adapter.HomePagerAdapter;
import com.mao.duoduo.bean.User;
import com.mao.duoduo.fragment.Text1Fragment;
import com.mao.duoduo.fragment.Text2Fragment;
import com.mao.duoduo.fragment.Text3Fragment;
import com.mao.duoduo.fragment.Text4Fragment;
import com.mao.duoduo.presenter.impl.HomePresenter;
import com.mao.duoduo.widget.CircleImageView;
import com.mao.duoduo.widget.ScrollListView;
import com.mao.music.activity.MusicMainActivity;
import com.mao.pulltozoomview.PullToZoomScrollViewEx;
import com.mao.utils.MaoLog;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mao on 2016/11/3.
 */
public class HomeActivity extends BaseActivity implements IHomeView, AdapterView.OnItemClickListener {

    private static final String TAG = "HomeActivity";

    private static final int ACCESS_COARSE_LOCATION_REQUEST_CODE = 103;
    private static final int GET_WEATHER_CITY_ID = 104;
    private static final int GET_CITY_WEATHER_DATA = 105;

    private HomePresenter mHomePresenter;

    @BindView(R.id.tb_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawerlayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.scroll_view)
    PullToZoomScrollViewEx mScrollView;
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

    private CircleImageView mCirHeader;
    private ImageView mIvWeather;
    private TextView mTvWeather;
    private TextView mTvAddress;
    private ScrollListView mListView;

    private Unbinder mUnBinder;

    private HomePagerAdapter mHomePagerAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<Fragment> mFragmentList;
    private String[] mListData = new String[]{"我的资料", "我的音乐", "我的电影", "我的购物", "我的书籍", "我的日记"};
    private ArrayAdapter mArrayAdapter;

    private LocationClient mLocationClient;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GET_CITY_WEATHER_DATA) {
                JSONObject data = (JSONObject) msg.obj;
                MaoLog.i(TAG, "weather_icon = " + data.optString("weather_icon") + "; wendu = "
                        + data.optString("temperature_curr") + data.optString("weather_curr"));
                Glide.with(MaoApplication.getInstance()).load(data.optString("weather_icon")).into(mIvWeather);
                mTvWeather.setText(data.optString("temperature_curr") + data.optString("weather_curr"));
            }
        }
    };

    @Override
    public void getWeatherResult(boolean result, JSONObject data) {
        if (result) {
            Message msg = Message.obtain();
            msg.what = GET_CITY_WEATHER_DATA;
            msg.obj = data;
            mHandler.sendMessage(msg);
        } else {

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomePresenter = new HomePresenter(this);
        initLocation();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        mLocationClient.unRegisterLocationListener(mLocationListener);
        mLocationClient = null;
        mLocationListener = null;
    }

//    @OnPageChange(R.id.vp_content)
//    public void onPageSelected(int position) {
//        MaoLog.i(TAG, "onPageSelected : position = " + position);
//        mViewPager.setCurrentItem(position);
//        switch (position) {
//            case 0:
//                mRbText1.setChecked(true);
//                break;
//            case 1:
//                mRbText2.setChecked(true);
//                break;
//            case 2:
//                mRbText3.setChecked(true);
//                break;
//            case 3:
//                mRbText4.setChecked(true);
//                break;
//        }
//    }

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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                break;
            case 1:
                Intent musicIntent = new Intent(HomeActivity.this, MusicMainActivity.class);
                startActivity(musicIntent);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                Intent bookIntent = new Intent(HomeActivity.this, BookMainActivity.class);
                startActivity(bookIntent);
                break;
            case 5:
                Intent noteIntent = new Intent(HomeActivity.this, NoteMainActivity.class);
                startActivity(noteIntent);
                break;
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_home);
        mUnBinder = ButterKnife.bind(this);
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

        mCirHeader = ButterKnife.findById(headView, R.id.civ_header);
        mIvWeather = ButterKnife.findById(contentView, R.id.iv_weather_icon);
        mTvWeather = ButterKnife.findById(contentView, R.id.tv_weather_wendu);
        mTvAddress = ButterKnife.findById(contentView, R.id.tv_address);
        mListView = ButterKnife.findById(contentView, R.id.lv_content);
        mListView.setOnItemClickListener(this);

        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListData);
        mListView.setAdapter(mArrayAdapter);

        mCirHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });

        Glide.with(this).load(BmobUser.getCurrentUser(User.class).getAvatar())
                .into((ImageView) headView.findViewById(R.id.civ_header));
        Glide.with(this).load(BmobUser.getCurrentUser(User.class).getAvatar())
                .into((ImageView) zoomView.findViewById(R.id.iv_zoom));

        mScrollView.setHeaderView(headView);
        mScrollView.setZoomView(zoomView);
        mScrollView.setScrollContentView(contentView);
    }

    private void initLocation() {
        mLocationClient = new LocationClient(MaoApplication.getInstance());
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        int span = 1000;
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(0);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(mLocationListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_COARSE_LOCATION_REQUEST_CODE);
        } else {
            mLocationClient.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACCESS_COARSE_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationClient.start();
            } else {

            }
        }
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

    private BDLocationListener mLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            sb.append("\ncity : ");
            sb.append(location.getCity());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            mTvAddress.setText(location.getCity());
            Toast.makeText(MaoApplication.getInstance(), sb.toString(), Toast.LENGTH_SHORT).show();
            mHomePresenter.getWeatherByName(location.getCity());
        }
    };

}
