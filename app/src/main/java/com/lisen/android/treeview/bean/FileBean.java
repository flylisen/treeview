package com.lisen.android.treeview.bean;

import com.lisen.android.treeview.annotation.TreeNodeId;
import com.lisen.android.treeview.annotation.TreeNodeName;
import com.lisen.android.treeview.annotation.TreeNodePid;

/**
 * Created by Administrator on 2016/8/13.
 */
public class FileBean {


    public FileBean() {}

    public FileBean(int id, int pId, String label) {
        this.id = id;
        this.pId = pId;
        this.label = label;
    }

    /**
     * 本身id
     */
    @TreeNodeId
    private int id;

    /**
     * 父id
     */
    @TreeNodePid
    private int pId;

    /**
     * 图标
     */
    @TreeNodeName
    private String label;

    /**
     * 描述
     */
    private String desc;

    public int getId() {
        return id;
    }

    public int getpId() {
        return pId;
    }

    public String getLabel() {
        return label;
    }

    public String getDesc() {
        return desc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
