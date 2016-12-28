package com.mao.diary.presenter.impl;

import com.mao.diary.activity.INoteMainView;
import com.mao.diary.model.INoteMainModel;
import com.mao.diary.model.impl.NoteMainModel;
import com.mao.diary.presenter.INoteMainPresenter;

/**
 * Created by Mao on 16-12-28.
 */
public class NoteMainPresenter implements INoteMainPresenter {

    private INoteMainView mNoteMainView;
    private INoteMainModel mNoteMainModel;

    public NoteMainPresenter(INoteMainView noteMainView) {
        this.mNoteMainView = noteMainView;
        mNoteMainModel = new NoteMainModel(this);
    }

    @Override
    public void getNotes() {
        mNoteMainModel.getNotes();
    }

    @Override
    public void getNotesResult(boolean result, Object data) {
        mNoteMainView.getNotesResult(result, data);
    }
}
