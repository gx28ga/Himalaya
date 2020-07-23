package com.axun.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public interface IAlbumDetailViewCallback {

    /**
     * 专辑详情内容加载
     * @param tracks
     */
    void onDetailListLoaded(List<Track> tracks);


    /**
     * 把album传给ui使用
     * @param album
     */
    void onAlbumLoaded(Album album);

    void onNetworkError(int errCode, String errMsg);

    void onLoading();
}
