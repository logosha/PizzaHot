package com.google.pizzahot.model;

/**
 * Created by Алексей on 28.10.2016.
 */

public class FoursquareResponse {

    private Meta meta;
    private Response response;


    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
