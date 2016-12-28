package com.mao.diary.model.impl;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import com.mao.diary.bean.Note;
import com.mao.diary.model.INoteMainModel;
import com.mao.diary.presenter.INoteMainPresenter;

import java.util.List;

/**
 * Created by Mao on 16-12-28.
 */
public class NoteMainModel implements INoteMainModel {

    private INoteMainPresenter mNoteMainPresenter;

    public NoteMainModel(INoteMainPresenter noteMainPresenter) {
        this.mNoteMainPresenter = noteMainPresenter;
    }

    @Override
    public void getNotes() {
        BmobQuery<Note> query = new BmobQuery<Note>();
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if (e == null) {
                    mNoteMainPresenter.getNotesResult(true, list);
                } else {
                    mNoteMainPresenter.getNotesResult(false, e.getMessage());
                }
            }
        });
    }

}
