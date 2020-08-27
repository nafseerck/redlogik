package com.redlogic.utils.api.listeners;


import com.redlogic.utils.api.models.ErrorObject;

public interface RetrofitListener {
    void onResponseSuccess(String responseBodyString);

    void onResponseError(ErrorObject errorObject);

}
