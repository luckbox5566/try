package app.update;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by dantevsyou on 2017/8/4.
 * 本Manager是去指定地址下载apk文件并安装
 */

 public class UpdateManager {

    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/" + "AutoUpdate" + "/";
    ;
    private String FILE_NAME = FILE_PATH;
    private Context context;
    private String version_path = "";
    private String apk_path = "";
    private ProgressDialog progressDialog;
    private String version;
    // 准备安装新版本应用标记
    private static final int INSTALL_TOKEN = 1;
    private String body = "";

    public UpdateManager(Context context, String apk_path, String version) {
        this.apk_path = apk_path;
        this.context = context;
        this.version = version;
    }

    public void pd() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient.Builder()
                        .build();
                String ip="";
                Request request = new Request.Builder()
                        .url("http://"+ip+"/download/version.xml")
                        .build();

                try {
                    System.out.print("执行请求" + "\n");
                    Response response = client.newCall(request).execute();
                    System.out.print("准备打印结果" + "\n");
                    System.out.print(response.code() + "\n");
                    //System.out.print(response.body().string()+"\n");
                    System.out.print("关闭" + "\n");
                    //Toast.makeText(context,response.body().string(),Toast.LENGTH_LONG).show();
                    body = response.body().string();
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    System.out.print("错误" + "/n");
                    e.printStackTrace();
                    //Toast.makeText(context,"无法连接服务器",Toast.LENGTH_LONG).show();
                }
            }
        }).start();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            start();
            return true;
        }
    });


    String url2 = "";

    public void start() {
        //剪切出版本号
        String version = body.substring(body.indexOf("<version>") + 9, body.indexOf("</version>"));
        FILE_NAME = FILE_NAME + body.substring(body.indexOf("<name>") + 6, body.indexOf("</name>"));
        url2 = body.substring(body.indexOf("<url>") + 5, body.indexOf("</url>"));
        ;
        System.out.print("version/url/FILE_NAME:" + version + "/" + url2 + "/" + FILE_NAME + "\n");
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            System.out.print("version/url当前版本号:" + info.versionCode + "\n");
            if (Integer.parseInt(version) > info.versionCode) {
                showDownloadDialog();

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void showDownloadDialog() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("正在更新...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        String[] params = new String[]{url2, NATIVE_SAVE_PATH, "enjoyor_ydlc_WFSL.apk"};
        new DownloadAsyncTask().execute(params);
        //new downloadAsyncTask().execute(url2);
    }

    /**
     * 安装软件
     *
     * @param apkFilePath
     * APK文件本地存储路径
     */

    private  String NATIVE_SAVE_PATH = "/app/";


    private void installApk(String apkFilePath,File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);

        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);

    }


    class DownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {

        private int per = 0;
        private ProgressDialog perDialog = null;
        private String fullPath = null;
        private File file;

        @Override
        protected Boolean doInBackground(String... params) {
            fullPath =  params[2];
            try {
                URL url = new URL(params[0]);
                HttpURLConnection huc = (HttpURLConnection) url
                        .openConnection();
                huc.setConnectTimeout(10 * 1000);
                huc.connect();
                if (huc.getResponseCode() == 200) {
                    perDialog.setMax(huc.getContentLength());

                    /*File path = new File(params[1]);
                    if (!path.exists()) {
                        path.mkdirs();
                    }*/
                    File apkFile = null;
                    makeRootDirectory(params[1]);
                    try {
                        apkFile = new File(params[1] + params[2]);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //file=new File(context.getFilesDir(),params[2]);
                   /* String s=file.getPath();
                    System.out.print("lujin:"+file.getPath());*/
//                    File apkFile = new File(path, params[2]);
//                    if (!apkFile.exists()) {
//                        apkFile.createNewFile();



                    
//                    }

                    InputStream is = huc.getInputStream();
                    file=getAlbumStorageDir(params[2]);
                    //FileOutputStream fos = context.openFileOutput(params[2], Context.MODE_PRIVATE);
                    FileOutputStream fos=new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int readSize;
                    while (true) {
                        readSize = is.read(buf);
                        if (readSize <= 0) {
                            break;
                        }
                        per += readSize;
                        this.publishProgress(per);
                        fos.write(buf, 0, readSize);
                    }
                    fos.close();
                    is.close();
                    return true;
                } else {
                    System.out.print("下载错误：");
                    return false;

                }
            } catch (MalformedURLException e) {
                System.out.print("下载错误：" + e);
                return false;
            } catch (IOException e) {
                System.out.print("下载错误：" + e);
                return false;

            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            perDialog.dismiss();
            if (result) {
                Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT)
                        .show();
                installApk(fullPath,file);
            } else {
                Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            perDialog = new ProgressDialog(context);
            perDialog.setTitle("软件下载");
            perDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            perDialog.setCancelable(false);
            perDialog.setCanceledOnTouchOutside(false);
            perDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            perDialog.setProgress(values[0]);
        }
    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }

    }



    public File getAlbumStorageDir(String albumName) {
       // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);

        return file;
    }
}