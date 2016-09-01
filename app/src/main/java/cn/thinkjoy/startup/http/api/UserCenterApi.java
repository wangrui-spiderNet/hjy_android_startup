package cn.thinkjoy.startup.http.api;

import android.content.Context;

import com.socks.okhttp.callback.OkCallback;

import java.util.HashMap;

import cn.thinkjoy.startup.base.MyApplication;
import cn.thinkjoy.startup.http.OkHttpManager;
import cn.thinkjoy.startup.http.RequestData;
import cn.thinkjoy.startup.util.ConstantSet;


/**
 * 用户相关接口
 * Created by zhouxiaohui on 16/7/1.
 */
public class UserCenterApi {


    private static UserCenterApi api;

    public static UserCenterApi getInstance() {

        if (api == null) {
            api = new UserCenterApi();
        }

        return api;
    }

    private UserCenterApi() {
    }

    /**
     * 登录
     *
     * @param okCallBack
     */
    public void loginMobile(Context mContext, String username, String password, OkCallback okCallBack) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("username", username);
        map.put("password", password);
        map.put(ConstantSet.SERVER_RESPONCE_CMD, CMDHelper.CMD_100101);
        RequestData.instance = null;
        map.putAll(RequestData.getInstance().getInitParams(mContext));
        OkHttpManager.getInstance().postAsync(mContext, URLHelpers.URL_SYSTEM + CMDHelper.CMD_100101, map, okCallBack);
    }

    /**
     * 升级
     */
    public void checkVersion(Context mContext, String appVersion, OkCallback okCallBack) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantSet.SERVER_RESPONCE_CMD, CMDHelper.CMD_100106);
        map.put("appVersion", appVersion);
        map.putAll(RequestData.getInstance().getInitParams(MyApplication.getAppContext()));
        OkHttpManager.getInstance().postAsync(mContext, URLHelpers.URL_SYSTEM + CMDHelper.CMD_100106, map, okCallBack);
    }

    /**
     * 相册
     */
    public void getAlbumList(Context mContext, int pulltype, long dt, int pagesize, OkCallback okCallBack) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantSet.SERVER_RESPONCE_CMD, CMDHelper.CMD_100104);
        map.put(ConstantSet.SERVER_PULL_REFRESH_TYPE, pulltype);
        map.put(ConstantSet.SERVER_RESPONCE_DT, dt);
        map.put(ConstantSet.SERVER_PAGE_SIZE, pagesize);
        map.putAll(RequestData.getInstance().getInitParams(mContext));
        OkHttpManager.getInstance().postAsync(mContext, URLHelpers.URL_SYSTEM + CMDHelper.CMD_100104, map, okCallBack);
    }

    /**
     * 取消收藏图片
     *
     * @param mContext
     * @param thumbId    图片id
     * @param type       1收藏，2取消收藏
     * @param okCallBack
     */
    public void collectOrCancelAlbum(Context mContext, int thumbId, String thumbUrl, int type, OkCallback okCallBack) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantSet.SERVER_RESPONCE_CMD, CMDHelper.CMD_100109);
        map.put("thumbId", thumbId);
        map.put("thumbUrl", thumbUrl);
        map.put("type", type);
        map.putAll(RequestData.getInstance().getInitParams(mContext));
        OkHttpManager.getInstance().postAsync(mContext, URLHelpers.URL_SYSTEM + CMDHelper.CMD_100109, map, okCallBack);
    }


    /**
     * 获取手机验证码
     *
     * @param mContext
     * @param accout
     * @param okCallBack
     */
    public void getAuthCode(Context mContext, String accout, OkCallback okCallBack) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantSet.SERVER_RESPONCE_CMD, CMDHelper.CMD_100102);
        map.put("phoneNumber", accout);
        map.put("type", 0);
        map.putAll(RequestData.getInstance().getInitParams(mContext));
        OkHttpManager.getInstance().postAsync(mContext, URLHelpers.URL_SYSTEM + CMDHelper.CMD_100102, map, okCallBack);
    }

    /**
     * 忘记密码
     *
     * @param mContext
     * @param account
     * @param okCallBack
     */
    public void doForgetPwd(Context mContext, String account, String authCode, String passwordNew, int type, OkCallback okCallBack) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantSet.SERVER_RESPONCE_CMD, CMDHelper.CMD_100103);
        map.put("account", account);
        map.put("authCode", authCode);
        map.put("passwordNew", passwordNew);
        map.put("type", 1);
        map.putAll(RequestData.getInstance().getInitParams(mContext));
        OkHttpManager.getInstance().postAsync(mContext, URLHelpers.URL_SYSTEM + CMDHelper.CMD_100103, map, okCallBack);
    }

    /**
     * @param accountid 修改密码必传
     * @param pwdnew    忘记密码必传，修改密码必传
     * @param pwdold    修改密码必传
     * @param type      1为找回密码，authCode不能为空；2为修改用户密码，此时oldPassword不能为空，authCode可以为空
     */
    public void modifyPwd(Context mContext, String accountid, String pwdnew, String pwdold,
                          String type, OkCallback okCallBack) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantSet.SERVER_RESPONCE_CMD, CMDHelper.CMD_100103);
        map.put("accountid", accountid);
        map.put("passwordNew", pwdnew);
        map.put("passwordOld", pwdold);
        map.put("type", type);
        map.putAll(RequestData.getInstance().getInitParams(mContext));
        OkHttpManager.getInstance().postAsync(mContext, URLHelpers.URL_SYSTEM + CMDHelper.CMD_100103, map, okCallBack);
    }

    /**
     * 修改头像
     *
     * @param mContext
     * @param accountid
     * @param original
     * @param thumb
     * @param okCallBack
     */
    public void modifyHeadImage(Context mContext, String accountid, String original, String thumb, OkCallback okCallBack) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantSet.SERVER_RESPONCE_CMD, CMDHelper.CMD_100105);
        map.put("accountId", accountid);
        map.put("original", original);
        map.put("thumb", thumb);
        map.putAll(RequestData.getInstance().getInitParams(mContext));
        OkHttpManager.getInstance().postAsync(mContext, URLHelpers.URL_SYSTEM + CMDHelper.CMD_100105, map, okCallBack);
    }


    /**
     * 100108教师所带班级列表
     *
     * @param mContext
     * @param okCallBack
     */
    public void getClass(Context mContext, OkCallback okCallBack) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantSet.SERVER_RESPONCE_CMD, CMDHelper.CMD_100108);
        map.putAll(RequestData.getInstance().getInitParams(mContext));
        OkHttpManager.getInstance().postAsync(mContext, URLHelpers.URL_SYSTEM + CMDHelper.CMD_100108, map, okCallBack);
    }

    /**
     * 100110教师所带科目列表
     *
     * @param mContext
     * @param okCallBack
     */
    public void getSubject(Context mContext, OkCallback okCallBack) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantSet.SERVER_RESPONCE_CMD, CMDHelper.CMD_100110);
        map.putAll(RequestData.getInstance().getInitParams(mContext));
        OkHttpManager.getInstance().postAsync(mContext, URLHelpers.URL_SYSTEM + CMDHelper.CMD_100110, map, okCallBack);
    }

    /**
     * 100111获取学生生日和关系
     *
     * @param mContext
     * @param okCallBack
     */
    public void getStudentBirAndRelation(Context mContext, String stuSequence, OkCallback okCallBack) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantSet.SERVER_RESPONCE_CMD, CMDHelper.CMD_100111);
        map.put("stuSequence", stuSequence);
        map.putAll(RequestData.getInstance().getInitParams(mContext));
        OkHttpManager.getInstance().postAsync(mContext, URLHelpers.URL_SYSTEM + CMDHelper.CMD_100111, map, okCallBack);
    }

    /**
     * 100112 修改生日或者父母姓名
     *
     * @param mContext
     * @param relationShip 关系(1父亲、2母亲、3哥哥、4弟弟、5姐姐、6妹妹、7爷爷、8奶奶、9其他，10监护人)
     * @param stuSequence
     * @param type         1:修改生日时间 2:修改家长姓名
     * @param updateDate
     * @param updateName
     * @param okCallBack
     */

    public void modifyBirAndParentName(Context mContext, int relationShip, String stuSequence,
                                       int type, long updateDate, String updateName, OkCallback okCallBack) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantSet.SERVER_RESPONCE_CMD, CMDHelper.CMD_100112);
        map.put("relationShip", relationShip);
        map.put("stuSequence", stuSequence);
        map.put("type", type);
        map.put("updateDate", updateDate);
        map.put("updateName", updateName);
        map.putAll(RequestData.getInstance().getInitParams(mContext));
        OkHttpManager.getInstance().postAsync(mContext, URLHelpers.URL_SYSTEM + CMDHelper.CMD_100112, map, okCallBack);
    }

    /**
     * 退出登录
     *
     * @param mContext
     * @param okCallBack
     */
    public void doLoginOut(Context mContext, OkCallback okCallBack) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantSet.SERVER_RESPONCE_CMD, CMDHelper.CMD_100107);
        map.putAll(RequestData.getInstance().getInitParams(mContext));
        OkHttpManager.getInstance().postAsync(mContext, URLHelpers.URL_SYSTEM + CMDHelper.CMD_100107, map, okCallBack);

    }


}
