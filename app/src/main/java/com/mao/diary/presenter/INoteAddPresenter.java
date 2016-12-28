package com.mao.diary.presenter;

/**
 * Created by Mao on 16-12-27.
 */
public interface INoteAddPresenter {

    public void addNote(String title, String content);

    public void addNoteResult(boolean result, String data);

}
