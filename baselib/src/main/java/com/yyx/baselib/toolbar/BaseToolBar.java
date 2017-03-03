package com.yyx.baselib.toolbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyx.baselib.R;


/**
 * Created by Administrator on 2017/2/22.
 * 没想好怎么封装，暂时不用
 */
@Deprecated
public class BaseToolBar {
    protected Toolbar mToolbar;
    protected Context mContext;
    protected LayoutInflater mInflater;

    public BaseToolBar(Context context, ViewGroup root) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mToolbar = (Toolbar) mInflater.inflate(R.layout.toolbar, root, false);
        root.addView(mToolbar, 0);
    }

    protected void setTitle(CharSequence title) {
        mToolbar.setTitle(title);
    }

    protected void setTitle(@StringRes int resId){
        mToolbar.setTitle(resId);
    }

    protected void setSubTitle(CharSequence subTitle) {
        mToolbar.setSubtitle(subTitle);
    }

    protected void setSubTitle(@StringRes int resId){
        mToolbar.setSubtitle(resId);
    }

    protected void setTitleTextColor(int color) {
        mToolbar.setTitleTextColor(color);
    }

    protected void setSubTitleTextColor(int color) {
        mToolbar.setSubtitleTextColor(color);
    }

    protected void setLogo(@DrawableRes int resId) {
        mToolbar.setLogo(resId);
    }

    protected void setLogo(Drawable drawable){
        mToolbar.setLogo(drawable);
    }

    protected void setNavigationIcon(@DrawableRes int resId) {
        mToolbar.setNavigationIcon(resId);
    }

    protected void ssetNavigationIcon(@Nullable Drawable icon){
        mToolbar.setNavigationIcon(icon);
    }

    protected void setNavigationOnClickListener(View.OnClickListener listener) {
        mToolbar.setNavigationOnClickListener(listener);
    }

    protected void inflateMenu(@MenuRes int resId) {
        mToolbar.inflateMenu(resId);
    }

    protected void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener listener) {
        mToolbar.setOnMenuItemClickListener(listener);
    }

    public void setCustomView(@LayoutRes int resId){

    }

}
