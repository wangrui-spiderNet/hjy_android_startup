package cn.thinkjoy.startup.util;

/**
 * 存放常量
 * Created by zhouxiaohui on 16/6/29.
 */
public class ConstantSet {

    /**
     * 请求成功
     */
    public static final int RESPONSE_SUCCESS = 1;
    /**
     * 请求失败
     */
    public static final int RESPONSE_FAILD = 0;

    //浙江幼教用户类型：1为教师；2为家长；3为学生；
    public static final int USER_TYPE_TEACHER = 1;
    public static final int USER_TYPE_PARENT = 2;
    public static final int USER_TYPE_STUDENT = 3;
    // 20为班群
    public static final int USER_TYPE_GROUPCLASS = 20;
    /**
     * 服务器返回的字段
     */
    public static final String SERVER_RESPONCE_CMD = "cmd";// 整形，操作指令（返回必带）
    public static final String SERVER_RESPONCE_STATE = "state";// 整形，状态码，参照状态码（返回必带）
    public static final String SERVER_RESPONCE_MSG = "msg";//字符串，状态消息，跟状态码匹配（返回必带）
    public static final String SERVER_RESPONCE_DT = "dt";//浮点型，服务器响应时间，从1970到当前时间的毫秒数（返回必带）
    public static final String SERVER_RESPONCE_CONTENT_MSG = "contentMsg";//字符串，需要客户段弹幕显示的内容
    public static final String SERVER_PULL_REFRESH_TYPE = "pullType";//字符串，需要客户段弹幕显示的内容
    public static final String SERVER_PAGE_SIZE = "size";//每页拉取的数量,默认10条
    public static final int XXT_CMD_STATE_SUCCESS = 1;//服务器返回状态成功
    public static final int XXT_CMD_STATE_ACCOUNT_OR_PASSWORD_ERR = 2;
    public static final int XXT_CMD_STATE_LOGIN_TOO_FREQUENTLY = 3;
    public static final int XXT_CMD_STATE_SESSION_ERR = 4;//账号互踢
    //大部分 拉取消息的方式，1为下拉更新，2为上拉获取，使用前请务必确认
    public static final int PULL_DOWN_REFRESH = 1;
    public static final int PULL_UP_MORE = 2;
    public static final int PAGE_SIZE = 10;
    public static final int SEND_DYNAMIC = 100;//发布动态


    //---------------------页面传递数据,key值---------------//

    public static final String IMAGE_URLS = "imageUrls";//图片资源路径  ArrayList<image>
    public static final String IMAGE_URLS_STRING = "imageUrls_string";//图片资源路径  ArrayList<string>
    public static final String IMAGE_INDEX = "imageIndex";//图片索引
    public static final String IMAGE_URL = "imageUrl";//
    public static final String STUDENT_BEAN = "studentBean";//学生对象
    public static final String STUDENT_ID = "studentId";//学生id
    public static final String HOMELAND_ID = "homeland_id";//家园圈id

    /**
     * 拍照,从相册获取图片
     */
    public static final int TAKE_PHOTO_CODE = 100;
    public static final int PICK_PHOTO_CODE = 200;
    public static final int SETTING = 300;
    public static final int MAX_COUNT_IMAGES = 9;//最多9张图片
    public static final String ALBUM_SELECTED_PICTURE = "albumSelectedPicture";
    public static final String ALBUM_SELECTED_COUNT = "albumSelectedCount";
    public static final String PICTURE_MAX_COUNT = "pictureMaxCount";
    public static final String FROMPAGE = "fromPage";//从哪个页面跳转过来
    public static final String SELECTED_CLASS = "selected_class";//选择的班级
    public static final String SELECTED_CLASSID = "selected_classId";//选择的班级ID
    public static final String SELECTED_CLASS_COUNT = "selected_class_count";//选择的班级个数
    public static final int EDIT_PHOTO = 1;//1可编辑图片数量,默认没有"删除"按钮;0不显示"删除"
    public static final String IS_CAN_EDIT_PHOTO = "iscan_edit_photo";//能否编辑图片
    /**
     * 通知 1:已收通知 2:已发通知
     */
    public static final int NOTICE_RECEIVE = 1;
    public static final int NOTICE_SEND = 2;
    public static final int NOTICE_READ = 1;
    public static final int NOTICE_UNREAD = 0;
    public static final int NOTICE_HASIMAGES = 1;
    public static final int NOTICE_NOIMAGES = 0;

    public static final String NOTICE_BEAN = "noticeBean";//noticeBean对象
    public static final String NOTICE_TYPE = "noticeType";//noticeType
    public static final String NOTICE_ID = "noticeId";//noticeType
    public static final String RECEIVERS_COUNT = "receivers_count";//receivers_count
    public static final String SELECTED_PERSONAL_RECEIVERS = "selected_personal_receivers";//接收通知的人_个人
    public static final String SELECTED_GROUP_RECEIVERS = "selected_group_receivers";//接收通知的人_群组
    /**
     * 设置
     */
    public static final int IM = 1;
    public static final int AUDIO = 2;
    public static final int IMAGES = 3;
    public static final int DOCUMENT = 4;


    /*
    登录相关
     */
    public static final String APPVERSION = "appversion";//版本号
    public static final String ACCOUNT = "account";//登录手机号
    public static final String PASSWORD = "password";//登录密码
    public static final String ISLOGIN = "islogin";//是否第一次登录

    /*
    * 修改密码和找回密码*/
    public static final String PWD_MODIFY = "2";//修改密码
    public static final String PWD_FINDBACK = "1";//找回密码


    /*更新头像通知*/
    public static final String DO_LOGOUT_ACTION = "DO_LOGOUT_ACTION";
    public static final String UPDATE_HEAD_URL = "UPDATE_HEAD_URL";
    public static final String NEW_HEAD_IMAGE_THUMB = "NEW_HEAD_IMAGE_THUMB";
    public static final String NEW_HEAD_IMAGE_ORIGINAL = "NEW_HEAD_IMAGE_ORIGINAL";
    public static final String REFRESH_HOME_ITEM = "REFRESH_HOME_ITEM";//刷新家园圈
    /**
     * 通讯录
     */
    public static final String REFRESH_CONTACT_DAY = "refresh_contact_day";
    public static final String IS_REFRESH_CONTACT = "is_refresh_contact";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final int  CHANGE_ROLE = 400;
    public static final int  FINAL_1 = 1;
    public static final int  FINAL_2 = 2;
    public static final int  FINAL_3 = 3;



}
