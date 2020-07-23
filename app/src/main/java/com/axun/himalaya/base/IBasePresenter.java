package com.axun.himalaya.base;

public interface IBasePresenter<T> {
    void registerViewCallback(T t);
    void unregisterViewCallback(T t);
}
