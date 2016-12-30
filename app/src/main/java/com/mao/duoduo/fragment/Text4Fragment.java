package com.mao.duoduo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.mao.duoduo.R;
import com.mao.duoduo.adapter.JokesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mao on 16-12-23.
 */
public class Text4Fragment extends Fragment {

    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_image)
    TextView tvImage;
    @BindView(R.id.vp_joke)
    ViewPager vpJoke;

    private Unbinder mUnBinder;
    private List<Fragment> mFragments;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text4, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        JokesAdapter adapter = new JokesAdapter(getFragmentManager());
        vpJoke.setAdapter(adapter);
        adapter.setFragments(mFragments);
        adapter.notifyDataSetChanged();
        return view;
    }

    @OnClick({R.id.tv_text, R.id.tv_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_text:

                break;
            case R.id.tv_image:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new JokeTextFragment());
        mFragments.add(new JokeImageFragment());

    }
}
