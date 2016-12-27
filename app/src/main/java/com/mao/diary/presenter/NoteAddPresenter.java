package com.mao.diary.presenter;

import com.mao.diary.activity.INoteAddView;
import com.mao.diary.model.INoteAddModel;
import com.mao.diary.model.NoteAddModel;

/**
 * Created by Mao on 16-12-27.
 */
public class NoteAddPresenter implements INoteAddPresenter {

    private INoteAddView mNoteAddView;
    private INoteAddModel mNoteAddModel;

    public NoteAddPresenter(INoteAddView noteAddView) {
        this.mNoteAddView = noteAddView;
        mNoteAddModel = new NoteAddModel(this);
    }

    @Override
    public void addNote(String title, String content) {
        mNoteAddModel.addNote(title, content);
    }

    @Override
    public void addNoteResult(boolean result, String data) {
        mNoteAddView.addNoteResult(result, data);
    }

}
