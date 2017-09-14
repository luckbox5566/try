package enjoyor.enjoyorzemobilehealth.entities;

import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class ShengMingTiZhengLuRuBean {
    private String title;
    private List<ContentBean> itemContent;

    public ShengMingTiZhengLuRuBean(String title, List<ContentBean> itemContent) {
        this.title = title;
        this.itemContent = itemContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ContentBean> getItemContent() {
        return itemContent;
    }

    public void setItemContent(List<ContentBean> itemContent) {
        this.itemContent = itemContent;
    }
}
