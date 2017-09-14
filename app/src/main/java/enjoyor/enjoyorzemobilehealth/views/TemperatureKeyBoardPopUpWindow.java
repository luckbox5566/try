package enjoyor.enjoyorzemobilehealth.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import enjoyor.enjoyorzemobilehealth.R;

/**
 * Created by Administrator on 2017/3/30.
 */

public class TemperatureKeyBoardPopUpWindow extends PopupWindow{
    private Context context;
    //private AppCompatEditText editText;
    private View view;
    private KeyboardView keyboardView;
    public TemperatureKeyBoardPopUpWindow(Context context, KeyboardView.OnKeyboardActionListener listener) {
        this.context = context;
        //this.editText = editText;
        this.view= LayoutInflater.from(context).inflate(R.layout.keyboardview_layout,null);
        init(listener);
    }
    private void init(KeyboardView.OnKeyboardActionListener listener) {
        keyboardView= (KeyboardView) view.findViewById(R.id.kv_edit);
        Keyboard keyboard=new Keyboard(context, R.xml.keyboard);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setEnabled(true);
        //注意在popUpWindow中显示自定义键盘不能设置输入预览
        keyboardView.setPreviewEnabled(false);

        //设置监听
        keyboardView.setOnKeyboardActionListener(listener);
//        keyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);

        this.setContentView(this.view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.popUp_window_anim);
        //Log.i("Data","onKey执行了");
    }
    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener=new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {
//            Toast.makeText(context,"点击了"+primaryCode,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Log.i("Data","onKey执行了");
//            Editable editable = editText.getText();
//            int start = editText.getSelectionStart();
            switch (primaryCode) {
                case Keyboard.KEYCODE_CANCEL:
                    //hideKeyboard();
                    Toast.makeText(context,"点击了"+primaryCode,Toast.LENGTH_SHORT).show();
                    break;
                case Keyboard.KEYCODE_DELETE:
                    Log.i("Data", "点击了删除");
                    Toast.makeText(context,"点击了"+primaryCode,Toast.LENGTH_SHORT).show();
//                    if (!TextUtils.isEmpty(editable)) {
//                        if (start > 0) {
//                            editable.delete(start - 1, start);
//                        }
//                    }
                    break;
                case 46:
                    //点击小数点情况
                    Log.i("Data", "点击了小数点");
                    Toast.makeText(context,"点击了"+primaryCode,Toast.LENGTH_SHORT).show();
//                    if (TextUtils.isEmpty(editable)) {
//                        editable.append("0.");
//                    } else if (!editable.toString().contains(".")) {
//                        //不包含"."时插入
//                        editable.insert(start, Character.toString((char) primaryCode));
//                    }
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    int num = primaryCode + 34;
//                    editable.clear();
//                    editable.insert(0, num + ".");
                    Toast.makeText(context,"点击了"+primaryCode,Toast.LENGTH_SHORT).show();
                    break;
                case 00011:
//                    editable.clear();
                    Toast.makeText(context,"点击了"+primaryCode,Toast.LENGTH_SHORT).show();
                    break;
                case 00012:
                case 47:
                    Toast.makeText(context,"点击了"+primaryCode,Toast.LENGTH_SHORT).show();
                    break;
                default:
//                    editable.insert(start, Character.toString((char) primaryCode));
                    Toast.makeText(context,"点击了"+primaryCode,Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };
}
