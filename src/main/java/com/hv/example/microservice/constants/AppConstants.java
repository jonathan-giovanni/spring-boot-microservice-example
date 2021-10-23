package com.hv.example.microservice.constants;

public class AppConstants {

    private AppConstants(){
        //empty
    }

    public static final String ERROR                                = "ERROR";
    public static final String RESULT                               = "RESULT";
    public static final String EXCEPTION                            = "An exception was throw: %s";
    public static final String CLIENT_EXCEPTION                     = "An exception was throw when external service was called";

    public static final String MSG_PROCESS_STARTED                  = "Process started: %s";
    public static final String MSG_PROCESS_ENDED                    = "Process ended with headers: %s";
    public static final String SERVICE_CODE                         = "service-code";
    public static final String SERVICE_MSG                          = "service-message";

}
