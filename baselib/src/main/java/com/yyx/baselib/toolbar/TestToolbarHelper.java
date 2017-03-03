package com.yyx.baselib.toolbar;

/**
 * Created by Administrator on 2017/2/23.
 */

public class TestToolbarHelper<T extends BaseToolBar> {
    private T mOperTb;  //toolbar的操作对象

    public void setOperationToolBar(T oper){
        mOperTb = oper;
    }

    public T getOper(){
        return mOperTb;
    }
}
