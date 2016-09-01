package cn.thinkjoy.startup.bean;

import cn.thinkjoy.startup.http.ResponseData;

/**
 * Created by xjliu on 16/7/5.
 */
public class SampleBean extends ResponseData {
    int resultState;
    String  items;//列表

    public int getResultState() {
        return resultState;
    }

    public void setResultState(int resultState) {
        this.resultState = resultState;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
