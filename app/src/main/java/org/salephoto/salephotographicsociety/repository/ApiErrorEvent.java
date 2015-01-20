package org.salephoto.salephotographicsociety.repository;

import retrofit.RetrofitError;

public class ApiErrorEvent {
    private RetrofitError error;

    public ApiErrorEvent(final RetrofitError error) {
        this.error = error;
    }

    public RetrofitError getError() {
        return error;
    }

}
