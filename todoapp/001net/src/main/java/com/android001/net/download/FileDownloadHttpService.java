package com.android001.net.download;

import com.android001.net.base.IHttpListener;
import com.android001.net.base.IHttpService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
* class design：
* @author android001
* created at 2017/8/28 上午8:08
 * 需要实现：1>网络核心：httpClient;2>接口参数实例化
*/

public class FileDownloadHttpService implements IHttpService {


    //三个请求参数
    private String url;
    private Map<String,String> headerMap = Collections.synchronizedMap(new HashMap<String, String>());
    private byte[] requestData;
    //三个操作执行
    private AtomicBoolean execute = new AtomicBoolean(false);
    private AtomicBoolean pause = new AtomicBoolean(false);
    private AtomicBoolean cancel = new AtomicBoolean(false);
    //一个监听
    private IHttpListener httpListener;


    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void addRequestHeader(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    @Override
    public void addRequestData(byte[] requestData) {
        this.requestData = requestData;
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isExecute() {
        return false;
    }

    @Override
    public void pause() {

    }

    @Override
    public boolean isPause() {
        return false;
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isCancel() {
        return false;
    }

    @Override
    public void setHttpListener(IHttpListener httpListener) {

    }
}
