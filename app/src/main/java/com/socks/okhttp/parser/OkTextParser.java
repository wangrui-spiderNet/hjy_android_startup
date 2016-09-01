package com.socks.okhttp.parser;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zhaokaiqiang on 15/11/22.
 */
public class OkTextParser extends OkBaseParser<String> {

    @Override
    public String parse(Response response) throws IOException {

        if (response.isSuccessful()) {
            return response.body().string();
        }

        return null;
    }
}