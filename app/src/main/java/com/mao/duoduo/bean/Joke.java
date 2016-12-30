package com.mao.duoduo.bean;

/**
 * Created by Mao on 16-12-30.
 */
public class Joke {

    // 笑话内容
    private String content;

    // 笑话更新时间
    private String updateTime;

    public Joke() {

    }

    public Joke(String content, String updateTime) {
        this.content = content;
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
