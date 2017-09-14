package com.example.my_xml.entities;

/**
 * 病人列表
 *
 * Created by chenlikang
 */

public class BRLB extends BrtwEntity {
    private static final long serialVersionUID = 1L;
    private String DR;
    private String BINGRENZYID;
    private String BINGRENID;
    private String YINGERID;
    private String BINGANHAO;

    private String CHUANGWEIHAO;
    private String CHUANGWEIID;


    private String XINGMING;
    private String XINGBIE;


    private String NIANLING;
    private String HUNYING;
    private String XINGBIEH;
    private String CHUSHENGRQ;

    private String MINZU;
    private String JIGUAN;
    private String HULIDJ;
    private String ZHUZHIYS;
    private String GUOMINSHI;
    private String TiWenZhi;

    private String FEIYONGXZ;
    private String YUJIAOJIN;
    private String YIXIAOFEI;
    private String RUYUANSJ;
    private String ZHUYUANCS;



    private String CHUYUANBZ;
    private String KESHIMC;
    private String KESHIDM;
    private String BINGRENZT;
    private String CHUYUANSJ;

    private String LIANXIREN;
    private String LIANXIFS;
    private String BINGQUDM;
    private String BINGQUMC;
    private String ZHENDUAN;

    // /**
    // *
    // * @return （XINGMING + "." + BINGRENZYID）
    // */
    // public String toStringNameYearBed() {
    // // TODO Auto-generated method stub
    // return XINGMING + "." + BINGRENZYID;
    //
    // }

    /**
     * XINGMING + "  " + NIANLING + "岁  " + CHUANGWEIHAO + "号床"
     *
     */
    public String toString() {
        if (XINGMING.equals("")) {
            return "";
        }
        return XINGMING + "  " + NIANLING + "  " + CHUANGWEIHAO + "号床";
    }



    public String getTiWenZhi() {
        return TiWenZhi;
    }



    public void setTiWenZhi(String tiWenZhi) {
        TiWenZhi = tiWenZhi;
    }

    public String getXingBieH() {
        return XINGBIEH;
    }



    public void setXingBieH(String xingBieH) {
        XINGBIEH = xingBieH;
    }



    public String getDR() {
        return DR;
    }

    public void setDR(String dR) {
        DR = dR;
    }

    public String getBINGRENZYID() {
        return BINGRENZYID;
    }

    public void setBINGRENZYID(String bINGRENZYID) {
        BINGRENZYID = bINGRENZYID;
    }

    public String getBINGRENID() {
        return BINGRENID;
    }

    public void setBINGRENID(String bINGRENID) {
        BINGRENID = bINGRENID;
    }

    public String getYINGERID() {
        return YINGERID;
    }

    public void setYINGERID(String yINGERID) {
        YINGERID = yINGERID;
    }

    public String getBINGANHAO() {
        return BINGANHAO;
    }

    public void setBINGANHAO(String bINGANHAO) {
        BINGANHAO = bINGANHAO;
    }

    public String getCHUANGWEIHAO() {
        return CHUANGWEIHAO;
    }

    public void setCHUANGWEIHAO(String cHUANGWEIHAO) {
        CHUANGWEIHAO = cHUANGWEIHAO;
    }

    public String getCHUANGWEIID() {
        return CHUANGWEIID;
    }

    public void setCHUANGWEIID(String cHUANGWEIID) {
        CHUANGWEIID = cHUANGWEIID;
    }

    public String getXINGMING() {
        return XINGMING;
    }

    public void setXINGMING(String xINGMING) {
        XINGMING = xINGMING;
    }

    public String getXINGBIE() {
        return XINGBIE;
    }

    public void setXINGBIE(String xINGBIE) {
        XINGBIE = xINGBIE;
    }

    public String getNIANLING() {
        return NIANLING;
    }

    public void setNIANLING(String nIANLING) {
        NIANLING = nIANLING;
    }

