package com.axun.himalaya.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container.getContext() != null) {
            View rootView = onSubviewLoaded(inflater, container);
            return rootView;
        }
        return null;
    }

    protected abstract View onSubviewLoaded(LayoutInflater layoutInflater, ViewGroup container);
}
