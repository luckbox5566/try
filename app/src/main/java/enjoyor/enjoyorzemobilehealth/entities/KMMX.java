package enjoyor.enjoyorzemobilehealth.entities;

/**
 * Created by Administrator on 2017/7/24.
 */

public class KMMX {
    private boolean isChecked;
    private String ITEM_ID;
    private String ITEM_CLASS_ID;
    private String WARDCODE;
    private String ITEM_SUB_CLASS_ID;
    private String ITEM_SUB_CLASS_NAME;
    private String ITEM_NAME;

    public String getITEM_SUB_CLASS_NAME() {
        return ITEM_SUB_CLASS_NAME;
    }

    public void setITEM_SUB_CLASS_NAME(String ITEM_SUB_CLASS_NAME) {
        this.ITEM_SUB_CLASS_NAME = ITEM_SUB_CLASS_NAME;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getITEM_ID() {
        return ITEM_ID;
    }

    public void setITEM_ID(String ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }

    public String getITEM_CLASS_ID() {
        return ITEM_CLASS_ID;
    }

    public void setITEM_CLASS_ID(String ITEM_CLASS_ID) {
        this.ITEM_CLASS_ID = ITEM_CLASS_ID;
    }

    public String getWARDCODE() {
        return WARDCODE;
    }

    public void setWARDCODE(String WARDCODE) {
        this.WARDCODE = WARDCODE;
    }

    public String getITEM_SUB_CLASS_ID() {
        return ITEM_SUB_CLASS_ID;
    }

    public void setITEM_SUB_CLASS_ID(String ITEM_SUB_CLASS_ID) {
        this.ITEM_SUB_CLASS_ID = ITEM_SUB_CLASS_ID;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }
}
