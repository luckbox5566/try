package enjoyor.enjoyorzemobilehealth.scan;

import android.view.KeyEvent;

/**
 * Created by chenlikang
 */

public interface ScanInterface {
    int KEYCODE = 92;

    /**
     * 按键落下
     * @param fname
     *
     * @param keyCode
     * @param event
     * @return
     */
    boolean onKeyDown(int keyCode, KeyEvent event , String fname);

    /**
     * 处理扫描的到的数据
     *
     * @param data
     */



    void handleData(String data , int keycode);

    boolean onKeyUp(int keyCode, KeyEvent event);
}
