package com.jiafei.test;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    HttpNetwork httpNetwork = new HttpNetwork();
    String openRes;
    private ScrollView indoorlayout;
    private TextView titletetx;
    private TextView titleupdata;
    private TextView wendutext;
    private TextView shidutext;
    private TextView ranqitext;
    private TextView temptext;
    private TextView humitext;
    private TextView gastext;
    private TextView tempAdvicettext;
    private TextView humiAdvicetext;
    private TextView gasAdvicetext;

    private static final String DeviceID ="957047544";
    private static final String ApiKey ="h=ea0UG=PDxXAFUpcvP=UfFud8E=";
    private static final String key1 ="CS";
    private static final String key2 ="RHS";
    private static final String key3 ="GAS";
    String value1,value2,value3;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor);

        indoorlayout=(ScrollView) findViewById(R.id.indoor_layout);
        titletetx=(TextView) findViewById(R.id.title_text);
        titleupdata=(TextView) findViewById(R.id.updata_time);
        wendutext=(TextView) findViewById(R.id.wendu_text);
        shidutext=(TextView) findViewById(R.id.shidu_text);
        ranqitext=(TextView) findViewById(R.id.ranqi_text);
        temptext=(TextView) findViewById(R.id.temp_textp);
        humitext=(TextView) findViewById(R.id.humi_text);
        gastext=(TextView) findViewById(R.id.gas_text);
        tempAdvicettext=(TextView) findViewById(R.id.tempAdvicet_text);
        humiAdvicetext=(TextView) findViewById(R.id.humiAdvice_text);
        gasAdvicetext=(TextView) findViewById(R.id.gasAdvice_text);

        InitDate();
        Get();
    }

    private void InitDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        titletetx.setText(String.format("日期"));
        titleupdata.setText(date);
        Values.startDateInfor=date+"T"+ systemTime.getNowTime();
        Log.i("时间初始化","现在的时间是："+date);
    }


    public void Get(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        OkHttpClient client=new OkHttpClient();
                        Request request=new Request.Builder().url("http://api.heclouds.com/devices/"+DeviceID+"/datapoints?datastream_id="+key1).header("api-key",ApiKey).build();
                        Response response=client.newCall(request).execute();
                        String responseData =response.body().string();
                        parseJSONWithGSON(responseData);

                        JsonRootBean app =new Gson().fromJson(responseData,JsonRootBean.class);
                        List<Datastreams> streams=app.getData().getDatastreams();
                        List<Datapoints> points=streams.get(0).getDatapoints();
                        value1=points.get(0).getValue();
                        temptext.post(new Runnable() {
                            @Override
                            public void run() {
                                temptext.setText(String.format("温度：%s℃",value1));
                            }
                        });
                        Integer num1 = Integer.parseInt(value1);
                        if(num1>30){
                            tempAdvicettext.post(new Runnable() {
                                @Override
                                public void run() {
                                    tempAdvicettext.setText(String.format("当室内温度较高，汗流浃背可能会影响您的生活，打开风扇或空调，享受凉快的生活吧！"));
                                    wendutext.setText(String.format("热"));
                                }
                            });
                        }
                        else if(num1<10){
                            tempAdvicettext.post(new Runnable() {
                                @Override
                                public void run() {
                                    tempAdvicettext.setText(String.format("当前室内温度较低，丝丝寒冷会扰乱您的心意，不如穿好冬衣或打开暖风机，以防着凉，享受温暖的生活吧！"));
                                    wendutext.setText(String.format("冷"));
                                }
                            });
                        }
                        else{
                            tempAdvicettext.post(new Runnable() {
                                @Override
                                public void run() {
                                    tempAdvicettext.setText(String.format("当前是室内温度正常，请您根据当前体感穿衣，若天气晴朗，不如到户外锻炼健身，享受健康生活！"));
                                    wendutext.setText(String.format("正常"));
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        OkHttpClient client=new OkHttpClient();
                        Request request=new Request.Builder().url("http://api.heclouds.com/devices/"+DeviceID+"/datapoints?datastream_id="+key2).header("api-key",ApiKey).build();
                        Response response=client.newCall(request).execute();
                        String responseData =response.body().string();
                        parseJSONWithGSON(responseData);

                        JsonRootBean app =new Gson().fromJson(responseData,JsonRootBean.class);
                        List<Datastreams> streams=app.getData().getDatastreams();
                        List<Datapoints> points=streams.get(0).getDatapoints();
                        value2=points.get(0).getValue();
                        humitext.post(new Runnable() {
                            @Override
                            public void run() {
                                humitext.setText(String.format("湿度：%s",value2));}//wendutext.setText(String.format("热"));
                        });
                        Integer num2 = Integer.parseInt(value2);
                        if(num2>80){
                            humiAdvicetext.post(new Runnable() {
                                @Override
                                public void run() {
                                    humiAdvicetext.setText(String.format("当前室内湿度较高，可能会给您带来不适感觉，请打开除湿器，或是挂上除湿袋，注重除湿除霉。"));
                                    shidutext.setText(String.format("湿"));
                                }
                            });
                        }
                        else if(num2<30){
                            humiAdvicetext.post(new Runnable() {
                                @Override
                                public void run() {
                                    humiAdvicetext.setText(String.format("当前室内湿度较低，会让您感到口舌干燥，咽痛，鼻腔出血等不适症状，请打开加湿器调整湿度。"));
                                    shidutext.setText(String.format("干"));
                                }
                            });
                        }
                        else{
                            humiAdvicetext.post(new Runnable() {
                                @Override
                                public void run() {
                                    humiAdvicetext.setText(String.format("当前室内湿度正常，体感较为舒适。"));
                                    shidutext.setText(String.format("正常"));
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        OkHttpClient client=new OkHttpClient();
                        Request request=new Request.Builder().url("http://api.heclouds.com/devices/"+DeviceID+"/datapoints?datastream_id="+key3).header("api-key",ApiKey).build();
                        Response response=client.newCall(request).execute();
                        String responseData =response.body().string();
                        parseJSONWithGSON(responseData);

                        JsonRootBean app =new Gson().fromJson(responseData,JsonRootBean.class);
                        List<Datastreams> streams=app.getData().getDatastreams();
                        List<Datapoints> points=streams.get(0).getDatapoints();
                        value3=points.get(0).getValue();
                        gastext.post(new Runnable() {
                            @Override
                            public void run() {
                                gastext.setText(String.format("燃气：%s",value3));
                            }
                        });
                        Integer num3 = Integer.parseInt(value3);
                        if(num3>90){
                            gasAdvicetext.post(new Runnable() {
                                @Override
                                public void run() {
                                    gasAdvicetext.setText(String.format("当前室内检测到燃气浓度较高，请您及时检查燃气是否关闭，管道线路是否有破损，安全规范地使用燃气。"));
                                    ranqitext.setText(String.format("浓度高"));
                                }
                            });
                        }
                        else{
                            gasAdvicetext.post(new Runnable() {
                                @Override
                                public void run() {
                                    gasAdvicetext.setText(String.format("当前室内可燃气体浓度正常，无安全隐患。"));
                                    ranqitext.setText(String.format("正常"));
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData){
        JsonRootBean app =new Gson().fromJson(jsonData,JsonRootBean.class);
        List<Datastreams> streams =app.getData().getDatastreams();
        List<Datapoints> points =streams.get(0).getDatapoints();
        int count=app.getData().getCount();
        for (int i=0;i< points.size();i++){
            String time=points.get(i).getAt();
            String value =points.get(i).getValue();
            Log.w("www","time=" +time);
            Log.w("www","value=" +value);
        }
    }

}