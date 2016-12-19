package com.mao.duoduo.presenter;

/**
 * Created by Mao on 2016/11/2.
 */
public interface IRegisterPresenter {

    void register(String name, String password);

    void registerResult(boolean result, String message);

}
