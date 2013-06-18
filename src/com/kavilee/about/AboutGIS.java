package com.kavilee.about;

import com.kavilee.ldsys.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class AboutGIS extends Activity{
//	private TextView myTextView;
//	final public String DEV_FILE = "/data/data/com.kavilee.ldsys/aboutgis.txt";//文件地址
//	final String TEXT_ENCODING = "UTF-8";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//设置窗口没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutgis);
		
//		myTextView = (TextView)findViewById(R.id.myTextView);
//		
//		String str;
//        str = getinfo(DEV_FILE);
//        String[] x;
//        x = str.split("/r");
//        myTextView.setText(x[0]);
//        int i;
//        for (i = 1; i <= x.length - 1; i++) {
//            myTextView.append(x[i]);
//        }
//	}
//
//	private String getinfo(String path) {
//		// TODO Auto-generated method stub
//		File file;
//        String str = "";
//        FileInputStream in;
//        try {
//            // 打开文件file的InputStream
//            file = new File(path);
//            in = new FileInputStream(file);
//            // 将文件内容全部读入到byte数组
//            int length = (int) file.length();
//            byte[] temp = new byte[length];
//            in.read(temp, 0, length);
//            // 将byte数组用UTF-8编码并存入display字符串中
//            str = EncodingUtils.getString(temp, TEXT_ENCODING);
//            // 关闭文件file的InputStream
// 
//            in.close();
//        } catch (IOException e) {
// 
//        }
//        return str;
    }
	

}
