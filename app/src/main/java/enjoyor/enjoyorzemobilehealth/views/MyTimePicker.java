package enjoyor.enjoyorzemobilehealth.views;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import enjoyor.enjoyorzemobilehealth.R;

/**
 * Created by Administrator on 2017/2/27.
 */

public class MyTimePicker extends AlertDialog implements View.OnClickListener, NumberPicker.Formatter, NumberPicker.OnValueChangeListener, NumberPicker.OnScrollListener {
    private NumberPicker mTimePicker;
    private Button cancleButton, okButton;
    private int measureWidth;

    String[] time = new String[]{"09:00", "12:00", "15:00", "18:00", "21:00"};
    private int selectPosition;
    private MyOnTimeSetListener myOnTimeSetListener;

    protected MyTimePicker(Context context) {
        super(context);
    }

    protected MyTimePicker(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected MyTimePicker(Context context, int themeResId) {
        super(context, themeResId);
    }

    public MyTimePicker(Context context, MyOnTimeSetListener myOnTimeSetListener) {
        super(context);
        this.myOnTimeSetListener = myOnTimeSetListener;
        init();
        initTimePickerData();
    }

    private void initTimePickerData() {
        //使NumberPicker不可以编辑
        mTimePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        mTimePicker.setDisplayedValues(time);
        mTimePicker.setMinValue(0);
        mTimePicker.setMaxValue(time.length - 1);
        mTimePicker.setValue(0);
        mTimePicker.setFormatter(this);
        mTimePicker.setOnValueChangedListener(this);
        mTimePicker.setOnScrollListener(this);
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Context contextThemeWrapper = new ContextThemeWrapper(
                getContext(), android.R.style.Theme_Holo_Light);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View view = localInflater.inflate(R.layout.dialog_view_time_picker, null, false);
        setView(view);
        mTimePicker = (NumberPicker) view.findViewById(R.id.np_time);
        LinearLayout buttonGroup = (LinearLayout) view.findViewById(R.id.buttonGroup);
        cancleButton = (Button) view.findViewById(R.id.cancelButton);
        okButton = (Button) view.findViewById(R.id.okButton);
        cancleButton.setOnClickListener(this);
        okButton.setOnClickListener(this);

        // 设置 显示 宽度
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        buttonGroup.measure(width, height);
        mTimePicker.measure(width, height);
        if (buttonGroup.getMeasuredWidth() > mTimePicker.getMeasuredWidth()) {
            this.measureWidth = buttonGroup.getMeasuredWidth();
        } else {
            this.measureWidth = mTimePicker.getMeasuredWidth();
        }

    }

    public void hideOrShow() {
        if (this == null) {
            return;
        }
        if (!this.isShowing()) {
            this.show();
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelButton:
                dismiss();
                break;
            case R.id.okButton:
                onOkButtonClick();
                dismiss();
                break;
        }
    }

    private void onOkButtonClick() {
        if (this.myOnTimeSetListener != null) {
            this.myOnTimeSetListener.onTimeSet(time[selectPosition] + "");
        }
    }

    @Override
    public String format(int value) {
        return null;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        this.selectPosition = newVal;
    }

    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {

    }

    public interface MyOnTimeSetListener {
        void onTimeSet(String time);
    }
}
