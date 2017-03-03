package com.yyx.baselib.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yyx.baselib.R;
import com.yyx.baselib.emptyview.HHProgressAlertDialog;
import com.yyx.baselib.toolbar.ToolBarHelper;
import com.yyx.baselib.utils.ActivityUtils;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;


/**
 * Created by Administrator on 2017/1/5.
 */

public abstract class BaseMvpActivity<V, T extends BasePresenter<V>> extends AppCompatActivity implements BGASwipeBackHelper.Delegate{

    protected T mPresenter;

    protected ToolBarHelper mTbHelper;
    protected Toolbar mToolBar;
    protected ActionBar mActionBar;
    protected TextView mTvTitle;

    protected FrameLayout mTitleLayout;
    protected FrameLayout mActivityContent;
    protected RelativeLayout mRoot;
    protected View mOverlay;

//    public UiHandler mUiHandler;
    public LayoutInflater mInflater;

    protected BaseMvpActivity mInstance = this;
    protected HHProgressAlertDialog mProgressDialog;

    protected BGASwipeBackHelper mSwipeBackHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setScreenOrientation();
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        BaseApplication.getInstance().addActivity(this);
//        mUiHandler = new UiHandler(this);
        mInflater = LayoutInflater.from(this);
        mProgressDialog = new HHProgressAlertDialog(mInstance);
        //设置加载对话框的样式
        mProgressDialog.setCancelable(false);
        mProgressDialog.setBackground(R.color.loading_bg);
        mProgressDialog.setProgressColor(R.color.progress);
        mProgressDialog.setLoadingTextColor(R.color.loading_text);

        setLayout();
        initView();
        otherLogic();
        setListener();
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
    }

    private void setScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void setContentView(int resId) {
        super.setContentView(R.layout.activity_base);
        mTitleLayout = getViewById(R.id.base_title);
        mActivityContent = getViewById(R.id.activity_content);
        mRoot = getViewById(R.id.base_root_view);
        mOverlay = getViewById(R.id.base_overlay);
        initTitleBar();
        mInflater.inflate(resId, mActivityContent, true);
    }

    /**
     * 初始化标题栏
     */
    private void initTitleBar() {
        mTbHelper = new ToolBarHelper(mInstance, mTitleLayout);
        mTbHelper.setCustomView(R.layout.toolbar_default_custom);
        mToolBar = mTbHelper.getToolBar();
        mTvTitle = getViewById(R.id.toolbar_mid_title, mToolBar);
        mToolBar.setTitleTextColor(Color.WHITE);
        mToolBar.setSubtitleTextColor(Color.WHITE);
        mToolBar.setTitle("");
        mTbHelper.setSupportActionBar(mInstance, mToolBar);
        mActionBar = mTbHelper.getActionBar();

    }

    protected void setTitleText(String title) {
        mTvTitle.setText(title);
    }

    protected void visibleToolbarLeftIcon(@DrawableRes int resId) {
        mActionBar.setHomeAsUpIndicator(resId);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
    }

    protected void hideTitleBar() {
        if (mActionBar != null)
            mActionBar.hide();
    }

    protected void showTitleBar() {
        if (mActionBar != null) {
            mActionBar.show();
        }

    }

    protected void showLoading() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    protected void dismissLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    //遮罩层的显示和隐藏
    protected void visibleOverlay(boolean visible) {
        if (visible) {
            mOverlay.setVisibility(View.VISIBLE);
        } else {
            mOverlay.setVisibility(View.GONE);
        }
    }

    protected void toast(String msg) {
        if (mInstance == null)
            return;
        ActivityUtils.toast(mInstance, msg);
    }

    protected void gotoActivity(Class<?> clazz) {
        if (mInstance == null)
            return;
        ActivityUtils.startActivity(mInstance, clazz);
    }

    @Override
    public boolean onSupportNavigateUp() {
        back();
        return true;
    }

    protected void back() {
        onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        mPresenter.attach((V) this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.dettach();
        BaseApplication.getInstance().removeActivity(this);
        super.onDestroy();
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
     * 查找指定View的子控件
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id, View view) {
        return (VT) view.findViewById(id);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

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


//    /**
//     * 处理htttp请求返回结果
//     */
//    protected abstract void progressResult(Message msg);


//    public class UiHandler extends Handler {
//        WeakReference<Activity> context;
//
//        public UiHandler(Activity context) {
//            this.context = new WeakReference<Activity>(context);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            //当页面finish掉后，不执行队请求结果的处理
//            if (context == null || context.get() == null)
//                return;
//            progressResult(msg);
//        }
//    }
}
