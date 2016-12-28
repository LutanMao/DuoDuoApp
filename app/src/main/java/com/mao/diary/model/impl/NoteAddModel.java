package com.mao.diary.model.impl;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import com.mao.diary.bean.Note;
import com.mao.diary.model.INoteAddModel;
import com.mao.diary.presenter.INoteAddPresenter;
import com.mao.duoduo.utils.MaoLog;

import java.util.Date;

/**
 * Created by Mao on 16-12-27.
 */
public class NoteAddModel implements INoteAddModel {

    private static final String TAG = "NoteAddModel";

    private INoteAddPresenter mNoteAddPresenter;

    public NoteAddModel(INoteAddPresenter noteAddPresenter) {
        this.mNoteAddPresenter = noteAddPresenter;
    }

    @Override
    public void addNote(String title, String content) {
        Note note = new Note();
        BmobDate updateDate = new BmobDate(new Date());
        BmobDate publishDate = new BmobDate(new Date());
        note.setTitle(title);
        note.setContent(content);
        note.setFavorite(false);
        note.setPhoto(null);
        note.setUpdateTime(updateDate);
        note.setPublishTime(publishDate);
        note.save(new SaveListener<String>() {

            @Override
            public void done(String s, BmobException e) {
                if (null == e) {
                    MaoLog.e(TAG, "Add note success");
                    mNoteAddPresenter.addNoteResult(true, "Success");
                } else {
                    MaoLog.e(TAG, "Add note failure");
                    mNoteAddPresenter.addNoteResult(false, e.getMessage());
                }
            }

        });
    }

}
