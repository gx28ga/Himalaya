package com.axun.himalaya.presenters;

import com.axun.himalaya.interfaces.IRecommendPresenter;
import com.axun.himalaya.interfaces.IRecommendViewCallback;
import com.axun.himalaya.utils.Constants;
import com.axun.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendPresenter implements IRecommendPresenter {
    private static List<IRecommendViewCallback> mCallbacks = new ArrayList<>();
    private static final String TAG = "RecommendPresenter";
    private static RecommendPresenter sInstance = null;

    private RecommendPresenter() {}

    public static RecommendPresenter getInstance() {
        if (sInstance == null) {
            synchronized (RecommendPresenter.class) {
                sInstance = new RecommendPresenter();
            }
        }
        return sInstance;
    }

    @Override
    public void getRecommendList() {
        updateLoading();
        // 获取推荐内容
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.LIKE_COUNT, Constants.COUNT_DEFAULT + "");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(GussLikeAlbumList gussLikeAlbumList) {
                if (gussLikeAlbumList != null) {
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    handleRecommendResult(albumList);
                }
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG, i + s);
                handleError();
            }
        });

    }

    private void updateLoading() {
        if (mCallbacks != null) {
            for (IRecommendViewCallback callback: mCallbacks) {
                callback.onLoading();
            }
        }
    }


    private void handleError() {
        if (mCallbacks != null) {
            for (IRecommendViewCallback callback: mCallbacks) {
                callback.onNetworkError();
            }
        }
    }

    private void handleRecommendResult(List<Album> albumList) {
        if (albumList != null) {
            if (albumList.size() == 0) {
                for (IRecommendViewCallback callback: mCallbacks) {
                    callback.onEmpty( );
                }

            } else {
                for (IRecommendViewCallback callback: mCallbacks) {
                    callback.onRecommendListLoaded(albumList);
                }
            }
        }


    }

    @Override
    public void registerViewCallback(IRecommendViewCallback callback) {
        if(!mCallbacks.contains(callback)) {
            mCallbacks.add(callback);
        }
    }

    @Override
    public void unregisterViewCallback(IRecommendViewCallback callback) {
        if (callback != null) {
            mCallbacks.remove(callback);
        }
    }
}
