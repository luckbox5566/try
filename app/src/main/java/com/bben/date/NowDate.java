package com.bben.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dantevsyou on 2017/8/13.
 */

public final class NowDate {
    public static String get(){
        SimpleDateFormat formatter=new   SimpleDateFormat("yyyy-MM-dd");
        Date curDate =  new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

}
