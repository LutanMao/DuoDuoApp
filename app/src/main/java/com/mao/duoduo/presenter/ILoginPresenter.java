package com.mao.duoduo.presenter;

/**
 * Created by Mao on 2016/11/2.
 */
public interface ILoginPresenter {

    void login(String name, String password);

    void loginResult(boolean result, Object data);

}
