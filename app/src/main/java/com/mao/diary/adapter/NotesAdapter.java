package com.mao.diary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mao.diary.bean.Note;
import com.mao.duoduo.R;

import java.util.List;

/**
 * Created by Mao on 16-12-28.
 */
public class NotesAdapter extends BaseAdapter {

    private Context mContext;
    private List<Note> mNotes;

    public NotesAdapter(Context context) {
        this.mContext = context;
    }

    public void setNotes(List<Note> notes) {
        this.mNotes = notes;
    }

    @Override
    public int getCount() {
        if (null == mNotes || mNotes.isEmpty()) {
            return 0;
        } else {
            return mNotes.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return mNotes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_notes, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        Note note = mNotes.get(i);
        holder.tvTitle.setText(note.getTitle());
        holder.tvTime.setText(note.getPublishTime().getDate().toString());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_item_image)
        ImageView ivImage;
        @BindView(R.id.tv_item_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_time)
        TextView tvTime;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
