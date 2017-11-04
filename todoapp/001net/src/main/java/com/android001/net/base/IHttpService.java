package com.android001.net.base;

import java.util.Map;

/**
 * 设置url,添加请求头,,发起请求,暂停(是否暂停),取消(是否取消),设置监听
*/
public interface IHttpService
{
    //三个请求参数:url、请求头header、请求体data
    void setUrl(String url);

    void addRequestHeader(Map<String,String> headerMap);// Map<String,String> getHttpHeadMap()

    void addRequestData(byte[] requestData);

    //这里可能还需要一个requestMethod:get||post

    // 三个操作：执行、暂停、取消
    void execute();
    boolean isExecute();

    void pause();
    boolean isPause();

    void cancel();
    boolean isCancel();

    // 一个监听：IHttpListener
    void setHttpListener(IHttpListener httpListener);

}
