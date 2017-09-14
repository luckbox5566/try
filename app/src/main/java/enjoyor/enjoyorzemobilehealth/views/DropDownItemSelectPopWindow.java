package enjoyor.enjoyorzemobilehealth.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import enjoyor.enjoyorzemobilehealth.R;

/**
 * Created by Administrator on 2017/4/12.
 */

public class DropDownItemSelectPopWindow extends PopupWindow implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Context mContext;
    private List<String> mList;
    private View view;
    private LayoutInflater inflater;
    private ListView mListView;
    //private AutoWidthListView mListView;
    private OnGetBackData onGetBackData;
    private int selectPosition=0;

    public DropDownItemSelectPopWindow(Context context, List<String> mList) {
        this.mContext = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        view = inflater.inflate(R.layout.list_popupwindow, null);
        mListView = (ListView) view.findViewById(R.id.lv_pop_content);
        //mListView= (AutoWidthListView) view.findViewById(RcyMoreAdapter.id.lv_pop_content);
        //Button btnSure = (Button) view.findViewById(RcyMoreAdapter.id.btn_sure);
        //Button btnCancel = (Button) view.findViewById(RcyMoreAdapter.id.btn_cancle);
        mListView.setAdapter(new listPopUpWindowAdapter());
        mListView.setOnItemClickListener(this);
        //btnSure.setOnClickListener(this);
        //btnCancel.setOnClickListener(this);

        this.setContentView(this.view);
        //popUpWindow宽度自适应ListView的宽度
        //mListView.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        //this.setWidth(mListView.getMeasuredWidth());

        //this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setWidth(150);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffF8F8F8")));
        this.setOutsideTouchable(true);

    }

    public void setOnGetBackData(OnGetBackData onGetBackData) {
        this.onGetBackData = onGetBackData;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case RcyMoreAdapter.id.btn_sure:
//                if(onGetBackData!=null){
//                    onGetBackData.getPosition(selectPosition);
//                }
//                dismiss();
//                break;
//            case RcyMoreAdapter.id.btn_cancle:
//                dismiss();
//                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectPosition=position;
        onGetBackData.getPosition(selectPosition);
        //popUpWindow消失
        dismiss();
    }

    class listPopUpWindowAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_list_popupwindow,null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv_item_pop);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(mList.get(position));
            return convertView;
        }

        class ViewHolder {
            TextView tv;
        }
    }

    public interface OnGetBackData {
        /**
         * 回传选中的item的position
         *
         * @param pos
         */
        void getPosition(int pos);
    }
}
