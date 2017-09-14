package enjoyor.enjoyorzemobilehealth.entities;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/31.
 */

public class HuLiJiLu implements Serializable{
    private String HuLiJiLuDanHao;
    private String Bed;
    private String CaiJiRen;
    private String CaiJiRenID;
    private String CaiJiSJ;
    private String ZongJieLX;
    private String TeShuQingKuang;

    public String getHuLiJiLuDanHao() {
        return HuLiJiLuDanHao;
    }

    public void setHuLiJiLuDanHao(String huLiJiLuDanHao) {
        HuLiJiLuDanHao = huLiJiLuDanHao;
    }

    public String getBed() {
        return Bed;
    }

    public void setBed(String bed) {
        Bed = bed;
    }

    public String getCaiJiRen() {
        return CaiJiRen;
    }

    public void setCaiJiRen(String caiJiRen) {
        CaiJiRen = caiJiRen;
    }

    public String getCaiJiRenID() {
        return CaiJiRenID;
    }

    public void setCaiJiRenID(String caiJiRenID) {
        CaiJiRenID = caiJiRenID;
    }

    public String getCaiJiSJ() {
        return CaiJiSJ;
    }

    public void setCaiJiSJ(String caiJiSJ) {
        CaiJiSJ = caiJiSJ;
    }

    public String getZongJieLX() {
        return ZongJieLX;
    }

    public void setZongJieLX(String zongJieLX) {
        ZongJieLX = zongJieLX;
    }

    public String getTeShuQingKuang() {
        return TeShuQingKuang;
    }

    public void setTeShuQingKuang(String teShuQingKuang) {
        TeShuQingKuang = teShuQingKuang;
    }
}
