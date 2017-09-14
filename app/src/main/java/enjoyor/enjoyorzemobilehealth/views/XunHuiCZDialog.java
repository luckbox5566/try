package enjoyor.enjoyorzemobilehealth.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.infosearch.XhcxActivity;
import enjoyor.enjoyorzemobilehealth.adapter.XunHuiAdapter;
import enjoyor.enjoyorzemobilehealth.entities.JCJG;
import enjoyor.enjoyorzemobilehealth.entities.XunHuiCZ;

/**
 * Created by Administrator on 2017/7/17.
 */

public class XunHuiCZDialog extends Dialog implements View.OnClickListener{
    private String lx;
    MyInterface myListener;
    private List<XunHuiCZ> data;
    private  Context context;
    private FlowLayout listxhxz;
    private XunHuiAdapter adapter;
    private List<String> names = new ArrayList<>();



    public XunHuiCZDialog(Context context,List <XunHuiCZ> data,String lx,MyInterface myListener){
        super(context, R.style.MyDialog);
        setContentView(R.layout.dialog_xhcx);
        listxhxz = (FlowLayout) findViewById(R.id.listxhxz);
        this.context = context;
        this.data = data;
        this.lx=lx;
        this.myListener = myListener;

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置为居中
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
       lp.width = display.getWidth(); // 设置dialog宽度为屏幕的4/5
        /*lp.height = (int) (display.getHeight());*/
        lp.y = 0;
        getWindow().setAttributes(lp);
       // setCanceledOnTouchOutside(false);// 点击Dialog外部消失
        setCancelable(true);
        for(int i=0;i<data.size();i++){
            names.add(data.get(i).getXunHuiCZ());
        }





        listxhxz.addData(names,lx);
        /**
         * 设置点击事件
         */
        listxhxz.setFlowLayoutListener(new FlowLayout.FlowLayoutListener() {
            @Override
            public void onItemClick(View view, int position) {
                myListener.method(names.get(position));
                dismiss();
                //Toast.makeText(context,names.get(poition),Toast.LENGTH_SHORT).show();
            }
        });
        /*adapter =new XunHuiAdapter(context,data);
        listxhxz.setAdapter(adapter);
        listxhxz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myListener.method(data.get(position).getXunHuiCZ());
                dismiss();
               // Toast.makeText(context, data.get(position).getXunHuiCZID() + "   " + data.get(position).getXunHuiCZ(), Toast.LENGTH_SHORT).show();
            }
        });*/






    }
    @Override
    public void onClick(View v) {

    }


}
