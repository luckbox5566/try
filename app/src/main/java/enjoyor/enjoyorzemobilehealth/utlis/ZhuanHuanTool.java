package enjoyor.enjoyorzemobilehealth.utlis;

/**
 * Created by chenlikang
 */

public  class ZhuanHuanTool {

    public static int toInt(String s){
        int result=0;
        switch (s){
            case "未执行":
                result=0;
                break;
            case "结束":
                result=1;
                break;
            case "异常中断":
                result=2;
                break;
            case "暂停":
                result=3;
                break;
            case "停用":
                result=4;
                break;
            case "继续":
                result=5;
                break;
            case "开始":
                result=6;
                break;
            case "复核":
                result=7;
                break;
            case "摆药":
                result=8;
                break;
            case "收药":
                result=9;
                break;
        }
        return result;
    }

    public static String toString1(int i){
        String result="";
        switch (i){
            case 0:
                result="未执行";
                break;
            case 1:
                result="结束";
                break;
            case 2:
                result="异常中断";
                break;
            case 3:
                result= "暂停";
                break;
            case 4:
                result="停用";
                break;
            case 5:
                result= "继续";
                break;
            case 6:
                result="开始";
                break;
            case 7:
                result="复核";
                break;
            case 8:
                result="摆药";
                break;
            case 9:
                result="收药";
                break;
        }
        return result;
    }
}
