package cn.thinkjoy.startup.http.api;


import cn.thinkjoy.startup.base.MyApplication;

/**
 * 公共地址
 */
public class URLHelpers {

    public static String BASE_URL = "http://"+ MyApplication.getConfig().getAddress()+"/babymobile/";
//    public static String BASE_URL = "http://"+kgApplication.getConfig().getAddress()+"/";


    public static String URL_SYSTEM = BASE_URL + "system/";//登录


    /**
     * 上传图片
     */

    public static String SENDIMAGE_URL = BASE_URL + "upload/msgpic/"; // 福建


    /**
     * 发送语音
     */
    public static String SENDVOICE_URL = BASE_URL + "upload/audio/";// 福建


    public static String URL_FAMILY_CIRCLE = BASE_URL + "familyCircle/";//家园圈
    public static String URL_COOKBOOK = BASE_URL + "babyCookBook/";//菜谱
    public static String URL_LESSON_PLAN = BASE_URL + "coursePlan/";//课程计划
    public static String URL_GROWTH_RECORD = BASE_URL + "growRecord/";//成长记录
    public static String URL_NOTICE = BASE_URL + "notice/";//通知
    public static String URL_ATTENDANCE = BASE_URL + "attendance/";//考勤
    /**
     * 通讯录请求URL
     */
    public static String CONTACT_URL = BASE_URL + "linkman/";

    /**
     * 获取群组成员
     */
    public static String CONTACTS_URL = BASE_URL + "msg/10026";

    /**
     * 消息地址
     */
    public static String MSG_URL = BASE_URL + "msg/10017";//测试
}
