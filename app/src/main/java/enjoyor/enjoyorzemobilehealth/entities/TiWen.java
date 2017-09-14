package enjoyor.enjoyorzemobilehealth.entities;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11.
 */

public class TiWen implements Serializable{
    private String CaiJiRQ;
    private String CaiJiSJ;
    private String TiWen;

    public String getCaiJiRQ() {
        return CaiJiRQ;
    }

    public void setCaiJiRQ(String caiJiRQ) {
        CaiJiRQ = caiJiRQ;
    }

    public String getCaiJiSJ() {
        return CaiJiSJ;
    }

    public void setCaiJiSJ(String caiJiSJ) {
        CaiJiSJ = caiJiSJ;
    }

    public String getTiWen() {
        return TiWen;
    }

    public void setTiWen(String tiWen) {
        TiWen = tiWen;
    }
}
