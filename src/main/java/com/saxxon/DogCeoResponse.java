package com.saxxon;

import com.google.gson.JsonElement;

public class DogCeoResponse {
    /* The message format must be JsonElement and not more strongly typed. Why? Because
    *  if there was an error processing the request, the property type will be String instead
    *  of whatever kind of data it is we are looking for. These cannot be automatically
    *  converted, so we handle this further down the chain. */
    public final JsonElement message;
    public final String status;

    public DogCeoResponse(JsonElement message, String status) {
        this.message = message;
        this.status = status;
    }
}
