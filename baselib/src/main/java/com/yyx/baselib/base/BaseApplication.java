package com.yyx.baselib.base;

import android.app.Activity;
import android.app.Application;
import android.support.compat.BuildConfig;


import com.yyx.baselib.utils.LogUtils;

import java.util.Stack;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * Created by Administrator on 2016/2/29.
 */
public class BaseApplication extends Application {

    private Stack<Activity> mActivities;
    private static BaseApplication mInstance;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mActivities = new Stack<>();
        init();
    }


    private void init() {
        //是否打印日志
        LogUtils.isDebug = BuildConfig.DEBUG;
        BGASwipeBackManager.getInstance().init(this);
    }

    public Stack<Activity> getActivities(){
        return mActivities;
    }

    /**
     * 添加到Activity栈中
     * @param activity
     */
    public void addActivity(Activity activity){
        mActivities.push(activity);
    }

    /**
     * 结束Activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        activity.finish();
        mActivities.remove(activity);
    }

    /**
     * 移除Activity
     * @param activity
     */
    public void removeActivity(Activity activity){
        mActivities.remove(activity);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity(){
        while (mActivities.size() > 0){
            mActivities.pop().finish();
        }
    }

    /**
     * 返回到指定的页面
     */
    public void backToActivity(Class<?> clazz){
        while (mActivities.size() > 0){
            Activity a = mActivities.lastElement();
            if (a.getClass().getSimpleName().equals(clazz.getSimpleName())){
                break;
            }
            a.finish();
            mActivities.remove(a);
        }
    }

}
