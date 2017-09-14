package enjoyor.enjoyorzemobilehealth.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.utlis.ToastUtils;
import my_network.NetWork;
import my_network.ZhierCall;

/**
 * Created by Administrator on 2017/7/20.
 */

public class XunHuiAddDialog extends Dialog implements View.OnClickListener{
    private String xhlx;
    MyInterface myListener;
    private Context context;
    private TextView zyid;
    private TextView xm;
    private TextView xh_commit;
    private ZhierCall zhierCall;
    private String XunHuiRenID;
    private String XunHuiRen;
    private String BingQuDM;
    private String BingRenZYID;




    public XunHuiAddDialog(Context context,String xhlx,String bqdm,MyInterface myListener){
        super(context, R.style.MyDialog);
        setContentView(R.layout.activity_xhcx_add);
        zyid = (TextView) findViewById(R.id.zyid);
        xm = (TextView) findViewById(R.id.xm);
        xh_commit = (TextView) findViewById(R.id.xh_commit);
        xh_commit.setOnClickListener(this);
        this.context = context;
        this.xhlx = xhlx;
        this.BingQuDM = bqdm;
        this.myListener = myListener;

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth()*4/5; // 设置dialog宽度为屏幕的4/5
        lp.height = (int) (display.getHeight());
        getWindow().setAttributes(lp);
        // setCanceledOnTouchOutside(false);// 点击Dialog外部消失
        setCancelable(true);



    }
    @Override
    public void onClick(View v) {
        adddData();
    }
    private void adddData(){
        XunHuiRenID = MyApplication.getInstance().getYhgh();
        XunHuiRen = MyApplication.getInstance().getYhxm();
        BingRenZYID = zyid.getText().toString();
        MyApplication instance = MyApplication.getInstance();
        String XingMing="";
        int i;
        for(i = 0;i<instance.getListBRLB().size();i++){
            if(BingRenZYID.equals(instance.getListBRLB().get(i).getBINGRENZYID())){
                XingMing =instance.getListBRLB().get(i).getXINGMING();
                break;
            }
        }
        if(XingMing==""){
            ToastUtils.makeToast(MyApplication.getContext(), "输入的病人ZYID有误");
        }else{
            String canshu=BingQuDM+"¤"+BingRenZYID+"¤"+XingMing+"¤"+XunHuiRenID+"¤"+xhlx+"¤"+""+"¤"+XunHuiRen;
            zhierCall = (new ZhierCall())
                    .setId(XunHuiRenID)
                    .setNumber("03043006")
                    .setMessage(NetWork.GongYong)
                    .setCanshu(canshu)
                    .setContext(context)
                    .setPort(5000)
                    .build();
            zhierCall.start(new NetWork.SocketResult() {
                @Override
                public void success(String data) {
                    ToastUtils.makeToast(MyApplication.getContext(), "保存成功");
                    myListener.updateData(BingRenZYID);
                    dismiss();
                }
                @Override
                public void fail(String info) {
                    ToastUtils.makeToast(MyApplication.getContext(), "保存失败");
                }

            });
        }

    }
}
