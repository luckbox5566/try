package enjoyor.enjoyorzemobilehealth.adapter;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.ShengMingTiZhengLuRuActivity;
import enjoyor.enjoyorzemobilehealth.entities.CanShu;
import enjoyor.enjoyorzemobilehealth.entities.ContentBean;
import enjoyor.enjoyorzemobilehealth.views.DropDownItemSelectPopWindow;
import enjoyor.enjoyorzemobilehealth.views.TemperatureKeyBoardPopUpWindow;

/**
 * Created by Administrator on 2017/2/28.
 */

public class SMTZLuRuContentAdapter extends BaseAdapter {
    private ShengMingTiZhengLuRuActivity context;
    private List<ContentBean> mDatas;
    private List<ContentBean> tempDatas = new ArrayList<>();
    //private UpdateDataListener updateDataListener;
    private boolean isFirstSetValue;
    private boolean isFirstSelected;
    private List<CanShu> mCanShuList;
    //private List<String> spValue;

    public TemperatureKeyBoardPopUpWindow temperaturePopUpWindow;

    public SMTZLuRuContentAdapter(ShengMingTiZhengLuRuActivity context, List<ContentBean> mDatas, List<CanShu> mCanShuList) {
        this.context = context;
        this.mDatas = mDatas;
        this.mCanShuList = mCanShuList;
        //spValue=new ArrayList<>();
        try {
            //深拷贝
            tempDatas = deepCopy(mDatas);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //this.updateDataListener=updateDataListener;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gv_smtz_lv_content, parent, false);
            holder.tvType = (TextView) convertView.findViewById(R.id.tv_type);
            //holder.etTypeInput = (EditText) convertView.findViewById(RcyMoreAdapter.id.et_type_input);
            holder.etTypeInput = (AppCompatEditText) convertView.findViewById(R.id.et_type_input);
            //holder.spContentSelect = (AppCompatSpinner) convertView.findViewById(RcyMoreAdapter.id.sp_value_select);
            //holder.tvContent = (TextView) convertView.findViewById(RcyMoreAdapter.id.tv_value_select);
            holder.tvDanWei = (TextView) convertView.findViewById(R.id.tv_type_danwei);
            //给控件设置tag
            holder.etTypeInput.setTag(position);
            //设置监听
            holder.etTypeInput.addTextChangedListener(new MyEdittextTextWatcher(holder));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.etTypeInput.setTag(position);
        }

        final ContentBean bean = mDatas.get(position);
        final ContentBean temp = tempDatas.get(position);
        holder.tvType.setText(bean.getKongJianMC());

        //设置标志为true
        isFirstSetValue = true;
        Log.i("Data", "true+false---" + isFirstSetValue);
        String value = bean.getContentValue();
        final String id = bean.getJiChuXiangMuID();
        //String value = tempDatas.get(position).getContentValue();
        Log.i("Data", "value---" + value);
        //判断是否是下拉列表
        if (bean.getKongJianLeiXing().equals("1")) {
            if (value == null || value.length() <= 0) {
                holder.etTypeInput.setText("");
            } else {
                holder.etTypeInput.setText(value);
            }
            //标志置为false
            isFirstSetValue = false;
        } else if (bean.getKongJianLeiXing().equals("2")) {
            //holder.etTypeInput.setVisibility(View.GONE);
            //holder.tvContent.setVisibility(View.VISIBLE);
            //holder.tvContent.setText(value);
            holder.etTypeInput.setText(value);
            //标志置为false
            isFirstSetValue = false;
//            final String spTempValue = temp.getContentValue();
//            final List<String> spValue = new ArrayList<>();
//            spValue.add("清空");
//            for (int i = 0; i < mCanShuList.size(); i++) {
//                CanShu canShu = mCanShuList.get(i);
//                if (canShu.getJiChuXiangMuID().equals(id)) {
//                    spValue.add(canShu.getCanShuZhi());
//                }
//            }
//            holder.tvContent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.i("Data","执行了onClick");
//                    if (spValue.size() > 1) {
//                        DropDownItemSelectPopWindow itemSelectPopWindow = new DropDownItemSelectPopWindow(context, spValue);
//                        itemSelectPopWindow.setOnGetBackData(new DropDownItemSelectPopWindow.OnGetBackData() {
//                            @Override
//                            public void getPosition(int pos) {
//                                if(spValue.get(pos).equals("清空")){
//                                    holder.tvContent.setText("");
//                                    bean.setContentValue("");
//                                }else {
//                                    holder.tvContent.setText(spValue.get(pos));
//                                    bean.setContentValue(spValue.get(pos));
//                                }
//                                if (TextUtils.isEmpty(spTempValue) && !TextUtils.isEmpty(bean.getContentValue())) {
//                                    //新增标志为"1"
//                                    bean.setPanDuanBZ("1");
//                                    Log.i("Data", "sp中新增数据了");
//                                } else if (!TextUtils.isEmpty(spTempValue) && !TextUtils.equals(spTempValue, bean.getContentValue())) {
//                                    //修改标志为"0"
//                                    bean.setPanDuanBZ("0");
//                                } else {
//                                    bean.setPanDuanBZ("2");
//                                }
//                                //往Activity回传数据
//                                context.getChangeData(mDatas);
//                            }
//                        });
//                        itemSelectPopWindow.showAtLocation(context.findViewById(RcyMoreAdapter.id.ll_smtz_root_main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
//                    }
//                }
//            });


//            //标志为true
//            isFirstSelected=true;
//            final String spTempValue=temp.getContentValue();
//            final List<String> spValue=new ArrayList<>();
//            holder.etTypeInput.setVisibility(View.GONE);
//            holder.spContentSelect.setVisibility(View.VISIBLE);
//            for(int i=0;i<mCanShuList.size();i++){
//                CanShu canShu=mCanShuList.get(i);
//                if(canShu.getJiChuXiangMuID().equals(id)){
//                    spValue.add(canShu.getCanShuZhi());
//                }
//            }
//            //holder.spContentSelect.setPrompt(bean.getKongJianMC());
//            holder.spContentSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    if(isFirstSelected){
//                        isFirstSelected=false;
//                    }else{
//                        bean.setContentValue(spValue.get(position));
//                        if(TextUtils.isEmpty(spTempValue)&&!TextUtils.isEmpty(bean.getContentValue())){
//                            //新增标志为"1"
//                            bean.setPanDuanBZ("1");
//                            Log.i("Data","sp中新增数据了");
//                        }else if(!TextUtils.isEmpty(spTempValue)&&!TextUtils.equals(spTempValue,bean.getContentValue())){
//                            //修改标志为"0"
//                            bean.setPanDuanBZ("0");
//                        }else{
//                            bean.setPanDuanBZ("2");
//                        }
//                        //往Activity回传数据
//                        context.getChangeData(mDatas);
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
//            ArrayAdapter spAdapter=new ArrayAdapter(context,RcyMoreAdapter.layout.sp_item_select,spValue);
//            spAdapter.setDropDownViewResource(RcyMoreAdapter.layout.sp_item_drop);
//            //设置适配器
//            holder.spContentSelect.setAdapter(spAdapter);
//            //isFirstSelected=false;
        }

        holder.etTypeInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//              if(!holder.etTypeInput.hasFocus()){
//
//              }
                //注意：默认按下时手指抬起还会执行一次onTouch，所以进行了是否是按下动作的判断
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.i("Data", "执行了onTouch");
//                    if (!TextUtils.isEmpty(bean.getShuZhiDW())) {
//                        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                        //manager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
//                        if (manager != null && manager.isActive()) {
//                            Log.i("Data", "执行了屏蔽系统键盘");
//                            manager.hideSoftInputFromInputMethod(v.getWindowToken(), 0);
//                            //manager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
//                        }
//
//                        showPopUpWindow(holder.etTypeInput);
////                      int inputType=holder.etTypeInput.getInputType();
////                      holder.etTypeInput.setInputType(InputType.TYPE_NULL);
////                      Log.i("Data","执行了屏蔽系统键盘");
////                      //keyboardUtil.showKeyboard();
////                      holder.etTypeInput.setInputType(inputType);
//                    } else {
//                        //keyboardUtil.hideKeyboard();
//                        if (temperaturePopUpWindow != null && temperaturePopUpWindow.isShowing()) {
//                            temperaturePopUpWindow.dismiss();
//                        }
//                    }

                    if (bean.getKongJianLeiXing().equals("1")) {
//                        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                        //manager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
//                        if (manager != null && manager.isActive()) {
//                            Log.i("Data", "执行了屏蔽系统键盘");
//                            manager.hideSoftInputFromInputMethod(v.getWindowToken(), 0);
//                            //manager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
//                        }

                        //屏蔽系统软键盘但不使edittext光标消失
                        if(android.os.Build.VERSION.SDK_INT <= 10){
                            holder.etTypeInput.setInputType(InputType.TYPE_NULL);
                        }else {
                            Class<EditText> cls=EditText.class;
                            Method method;
                            try {
                                method = cls.getMethod("setShowSoftInputOnFocus",boolean.class);
                                method.setAccessible(true);
                                method.invoke(holder.etTypeInput, false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                method = cls.getMethod("setSoftInputShownOnFocus",boolean.class);
                                method.setAccessible(true);
                                method.invoke(holder.etTypeInput, false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        showPopUpWindow(holder.etTypeInput);

//                      int inputType=holder.etTypeInput.getInputType();
//                      holder.etTypeInput.setInputType(InputType.TYPE_NULL);
//                      Log.i("Data","执行了屏蔽系统键盘");
//                      //keyboardUtil.showKeyboard();
//                      holder.etTypeInput.setInputType(inputType);
                    } else {
//                        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                        manager.hideSoftInputFromWindow(v.getWindowToken(),0);
//                        if (manager != null && manager.isActive()) {
//                            Log.i("Data", "执行了屏蔽系统键盘");
//                            //manager.hideSoftInputFromInputMethod(v.getWindowToken(), 0);
//                            manager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
//                        }
//                        int inputType = holder.etTypeInput.getInputType();
//                        Log.i("Data", "执行了屏蔽系统键盘");
//                        holder.etTypeInput.setInputType(inputType);

                        //屏蔽系统软键盘但不使edittext光标消失
                        if(android.os.Build.VERSION.SDK_INT <= 10){
                            holder.etTypeInput.setInputType(InputType.TYPE_NULL);
                        }else {
                            Class<EditText> cls=EditText.class;
                            Method method;
                            try {
                                method = cls.getMethod("setShowSoftInputOnFocus",boolean.class);
                                method.setAccessible(true);
                                method.invoke(holder.etTypeInput, false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                method = cls.getMethod("setSoftInputShownOnFocus",boolean.class);
                                method.setAccessible(true);
                                method.invoke(holder.etTypeInput, false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        final String spTempValue = temp.getContentValue();
                        final List<String> spValue = new ArrayList<>();
                        spValue.add("清空");
                        for (int i = 0; i < mCanShuList.size(); i++) {
                            CanShu canShu = mCanShuList.get(i);
                            if (canShu.getJiChuXiangMuID().equals(id)) {
                                spValue.add(canShu.getCanShuZhi());
                            }
                        }
                        if (spValue.size() > 1) {
                            DropDownItemSelectPopWindow itemSelectPopWindow = new DropDownItemSelectPopWindow(context, spValue);
                            itemSelectPopWindow.setOnGetBackData(new DropDownItemSelectPopWindow.OnGetBackData() {
                                @Override
                                public void getPosition(int pos) {
                                    if (spValue.get(pos).equals("清空")) {
                                        holder.etTypeInput.setText("");
                                        bean.setContentValue("");
                                    } else {
                                        holder.etTypeInput.setText(spValue.get(pos));
                                        bean.setContentValue(spValue.get(pos));
                                    }
                                    if (TextUtils.isEmpty(spTempValue) && !TextUtils.isEmpty(bean.getContentValue())) {
                                        //新增标志为"1"
                                        bean.setPanDuanBZ("1");
                                        Log.i("Data", "sp中新增数据了");
                                    } else if (!TextUtils.isEmpty(spTempValue) && !TextUtils.equals(spTempValue, bean.getContentValue())) {
                                        //修改标志为"0"
                                        bean.setPanDuanBZ("0");
                                    } else {
                                        bean.setPanDuanBZ("2");
                                    }
                                    //往Activity回传数据
                                    context.getChangeData(mDatas);
                                }
                            });
                            //itemSelectPopWindow.showAtLocation(context.findViewById(RcyMoreAdapter.id.ll_smtz_root_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                            itemSelectPopWindow.showAsDropDown(holder.etTypeInput,holder.etTypeInput.getWidth()/2,0);
                        }
                    }
                }
                return false;
            }
        });

        String danWei = bean.getShuZhiDW();
        if (danWei == null || danWei.length() <= 0) {
            holder.tvDanWei.setVisibility(View.INVISIBLE);
        } else {
            holder.tvDanWei.setText(danWei);
        }

//        holder.etTypeInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                if(s.toString()==null||s.toString().length()<=0){
////                    bean.setContentValue("");
////
////                }else{
////                    bean.setContentValue(s.toString());
////                }
////                //往Activity回传数据
////                context.getChangeData(mDatas);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                //String text=holder.etTypeInput.getText().toString();
//                String text = s.toString();
//                if (TextUtils.isEmpty(bean.getContentValue()) && !TextUtils.isEmpty(text)) {
//                    bean.setContentValue(text);
//                    //新增标志为1
//                    bean.setPanDuanBZ("1");
//                    Log.i("Data","标志"+bean.getPanDuanBZ());
//                } else if (!TextUtils.isEmpty(bean.getContentValue()) && TextUtils.isEmpty(text)) {
//                    bean.setContentValue("");
//                    //修改标志为0
//                    bean.setPanDuanBZ("0");
//                    Log.i("Data","标志"+bean.getPanDuanBZ());
//                } else if (!TextUtils.isEmpty(bean.getContentValue()) && !TextUtils.isEmpty(text) && !TextUtils.equals(bean.getContentValue(), text)) {
//                    bean.setContentValue(text);
//                    //修改标志为0
//                    bean.setPanDuanBZ("0");
//                    Log.i("Data","标志"+bean.getPanDuanBZ());
//                } else {
//                    //为了提交插入和修改做判断而设置的
//                    bean.setPanDuanBZ("2");
//                    Log.i("Data","标志"+bean.getPanDuanBZ());
//                }
//                //往Activity回传数据
//                context.getChangeData(mDatas);
//            }
//        });
        return convertView;
    }

    private void showPopUpWindow(final AppCompatEditText editText) {
        KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {

            }

            @Override
            public void onRelease(int primaryCode) {

            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                //Log.i("Data","onKey执行了");
                Editable editable = editText.getText();
                int start = editText.getSelectionStart();
                switch (primaryCode) {
                    case Keyboard.KEYCODE_CANCEL:
                        //hideKeyboard();
                        if (temperaturePopUpWindow != null) {
                            temperaturePopUpWindow.dismiss();
                        }
                        break;
                    case Keyboard.KEYCODE_DELETE:
                        Log.i("Data", "点击了删除");
                        if (!TextUtils.isEmpty(editable)) {
                            if (start > 0) {
                                editable.delete(start - 1, start);
                            }
                        }
                        break;
                    case 46:
                        //点击小数点情况
                        Log.i("Data", "点击了小数点");
                        if (TextUtils.isEmpty(editable)) {
                            editable.append("0.");
                        } else if (!editable.toString().contains(".")) {
                            //不包含"."时插入
                            editable.insert(start, Character.toString((char) primaryCode));
                        }
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
                        editable.clear();
                        editable.insert(0, num + ".");
                        break;
                    case 00011:
                        editable.clear();
                        break;
                    case 00012:
                    case 47:
                        break;
                    default:
                        editable.insert(start, Character.toString((char) primaryCode));
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
        temperaturePopUpWindow = new TemperatureKeyBoardPopUpWindow(context, mOnKeyboardActionListener);
        temperaturePopUpWindow.setFocusable(true);
        temperaturePopUpWindow.showAtLocation(context.findViewById(R.id.ll_smtz_root_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

//        View popUpView=LayoutInflater.from(context).inflate(RcyMoreAdapter.layout.keyboardview_layout,null);
//        KeyboardView keyboardView= (KeyboardView) popUpView.findViewById(RcyMoreAdapter.id.kv_edit);
//        Keyboard keyboard=new Keyboard(context,RcyMoreAdapter.xml.keyboard);
//        keyboardView.setKeyboard(keyboard);
//        keyboardView.setEnabled(true);
//        keyboardView.setPreviewEnabled(true);
//        keyboardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
//            @Override
//            public void onPress(int primaryCode) {
//
//            }
//
//            @Override
//            public void onRelease(int primaryCode) {
//
//            }
//
//            @Override
//            public void onKey(int primaryCode, int[] keyCodes) {
//                Editable editable = editText.getText();
//                int start = editText.getSelectionStart();
//                switch (primaryCode) {
//                    case Keyboard.KEYCODE_CANCEL:
//                        //hideKeyboard();
//                        break;
//                    case Keyboard.KEYCODE_DELETE:
//                        Log.i("Data", "点击了删除");
//                        if (!TextUtils.isEmpty(editable)) {
//                            if (start > 0) {
//                                editable.delete(start - 1, start);
//                            }
//                        }
//                        break;
//                    case 46:
//                        //点击小数点情况
//                        Log.i("Data", "点击了小数点");
//                        if (TextUtils.isEmpty(editable)) {
//                            editable.append("0.");
//                        } else if (!editable.toString().contains(".")) {
//                            //不包含"."时插入
//                            editable.insert(start, Character.toString((char) primaryCode));
//                        }
//                        break;
//                    case 1:
//                    case 2:
//                    case 3:
//                    case 4:
//                    case 5:
//                    case 6:
//                    case 7:
//                    case 8:
//                        int num = primaryCode + 34;
//                        editable.clear();
//                        editable.insert(0, num + ".");
//                        break;
//                    case 00011:
//                        editable.clear();
//                        break;
//                    case 00012:
//                    case 47:
//                        break;
//                    default:
//                        editable.insert(start, Character.toString((char) primaryCode));
//                        break;
//                }
//            }
//
//            @Override
//            public void onText(CharSequence text) {
//
//            }
//
//            @Override
//            public void swipeLeft() {
//
//            }
//
//            @Override
//            public void swipeRight() {
//
//            }
//
//            @Override
//            public void swipeDown() {
//
//            }
//
//            @Override
//            public void swipeUp() {
//
//            }
//        });
//        PopupWindow mPopupWindow=new PopupWindow(popUpView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        //设置动画
//        mPopupWindow.setAnimationStyle(RcyMoreAdapter.style.popUp_window_anim);
//        //设置背景
//        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
//        //设置弹出窗体可点击
//        mPopupWindow.setFocusable(true);
//        //设置窗体以外的部分可点击
//        mPopupWindow.setOutsideTouchable(true);
//        //从布局底部弹出
//        mPopupWindow.showAtLocation(context.findViewById(RcyMoreAdapter.id.ll_smtz_root_main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
    }


    class ViewHolder {
        TextView tvType;
        //EditText etTypeInput;
        AppCompatEditText etTypeInput;
        //AppCompatSpinner spContentSelect;
        //TextView tvContent;
        TextView tvDanWei;
    }

    //    public interface UpdateDataListener{
//        void transferData(List<ContentBean> data);
//    }


    private class MyEdittextTextWatcher implements TextWatcher {
        ViewHolder holder = null;

        public MyEdittextTextWatcher(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int position = (int) holder.etTypeInput.getTag();
            //String text=holder.etTypeInput.getText().toString();
            String text = s.toString();

            //设置光标始终在文字右边
            holder.etTypeInput.setSelection(text.length());

            ContentBean bean = mDatas.get(position);
            ContentBean tempBean = tempDatas.get(position);

            if (!isFirstSetValue) {
                if (TextUtils.isEmpty(tempBean.getContentValue()) && !TextUtils.isEmpty(text)) {
                    bean.setContentValue(text);
                    //新增标志为1
                    bean.setPanDuanBZ("1");
//                    tempBean.setContentValue(text);
//                    //新增标志为1
//                    tempBean.setPanDuanBZ("1");
                    Log.i("Data", "标志" + bean.getPanDuanBZ());
                } else if (!TextUtils.isEmpty(tempBean.getContentValue()) && !TextUtils.equals(tempBean.getContentValue(), text)) {
                    bean.setContentValue(text);
                    //修改标志为0
                    bean.setPanDuanBZ("0");
                    //Log.i("Data", "标志" + !TextUtils.isEmpty(bean.getContentValue()));
//                    tempBean.setContentValue(text);
//                    //修改标志为0
//                    tempBean.setPanDuanBZ("0");
                    //Log.i("Data", "标志" + tempBean.getPanDuanBZ());
                    Log.i("Data", "标志" + !TextUtils.isEmpty(tempBean.getContentValue()));
                    Log.i("Data", "标志" + !TextUtils.equals(tempBean.getContentValue(), text));
                } else {
                    //为了提交插入和修改做判断而设置的
                    bean.setContentValue(text);
                    bean.setPanDuanBZ("2");
                    Log.i("Data", "标志" + bean.getPanDuanBZ());
//                    tempBean.setContentValue(text);
//                    tempBean.setPanDuanBZ("2");
                    //Log.i("Data", "标志" + bean.getPanDuanBZ());
                }
                if (bean.getKongJianMC().equals("体温") || bean.getKongJianMC().equals("降后体温")) {
                    if (bean.getContentValue() != null) {
                        try {
                            int value = Integer.parseInt(bean.getContentValue());
                            if (value > 43) {
                                Toast.makeText(context, "您的输入值超出合理范围，请重新输入！", Toast.LENGTH_SHORT).show();
                            } else {
                                //往Activity回传数据
                                context.getChangeData(mDatas);
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //往Activity回传数据
                        context.getChangeData(mDatas);
                    }
                } else {
                    //往Activity回传数据
                    context.getChangeData(mDatas);
                }
                //context.getChangeData(tempDatas);
            }
        }
    }

    public List<ContentBean> deepCopy(List<ContentBean> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        List<ContentBean> dest = (List<ContentBean>) in.readObject();
        return dest;
    }
}
