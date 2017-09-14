package enjoyor.enjoyorzemobilehealth.entities;

/**
 * Created by admin on 2017/6/1.
 */

public class CheckBoxBean {
    private boolean isChecked;
    public String csxx;
    public String name;
    public String sorce;
    public String msg;
    public int fen;


    public String getCsxx() {
        return csxx;
    }

    public void setCsxx(String csxx) {
        this.csxx = csxx;
    }


//
//    public CheckBoxBean(String name, String csxx) {
//        this.csxx = csxx;
//        this.name = name;
//    }

    public CheckBoxBean(String name, String csxx, String msg) {
        this.csxx = csxx;
        this.name = name;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


//    public CheckBoxBean(String name, String sorce) {
//        this.name = name;
//        this.sorce = sorce;
//    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSorce() {
        return sorce;
    }

    public void setSorce(String sorce) {
        this.sorce = sorce;
    }
}