    public String getHUNYING() {
        return HUNYING;
    }

    public void setHUNYING(String hUNYING) {
        HUNYING = hUNYING;
    }

    public String getMINZU() {
        return MINZU;
    }

    public void setMINZU(String mINZU) {
        MINZU = mINZU;
    }

    public String getJIGUAN() {
        return JIGUAN;
    }

    public void setJIGUAN(String jIGUAN) {
        JIGUAN = jIGUAN;
    }

    public String getHULIDJ() {
        return HULIDJ;
    }

    public void setHULIDJ(String hULIDJ) {
        HULIDJ = hULIDJ;
    }

    public String getZHUZHIYS() {
        return ZHUZHIYS;
    }

    public void setZHUZHIYS(String zHUZHIYS) {
        ZHUZHIYS = zHUZHIYS;
    }

    public String getGUOMINSHI() {
        return GUOMINSHI;
    }

    public void setGUOMINSHI(String gUOMINSHI) {
        GUOMINSHI = gUOMINSHI;
    }

    public String getFEIYONGXZ() {
        return FEIYONGXZ;
    }

    public void setFEIYONGXZ(String fEIYONGXZ) {
        FEIYONGXZ = fEIYONGXZ;
    }

    public String getYUJIAOJIN() {
        return YUJIAOJIN;
    }

    public void setYUJIAOJIN(String yUJIAOJIN) {
        YUJIAOJIN = yUJIAOJIN;
    }

    public String getYIXIAOFEI() {
        return YIXIAOFEI;
    }

    public void setYIXIAOFEI(String yIXIAOFEI) {
        YIXIAOFEI = yIXIAOFEI;
    }

    public String getRUYUANSJ() {
        return RUYUANSJ;
    }

    public void setRUYUANSJ(String rUYUANSJ) {
        RUYUANSJ = rUYUANSJ;
    }

    public String getZHUYUANCS() {
        return ZHUYUANCS;
    }

    public void setZHUYUANCS(String zHUYUANCS) {
        ZHUYUANCS = zHUYUANCS;
    }

    public String getLIANXIREN() {
        return LIANXIREN;
    }

    public void setLIANXIREN(String lIANXIREN) {
        LIANXIREN = lIANXIREN;
    }

    public String getLIANXIFS() {
        return LIANXIFS;
    }

    public void setLIANXIFS(String lIANXIFS) {
        LIANXIFS = lIANXIFS;
    }

    public String getBINGQUDM() {
        return BINGQUDM;
    }

    public void setBINGQUDM(String bINGQUDM) {
        BINGQUDM = bINGQUDM;
    }

    public String getBINGQUMC() {
        return BINGQUMC;
    }

    public void setBINGQUMC(String bINGQUMC) {
        BINGQUMC = bINGQUMC;
    }

    public String getZHENDUAN() {
        return ZHENDUAN;
    }

    public void setZHENDUAN(String zHENDUAN) {
        ZHENDUAN = zHENDUAN;
    }

    public String getCHUYUANBZ() {
        return CHUYUANBZ;
    }

    public void setCHUYUANBZ(String cHUYUANBZ) {
        CHUYUANBZ = cHUYUANBZ;
    }

    public String getKESHIMC() {
        return KESHIMC;
    }

    public void setKESHIMC(String kESHIMC) {
        KESHIMC = kESHIMC;
    }

    public String getKESHIDM() {
        return KESHIDM;
    }

    public void setKESHIDM(String kESHIDM) {
        KESHIDM = kESHIDM;
    }

    public String getBINGRENZT() {
        return BINGRENZT;
    }

    public void setBINGRENZT(String bINGRENZT) {
        BINGRENZT = bINGRENZT;
    }

    public String getCHUYUANSJ() {
        return CHUYUANSJ;
    }

    public void setCHUYUANSJ(String cHUYUANSJ) {
        CHUYUANSJ = cHUYUANSJ;
    }
}
