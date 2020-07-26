package com.nepumuk.notizen.objects;

public class UnpackingDataError extends Error{

    public UnpackingDataError(String message, Exception ex){
        super(message, ex);
    }
}
