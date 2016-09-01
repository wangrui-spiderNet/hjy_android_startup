package cn.thinkjoy.startup.bean;

import java.io.Serializable;

/**
 * Created by xjliu on 16/7/3.
 */
public class Role implements Serializable {

    /**
     * items : [{"accountId":87452,"avatarThumb":"测试内容4344","canDelete":43256,"classId":85654,"className":"测试内容kf84","ctdEnable":80415,"isComplete":41116,"level":21680,"phone":"测试内容1m01","relation":"测试内容5r1k","schoolId":65413,"schoolName":"测试内容2iro","stuName":"测试内容44sm","stuNumber":"测试内容v7g3","studentId":18708,"userId":27115,"userName":"测试内容w456","userType":55412}]
     */
    private int accountId;
    private String avatarThumb;
    private int canDelete;
    private int classId;
    private String className;
    private int ctdEnable;
    private int isComplete;
    private int level;
    private String phone;
    private String relation;//学生关系（家长）	number	关系(1父亲、2母亲、3哥哥、4弟弟、5姐姐、6妹妹、7爷爷、8奶奶、9其他
    private int schoolId;
    private String schoolName;
    private String stuName;
    private String stuNumber;
    private long studentId;
    private int userId;
    private String userName;
    private int userType;
    private int isDefault;
    private String areaAbb;
    private String stuSequence;
    private long inSchoolDate;

    public long getInSchoolDate() {
        return inSchoolDate;
    }

    public void setInSchoolDate(long inSchoolDate) {
        this.inSchoolDate = inSchoolDate;
    }

    public String getStuSequence() {
        return stuSequence;
    }

    public void setStuSequence(String stuSequence) {
        this.stuSequence = stuSequence;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAvatarThumb() {
        return avatarThumb;
    }

    public void setAvatarThumb(String avatarThumb) {
        this.avatarThumb = avatarThumb;
    }

    public int getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(int canDelete) {
        this.canDelete = canDelete;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCtdEnable() {
        return ctdEnable;
    }

    public void setCtdEnable(int ctdEnable) {
        this.ctdEnable = ctdEnable;
    }

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuNumber() {
        return stuNumber;
    }

    public void setStuNumber(String stuNumber) {
        this.stuNumber = stuNumber;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getAreaAbb() {
        return areaAbb;
    }

    public void setAreaAbb(String areaAbb) {
        this.areaAbb = areaAbb;
    }
}
