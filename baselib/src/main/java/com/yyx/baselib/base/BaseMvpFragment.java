package com.yyx.baselib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;

import com.shizhefei.fragment.LazyFragment;
import com.yyx.baselib.R;
import com.yyx.baselib.emptyview.HHProgressAlertDialog;
import com.yyx.baselib.utils.ActivityUtils;

/**
 * Created by Administrator on 2017/1/6.
 */

public abstract class BaseMvpFragment<V, T extends BasePresenter<V>> extends LazyFragment {
    protected T mPresenter;
    protected LayoutInflater mInflater;
    protected Context mInstance;
    protected HHProgressAlertDialog mProgressDialog;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        init();
        setLayout();
        initView();
        otherLogic();
        setListener();
    }

    //初始化
    private void init(){
        mInflater = LayoutInflater.from(getApplicationContext());
        mInstance = getActivity();
        mProgressDialog = new HHProgressAlertDialog(mInstance);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setBackground(R.color.loading_bg);
        mProgressDialog.setProgressColor(R.color.progress);
        mProgressDialog.setLoadingTextColor(R.color.loading_text);
        mPresenter = initPresenter();
        mPresenter.attach((V)this);
    }

    protected void toast(String msg){
        if (mInstance == null)
            return;
        ActivityUtils.toast(mInstance, msg);
    }

    protected void gotoActivity(Class<?> clazz){
        if (mInstance == null)
            return;
        ActivityUtils.startActivity(mInstance, clazz);
    }

    protected void showLoading(){
        if (mProgressDialog != null && !mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    protected void dismissLoading(){
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.cancel();
        }
    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }

    /**
     * 查找对应View子view
     * @param id
     * @param view
     * @param <VT>
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id, View view) {
        return (VT) view.findViewById(id);
    }

    @Override
    protected void onDestroyViewLazy() {
        mPresenter.dettach();
        super.onDestroyViewLazy();
    }

    /**
     * 初始换Presenter
     * @return
     */
    protected abstract T initPresenter();

    /**
     * 初始化布局
     */
    protected abstract void setLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 其他业务逻辑
     */
    protected abstract void otherLogic();

    /**
     * 初始化监听器
     */
    protected abstract void setListener();
}
