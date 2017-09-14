package enjoyor.enjoyorzemobilehealth.entities;

import android.graphics.drawable.Drawable;

/**
 * Created by chenlikang
 */

public class HomePageEntity {
    private Drawable drawable;
    private String  name;

    public HomePageEntity(Drawable drawable, String name) {
        this.drawable = drawable;
        this.name = name;
    }


    public Drawable getDrawable() {
        return drawable;
    }

    public String getName() {
        return name;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setName(String name) {
        this.name = name;
    }
}
