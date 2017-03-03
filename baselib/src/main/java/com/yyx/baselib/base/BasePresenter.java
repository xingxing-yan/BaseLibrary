package com.yyx.baselib.base;

/**
 * Created by Administrator on 2017/1/5.
 */

public abstract class BasePresenter<T> {
    protected T mView;

    public void attach(T view){
        this.mView = view;
    }

    public void dettach(){
        mView = null;
    }


}
