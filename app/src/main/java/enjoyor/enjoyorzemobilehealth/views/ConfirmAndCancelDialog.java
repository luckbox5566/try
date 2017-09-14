package enjoyor.enjoyorzemobilehealth.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;

import enjoyor.enjoyorzemobilehealth.R;

/**
 * Created by Administrator on 2017/7/4.
 */

public class ConfirmAndCancelDialog extends Dialog{
    private Context mContext;
    private Button mConfirm;
    private Button mCancel;

    private OnConfirmOrCancelClickListener onConfirmOrCancelClickListener;

    public ConfirmAndCancelDialog(@NonNull Context context) {
        super(context);
        mContext=context;
    }

    public ConfirmAndCancelDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局
        setContentView(R.layout.dialog_confirm_cancel_layout);
        //设置点击对话框之外的区域对话框不消失
        this.setCanceledOnTouchOutside(false);
        mConfirm= (Button) findViewById(R.id.dialog_confirm);
        mCancel= (Button) findViewById(R.id.dialog_cancel);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onConfirmOrCancelClickListener!=null){
                    onConfirmOrCancelClickListener.onConfirm();
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onConfirmOrCancelClickListener!=null){
                    onConfirmOrCancelClickListener.onCancel();
                }
//                //对话框消失
//                ConfirmAndCancelDialog.this.dismiss();
            }
        });
    }
    public interface OnConfirmOrCancelClickListener{
        void onConfirm();
        void onCancel();
    }

    public void setOnConfirmOrCancelClickListener(OnConfirmOrCancelClickListener onConfirmOrCancelClickListener) {
        this.onConfirmOrCancelClickListener = onConfirmOrCancelClickListener;
    }
}
