package enjoyor.enjoyorzemobilehealth.entities;

import java.util.List;

/**
 * Created by admin on 2017/6/2.
 */

public class CbMoreBean {

    /**
     * title : 感觉
     * CbMoreBean : [{"name":"持久潮湿","sorce":"1分"},{"name":"经常潮湿","sorce":"2分"},{"name":"有时潮湿","sorce":"3分"},{"name":"很少潮湿","sorce":"4分"}]
     */

    private String tag;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String title;
    private List<Bean> CbMoreBean;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Bean> getCbMoreBean() {
        return CbMoreBean;
    }

    public void setCbMoreBean(List<Bean> CbMoreBean) {
        this.CbMoreBean = CbMoreBean;
    }

    public static class Bean {
        /**
         * name : 持久潮湿
         * sorce : 1分
         */
        private boolean isChecked;
        private String name;
        private String sorce;
        private String msg;


        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String name) {
            this.msg = name;
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
}
