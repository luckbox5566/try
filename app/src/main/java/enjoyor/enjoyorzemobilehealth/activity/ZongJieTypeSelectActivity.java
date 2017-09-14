package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import enjoyor.enjoyorzemobilehealth.R;

/**
 * Created by Administrator on 2017/6/5.
 */

public class ZongJieTypeSelectActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private ImageView mIvBack;
    private ListView mLvTypeSelect;
    private String[] mTypeData;
    private LayoutInflater inflater;

    private String selectedType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zongjietypeselect);
        context = this;
        initData();
        initView();
    }

    private void initData() {
        inflater = LayoutInflater.from(context);
        mTypeData = getResources().getStringArray(R.array.zongjietype);
        selectedType=mTypeData[0];
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mLvTypeSelect = (ListView) findViewById(R.id.lv_zongjietype_select);
        mLvTypeSelect.setAdapter(new MyTypeSelectAdapter());
        mLvTypeSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedType=mTypeData[position];
                Intent intent=new Intent();
                intent.putExtra("type",selectedType);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        mIvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
//                Intent intent=new Intent();
//                intent.putExtra("type",selectedType);
//                setResult(RESULT_OK,intent);
                finish();
                break;
            default:
                break;
        }
    }

    private class MyTypeSelectAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mTypeData.length;
        }

        @Override
        public Object getItem(int position) {
            return mTypeData[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_lv_zongjietype, null);
                holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvContent.setText(mTypeData[position]);
            return convertView;
        }

        class ViewHolder {
            TextView tvContent;
        }
    }
}
