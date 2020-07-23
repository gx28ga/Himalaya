package com.axun.himalaya;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.axun.himalaya.adapters.DetailListAdapter;
import com.axun.himalaya.base.BaseActivity;
import com.axun.himalaya.interfaces.IAlbumDetailViewCallback;
import com.axun.himalaya.presenters.AlbumDetailPresenter;
import com.axun.himalaya.utils.ImageBlur;
import com.axun.himalaya.views.UILoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public class DetailActivity extends BaseActivity implements IAlbumDetailViewCallback, UILoader.OnRetryClickListener, DetailListAdapter.OnItemClickListener {
    private static final String TAG = "DetailActivity";
    private ImageView mCoverBg;
    private ImageView mSmallCover;
    private TextView mAlbumTitle;
    private TextView mAlbumAuthor;
    private AlbumDetailPresenter mInstance;
    private TextView trackCapsulePanelToggle;
    private int mCurrentPage = 1;
    private RecyclerView detailList;
    private RecyclerView mDetailList;
    private DetailListAdapter mDetailListAdapter;
    private FrameLayout mDetailListContainer;
    private UILoader mUiLoader;
    private long mCurrentId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        initViews();
        mInstance = AlbumDetailPresenter.getInstance();
        mInstance.registerViewCallback(this);

        // 获取专辑的详情内容


    }

    private View createSuccessView(ViewGroup container) {
        View detailListView = LayoutInflater.from(this).inflate(R.layout.item_detail_list, container, false);
        mDetailList = detailListView.findViewById(R.id.album_detail_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mDetailList.setLayoutManager(linearLayoutManager);

        mDetailListAdapter = new DetailListAdapter();
        mDetailListAdapter.setOnItemClickListener(this);
        mDetailList.setAdapter(mDetailListAdapter);
        mDetailList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 2;
                outRect.bottom = 2;
            }
        });
        return detailListView;
    }

    private void initViews() {
        mDetailListContainer = findViewById(R.id.detail_list_container);

        if (mUiLoader == null) {
            mUiLoader = new UILoader(this) {
                @Override
                public View getSuccessView(ViewGroup viewGroup) {
                    return createSuccessView(viewGroup);
                }
            };
            mUiLoader.setOnRetryClickListener(this);
            mDetailListContainer.removeAllViews();
            mDetailListContainer.addView(mUiLoader);
        }


        mCoverBg = findViewById(R.id.cover_bg);
        mSmallCover = findViewById(R.id.small_cover);
        mAlbumTitle = findViewById(R.id.album_title);
        mAlbumAuthor = findViewById(R.id.album_author);
        trackCapsulePanelToggle = findViewById(R.id.track_capsule_panel_toggle);
        Drawable drawable = getResources().getDrawable(R.mipmap.arrow_icon);

        drawable.setBounds(0, 0, 20, 10);
        trackCapsulePanelToggle.setCompoundDrawables(null, null, drawable, null);


    }


    @Override
    public void onDetailListLoaded(List<Track> tracks) {
        if (tracks == null || tracks.size() == 0) {
            if (mUiLoader != null) {
                mUiLoader.updateUiStatus(UILoader.UIStatus.EMPTY);
            }

        } else {
            mUiLoader.updateUiStatus(UILoader.UIStatus.SUCCESS);
            mDetailListAdapter.setData(tracks);
        }
    }

    @Override
    public void onAlbumLoaded(Album album) {
        long id = album.getId();
        mCurrentId = id;
        if (mInstance != null) {

            mInstance.getAlbumDetail(id, mCurrentPage);
        }


        if (mUiLoader != null) {
            mUiLoader.updateUiStatus(UILoader.UIStatus.LOADING);
        }

        if (mAlbumTitle != null) {

            mAlbumTitle.setText(album.getAlbumTitle());
        }
        if (mAlbumAuthor != null) {
            mAlbumAuthor.setText(album.getAnnouncer().getNickname());

        }
        if (mCoverBg != null) {
            Picasso.with(this).load(album.getCoverUrlLarge()).into(mCoverBg, new Callback() {
                @Override
                public void onSuccess() {
                    ImageBlur.makeBlur(mCoverBg, DetailActivity.this);
                }

                @Override
                public void onError() {

                }
            });


        }
        if (mSmallCover != null) {
            Picasso.with(this).load(album.getCoverUrlSmall()).into(mSmallCover);

        }
    }

    @Override
    public void onNetworkError(int errCode, String errMsg) {
        mUiLoader.updateUiStatus(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onLoading() {
        mUiLoader.updateUiStatus(UILoader.UIStatus.LOADING);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mInstance != null) {
            mInstance.registerViewCallback(this);
        }
    }

    @Override
    public void onRetryClick() {
        mInstance.getAlbumDetail(mCurrentId, mCurrentPage);
    }


    @Override
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(this, PlayerActivity.class);
        startActivity(intent);
    }
}
