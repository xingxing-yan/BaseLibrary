package com.yyx.baselib.toolbar;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyx.baselib.R;


/**
 * Created by Administrator on 2017/2/21.
 */

public class ToolBarHelper {
    private Context mContext;
    private LayoutInflater mInflater;
    private Toolbar mToolbar;
    private View mCustomView;   //自定义View

    private ActionBar mActionBar;

    public ToolBarHelper(Context context, ViewGroup root) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mToolbar = (Toolbar) mInflater.inflate(R.layout.toolbar, root, false);
        root.addView(mToolbar, 0);
    }

    /**
     * 设置自定义的View
     * @param resId
     */
    public void setCustomView(@LayoutRes int resId) {
        if (mCustomView != null){
            mToolbar.removeView(mCustomView);
        }
        mCustomView = mInflater.inflate(resId, mToolbar, false);
        mToolbar.addView(mCustomView);
    }

    public void setSupportActionBar(AppCompatActivity activity, Toolbar toolbar){
        activity.setSupportActionBar(toolbar);
        mActionBar = activity.getSupportActionBar();
    }

    public ActionBar getActionBar(){
        return mActionBar;
    }


    public Toolbar getToolBar() {
        return mToolbar;
    }

    public View getCustomView(){
        return mCustomView;
    }

    public void hideToolBar(){
        mToolbar.setVisibility(View.GONE);
    }

    public void showToolBar(){
        mToolbar.setVisibility(View.VISIBLE);
    }
}
