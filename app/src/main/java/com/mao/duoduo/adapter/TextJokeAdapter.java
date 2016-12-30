package com.mao.duoduo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mao.duoduo.R;
import com.mao.duoduo.bean.Joke;

import java.util.List;

/**
 * Created by Mao on 16-12-30.
 */
public class TextJokeAdapter extends BaseAdapter {

    private Context mContext;
    private List<Joke> mJokesList;

    public TextJokeAdapter(Context context) {
        this.mContext = context;
    }

    public void setJokeList(List<Joke> list) {
        this.mJokesList = list;
    }

    @Override
    public int getCount() {
        if (null == mJokesList || mJokesList.isEmpty()) {
            return 0;
        } else {
            return mJokesList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return mJokesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_joke_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Joke joke = mJokesList.get(i);
        holder.tvJokeContent.setText(joke.getContent());
        holder.tvJokeUpdateTime.setText(joke.getUpdateTime());
        return null;
    }

    static class ViewHolder {
        @BindView(R.id.tv_joke_content)
        TextView tvJokeContent;
        @BindView(R.id.tv_joke_update_time)
        TextView tvJokeUpdateTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
