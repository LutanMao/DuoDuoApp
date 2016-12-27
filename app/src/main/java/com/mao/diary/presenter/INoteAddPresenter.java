package com.mao.diary.presenter;

import com.mao.base.IBasePresenter;

/**
 * Created by Mao on 16-12-27.
 */
public interface INoteAddPresenter extends IBasePresenter {

    public void addNote(String title, String content);

    public void addNoteResult(boolean result, String data);

}
