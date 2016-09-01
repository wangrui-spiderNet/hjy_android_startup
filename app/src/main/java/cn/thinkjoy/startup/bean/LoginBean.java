package cn.thinkjoy.startup.bean;

import java.io.Serializable;
import java.util.List;

import cn.thinkjoy.startup.http.ResponseData;


/**
 * Created by xjliu on 16/7/1.
 */
public class LoginBean extends ResponseData implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    /**
     * downUrl : 测试内容206d
     * hasNew : 63411
     * newVersion : 测试内容j58x
     * session : 测试内容1n6z
     * updateMsg : 测试内容xjn4
     */

    private String downUrl;
    private int hasNew;
    private String newVersion;
    private String session;
    private String updateMsg;
    /**
     * accountId : 87452
     * avatarThumb : 测试内容4344
     * canDelete : 43256
     * classId : 85654
     * className : 测试内容kf84
     * ctdEnable : 80415
     * isComplete : 41116
     * level : 21680
     * phone : 测试内容1m01
     * relation : 测试内容5r1k
     * schoolId : 65413
     * schoolName : 测试内容2iro
     * stuName : 测试内容44sm
     * stuNumber : 测试内容v7g3
     * studentId : 18708
     * userId : 27115
     * userName : 测试内容w456
     * userType : 55412
     */

    private List<Role> items;

    public LoginBean() {
    }




    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public int getHasNew() {
        return hasNew;
    }

    public void setHasNew(int hasNew) {
        this.hasNew = hasNew;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getUpdateMsg() {
        return updateMsg;
    }

    public void setUpdateMsg(String updateMsg) {
        this.updateMsg = updateMsg;
    }

    public List<Role> getItems() {
        return items;
    }

    public void setItems(List<Role> items) {
        this.items = items;
    }

}
