package com.kavilee.ldsys.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.kavilee.ldsys.R;
import com.kavilee.model.User;

public class UserListAdapter extends BaseAdapter {

	OnItemClickListener onItemClickListener;
	Context context;
	List<User> list;
	boolean flag_first = false;
	float startX, endX;

	public UserListAdapter(Context context, List<User> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public User getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.listview_item_user, null);
		TextView title = (TextView) convertView.findViewById(R.id.ItemTitle);
		TextView orderName = (TextView) convertView.findViewById(R.id.ItemText);
		final Button del = (Button) convertView.findViewById(R.id.del);

		User item = getItem(position);
		title.setText("编号: " + item.getUserID());
		orderName.setText("用户名: " + item.getUserName());
		
		convertView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					flag_first = true;
					startX = event.getX();
					//如果之前出现了删除按钮，就隐藏
					break;

				case MotionEvent.ACTION_MOVE:
					endX =event.getX();
					//滑动距离超过50就显示
					if(flag_first && Math.abs(endX-startX) > 50){
						if(del.getVisibility() == View.GONE){
							//del.setVisibility(View.VISIBLE);
						}
						else{
							del.setVisibility(View.GONE);
						}
						flag_first =false;
					}
					break;
				case MotionEvent.ACTION_UP:
					//不够50就当成点击事件
					if(Math.abs(endX-startX) < 50){
						if(onItemClickListener != null){
							onItemClickListener.onItemClick(null, null, position, 0);
						}
					}
					break;
				}
				return true;
			}
		});
		return convertView;
	}
	public OnItemClickListener getOnItemClickListener() {
		return onItemClickListener;
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}
	
}
