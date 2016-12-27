package com.mao.diary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mao.diary.presenter.NoteAddPresenter;
import com.mao.duoduo.R;

/**
 * Created by Mao on 16-12-27.
 */
public class NoteAddActivity extends AppCompatActivity implements INoteAddView {

    @BindView(R.id.et_note_title)
    EditText mEtTitle;
    @BindView(R.id.et_note_content)
    EditText mEtContent;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    private NoteAddPresenter mNoteAddPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @OnClick(R.id.btn_submit)
    public void submit(View view) {
        mNoteAddPresenter.addNote(mEtTitle.getText().toString(), mEtContent.getText().toString());
    }

    private void initView() {
        setContentView(R.layout.activity_note_add);
        ButterKnife.bind(this);
    }

    private void initData() {
        mNoteAddPresenter = new NoteAddPresenter(this);
    }

    @Override
    public void addNoteResult(boolean result, String data) {
        if (result) {
            setResult(RESULT_OK);
            finish();
        } else {

        }
    }
}
