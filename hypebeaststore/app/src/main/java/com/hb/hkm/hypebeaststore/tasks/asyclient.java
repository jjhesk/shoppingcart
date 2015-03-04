package com.hb.hkm.hypebeaststore.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.hb.hkm.hypebeaststore.life.Config;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by hesk on 2/3/15.
 */
public abstract class asyclient extends AsyncTask<Void, Void, String> {


    protected boolean isError = false;
    protected String errorMessage, submission_body_json, url;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    protected static String TAG = "call.api";
    protected Context ctx;
    protected HttpParams httpParams;
    protected OkHttpClient client = new OkHttpClient();
    protected callback mcallback;
    protected status _mstatus;
    protected int failure_code;

    enum status {
        IDEL, PROCESSING, COMPLETE
    }

    public interface callback {
        public void onSuccess(final String data);

        public void onFailure(final String message, final int code);

        public void beforeStart(final asyclient task);
    }

    public asyclient(Context ccc, callback cb) {
        httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
        HttpConnectionParams.setSoTimeout(httpParams, 5000);
        ctx = ccc;
        mcallback = cb;
        _mstatus = status.IDEL;
        // text_mc = ccc.getResources().getString(R.string.mock_dt);
        // Log.d(TAG, text_mc);
    }

    private String text_mc;

    public asyclient setURL(String e) throws Exception {
        if (e == null) return this;
        url = e;
        return this;
    }

    protected void setError(String e) {
        isError = true;
        errorMessage = e;
    }

    abstract protected void GSONParser(final String data) throws JSONException, JsonSyntaxException, JsonIOException, JsonParseException;

    private Response makeUrlRequest() throws IOException {

        Request.Builder request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("User-Agent", Config.setting.useragent);
        //according to HB new API design and spec
        if (Config.setting.APIversion == 2)
            request.header("X-Api-Version", "2.0");

        final Response response = client.newCall(request.build()).execute();
        return response;
    }

    abstract protected void ViewConstruction();


    @Override
    protected String doInBackground(Void... params) {
        String body = "";
        _mstatus = status.PROCESSING;
        try {
            Response r = makeUrlRequest();
            failure_code = r.code();
            if (failure_code == 200) {
                body = r.body().string();
            } else {
                Log.d(TAG, "error code: " + failure_code);
                Log.d(TAG, "found error on curl: " + url);
                throw new Exception("server error: " + failure_code);
            }

            GSONParser(body);
            ViewConstruction();
            body = "complete";

        } catch (JsonIOException e) {
            setError(e.getMessage());
        } catch (JsonSyntaxException e) {
            setError(e.getMessage());
        } catch (JsonParseException e) {
            setError(e.getMessage());
        } catch (NoClassDefFoundError e) {
            setError(e.getMessage());
        } catch (Exception e) {
            Log.d("work ERROR", e.getMessage());
            setError(e.getMessage());
        }
        return body;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mcallback != null) mcallback.beforeStart(this);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        _mstatus = status.COMPLETE;
        if (mcallback != null) {
            if (isError) {
                mcallback.onFailure(errorMessage, failure_code);
            } else {
                mcallback.onSuccess(result);
            }
        }
    }
}