package enjoyor.enjoyorzemobilehealth.application;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

import com.example.my_xml.entities.BRLB;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by youxi on 2016-9-12.
 */
public class MyApplication extends Application {
    protected static final String SEPARATE = "¤";
    private static MyApplication singleton;
    public static  int i=0;
    List<BRLB> listBRLB=new ArrayList<>();
    int choosebr=0;
    String yhxm=null;//用户姓名  评估护士人
    String yhgh=null;//登录ID，用户工号
    String listZXYZ=null;
    String changeYIZHU=null;
    String bqdm;//病区代码，ID
    String bryz_no="4";//病人医嘱代码
    String ip="";

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBryz_no() {
        return bryz_no;
    }

    public void setBryz_no(String bryz_no) {
        this.bryz_no = bryz_no;
    }

    private BRLB other_brlb=null;

    public BRLB getOther_brlb() {
        return other_brlb;
    }

    public void setOther_brlb(BRLB other_brlb) {
        this.other_brlb = other_brlb;
    }

    public String getBqdm() {
        return bqdm;
    }

    public void setBqdm(String bqdm) {
        this.bqdm = bqdm;
    }

    public String getChangeYIZHU() {
        return changeYIZHU;
    }

    public void setChangeYIZHU(String changeYIZHU) {
        this.changeYIZHU = changeYIZHU;
    }

    public String getListZXYZ() {
        return listZXYZ;
    }

    public void setListZXYZ(String listZXYZ) {
        this.listZXYZ = listZXYZ;
    }

    public String getYhgh() {
        return yhgh;
    }

    public void setYhgh(String yhgh) {
        this.yhgh = yhgh;
    }

    public String getYhxm() {
        return yhxm;
    }

    public void setYhxm(String yhxm) {
        this.yhxm = yhxm;
    }

    public int getChoosebr() {
        return choosebr;
    }

    public void setChoosebr(int choosebr) {
        this.choosebr = choosebr;
    }

    /**
     *  XML解析标记
     */
    public static final int END = 1;
    public static final int NODE = 2;
    public static final int TUPIAN = 3;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton=this;
        mContext = getApplicationContext();
    }
    public static MyApplication getInstance(){
        return singleton;
    }
    // 获取全局上下文
    public static Context getContext() {
        return mContext;
    }

    public List<BRLB> getListBRLB() {
        return listBRLB;
    }

    public void setListBRLB(List<BRLB> listBRLB) {
        this.listBRLB = listBRLB;
    }

    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getWindowParams() {
        return windowParams;
    }

}
