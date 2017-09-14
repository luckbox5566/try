package enjoyor.enjoyorzemobilehealth.utlis;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;
import android.widget.Toast;

import enjoyor.enjoyorzemobilehealth.activity.LoginActivity;

public class ToastUtils {

    protected static final String TAG = "ToastUtils";
    private static ToastUtils mInstance;
    private Context mContext;

    private static Toast toast;
    private static ProgressDialog progressDialog;


//
//    private ToastUtils(Context context){
//        if (null == mToast){
//            mToast = Toast.makeText(context.getApplicationContext(),"",Toast.LENGTH_LONG);
//        }
//    }
//
//    public static ToastUtils getInstance(Context context) {
//        if (mInstance == null){
//            mInstance = new ToastUtils(context.getApplicationContext());
//        }
//        return mInstance;
//    }
//
//    public void showShortToast(String mString){
//        if (mToast == null){
//            return;


//        }
//        mToast.setText(mString);
//        mToast.setDuration(Toast.LENGTH_SHORT);
//        // mToast.setGravity(Gravity.CENTER, 0, 0);
//        mToast.show();
//    }
//
//    public void showLongToast(String mString){
//        if (mToast == null){
//            return;
//        }
//        mToast.setText(mString);
//        mToast.setDuration(Toast.LENGTH_LONG);
//        // mToast.setGravity(Gravity.CENTER, 0, 0);
//        mToast.show();
//    }


    private ToastUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }
    public static ToastUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ToastUtils.class) {
                if (mInstance == null) {
                    mInstance = new ToastUtils(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }
    /**
     * 信息提示
     *
     * @param context
     * @param content
     */
    public static void makeToast(Context context, String content) {
        if (context == null) return;
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showLoading(Context context) {
        if (progressDialog != null && progressDialog.isShowing()) return;
        progressDialog = new ProgressDialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("请求网络中...");
        progressDialog.show();
    }

    public static void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
