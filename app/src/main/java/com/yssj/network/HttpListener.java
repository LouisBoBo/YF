package com.yssj.network;


import com.google.gson.reflect.TypeToken;
import com.yssj.utils.LogYiFu;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by dzc on 15/12/10.
 */
public abstract class HttpListener<T> {


    public abstract void onSuccess(T result);

    public abstract void onError();


    Type getType() {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (type instanceof Class) {
            return type;
        } else {
            return new TypeToken<T>() {
            }.getType();

        }
    }
}
