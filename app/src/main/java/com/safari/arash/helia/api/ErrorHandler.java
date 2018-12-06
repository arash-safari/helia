package com.safari.arash.helia.api;

import com.safari.arash.helia.R;

/**
 * Created by shayan4shayan on 3/15/18.
 */

public class ErrorHandler {
    public static int getErrorStirng(int code) {
        switch (code) {
            case 0:
                return R.string.no_error;
            case 1:
                return R.string.error_parameter_wrong;
            case 2:
                return R.string.error_json_format;
            case 3:
                return R.string.error_server;
            case 4:
                return R.string.error_status;
            case 5:
                return R.string.error_application;
            case 6:
                return R.string.error_timeout;
            default:
                return R.string.error_unknown;
        }
    }
}
