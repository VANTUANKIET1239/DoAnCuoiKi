package com.example.doanbanhoa;


public interface Callback<T> {
    void onSuccess(T data);

    void onError(String errorMessage);
}