package com.example.my_xml.entities;

import java.io.Serializable;

/**
 * 病人体温
 *
 * Created by chenlikang
 */

public class BrtwEntity implements Serializable{
    private static final long serialVersionUID = 5223843029383088920L;
    // String ChuangWeiHao;

    String TiWenZhi;
    String PanDuanBZ;
    String ShengMingTZID;

    public String getPanDuanBZ() {
        return PanDuanBZ;
    }

    public void setPanDuanBZ(String panDuanBZ) {
        PanDuanBZ = panDuanBZ;
    }

    public String getShengMingTZID() {
        return ShengMingTZID;
    }

    public void setShengMingTZID(String shengMingTZID) {
        ShengMingTZID = shengMingTZID;
    }

    public String getTiWenZhi() {
        return TiWenZhi;
    }

    public void setTiWenZhi(String tiWenZhi) {
        TiWenZhi = tiWenZhi;
    }
}
