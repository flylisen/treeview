package com.lisen.android.treeview.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lisen.android.treeview.bean.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/14.
 */
public abstract class BaseTreeViewAdapter<T> extends BaseAdapter {

    protected List<Node> mAllNodeList;
    protected List<Node> mVisibleNodeList;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected ListView mTreeView;

    /**
     * 设置点击回调接口
     */
    public interface OnTreeNodeClickListener {
        void onTreeNodeClick(Node node, int position);
    }

    private OnTreeNodeClickListener mListener;

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener l) {
        mListener = l;
    }
    public BaseTreeViewAdapter(List<T> datas, Context context, ListView tree, int defaultExpandLevel) throws IllegalAccessException {
        mAllNodeList = TreeHelper.sortedNode(datas, defaultExpandLevel);
        mVisibleNodeList = TreeHelper.filterVisibleNode(mAllNodeList);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mTreeView = tree;
        if (mTreeView != null) {
            mTreeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    expandOrCollapse(position);
                    if (mListener != null) {
                        mListener.onTreeNodeClick(mVisibleNodeList.get(position), position);
                    }
                }
            });
        }
    }

    /**
     * 点击收缩或展开
     * @param position
     */
    private void expandOrCollapse(int position) {
        Node node = mVisibleNodeList.get(position);
        if (node != null) {
            //叶子节点，返回
            if (node.isLeaf()) {
                return;
            }
            //根据当前状态决定是展开还是收缩
            node.setExpand(!node.isExpand());
            //集合中的node的可见性发生变化，重新过滤得到可见node的集合
            mVisibleNodeList = TreeHelper.filterVisibleNode(mAllNodeList);
            //通知adapter数据发生改变
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mVisibleNodeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mVisibleNodeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Node node = mVisibleNodeList.get(position);
        convertView = getConvertView(node, position, convertView, parent);
        //设置左边距，视觉上形成层级关系
        convertView.setPadding(node.getLevel() * 30, 3, 3, 3);
        return convertView;
    }

    public abstract View getConvertView(Node node, int position, View convertView, ViewGroup parent);
}
