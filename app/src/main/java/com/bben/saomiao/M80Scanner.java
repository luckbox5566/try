package com.bben.saomiao;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.imscs.barcodemanager.BarcodeManager;
import com.imscs.barcodemanager.Constants;
import com.imscs.barcodemanager.ScanTouchManager;

import java.io.UnsupportedEncodingException;

import enjoyor.enjoyorzemobilehealth.scan.ScanInterface;

/**
 * Created by dantevsyou on 2017/8/14.
 */

public class M80Scanner implements ScanInterface,BarcodeManager.OnEngineStatus {
    private final int ID_SCANSETTING = 0x12;
    private final int ID_CLEAR_SCREEN = 0x13;
    private final int ID_SCANTOUCH = 0x14;

    private BarcodeManager mBarcodeManager = null;
    //private EditText mDecodeResultEdit = null;
    private final int SCANKEY_LEFT = 301;
    private final int SCANKEY_RIGHT = 300;
    private final int SCANKEY_CENTER = 302;
    private final int SCANTIMEOUT = 3000;
    long mScanAccount = 0;
    private boolean mbKeyDown = true;
    private boolean scanTouch_flag = true;
    private Handler mDoDecodeHandler;

    private WindowManager.LayoutParams windowManagerParams = null;
    private ScanTouchManager mScanTouchManager = null;

    class DoDecodeThread extends Thread {
        public void run() {
            Looper.prepare();

            mDoDecodeHandler = new Handler();

            Looper.loop();
        }
    }

    private DoDecodeThread mDoDecodeThread;

    public M80Scanner(Context context){
        mDoDecodeThread = new DoDecodeThread();
        mDoDecodeThread.start();
        if (mBarcodeManager == null) {
            // initialize decodemanager
            mBarcodeManager = new BarcodeManager(context, this);
        }
    }


    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event, String fname) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
            case SCANKEY_LEFT:
            case SCANKEY_CENTER:
            case SCANKEY_RIGHT:
            case KeyEvent.KEYCODE_SLASH: // hal key
                try {
                    if (mbKeyDown) {
                        DoScan();
                        mbKeyDown = false;
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            case KeyEvent.KEYCODE_BACK:
                return true;
            default:
                return true;
        }
    }

    @Override
    public void handleData(String data, int keycode) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
            case SCANKEY_LEFT:
            case SCANKEY_CENTER:
            case SCANKEY_RIGHT:
            case KeyEvent.KEYCODE_SLASH: // hal key
                try {
                    mbKeyDown = true;
                    cancleScan();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
            case KeyEvent.KEYCODE_BACK:
                //this.finish();
                return true;
            default:
                return true;
        }
    }

    private void doScanInBackground() {
        mDoDecodeHandler.post(new Runnable() {

            @Override
            public void run() {
                if (mBarcodeManager != null) {
                    // TODO Auto-generated method stub
                    try {
                        synchronized (mBarcodeManager) {
                            mBarcodeManager.executeScan(SCANTIMEOUT);
                        }

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void DoScan() throws Exception {
        doScanInBackground();
    }

    private Handler ScanResultHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.DecoderReturnCode.RESULT_SCAN_SUCCESS:
                    mScanAccount++;
                    String strDecodeResult = "";
                    BarcodeManager.ScanResult decodeResult = (BarcodeManager.ScanResult) msg.obj;
                    getFinalString(decodeResult);
                    int codeid = decodeResult.codeid;
                    int aimid = decodeResult.aimid;
                    int iLength = decodeResult.len;
                    strDecodeResult = getFinalString(decodeResult);
                    if (mBarcodeManager != null) {
                        mBarcodeManager.beepScanSuccess();
                    }
                    handleData(strDecodeResult,1000);

                    break;

                case Constants.DecoderReturnCode.RESULT_SCAN_FAIL: {
                    if (mBarcodeManager != null) {
                        mBarcodeManager.beepScanFail();
                    }
                    //mDecodeResultEdit.setText("Scan failed");

                }
                break;
                case Constants.DecoderReturnCode.RESULT_DECODER_READY: {
                    // Enable all sysbology if needed
                    // try {
                    // mDecodeManager.enableSymbology(SymbologyID.SYM_ALL);   //enable all Sym
                    // } catch (RemoteException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                }
                break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    private String getFinalString(BarcodeManager.ScanResult decodeRslt) {
//       String prefixIdString = getIdString(prefixId, decodeRslt);
//       String suffixIdString = getIdString(suffixId, decodeRslt);
        String actulResult = null;

        try {
            byte[] bytereuslt = null;
            if (mBarcodeManager != null) {
                bytereuslt = mBarcodeManager.getScanResultData();
            }
            if (bytereuslt != null){
//               char[] shortresult = new char[bytereuslt.length];
//               for (int i = 0; i< bytereuslt.length ;  i++){
//                   shortresult[i] = (char)(bytereuslt[i] & 0x00FF);
//               }
//               actulResult = String.copyValueOf(shortresult);
                try {
//                   switch (sendFormat) {
//                   case SEND_FORMAT_GB:
//                       actulResult = new String(bytereuslt, "GB2312");
//                       break;
//                   case SEND_FORMAT_UTF:
//                       actulResult = new String(bytereuslt, "UTF-8");
//                       break;
//                   case SEND_FORMAT_ISO:
//                       actulResult = new String(bytereuslt, "ISO-8859-1");
//                       break;
//                   case SEND_FORMAT_GBK:
//                   	actulResult = new String(bytereuslt, "GBK");
//                       break;
//                   case SEND_FORMAT_GB18030:
//                   	actulResult = new String(bytereuslt, "GB18030");
//                       break;
//                   default:
//                       break;
//                   }
                    actulResult = new String(bytereuslt, "GBK");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        catch(Exception ex)
        {

        }
        return actulResult;
    }

    private void cancleScan() throws Exception {
        if (mBarcodeManager != null) {
            mBarcodeManager.exitScan();
        }
    }

    @Override
    public void onEngineReady() {
        // TODO Auto-generated method stub
        ScanResultHandler.sendEmptyMessage(Constants.DecoderReturnCode.RESULT_DECODER_READY);
    }

    @Override
    public int scanResult(boolean suc,BarcodeManager.ScanResult result) {
        // TODO Auto-generated method stub
        Message m = new Message();
        m.obj = result;
        if (suc){
            // docode successfully
            m.what = Constants.DecoderReturnCode.RESULT_SCAN_SUCCESS;
        }else{
            m.what = Constants.DecoderReturnCode.RESULT_SCAN_FAIL;

        }
        ScanResultHandler.sendMessage(m);
        return 0;
    }
}
