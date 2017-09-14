package enjoyor.enjoyorzemobilehealth.entities;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/28.
 */

public class ContentBean implements Serializable{
    private String shengMingTZID;
    private String moKuaiFenLeiID;



    private String kongJianMC;
    private String contentValue;
    private String shuZhiDW;
    private String kongJianLeiXing;

    private String jiChuXiangMuID;
    private String ShuZhi2;
    private String jiLuSJ;
    private String caiJiRen;
    private String caiJiRQ;
    private String caiJiSJ;
    private String xiuGaiRen;
    private String xiuGaiSJ;
    private String xiuGaiBZ;
    private String bingQuID;
    private String bingRenZYID;
    private String bingRenXM;
    private String panDuanBZ="2";

    public ContentBean(String kongJianMC, String kongJianLeiXing,String shuZhiDW, String jiChuXiangMuID,String moKuaiFenLeiID) {
        this.kongJianMC = kongJianMC;
        this.kongJianLeiXing=kongJianLeiXing;
        this.shuZhiDW = shuZhiDW;
        this.jiChuXiangMuID = jiChuXiangMuID;
        this.moKuaiFenLeiID=moKuaiFenLeiID;
    }

    public ContentBean(String kongJianMC, String kongJianLeiXing,String shuZhiDW, String jiChuXiangMuID,String moKuaiFenLeiID,String contentValue) {
        this.kongJianMC = kongJianMC;
        this.kongJianLeiXing=kongJianLeiXing;
        this.shuZhiDW = shuZhiDW;
        this.jiChuXiangMuID = jiChuXiangMuID;
        this.moKuaiFenLeiID=moKuaiFenLeiID;
        this.contentValue=contentValue;
    }

    public String getShengMingTZID() {
        return shengMingTZID;
    }

    public void setShengMingTZID(String shengMingTZID) {
        this.shengMingTZID = shengMingTZID;
    }

    public String getMoKuaiFenLeiID() {
        return moKuaiFenLeiID;
    }

    public void setMoKuaiFenLeiID(String moKuaiFenLeiID) {
        this.moKuaiFenLeiID = moKuaiFenLeiID;
    }

    public String getKongJianMC() {
        return kongJianMC;
    }

    public void setKongJianMC(String kongJianMC) {
        this.kongJianMC = kongJianMC;
    }

    public String getKongJianLeiXing() {
        return kongJianLeiXing;
    }

    public void setKongJianLeiXing(String kongJianLeiXing) {
        this.kongJianLeiXing = kongJianLeiXing;
    }

    public String getContentValue() {
        return contentValue;
    }

    public void setContentValue(String contentValue) {
        this.contentValue = contentValue;
    }

    public String getShuZhiDW() {
        return shuZhiDW;
    }

    public void setShuZhiDW(String shuZhiDW) {
        this.shuZhiDW = shuZhiDW;
    }

    public String getJiChuXiangMuID() {
        return jiChuXiangMuID;
    }

    public void setJiChuXiangMuID(String jiChuXiangMuID) {
        this.jiChuXiangMuID = jiChuXiangMuID;
    }

    public String getShuZhi2() {
        return ShuZhi2;
    }

    public void setShuZhi2(String shuZhi2) {
        ShuZhi2 = shuZhi2;
    }

    public String getJiLuSJ() {
        return jiLuSJ;
    }

    public void setJiLuSJ(String jiLuSJ) {
        this.jiLuSJ = jiLuSJ;
    }

    public String getCaiJiRen() {
        return caiJiRen;
    }

    public void setCaiJiRen(String caiJiRen) {
        this.caiJiRen = caiJiRen;
    }

    public String getCaiJiRQ() {
        return caiJiRQ;
    }

    public void setCaiJiRQ(String caiJiRQ) {
        this.caiJiRQ = caiJiRQ;
    }

    public String getCaiJiSJ() {
        return caiJiSJ;
    }

    public void setCaiJiSJ(String caiJiSJ) {
        this.caiJiSJ = caiJiSJ;
    }

    public String getXiuGaiRen() {
        return xiuGaiRen;
    }

    public void setXiuGaiRen(String xiuGaiRen) {
        this.xiuGaiRen = xiuGaiRen;
    }

    public String getXiuGaiSJ() {
        return xiuGaiSJ;
    }

    public void setXiuGaiSJ(String xiuGaiSJ) {
        this.xiuGaiSJ = xiuGaiSJ;
    }

    public String getXiuGaiBZ() {
        return xiuGaiBZ;
    }

    public void setXiuGaiBZ(String xiuGaiBZ) {
        this.xiuGaiBZ = xiuGaiBZ;
    }

    public String getBingQuID() {
        return bingQuID;
    }

    public void setBingQuID(String bingQuID) {
        this.bingQuID = bingQuID;
    }

    public String getBingRenZYID() {
        return bingRenZYID;
    }

    public void setBingRenZYID(String bingRenZYID) {
        this.bingRenZYID = bingRenZYID;
    }

    public String getBingRenXM() {
        return bingRenXM;
    }

    public void setBingRenXM(String bingRenXM) {
        this.bingRenXM = bingRenXM;
    }

    public String getPanDuanBZ() {
        return panDuanBZ;
    }

    public void setPanDuanBZ(String panDuanBZ) {
        this.panDuanBZ = panDuanBZ;
    }
}
