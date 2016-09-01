package cn.thinkjoy.startup.widget;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import cn.thinkjoy.startup.R;


/**
 * 
 * <p> Title: CustomProgressDialog.java </p>
 * <p> Description: </p>
 * <p> Copyright：Copyrigth (c) 2014 </p> 
 * <p> Company:Monda Group </P>
 * @Time 2014年11月19日
 * @version 1.0.0
 */
public class CustomProgressDialog extends Dialog {

	public CustomProgressDialog(Context context) {
		super(context, R.style.custom_dialog);
		this.setContentView(
				R.layout.progress_dialog);
		setCancelable(true);
	}

	public void setMessage(CharSequence message) {
		((TextView) findViewById(R.id.progress_dialog_msg)).setText(message);
	}
	
}