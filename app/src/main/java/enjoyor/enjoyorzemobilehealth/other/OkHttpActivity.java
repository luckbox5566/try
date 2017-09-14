package enjoyor.enjoyorzemobilehealth.other;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import enjoyor.enjoyorzemobilehealth.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {
     TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        textView= (TextView) findViewById(R.id.ok_textview);

     new Thread(new Runnable() {
         @Override
         public void run() {

             OkHttpClient client =  new OkHttpClient.Builder()
                     .build();
             Request request = new Request.Builder()
                     .url("http://192.168.7.52:8080/my/aa")
                     //.url("http://www.baidu.com")
                     .build();

             try {
                 System.out.print("执行请求"+"\n");
                 Response response = client.newCall(request).execute();
                 System.out.print("准备打印结果"+"\n");
                 System.out.print(response.code()+"\n");
                 System.out.print(response.body().string()+"\n");
                 System.out.print("关闭"+"\n");

             } catch (IOException e) {
                 System.out.print("错误"+"/n");
                 e.printStackTrace();
             }
         }
     }).start();
      // Socket socket=

        AsyncTask asyncTask=new AsyncTask() {

            // 第一个执行方法
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                return null;
            }

            //在doInBackgroound完成后执行
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
            }

            //进度更新
            @Override
            protected void onProgressUpdate(Object[] values) {
                super.onProgressUpdate(values);
            }
        }.execute();

        handler.sendMessage(null);

        Handler xx=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };


        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler()
                {
                    public void handleMessage(android.os.Message msg)
                    {
                        Log.e("TAG",Thread.currentThread().getName());
                    }
                };
            }
        };
    }

    class MyHandler extends Handler {

        // 子类必须重写此方法，接受数据
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            Bundle bundle=msg.getData();
            String s=bundle.getString("xxx","错误");
            textView.setText(s);

        }
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

}
