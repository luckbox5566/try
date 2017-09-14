package enjoyor.enjoyorzemobilehealth.entities;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/2.
 */

public class CanShu implements Serializable {
    private static final long serialVersionUID = 1185797085725667327L;
    /**
     * 文本框标志
     */
    public static final String TVFlag = "1";
    /**
     * 下拉框标志
     */
    public static final String SpFlag = "2";
    private String DR;
    private String JiChuXMMXID;
    private String CanShuMC;
    private String CanShuZhi;
    private String JiChuXiangMuID;

    public String getDR() {
        return DR;
    }

    public void setDR(String dR) {
        DR = dR;
    }

    public String getJiChuXMMXID() {
        return JiChuXMMXID;
    }

    public void setJiChuXMMXID(String jiChuXMMXID) {
        JiChuXMMXID = jiChuXMMXID;
    }

    public String getCanShuMC() {
        return CanShuMC;
    }

    public void setCanShuMC(String canShuMC) {
        CanShuMC = canShuMC;
    }

    public String getCanShuZhi() {
        return CanShuZhi;
    }

    public void setCanShuZhi(String canShuZhi) {
        CanShuZhi = canShuZhi;
    }

    public String getJiChuXiangMuID() {
        return JiChuXiangMuID;
    }

    public void setJiChuXiangMuID(String jiChuXiangMuID) {
        JiChuXiangMuID = jiChuXiangMuID;
    }

    // public static final Parcelable.Creator<CanShu> CREATOR = new
    // Creator<CanShu>() {
    //
    // @Override
    // public CanShu createFromParcel(Parcel source) {
    // // TODO Auto-generated method stub
    // CanShu canshu = new CanShu();
    // canshu.DR = source.readString();
    // canshu.JiChuXMMXID = source.readString();
    // canshu.CanShuMC = source.readString();
    // canshu.CanShuZhi = source.readString();
    // canshu.JiChuXiangMuID = source.readString();
    // return canshu;
    // }
    //
    // @Override
    // public CanShu[] newArray(int size) {
    // // TODO Auto-generated method stub
    // return new CanShu[size];
    // }
    // };
    //
    // @Override
    // public int describeContents() {
    // // TODO Auto-generated method stub
    // return 0;
    // }
    //
    // @Override
    // public void writeToParcel(Parcel dest, int flags) {
    // // TODO Auto-generated method stub
    // dest.writeString(DR);
    // dest.writeString(JiChuXMMXID);
    // dest.writeString(CanShuMC);
    // dest.writeString(CanShuZhi);
    // dest.writeString(JiChuXiangMuID);
    // }
}
