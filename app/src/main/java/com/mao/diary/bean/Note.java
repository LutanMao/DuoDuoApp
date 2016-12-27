package com.mao.diary.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Mao on 16-12-27.
 */
public class Note extends BmobObject {

    // 最后一次编辑时间
    private BmobDate updateTime;
    // 发布时间
    private BmobDate publishTime;
    // 日记中的图片
    private BmobFile photo;
    // 日记内容
    private String content;
    // 是否被收藏
    private boolean favorite;
    // 日记标题
    private String title;

    public Note() {

    }

    public Note(String title, String content, boolean favorite, BmobFile photo) {
        this.title = title;
        this.content = content;
        this.favorite = favorite;
        this.photo = photo;
    }

    public BmobDate getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(BmobDate updateTime) {
        this.updateTime = updateTime;
    }

    public BmobDate getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(BmobDate publishTime) {
        this.publishTime = publishTime;
    }

    public BmobFile getPhoto() {
        return photo;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
