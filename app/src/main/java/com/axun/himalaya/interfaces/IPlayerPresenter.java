package com.axun.himalaya.interfaces;

import com.axun.himalaya.base.IBasePresenter;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

public interface IPlayerPresenter extends IBasePresenter<IPlayerCallback> {
    /**
     * 播放
     */
    void play();

    /**
     * 暂停
     */
    void pause();

    /**
     * 停止
     */
    void stop();

    /**
     * 前进
     */
    void forward();

    /**
     * 后退
     */
    void backward();

    /**
     * 上一首
     */
    void prev();

    /**
     * 下一首
     */
    void next();

    /**
     * 切换播放模式
     * @param mode
     */
    void switchPlayMode(XmPlayListControl.PlayMode  mode);


    /**
     * 获取播放列表
     */
    void getPlaylist();

    /**
     * 切歌
     * @param index
     */
    void playByIndex(int index);

    void seekTo();
}
