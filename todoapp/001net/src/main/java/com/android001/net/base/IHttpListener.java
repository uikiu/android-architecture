package com.android001.net.base;
/**
* class design：
* @author android001
* created at 2017/8/28 上午8:04
*/

public interface IHttpListener<T> {

    public void onSuccess(T t);

    public void onFail();//暂时使用空参数列表，未来是Resp
}
