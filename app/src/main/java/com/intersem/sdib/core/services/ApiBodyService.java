package com.intersem.sdib.core.services;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ApiBodyService extends Request<JSONObject> {
    private Response.Listener<JSONObject> listener;
    private Map<String, String> headers;
    private String body;

    public ApiBodyService(int method, String url, String body, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = responseListener;
        this.body = body;
    }

    public ApiBodyService(int method, String url, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = responseListener;
        this.body = body;
    }


    public ApiBodyService(int method, String url, String body, Map<String, String> headers, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = responseListener;
        this.body = body;
        this.headers = headers;
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    public byte[] getBody() throws AuthFailureError {
        try {
            return body.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            return super.getBody();
        }
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        // TODO Auto-generated method stub
        listener.onResponse(response);
    }
}
