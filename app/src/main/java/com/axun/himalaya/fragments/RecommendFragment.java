package com.axun.himalaya.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.axun.himalaya.DetailActivity;
import com.axun.himalaya.R;
import com.axun.himalaya.adapters.RecommendAdapter;
import com.axun.himalaya.base.BaseFragment;
import com.axun.himalaya.interfaces.IRecommendViewCallback;
import com.axun.himalaya.presenters.AlbumDetailPresenter;
import com.axun.himalaya.presenters.RecommendPresenter;
import com.axun.himalaya.views.UILoader;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class RecommendFragment extends BaseFragment implements IRecommendViewCallback, UILoader.OnRetryClickListener, RecommendAdapter.onRecommendItemClickListener {
    private static final String TAG = "RecommendFragment";
    private RecommendAdapter mRecommendListAdapter;
    private View mRootView;
    private RecommendPresenter mPresenter;
    private UILoader mUILoader;

    @Override
    protected View onSubviewLoaded(final LayoutInflater layoutInflater, final ViewGroup container) {
        mUILoader = new UILoader(getContext()) {
            @Override
            public View getSuccessView(ViewGroup container) {
                return createSuccessView(layoutInflater, container );
            }
        };

        mPresenter = RecommendPresenter.getInstance();

        mPresenter.registerViewCallback(this);

        mPresenter.getRecommendList();

        if (mUILoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) mUILoader.getParent()).removeView(mUILoader);
        }

        mUILoader.setOnRetryClickListener(this);

        return mUILoader;




    }

    private View createSuccessView(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView = layoutInflater.inflate(R.layout.fragment_recommend, container, false);
        RecyclerView recommendRv = mRootView.findViewById(R.id.recommend_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(), RecyclerView.VERTICAL, false);
        recommendRv.setLayoutManager(linearLayoutManager);

        // 设置分割线
        recommendRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 5);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 5);
                outRect.left = UIUtil.dip2px(view.getContext(), 5);
                outRect.right = UIUtil.dip2px(view.getContext(), 5);
            }
        });
        mRecommendListAdapter = new RecommendAdapter();
        mRecommendListAdapter.setOnRecommendItemClickListener(this);
        recommendRv.setAdapter(mRecommendListAdapter);
        return mRootView;
    }

    @Override
    public void onRecommendListLoaded(List<Album> result) {
        mRecommendListAdapter.setData(result);
        mUILoader.updateUiStatus(UILoader.UIStatus.SUCCESS);
    }

    @Override
    public void onNetworkError() {
        mUILoader.updateUiStatus(UILoader.UIStatus.NETWORK_ERROR);

    }

    @Override
    public void onEmpty() {
        Log.d(TAG, "onEmpty: ");
        mUILoader.updateUiStatus(UILoader.UIStatus.EMPTY);

    }

    @Override
    public void onLoading() {
        mUILoader.updateUiStatus(UILoader.UIStatus.LOADING);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.unregisterViewCallback(this);
        }
    }

    @Override
    public void onRetryClick() {
        // 网络不佳 点击重试
        if (mPresenter != null) {
            mPresenter.getRecommendList();
        }
    }

    @Override
    public void onItemClick(int position, Album album) {
        AlbumDetailPresenter.getInstance().setTargetAlbum(album);
        Intent intent = new Intent();
        intent.setClass(getContext(), DetailActivity.class);
        startActivity(intent);
    }
}
