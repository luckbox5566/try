package enjoyor.enjoyorzemobilehealth.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.danger.DangerActivity;
import enjoyor.enjoyorzemobilehealth.fragment.danger.RightFragment;


/**
 * 左侧菜单ListView的适配器
 * @author Administrator
 *
 */
public class LeftAdapter extends BaseAdapter {

	private Context context;
	private int selectItem = 0;
	private String [] data ;
	private int mPosition=-1;


	public LeftAdapter(Context context, String[] data) {
		this.context = context;
		this.data = data;
	}

	public int getSelectItem() {
		return selectItem;
	}
	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int i, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		mPosition = i;
		ViewHolder holder = null;
		if(arg1 == null) {
			holder = new ViewHolder();
			arg1 = View.inflate(context, R.layout.item_left, null);
			holder.tv_name = (TextView)arg1.findViewById(R.id.item_name);
			arg1.setTag(holder);
		}else {
			holder = (ViewHolder)arg1.getTag();
		}

		if(i == DangerActivity.mPosition){
			holder.tv_name.setBackgroundColor(Color.WHITE);
			holder.tv_name.setTextColor(context.getResources().getColor(R.color.my_bule));
		}else {
			holder.tv_name.setBackgroundColor(context.getResources().getColor(R.color.menu_item_bg));
			holder.tv_name.setTextColor(context.getResources().getColor(R.color.menu_t));
		}
		holder.tv_name.setText(data[i]);
		return arg1;
	}
	static class ViewHolder{
		private TextView tv_name;
	}
}
