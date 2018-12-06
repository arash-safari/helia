package com.safari.arash.helia.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.safari.arash.helia.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by shayan on 6/30/2017.
 * base class for send request to server
 */

public abstract class BaseApiConnection implements Response.ErrorListener, Response.Listener<JSONObject> {
    protected static final String AUTHORISATION = "X-API-TOKEN";
    protected static final String TOKEN = "X-API-TOKEN";
    protected static final String VERSION = "X-VERSION";
    protected static final String PHONE = "X-MOBILE";

    public enum RequestType {
        JSON_OBJECT, JSON_ARRAY, STRING
    }

    private RequestType type = RequestType.STRING;

    public void setType(RequestType type) {
        this.type = type;
    }

    /**
     * base url of the api
     */
    protected String baseUrl = "https://mobin.co.ir/drapp/";

    /**
     * TAG for log
     */
    private final String TAG = getClass().getSimpleName();
    /**
     * context for create RequestQueue
     */
    private final Context context;
    /**
     * interface to send error or response to activity
     */
    protected BaseApiInterface mApiInterface = new BaseApiInterface() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, "onResponse: no Listener set");
        }

        @Override
        public void onError(String message) {
            Log.d(TAG, "onError: no listener set");
        }

        @Override
        public void onLogout() {
            Log.d(TAG, "onError: no listener set");
        }
    };

    private JSONObject jsonParams = new JSONObject();

    public void setJsonParams(@NonNull JSONObject jsonParams) {
        this.jsonParams = jsonParams;
    }


