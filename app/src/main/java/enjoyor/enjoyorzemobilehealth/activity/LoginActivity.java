package enjoyor.enjoyorzemobilehealth.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.entities.CaiDan;
import com.example.my_xml.entities.MyserverTime;
import com.example.my_xml.entities.YongHuXX;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import app.update.UpdateManager;
import app.update.UpdateTool;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.T_BingQuXX;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import enjoyor.enjoyorzemobilehealth.utlis.SaveUtils;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;

/**
 * 登录界面.
 */
public class LoginActivity extends LoginSildingBaseActivity {
    ZhierCall zhierCall;

    BRLB brlb;
    List<CaiDan> caidanList;
    YongHuXX yonghuXX;
    MyserverTime myserverTime;
    List<T_BingQuXX> t_bingQuXXList = new ArrayList<>();

    ProgressDialog progressDialog;
    EditText editText1, editText2;
    LinearLayout view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initData();

        UpdateManager updateManager=new UpdateManager(this,"","");
        updateManager.pd();


      /* zhierCall = (new ZhierCall())
                .setId("1000")
                .setNumber("0301706")
                .setMessage(NetWork.SYSTEM_MESSAGE)
                .setCanshu("8")
                .setContext(LoginActivity.this)
                .setPort(5000)
                .build();*/
        getSlidingMenu().setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {
                View view = getSlidingMenu().getMenu();
                EditText editText = (EditText) view.findViewById(R.id.ip);
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String name = pref.getString("ip", "192.168.7.82");//第二个参数为默认值
                editText.setText(name);

            }
        });

        getSlidingMenu().setOnClosedListener(new SlidingMenu.OnClosedListener() {
            @Override
            public void onClosed() {
                View view = getSlidingMenu().getMenu();
                EditText editText = (EditText) view.findViewById(R.id.ip);
                String ip = editText.getText().toString();

                MyApplication.getInstance().setIp(ip);
                SharedPreferences pref = LoginActivity.this.getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("ip", ip);
                editor.commit();
            }
        });

        editText1 = (EditText) findViewById(R.id.zhanghao);
        editText2 = (EditText) findViewById(R.id.mima);
        view = (LinearLayout) findViewById(R.id.view);

        initUser();

    }

    private void initUser() {
        if (SaveUtils.contains(this, Constant.USERID) && SaveUtils.contains(this, Constant.PASSWORD)){
            String userid= (String) SaveUtils.get(this, Constant.USERID, "");
            String password= (String)SaveUtils.get(this, Constant.PASSWORD, "");
            editText1.setText(userid);
            editText2.setText(password);
        }

    }

    private void initData() {
        brlb = new BRLB();
        caidanList = new ArrayList<CaiDan>();
        yonghuXX = new YongHuXX();
        myserverTime = new MyserverTime();
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正在验证用户名和密码...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @OnClick(R.id.login)
    public void login() {
        MyApplication.getInstance().setYhgh(editText1.getText().toString());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

        showProgress();

        final String s1 = editText1.getText().toString();
        final String s2 = editText2.getText().toString();
        //网络参数设置
        zhierCall = (new ZhierCall())
                .setId("1000")
                .setNumber("0300801")
                .setMessage(NetWork.SYSTEM_MESSAGE)
                .setCanshu(s1 + "," + s2 + ",0,20")
                .setContext(this)
                .setPort(5000)
                .build();
        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                SaveUtils.put(LoginActivity.this, Constant.USERID, s1);
                SaveUtils.put(LoginActivity.this, Constant.PASSWORD, s2);
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{BRLB.class, CaiDan.class, YongHuXX.class,
                                MyserverTime.class});
                //Log.d("login5",data);
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    //保存用户的工号和姓名
                    MyApplication.getInstance().setYhgh(yonghuXX.getYongHuGH());
                    MyApplication.getInstance().setYhxm(yonghuXX.getYongHuXM());

                    System.out.print("第二次请求病区信息。。。。。。" + yonghuXX.getBuMenID());
                    progressDialog.setMessage("获取病区信息...");

                    String s1 = editText1.getText().toString();
                    String s2 = editText2.getText().toString();

                    zhierCall = (new ZhierCall())
                            .setId(s1)
                            .setNumber("0301706")
                            .setMessage(NetWork.SYSTEM_MESSAGE)
                            .setCanshu(yonghuXX.getBuMenID())
                            .setContext(LoginActivity.this)
                            .setPort(5000)
                            .build();
                    t_bingQuXXList = null;
                    t_bingQuXXList = new ArrayList<>();
                    zhierCall.start(new NetWork.SocketResult() {
                        @Override
                        public void success(String data) {

                            StringXmlParser parser = new StringXmlParser(xmlHandler2,
                                    new Class[]{T_BingQuXX.class});
                            //Log.d("login5",data);
                            parser.parse(data);
                        }

                        @Override
                        public void fail(String info) {

                        }
                    });
                    //progressDialog.dismiss();
                    //startActivity(new Intent(LoginActivity.this,BrlbActivity.class));
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            brlb = (BRLB) msg.obj;
                            break;
                        case 1:
                            caidanList.add((CaiDan) msg.obj);
                            break;
                        case 2:
                            yonghuXX = (YongHuXX) msg.obj;
                            //saveToApp(yonghuXX);
                            break;
                        case 3:
                            myserverTime = (MyserverTime) msg.obj;
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    MyXmlHandler xmlHandler2 = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    System.out.print("第二次请求病区信息。。。。。。" + yonghuXX.getBuMenID());
                    progressDialog.dismiss();
                    if (t_bingQuXXList.get(0).getBuMenID().equals("1")) {
                        String s = t_bingQuXXList.get(0).getBingQuDM();
                        Intent intent = new Intent(LoginActivity.this, BrlbActivity.class);
                        intent.putExtra("id", editText1.getText().toString());
                        intent.putExtra("canshu", s);
                        MyApplication.getInstance().setYhxm(editText1.getText().toString());
                        MyApplication.getInstance().setYhxm(editText2.getText().toString());
                        MyApplication.getInstance().setBqdm(t_bingQuXXList.get(0).getBingQuDM());
                        startActivity(intent);
                    } else {
                        listDialogDemo();
                    }
                    //startActivity(new Intent(LoginActivity.this,BrlbActivity.class));
                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            t_bingQuXXList.add((T_BingQuXX) msg.obj);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void listDialogDemo() {
        final String[] names = new String[t_bingQuXXList.size()];
        int i = 0;
        for (T_BingQuXX x : t_bingQuXXList) {
            names[i] = x.getBingQuMC();
            i++;
        }

        new AlertDialog.Builder(LoginActivity.this).setTitle("列表对话框")
                .setItems(names, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = t_bingQuXXList.get(which).getBingQuDM();
                        Intent intent = new Intent(LoginActivity.this, BrlbActivity.class);
                        intent.putExtra("id", editText1.getText().toString());
                        intent.putExtra("canshu", s);
                        MyApplication.getInstance().setBqdm(t_bingQuXXList.get(which).getBingQuDM());
                        startActivity(intent);

                        SaveUtils.put(LoginActivity.this,Constant.BQMC,names[which]);
                        SharedPreferences preferences3 = getSharedPreferences("init", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor3 = preferences3.edit();
                        editor3.putString("bqdm", s);
                        editor3.putString("id", editText1.getText().toString());
                        editor3.commit();

                        Toast.makeText(LoginActivity.this, names[which], Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).setNegativeButton("取消", null).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*Log.d("login", event.getRepeatCount() + "");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           *//* ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
            am.restartPackage(getPackageName());*//*

            return true;
        }*/
        return super.onKeyDown(keyCode, event);
    }
}

