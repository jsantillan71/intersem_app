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

public class ApiService extends Request<JSONObject> {
    private Response.Listener<JSONObject> listener;
    private Map<String, String> headers;
    private Map<String, String> params;
    private String body;

    public ApiService(int method, String url, Map<String, String> params, Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    public ApiService(int method, String url, Map<String, String> params, Map<String, String> headers, Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        this.headers = headers;
    }

    @Override
    protected String getParamsEncoding() {
        return "utf-8";
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    public byte[] getBody() throws AuthFailureError {
        return body != null ? params.toString().getBytes() : super.getBody();
    }

    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
        return params != null ? params : super.getParams();
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
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
