package com.mao.diary.activity.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mao.diary.activity.INoteMainView;
import com.mao.diary.adapter.NotesAdapter;
import com.mao.diary.bean.Note;
import com.mao.diary.presenter.impl.NoteMainPresenter;
import com.mao.duoduo.R;

import java.util.List;

/**
 * Created by Mao on 16-12-27.
 */
public class NoteMainActivity extends AppCompatActivity implements INoteMainView {

    private static final int ADD_NEW_NOTE = 300;

    @BindView(R.id.tv_add_note)
    TextView mTvAddNote;
    @BindView(R.id.lv_notes)
    ListView mLvNotes;

    private NoteMainPresenter mNoteMainPresenter;
    private NotesAdapter mNotesAdapter;

    @Override
    public void getNotesResult(boolean result, Object data) {
        if (result) {
            List<Note> notes = (List<Note>) data;
            mNotesAdapter.setNotes(notes);
            mNotesAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, (String) data, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @OnClick(R.id.tv_add_note)
    public void onClick(View view) {
        Intent intent = new Intent(NoteMainActivity.this, NoteAddActivity.class);
        startActivityForResult(intent, ADD_NEW_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NEW_NOTE && resultCode == RESULT_OK) {
            mNoteMainPresenter.getNotes();
        }
    }

    private void initView() {
        setContentView(R.layout.activity_note_main);
        ButterKnife.bind(this);
        mNotesAdapter = new NotesAdapter(this);
        mLvNotes.setAdapter(mNotesAdapter);
    }

    private void initData() {
        mNoteMainPresenter = new NoteMainPresenter(this);
        mNoteMainPresenter.getNotes();
    }

}
