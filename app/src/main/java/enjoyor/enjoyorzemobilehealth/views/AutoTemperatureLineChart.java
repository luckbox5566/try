package enjoyor.enjoyorzemobilehealth.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.entities.TiWen;
import enjoyor.enjoyorzemobilehealth.utlis.ScreenUtils;

/**
 * Created by Administrator on 2017/5/3.
 */

public class AutoTemperatureLineChart extends View {
    //private String[] xLineData = new String[]{"01/01", "01/01", "01/01", "01/01", "01/01", "01/01"};
    private String[] yLineData = new String[]{"35", "36", "37", "38", "39", "40", "41", "42"};
    //private String[] value = new String[]{"36.1", "36.6", "38.2", "36.5", "39.3", "37.5"};

    private List<TiWen> valueList = new ArrayList<>();

    private static final int PER_PAGE_COUNT = 4;
    private Context context;

    public AutoTemperatureLineChart(Context context) {
        super(context);
    }

    public AutoTemperatureLineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoTemperatureLineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //    public AutoTemperatureLineChart(Context context, String[] xLineData, String[] value) {
//        super(context);
//        this.context=context;
//        this.xLineData = xLineData;
//        this.value = value;
//    }

    public AutoTemperatureLineChart(Context context, List<TiWen> temperatureList) {
        super(context);
        this.context = context;
        this.valueList=temperatureList;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //图标宽度
        int chartWidth = this.getWidth();
        Log.i("data", chartWidth + "chartWidth------------");
        //图标高度
        int chartHeight = this.getHeight();
        Log.i("data", chartHeight + "chartHeight------------");
        //上边距
        int padding_top = chartHeight / 8;
        //右边距
        int padding_right = ScreenUtils.getScreenWidth(context) / 8;
        //左边距
        int padding_left = ScreenUtils.getScreenWidth(context) / 8;
        //下边距
        int padding_bottom = chartHeight / 6;

        //原点坐标
        int xPoint = padding_left;
        int yPoint = chartHeight - padding_bottom;


        //画X轴
        Paint paintXY = new Paint();
        //虚线效果，第一个参数:先画长度3的实线，间隔长度为8的空白，数组中可以有很多数，需要成对出现
        // 第二个参数为第一种参数这种效果执行时后面复制这种效果间隔的空白的长度
        DashPathEffect pathEffect = new DashPathEffect(new float[]{3, 8}, 0);
        paintXY.setStyle(Paint.Style.STROKE);
        paintXY.setStrokeWidth(3);
        paintXY.setColor(0xFF91908E);
        paintXY.setAntiAlias(true);
        Path path = new Path();
        path.moveTo(xPoint, yPoint);
        path.lineTo(chartWidth - padding_right, yPoint);
        paintXY.setPathEffect(pathEffect);
        //画X轴虚线
        //canvas.drawLine(xPoint,yPoint,chartWidth-padding_right,yPoint,paintXY);
        canvas.drawPath(path, paintXY);

        //画X轴,Y轴文字的画笔
        Paint paintXYText = new Paint();
        paintXYText.setStyle(Paint.Style.FILL);
        paintXYText.setStrokeWidth(1);
        paintXYText.setColor(0xFF91908E);
        paintXYText.setAntiAlias(true);
        paintXYText.setTextSize(50);
        //画X轴文字
        //int perWidth=(chartWidth-padding_left-padding_right)/(xLineData.length-1);
        //int perWidth=(chartWidth-padding_left-padding_right)/PER_PAGE_COUNT;
        int perWidth = (ScreenUtils.getScreenWidth(context) - padding_left - padding_right) / (PER_PAGE_COUNT - 1);
        for (int i = 0; i < valueList.size(); i++) {
            String xRQData=valueList.get(i).getCaiJiRQ();
            String xSJdata=valueList.get(i).getCaiJiSJ();
            float textWidthRQ = paintXYText.measureText(xRQData);
            float textWidthSJ = paintXYText.measureText(xSJdata);
            canvas.drawText(xRQData, padding_left + i * perWidth - textWidthRQ / 2, yPoint + 80, paintXYText);
            canvas.drawText(xSJdata, padding_left + i * perWidth - textWidthSJ / 2, yPoint + 130, paintXYText);
        }
        //画X轴单位
        canvas.drawText("(t)", chartWidth - padding_right + 20, yPoint + 10, paintXYText);


        //画Y轴相关的文字，单位及横向虚线
        int perHeight = (chartHeight - padding_top - padding_bottom) / (yLineData.length - 1);
        for (int i = 0; i < yLineData.length; i++) {
            if (i > 0) {
                Path pathY = new Path();
                pathY.moveTo(xPoint, yPoint - perHeight * i);
                pathY.lineTo(chartWidth - padding_right, yPoint - perHeight * i);
                //画折线区域内横向虚线
                canvas.drawPath(pathY, paintXY);
            }
            //画Y轴文字
            canvas.drawText(yLineData[i], xPoint - 80, yPoint - i * perHeight + 15, paintXYText);
        }
        //画Y轴单位
        canvas.drawText("(℃)", xPoint - 80, yPoint - (yLineData.length - 1) * perHeight - 50, paintXYText);


        //画折线区域内点及折线的画笔
        Paint paintContent = new Paint();
        paintContent.setStyle(Paint.Style.FILL);
        paintContent.setStrokeWidth(8);
        paintContent.setColor(0xFFFF4900);
        paintContent.setAntiAlias(true);

        //画折线区域内数字的画笔
        Paint paintContentText = new Paint();
        paintContentText.setStyle(Paint.Style.FILL);
        paintContentText.setStrokeWidth(1);
        paintContentText.setColor(0xFFFF4900);
        paintContentText.setAntiAlias(true);
        paintContentText.setTextSize(50);
        //画温度值及连接线
        for (int i = 0; i <valueList.size(); i++) {
            String temperatureData=valueList.get(i).getTiWen();
            int xPos = xPoint + perWidth * i;
            float yPos = yPoint - yPosition(temperatureData) * perHeight;
            //画温度圆圈
            canvas.drawCircle(xPos, yPos, 18, paintContent);
            //画圆圈上数字
            canvas.drawText(temperatureData, xPos, yPos - 30, paintContentText);

            //有效值判断
            if (i > 0 && yPosition(valueList.get(i-1).getTiWen()) != -999f && yPosition(temperatureData) != -999f) {
                int xPrePos = xPoint + perWidth * (i - 1);
                float yPrePos = yPoint - yPosition(valueList.get(i-1).getTiWen()) * perHeight;
                //画相邻圆圈间的连接线
                canvas.drawLine(xPrePos, yPrePos, xPos, yPos, paintContent);
            }
        }
    }

    /**
     * 计算相对于y轴原点偏移量
     *
     * @param yValue
     * @return
     */
    private float yPosition(String yValue) {
        try {
            return Float.parseFloat(yValue) - 35;
        } catch (Exception e) {
            e.printStackTrace();
            return -999f;
        }
    }

}
