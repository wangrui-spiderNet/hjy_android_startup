package cn.thinkjoy.startup.http;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import cn.thinkjoy.startup.R;
import cn.thinkjoy.startup.base.MyApplication;
import cn.thinkjoy.startup.bean.Role;
import cn.thinkjoy.startup.util.APPPreferenceUtil;
import cn.thinkjoy.startup.util.DeviceUtils;


/**
 * 请求参数比传
 * Created by xjliu on 16/7/3.
 */
public class RequestData {
    // 固定的 header 字段
    private String clientOS; // 客户端操作系统
    private String abb; // 	地区简称
    private String apiVersion; // 协议版本
    private String appVersion; // 客户端版本
    private String channel; // 		渠道
    private int classId; // 班级ID
    private String machineCode; // 手机的唯一编码
    private int userId; // 用户Id
    private int schoolId; // schoolId	学校ID
    private String session; // 登录后session值
    private int userType; // 	用户类型
    private int accountId; // 	帐户id
    // 基本参数
    public static RequestData instance;
    private String appName;

    public static RequestData getInstance() {
        if (instance == null) {
            instance = new RequestData();
        }
        return instance;
    }

    public Map<String, Object> getInitParams(Context context) {
        Map<String, Object> params = new HashMap<String, Object>();
        apiVersion = "1.0";
//        apiVersion = "4.0";
        clientOS = "android";
        machineCode = DeviceUtils.getIMEI(context);
//        machineCode = "";
        channel = "offical";
        appName = "XXTZheJiangBabyProvince";
//        appName = "XXTZheJiangProvince";
        Role currentRole = MyApplication.getInstance().getRole();
        if (currentRole != null) {
            userId = currentRole.getUserId();
            classId = currentRole.getClassId();
            schoolId = currentRole.getSchoolId();
            abb = currentRole.getAreaAbb();
            userType = currentRole.getUserType();
            accountId = currentRole.getAccountId();
        } else {
            userId = 0;
            classId = 0;
            schoolId = 0;
            abb = "";
            userType = 0;
            accountId = 0;
        }
        session = APPPreferenceUtil.getInstance().getSession();
        appVersion = DeviceUtils.getVersionName(context);
        if (TextUtils.isEmpty(appVersion)) {
            appVersion = context.getResources().getString(R.string.app_version);
        }
//        appVersion = "4.0";
        params.put("clientOS", clientOS);
        params.put("platform", clientOS);
        params.put("apiVersion", apiVersion);
        params.put("appVersion", appVersion);
        params.put("channel", channel);
        params.put("machineCode", machineCode);
        params.put("classId", classId);
        params.put("abb", abb);
//        params.put("area", abb);  //中学版通讯录字段
        params.put("schoolId", schoolId);
        params.put("session", session);
        params.put("userId", userId);
        params.put("userType", userType);
        params.put("accountId", accountId);
        params.put("appName", appName);
        return params;
    }

}
