package com.kavilee.ldsys.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kavilee.db.OrderDao;
import com.kavilee.interfaces.DeleteOrderInterface;
import com.kavilee.ldsys.R;
import com.kavilee.model.Order;

public class OrderListAdapter extends BaseAdapter {

	OnItemClickListener onItemClickListener;
	Context context;
	List<Order> list;
	boolean flag_first = false;
	float startX, endX;
	DeleteOrderInterface deleteOrderCallBack;
	boolean canMoveDelete = true;//判断是否开启滑动删除功能，默认是开启的，配送员页面需要将此标识设为false

	public void setCanMoveDelete(boolean canMoveDelete) {
		this.canMoveDelete = canMoveDelete;
	}

	public OrderListAdapter(Context context, List<Order> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Order getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.listview_item_order, null);
		TextView title = (TextView) convertView.findViewById(R.id.ItemTitle);
		TextView orderName = (TextView) convertView.findViewById(R.id.ItemText);
		TextView orderPrice = (TextView) convertView
				.findViewById(R.id.ItemText2);
		final Button del = (Button) convertView.findViewById(R.id.del);
		del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				OrderDao dao = new OrderDao(context);
				boolean result = dao
						.deleteOrder(getItem(position).getOrderID());
				if (result) {
					Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
					deleteOrderCallBack.onSuccess();
				} else {
					Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
					deleteOrderCallBack.onFaild();
				}
			}
		});
		Order item = getItem(position);
		title.setText("订单号: " + item.getOrderID());
		orderName.setText("货物名: " + item.getGoodsName());
		orderPrice.setText("订单价格: " + item.getOrderPrice() + "元");
		convertView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					flag_first = true;
					startX = event.getX();
					// 如果之前出现了删除按钮，就隐藏
					// if (del != null) {
					// del.setVisibility(View.GONE);
					// }
					break;

				case MotionEvent.ACTION_MOVE:
					endX = event.getX();
					// 滑动距离超过50就显示（补：而且必须是没有关闭删除功能 ,即canMoveDelete == true）
					if (flag_first && Math.abs(endX - startX) > 50 && canMoveDelete) {
						if (del.getVisibility() == View.GONE) {
							del.setVisibility(View.VISIBLE);
						} else {
							del.setVisibility(View.GONE);
						}
						flag_first = false;
					}
					break;
				case MotionEvent.ACTION_UP:
					// 不够50就当成点击事件
					if (Math.abs(endX - startX) < 50) {
						// Order order = getItem(position);
						// // TODO 管理员点击的订单保存到内存中
						// RunTime.adminClickedItemOrder = order;
						// Intent intent = new
						// Intent(context,OrderDetail_admin_con.class);
						// context.startActivity(intent);
						if (onItemClickListener != null) {
							onItemClickListener.onItemClick(null, null,
									position, 0);
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

	public DeleteOrderInterface getDeleteOrderCallBack() {
		return deleteOrderCallBack;
	}

	public void setDeleteOrderCallBack(DeleteOrderInterface deleteOrderCallBack) {
		this.deleteOrderCallBack = deleteOrderCallBack;
	}

}
