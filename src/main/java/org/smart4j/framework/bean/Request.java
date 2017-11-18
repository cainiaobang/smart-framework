package org.smart4j.framework.bean;

public class Request {

    private String requestMethod;
    private String requestPath;

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {

        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public Request(String requestMethod, String requestPath){
        this.requestMethod=requestMethod;
        this.requestPath=requestPath;

    }
}
