package com.example.felix.notizen.objects;

public class UnpackingDataError extends Error{

    public UnpackingDataError(Exception ex){
        super(ex);
    }

    public UnpackingDataError(String message, Exception ex){
        super(message, ex);
    }
}
