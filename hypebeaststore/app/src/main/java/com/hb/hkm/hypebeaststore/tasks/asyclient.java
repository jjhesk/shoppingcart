package com.hb.hkm.hypebeaststore.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.hb.hkm.hypebeaststore.Controllers.Config;
import com.hb.hkm.hypebeaststore.Controllers.DataBank;
import com.hb.hkm.hypebeaststore.datamodel.output;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

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

    public interface callback {
        public void onSuccess(final String data);

        public void onFailure(final String message);

        public void beforeStart(final asyclient task);
    }

    public boolean isSuccess(String str) throws JSONException {
        final JSONObject js = new JSONObject(str);
        final boolean t = js.getBoolean("success");
        return t;
    }

    private String getProductRawJson(final String data) throws JSONException {
        final JSONObject js = new JSONObject(data);
        // final String t = js.getJSONArray("products").toString();
        final String t = js.getString("products");
        Log.d(TAG, t);
        return t;
    }

    public asyclient(Context ccc, callback cb) {
        httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
        HttpConnectionParams.setSoTimeout(httpParams, 5000);
        ctx = ccc;
        mcallback = cb;
        // text_mc = ccc.getResources().getString(R.string.mock_dt);
        // Log.d(TAG, text_mc);
    }

    private String text_mc;

    public asyclient setURL(String e) {
        url = e;
        return this;
    }

    private void setError(String e) {
        isError = true;
        errorMessage = e;
    }

    private void GSONParser(final String data) throws JsonSyntaxException, JsonIOException, JsonParseException {
        final GsonBuilder gb = new GsonBuilder();
        //gb.registerTypeAdapterFactory(new GTool.NullStringToEmptyAdapterFactory());
        final Gson g = gb.create();

       /*
       DataBank.product_master_list = g.fromJson(data, new TypeToken<ArrayList<Product>>() {
       }.getType());
        */
        final JsonReader reader = new JsonReader(new StringReader(data.trim()));
        reader.setLenient(true);
        output output_time = g.fromJson(reader, output.class);
        Log.d(TAG, output_time.toString());
        DataBank.product_master_list = output_time;
    }

    private Response exe_command() throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("User-Agent", Config.setting.useragent)
                .build();
        final Response response = client.newCall(request).execute();
        return response;
    }

    abstract protected void ViewConstruction();

    @Override
    protected String doInBackground(Void... params) {
        String out = "";
        try {
            Response r = exe_command();
            if (r.code() == 200) {
                out = r.body().string();
            } else {
                throw new Exception("serer error");
            }

            GSONParser(out);
            ViewConstruction();
            out = "complete";


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
        return out;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mcallback != null) mcallback.beforeStart(this);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (mcallback != null) {
            if (isError) {
                mcallback.onFailure(errorMessage);
            } else {
                mcallback.onSuccess(result);
            }
        }
    }
}