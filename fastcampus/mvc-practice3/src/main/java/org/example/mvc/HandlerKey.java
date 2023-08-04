package org.example.mvc;

import org.example.mvc.controller.RequestMethod;

public class HandlerKey {
    private final RequestMethod requestMethod;
    private final String uriPath;

    public HandlerKey(RequestMethod requestMethod, String uriPath) {
         this.requestMethod = requestMethod;
         this.uriPath = uriPath;
    }
}
