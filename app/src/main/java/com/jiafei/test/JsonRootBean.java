package com.jiafei.test;

public class JsonRootBean {
    private int errno;
    private Data data;
    public Data getData(){return data;}
    private String error;

    public int getErrno() {
        return errno;
    }

    public String getError() {
        return error;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setError(String error) {
        this.error = error;
    }
}
