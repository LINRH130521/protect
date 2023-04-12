package com.jiafei.test;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpNetwork {
    private static final String ApiKey ="h=ea0UG=PDxXAFUpcvP=UfFud8E=";
    public String PostDataMethod(String commd){
        OkHttpClient client=new OkHttpClient();
        String url=String.format("http://api.heclouds.com/v1/synccmds?device_id=957047544&timeout=30");

        String body=String.format(commd);
        Log.w("url",url);
        Log.w("body",body);
        RequestBody bodyJson=RequestBody.create(MediaType.parse("application/json;charset=utf-8"),body);
        Request request = new Request.Builder().url("http://api.heclouds.com/cmds?device_id=957047544").post(bodyJson).header("api-key", ApiKey).build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("e","post请求失败");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
        Response response=null;
        String responseData="";
        try{
            response=client.newCall(request).execute();
            String jsonResStr=response.body().string();
            Log.e("onenet回应数据",jsonResStr);
            JSONObject jsonObject=new JSONObject(jsonResStr);
            responseData=jsonObject.getString("error");
            Log.e("val",responseData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseData;
    }

}



