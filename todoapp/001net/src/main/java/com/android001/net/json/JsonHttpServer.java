package com.android001.net.json;

import com.android001.net.base.IHttpListener;
import com.android001.net.base.IHttpService;

import java.util.Map;



public class JsonHttpServer implements IHttpService {

    private IHttpListener httpListener;
    private String url;
    private String requestMethod;
    private byte[] requestData;


    @Override
    public void setUrl(String url) {

    }

    @Override
    public void addRequestHeader(Map<String, String> headerMap) {

    }

    @Override
    public void addRequestData(byte[] requestData) {

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