//    private RetryPolicy policy = new RetryPolicy() {
//        int tryCount=0;
//        @Override
//        public int getCurrentTimeout() {
//            return 5000;
//        }
//
//        @Override
//        public int getCurrentRetryCount() {
//            return tryCount;
//        }
//
//        @Override
//        public void retry(VolleyError error) throws VolleyError {
//            tryCount++;
//            //getRequestQueue().cancelAll(null);
//        }
//    };
    private int MY_SOCKET_TIMEOUT_MS=10000;
    private RetryPolicy policy = new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    private Response.Listener<String> stringListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            reset();
            Log.d(TAG, "onResponse: " + response);
            mApiInterface.onResponse(response);
        }
    };

    /**
     * volley RequestQueue
     */
    private RequestQueue mRequestQueue;

    /**
     * request method
     */
    private int method = Request.Method.GET;
    /**
     * headers of the request
     */
    private Map<String, String> headers = new HashMap<>();
    /**
     * post or patch parameters
     */
    private Map<String, String> postParams = new HashMap<>();

    /**
     * constructor for Api connection
     *
     * @param context for create RequestQueue
     */
    public BaseApiConnection(Context context) {

        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    public BaseApiConnection setmApiInterface(BaseApiInterface mApiInterface) {
        this.mApiInterface = mApiInterface;
        return this;
    }

    /**
     * adds a new header
     *
     * @param key   is header key
     * @param value is header value
     */
    protected void addHeader(String key, String value) {
        headers.put(key, value);
    }

    /**
     * adds a new parameter to post or patch request
     *
     * @param key   is parameter key
     * @param value is parameter value
     */
    public void addPostParam(String key, String value) {
        postParams.put(key, value);
        Log.d("postParams", postParams.toString());
    }

    protected Map<String, String> getPostParams() {
        return postParams;
    }

    /**
     * sets header
     *
     * @param headers is a HashMap
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * sets postParams
     *
     * @param postParams is a HashMap
     */
    public void setPostParams(Map<String, String> postParams) {
        this.postParams = postParams;
    }

    /**
     * sets request url
     */
    public abstract String getUrl();

    /**
     * sets request method
     *
     * @param method is a integer and can be any of Method.GET or Method.POST or Method.DELETE or
     *               Method.PATCH or Method.PUT or ...
     */
    public void setMethod(int method) {
        this.method = method;

    }

    /**
     * Singleton for RequestQueue
     *
     * @return requestQueue
     */
    protected RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    /**
     * starts to send request to server
     */
    public void start() {
        Log.d(TAG, "url: " + getUrl());
        switch (type) {
            case STRING:
                sendStringRequest();
                break;
            case JSON_ARRAY:
                break;
            case JSON_OBJECT:
                sendJSONObjectRequest();
                break;
        }


    }


    private void sendJSONObjectRequest() {
        JsonObjectRequest request = new JsonObjectRequest(method, getUrl(), getJsonPostParams(), this, this) {
            @Override
            public Map<String, String> getHeaders() {
                return getDefaultHeader();
            }
        };


        request.setRetryPolicy(policy);

        getRequestQueue().add(request);
    }

    private void sendStringRequest() {
        StringRequest request = new StringRequest(method, getUrl(), stringListener, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return getDefaultPostParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getDefaultHeader();
            }
        };
        request.setRetryPolicy(policy);
        getRequestQueue().add(request);
    }

    /**
     * resets the Request method and header and post parameters
     */
    private void reset() {
        method = Request.Method.GET;
        headers.clear();
        postParams.clear();
    }

    /**
     * when request fails this method is called
     *
     * @param error is error of request that should be determined
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(TAG, "onErrorResponse: ");
        error.printStackTrace();
        reset();
        if (error != null && error.networkResponse != null) {
            Log.d(TAG, "error Response: " + new String(error.networkResponse.data));
            try {
                JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                int err = jsonObject.getInt("err");
                mApiInterface.onError(context.getString(ErrorHandler.getErrorStirng(err)));
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (error instanceof NoConnectionError) {
            Log.d(TAG, "onErrorResponse: no connection");
            mApiInterface.onError(context.getString(R.string.error_noConnection));
            return;
        }
        if (error instanceof TimeoutError) {
            Log.d(TAG, "onErrorResponse: Timeout Error");
            mApiInterface.onError(context.getString(R.string.error_timeout));
            return;
        }
        if (error instanceof ServerError) {
            Log.d(TAG, "onErrorResponse: server error");
            if (error.networkResponse != null)
                Log.d(TAG, new String(error.networkResponse.data));
            mApiInterface.onError(context.getString(R.string.error_server));
            return;
        }
        if (error instanceof NetworkError) {
            Log.d(TAG, "onErrorResponse: network error");
            mApiInterface.onError(context.getString(R.string.error_network));
            return;
        }
        if (error instanceof AuthFailureError) {
            mApiInterface.onLogout();
            return;
        }
        if (error == null || error.networkResponse == null) {
            Log.d(TAG, "onErrorResponse: unknown error");
            mApiInterface.onError(context.getString(R.string.error_unknown));
            return;
        }
        //// TODO: 6/30/2017 error should be handled
        if (error.networkResponse.statusCode == 401) {
            mApiInterface.onLogout();
        }

        mApiInterface.onError("");
    }

    /**
     * when request send successfully this method will be called
     *
     * @param response id response of request
     */
    @Override
    public void onResponse(JSONObject response) {
        reset();
        Log.d(TAG, "onResponse: " + response);
        try {
            if (response.has("err") && response.getInt("err") != 0) {

                mApiInterface.onError("");
                return;
            }
        } catch (JSONException e) {
            Log.d(TAG, "Error on Response: ");
            mApiInterface.onError(e.getMessage());
            //e.printStackTrace();
            //mApiInterface.onError("خطای سرور");
        }
        mApiInterface.onResponse(response.toString());

    }

    public Map<String, String> getDefaultPostParams() {

        Log.d(TAG, "getDefaultPostParams: " + postParams);
        return postParams;
    }

    protected Map<String, String> getDefaultHeader() {
        headers.put(VERSION, getAppVersion());
        headers.put("Content-Type", "application/json");
        headers.put("Cache-Control", "no-cache");
        Log.d(TAG, "getDefaultHeader: " + headers);
        return headers;
    }

    private String getAppVersion() {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
    }


    public String getBaseUrl() {
        return baseUrl;
    }

    public JSONObject getJsonPostParams() {

        for (String key : postParams.keySet()) {
            try {
                jsonParams.put(key, postParams.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("PostParams", jsonParams.toString());
        return jsonParams;
    }
}
