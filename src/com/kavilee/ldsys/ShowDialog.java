package com.kavilee.ldsys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ShowDialog extends AlertDialog{
	
	public ShowDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setTitle("警告");
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		setButton(BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	           }
		});
		setButton(BUTTON_NEGATIVE, "取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dismiss();
			}
		});
	}

	public void show(String string) {
		// TODO Auto-generated method stub
		setMessage(string);
		show();
	}

}	