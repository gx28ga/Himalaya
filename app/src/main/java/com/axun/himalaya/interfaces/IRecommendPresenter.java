package com.axun.himalaya.interfaces;

import com.axun.himalaya.base.IBasePresenter;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;

public interface IRecommendPresenter extends IBasePresenter<IRecommendViewCallback> {
    /**
     * 获取推荐内容
     */

    void getRecommendList();
}
