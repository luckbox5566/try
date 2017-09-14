package enjoyor.enjoyorzemobilehealth.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.entities.JCJG;

/**
 * Created by Administrator on 2017/7/11.
 */

public class JcjgDialog extends Dialog implements View.OnClickListener {
    private  JCJG data;
    private Context context;
    private ImageView iv_dialog_close;
    private TextView jcxm;
    private TextView jcsj;
    private TextView zdjg;
    public JcjgDialog(Context context, JCJG data) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.dialog_jcjg);
        iv_dialog_close = (ImageView)findViewById(R.id.iv_dialog_close);
        jcxm = (TextView)findViewById(R.id.jcxm) ;
        jcsj = (TextView)findViewById(R.id.jcsj) ;
        zdjg = (TextView)findViewById(R.id.zdjg) ;
        iv_dialog_close.setOnClickListener(this);
        this.context = context;
        this.data = data;
    }
    @Override
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
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失
        //遍历控件id,添加点击事件
        jcxm.setText(data.getJianChaXM());
        jcsj.setText(data.getJianChaSJ());
        zdjg.setText(data.getZhenDuanJG());




    }


    @Override
    public void onClick(View v) {
        dismiss();
    }
}
