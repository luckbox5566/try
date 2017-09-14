package enjoyor.enjoyorzemobilehealth.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/6/22.
 */

public class Jyjgxq extends ListView {
    public Jyjgxq(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Jyjgxq(Context context) {
        super(context);
    }

    public Jyjgxq(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
