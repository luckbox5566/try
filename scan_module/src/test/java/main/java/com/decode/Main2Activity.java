package com.decode;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.scan_module.R;
import com.imscs.barcodemanager.BarcodeManager;
import com.imscs.barcodemanager.Constants;
import com.imscs.barcodemanager.ScanTouchManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Main2Activity extends Activity implements BarcodeManager.OnEngineStatus{

    private final int ID_SCANSETTING = 0x12;
    private final int ID_CLEAR_SCREEN = 0x13;
    private final int ID_SCANTOUCH = 0x14;

    private BarcodeManager mBarcodeManager = null;
    private EditText mDecodeResultEdit = null;
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

    private Main2Activity.DoDecodeThread mDoDecodeThread;

    // private String strResultM;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.main);

        windowManagerParams = ((DecodeSampleApplication) getApplication()).getWindowParams();

        //initialize ScanTouch and set clicklistener
        mScanTouchManager = new ScanTouchManager(getApplicationContext(), windowManagerParams);
        mScanTouchManager.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                doScanInBackground();
            }
        });

      /*  initializeUI();
        mDoDecodeThread = new Main2Activity.DoDecodeThread();
        mDoDecodeThread.start();*/

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

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
                this.finish();
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
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
                this.finish();
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mBarcodeManager == null) {
            // initialize decodemanager
            mBarcodeManager = new BarcodeManager(this, this);
        }

        mScanTouchManager.createScanTouch();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, ID_SCANSETTING, 0, R.string.SymbologySettingMenu);
        menu.add(0, ID_CLEAR_SCREEN, 1, R.string.ClearScreenMenu);
        menu.add(0, ID_SCANTOUCH, 2, R.string.CloseScanTouch);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case ID_SCANSETTING: {
                mBarcodeManager.startSymbologySettingActvity();
                return true;
            }
            case ID_CLEAR_SCREEN: {

                String strPromotScan = this.getResources().getString(
                        R.string.PROMOT_CLICK_SCAN_BUTTON);
                mDecodeResultEdit.setText(strPromotScan);

                return true;
            }
            case ID_SCANTOUCH: {
                if (scanTouch_flag) {
                    item.setTitle(R.string.OpenScanTouch);
                    mScanTouchManager.removeScanTouch();
                } else {
                    item.setTitle(R.string.CloseScanTouch);
                    mScanTouchManager.createScanTouch();
                }
                scanTouch_flag = !scanTouch_flag;
            }
            default:
                return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mBarcodeManager != null) {
            try {
                mBarcodeManager.release();
                mBarcodeManager = null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //remove ScanTouch
        if (scanTouch_flag) {
            mScanTouchManager.removeScanTouch();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mBarcodeManager != null) {
            try {

                mBarcodeManager.release();
                mBarcodeManager = null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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

    private void initializeUI() {
        final Button button = (Button) findViewById(R.id.scanbutton);
        mDecodeResultEdit = (EditText) findViewById(R.id.edittext_scanresult);
        button.setOnTouchListener(new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                final int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        button.setBackgroundResource(R.drawable.android_pressed);
                        try {
                            DoScan();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        try {
                            button.setBackgroundResource(R.drawable.android_normal);
                            cancleScan();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                }
                return true;
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
                    strDecodeResult = "Decode Result: " + getFinalString(decodeResult)
                            + "\r\n" + "CodeID: " + "("
                            + String.valueOf((char) codeid) + "/"
                            + String.valueOf((char) aimid) + ")" + "\r\n"
                            + "Decode Length: " + iLength + "\r\n"
                            + "Success Count: " + mScanAccount + "\r\n";

                    mDecodeResultEdit.setText(strDecodeResult);
                    if (mBarcodeManager != null) {
                        mBarcodeManager.beepScanSuccess();
                    }
                    break;

                case Constants.DecoderReturnCode.RESULT_SCAN_FAIL: {
                    if (mBarcodeManager != null) {
                        mBarcodeManager.beepScanFail();
                    }
                    mDecodeResultEdit.setText("Scan failed");

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
//        String prefixIdString = getIdString(prefixId, decodeRslt);
//        String suffixIdString = getIdString(suffixId, decodeRslt);
        String actulResult = null;

        try {
            byte[] bytereuslt = null;
            if (mBarcodeManager != null) {
                bytereuslt = mBarcodeManager.getScanResultData();
            }
            if (bytereuslt != null){
//                char[] shortresult = new char[bytereuslt.length];
//                for (int i = 0; i< bytereuslt.length ;  i++){
//                    shortresult[i] = (char)(bytereuslt[i] & 0x00FF);
//                }
//                actulResult = String.copyValueOf(shortresult);
                try {
//                    switch (sendFormat) {
//                    case SEND_FORMAT_GB:
//                        actulResult = new String(bytereuslt, "GB2312");
//                        break;
//                    case SEND_FORMAT_UTF:
//                        actulResult = new String(bytereuslt, "UTF-8");
//                        break;
//                    case SEND_FORMAT_ISO:
//                        actulResult = new String(bytereuslt, "ISO-8859-1");
//                        break;
//                    case SEND_FORMAT_GBK:
//                    	actulResult = new String(bytereuslt, "GBK");
//                        break;
//                    case SEND_FORMAT_GB18030:
//                    	actulResult = new String(bytereuslt, "GB18030");
//                        break;
//                    default:
//                        break;
//                    }
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
