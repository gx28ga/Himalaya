package com.axun.himalaya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.axun.himalaya.R;
import com.axun.himalaya.utils.LogUtil;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.Arrays;
import java.util.List;

import static net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator.MODE_MATCH_EDGE;

public class IndicatorAdapter extends CommonNavigatorAdapter {
    private static final String TAG = "IndicatorAdapter";
    private Context mContext;
    private List<String> mData;
    private OnIndicatorTabClickListener mOnIndicatorTabClickListener;

    public IndicatorAdapter(Context mContext, List<String> mData) {

        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {

        if (mData != null) {

            return mData.size();
        }
        return 0;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
        simplePagerTitleView.setNormalColor(Color.parseColor("#aaffffff"));
        simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
        simplePagerTitleView.setText(mData.get(index));
        simplePagerTitleView.setTextSize(18);
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG, "clicked");
                if (mOnIndicatorTabClickListener != null) {
                    mOnIndicatorTabClickListener.onTabClick(index);
                }
            }
        });
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        linePagerIndicator.setColors(Color.WHITE);
        return linePagerIndicator;
    }

    public void setonIndicatorTabClickListener( OnIndicatorTabClickListener listener) {
        this.mOnIndicatorTabClickListener = listener;
    }


    public interface OnIndicatorTabClickListener {
        void onTabClick(int index);
    }
}
