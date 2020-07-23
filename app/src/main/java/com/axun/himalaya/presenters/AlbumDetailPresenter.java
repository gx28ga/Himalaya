package com.axun.himalaya.presenters;

import android.util.Log;

import com.axun.himalaya.interfaces.IAlbumDetailPresenter;
import com.axun.himalaya.interfaces.IAlbumDetailViewCallback;
import com.axun.himalaya.utils.Constants;
import com.axun.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumDetailPresenter implements IAlbumDetailPresenter {
    private static final String TAG = "AlbumDetailPresenter";
    private static AlbumDetailPresenter sInstance = null;
    private List<IAlbumDetailViewCallback> mCallBacks = new ArrayList<>();
    private Album mTargetAlbum;

    private AlbumDetailPresenter() {
    }

    public static AlbumDetailPresenter getInstance() {
        if (sInstance == null) {
            synchronized (AlbumDetailPresenter.class) {
                sInstance = new AlbumDetailPresenter();

            }
        }
        return sInstance;
    }

    @Override
    public void pullToRefresh() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void getAlbumDetail(long albumId, int page) {
        loading();
        LogUtil.d(TAG, albumId + "");
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.ALBUM_ID, albumId + "");
        map.put(DTransferConstants.SORT, "asc");
        map.put(DTransferConstants.PAGE, page + "");
        map.put(DTransferConstants.PAGE_SIZE, Constants.COUNT_DEFAULT + "");
        CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(TrackList trackList) {
                List<Track> tracks = trackList.getTracks();
                handlerAlbumDetailResult(tracks);
            }

            @Override
            public void onError(int i, String s) {
                handleNetWorkError(i, s);
                LogUtil.d(TAG, i + s);

            }
        });
    }

    private void loading() {
        for (IAlbumDetailViewCallback callback :
                mCallBacks
        ) {
            callback.onLoading();
        }
    }

    private void handleNetWorkError(int errCode, String errMsg) {
        for (IAlbumDetailViewCallback callback :
                mCallBacks
        ) {
            callback.onNetworkError(errCode, errMsg);
        }
    }

    private void handlerAlbumDetailResult(List<Track> tracks) {
        for (IAlbumDetailViewCallback callback :
                mCallBacks
        ) {
            callback.onDetailListLoaded(tracks);
        }
    }

    @Override
    public void registerViewCallback(IAlbumDetailViewCallback callback) {
        if (mCallBacks != null) {
            if (!mCallBacks.contains(callback)) {
                mCallBacks.add(callback);
                if (mTargetAlbum != null) {
                    callback.onAlbumLoaded(mTargetAlbum);
                }
            }
        }
    }

    @Override
    public void unregisterViewCallback(IAlbumDetailViewCallback callback) {
        if (mCallBacks != null) {
            if (mCallBacks.contains(callback)) {
                mCallBacks.remove(callback);

            }
        }
    }

    public void setTargetAlbum(Album album) {
        Log.d(TAG, "setTargetAlbum: ");
        mTargetAlbum = album;

    }
}
