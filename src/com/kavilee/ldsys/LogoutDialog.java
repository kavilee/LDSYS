package com.kavilee.ldsys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class LogoutDialog extends AlertDialog {

	public LogoutDialog(Context context) {
		super(context);
		setTitle("退出");
		setMessage("当前操作会使您退出本系统，您确定要退出？");
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		setButton(BUTTON_POSITIVE, "确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				 MyApp.exit();
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
