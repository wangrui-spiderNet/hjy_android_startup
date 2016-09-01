package cn.thinkjoy.startup.util;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {

    private static Toast toast;

    /**
     * Toast提醒
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * Toast提醒
     *
     * @param context
     */
    public static void showToast(Context context, int resId) {
        if (toast == null) {
            toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(context.getResources().getString(resId));
        }
        toast.show();
    }


}
