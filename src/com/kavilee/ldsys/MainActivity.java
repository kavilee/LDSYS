package com.kavilee.ldsys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;
/**
 * 应用打开时图片的淡出效果
 * 
 * @author kavilee11
 *
 */
public class MainActivity extends Activity{
	private Handler mHandler = new Handler();
    ImageView imageview;
    int alpha = 250;
    int b = 0;
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
        imageview = (ImageView) this.findViewById(R.id.ImageView01);
        
        imageview.setAlpha(alpha);
        
        new Thread(new Runnable() {
            public void run() {
                initApp(); //初始化程序
                
                while (b < 2) {
                    try {
                        if (b == 0) {
                            Thread.sleep(2000);
                            b = 1;
                        } else {
                            Thread.sleep(50);
                        }
                        updateApp();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                imageview.setAlpha(alpha);
                imageview.invalidate();
            }
        };

	}
	public void updateApp() {
        alpha -= 10;
        if (alpha <= 0) {
            b = 2;
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            //关闭当前activity
            MainActivity.this.finish();
        }
        mHandler.sendMessage(mHandler.obtainMessage());
    }
    
    public void initApp(){
        
    }
}
