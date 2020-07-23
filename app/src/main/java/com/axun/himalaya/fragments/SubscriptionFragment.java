package com.axun.himalaya.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axun.himalaya.R;
import com.axun.himalaya.base.BaseFragment;

public class SubscriptionFragment extends BaseFragment {

    @Override
    protected View onSubviewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView = layoutInflater.inflate(R.layout.fragment_subscription, container, false);
        return rootView;
    }
}
