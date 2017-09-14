package enjoyor.enjoyorzemobilehealth.scan;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

/**
 * Created by chenlikang
 */

public class ScanFactory {
    //public static Class<?>[] clas = new Class[] { FLYScanner.class };

    /**
     * 返回扫描实例
     *
     * @param flag
     *            0：富立叶扫描头
     *            1 : BayNexus
     * @param fragKind
     * @return
     */
    public ScanInterface getInstance(int flag , Context context, int fragKind) {
        if (0 == flag) {

        }
        else if (1==flag){

        }else if (2 == flag) {

        }else if (3 == flag) {

        }
        else if (4 == flag) {
            return new M80Scanner(context){

                @Override
                public void handleData(String data, int keycode) {
                    super.handleData(data, keycode);
                    ScanFactory.this.handleData(data ,10000);
                }

            };
        }
        return null;
    }

    /**
     * 处理扫描的到的数据
     *
     * @param data
     */
    public void handleData(String data ,int keycode ) {
        Activity fragment = getFragment();
        if (fragment instanceof FragScan) {

            // 相应碎片相应扫描数据

            ((FragScan) fragment).putDataToFrag(data , keycode);
        }
    }

    /**
     *
     * @return 当前碎片
     */
    public Activity getFragment() {
        return null;
    }

    /**
     * 碎片扫描接口
     *
     * @author xuhaijiang
     * @since 2013-8-28
     */
    public interface FragScan {
        /**
         * 将数据传给相应碎片
         *
         * @param data
         */
        void putDataToFrag(String data , int keycode);
    }
}
