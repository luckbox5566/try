package lock.luckbox.com.update;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dantevsyou on 2017/8/4.
 * 本Manager是去指定地址下载apk文件并安装
 */

 class UpdateManager {

    private static final String FILE_PATH= Environment.getExternalStorageDirectory()+"/" + "AutoUpdate" +"/";;
    private String FILE_NAME="";
    private Context context;
    private String version_path="";
    private String apk_path="";
    private ProgressDialog progressDialog;
    private String version;
    // 准备安装新版本应用标记
    private static final int INSTALL_TOKEN = 1;

    public UpdateManager(Context context,String apk_path,String version) {
        this.apk_path = apk_path;
        this.context = context;
        this.version=version;
        this.FILE_NAME=FILE_PATH+"ydlc"+version+".apk";
        this.apk_path=apk_path+FILE_NAME;
    }

    public void pd(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client =  new OkHttpClient.Builder()
                        .build();
                Request request = new Request.Builder()
                        .url("http://172.16.2.40/download/version.xml")
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
    }


    public void start(){
        showDownloadDialog();
    }

    public void showDownloadDialog() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("正在更新...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        new downloadAsyncTask().execute();
    }

    /**
     * 下载新版本应用
     */
    private class downloadAsyncTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {

            URL url;
            HttpURLConnection connection = null;
            InputStream in = null;
            FileOutputStream out = null;
            try {
                url = new URL(apk_path);
                connection = (HttpURLConnection) url.openConnection();

                in = connection.getInputStream();
                long fileLength = connection.getContentLength();
                File file_path = new File(FILE_PATH);
                if (!file_path.exists()) {
                    file_path.mkdir();
                }

                out = new FileOutputStream(new File(FILE_NAME));
                byte[] buffer = new byte[1024 * 1024];
                int len = 0;
                long readLength = 0;


                while ((len = in.read(buffer)) != -1) {

                    out.write(buffer, 0, len);
                    readLength += len;
                    int curProgress = (int) (((float) readLength / fileLength) * 100);
                    publishProgress(curProgress);

                    if (readLength >= fileLength) {

                        break;
                    }
                }

                out.flush();
                return INSTALL_TOKEN;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {

            progressDialog.dismiss();//关闭进度条
            //安装应用
            installApp();
        }
    }

    /**
     * 安装新版本应用
     */
    private void installApp() {
        File appFile = new File(FILE_NAME);
        if (!appFile.exists()) {
            return;
        }
        // 跳转到新版本应用安装页面
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + appFile.toString()), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
