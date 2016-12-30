package com.mao.duoduo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.mao.duoduo.R;
import com.mao.duoduo.activity.IJokeTextView;
import com.mao.duoduo.adapter.TextJokeAdapter;
import com.mao.duoduo.bean.Joke;
import com.mao.duoduo.presenter.impl.JokePresenter;

import java.util.List;

/**
 * Created by Mao on 16-12-30.
 */
public class JokeTextFragment extends Fragment implements IJokeTextView {

    @BindView(R.id.lv_jokes)
    ListView lvJokes;

    private Unbinder mUnBinder;
    private JokePresenter mJokePresenter;
    private TextJokeAdapter mJokeAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJokePresenter = new JokePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke_text, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        mJokeAdapter = new TextJokeAdapter(getActivity());
        lvJokes.setAdapter(mJokeAdapter);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mJokePresenter.getTextJokes();
        } else {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void getTextJokesResult(boolean result, Object data) {
        if (result) {
            mJokeAdapter.setJokeList((List<Joke>) data);
            mJokeAdapter.notifyDataSetChanged();
        } else {

        }
    }

}
