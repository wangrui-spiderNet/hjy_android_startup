package cn.thinkjoy.startup.http;

/**
 * 响应参数基类,必须继承此类
 * Created by xjliu on 16/7/1.
 */
public class ResponseData {

    /**
     * cmd : 52431
     * msg : 测试内容7fyf
     * state : 37628
     */

    private long cmd;
    private String msg;
    /**
     * 仅在聊天有用，发送完消息后服务端给返回的消息Id
     */
    private String msgId;
    private int state;

    private int resultState;

    public int getResultState() {
        return resultState;
    }

    public void setResultState(int resultState) {
        this.resultState = resultState;
    }

    public long getCmd() {
        return cmd;
    }

    public void setCmd(long cmd) {
        this.cmd = cmd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

}
