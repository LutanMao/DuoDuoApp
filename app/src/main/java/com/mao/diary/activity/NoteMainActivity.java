package com.mao.diary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mao.duoduo.R;

/**
 * Created by Mao on 16-12-27.
 */
public class NoteMainActivity extends AppCompatActivity implements INoteAddView{

    private static final int ADD_NEW_NOTE = 300;

    @BindView(R.id.tv_add_note)
    TextView mTvAddNote;
    @BindView(R.id.lv_notes)
    ListView mLvNotes;

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

        }
    }

    private void initView() {
        setContentView(R.layout.activity_note_main);
        ButterKnife.bind(this);
    }

    private void initData() {

    }

    @Override
    public void addNoteResult(boolean result, String data) {

    }
}
