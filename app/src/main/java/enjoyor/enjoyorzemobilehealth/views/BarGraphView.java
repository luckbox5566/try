package enjoyor.enjoyorzemobilehealth.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import enjoyor.enjoyorzemobilehealth.R;

/**
 * Created by Administrator on 2017/6/26.
 */

public class BarGraphView  extends View {
    private final String TAG = BarGraphView.class.getName();
    //画笔
    private Paint mPaint;

    //X轴最大值
    private float maxAxisValueX = 90;
    //x轴刻度线数量
    private int axisDivideSizeX = 3;
    private float maxAxisValueY = 70;
    private int axisDivideSizeY = 7;
    //视图宽度
    private int width;
    // 视图高度
    private int height;
    //坐标原点
    private final int originX = DensityUtil .dip2px(getContext(),50);
    private final int originY = DensityUtil .dip2px(getContext(),80);
    //柱状图数据
    private int columnInfo[][];

    public BarGraphView(Context context,AttributeSet attrs) {
        super(context,attrs);
        //创建画笔
        mPaint = new Paint();
        //获取配置的属性值

    }

    public void setAxisX(float maxValue, int divideSize) {
        maxAxisValueX = maxValue;
        axisDivideSizeX = divideSize;
    }


    public void setAxisY(float maxValue, int divideSize) {
        maxAxisValueY = maxValue;
        axisDivideSizeY = divideSize;
    }


    public void setColumnInfo(int[][] columnInfo) {
        this.columnInfo = columnInfo;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec) - 20;
        height = MeasureSpec.getSize(heightMeasureSpec) - 80;
    }

    @Override
    public void onDraw(Canvas canvas) {

        drawColumn(canvas, mPaint);
    }


    /**
     * 绘制柱状图
     */
    private void drawColumn(Canvas canvas, Paint paint) {
        if(columnInfo == null)
            return;
        float cellWidth = width / axisDivideSizeX;
        for (int i = 0; i < columnInfo.length; i++) {
            paint.setColor(columnInfo[i][1]);
            float leftTopY = originY - height * columnInfo[i][0] / maxAxisValueY;
            canvas.drawRect(originX + cellWidth * (i + 1), leftTopY, originX + cellWidth * (i + 2), originY, mPaint);//左上角x,y右下角x,y，画笔
        }
    }


}
