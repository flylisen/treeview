package com.lisen.android.treeview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lisen.android.treeview.R;
import com.lisen.android.treeview.bean.Node;
import com.lisen.android.treeview.util.BaseTreeViewAdapter;
import com.lisen.android.treeview.util.TreeHelper;

import java.util.List;

/**
 * Created by Administrator on 2016/8/14.
 */
public class SimpleTreeViewAdapter<T> extends BaseTreeViewAdapter<T> {


    public SimpleTreeViewAdapter(List<T> datas, Context context, ListView tree, int defaultExpandLevel) throws IllegalAccessException {
        super(datas, context, tree, defaultExpandLevel);

    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon_list_item);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name_list_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (node.getIcon() == -1) {

            holder.icon.setVisibility(View.INVISIBLE);
        } else {
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setImageResource(node.getIcon());
        }
        holder.name.setText(node.getName());
        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView name;
    }

    /**
     * 动态添加节点
     * @param name  要添加的节点的名称
     * @param position  被添加的位置
     */
    public void addNode( int position, String name) {
        Node node = mVisibleNodeList.get(position);
        //关键是找出原本的位置
        int indexOf = mAllNodeList.indexOf(node);
        Node newNode = new Node(-1, node.getId(), name);
        newNode.setParent(node);
        node.getChildrens().add(newNode);
        mAllNodeList.add(indexOf + 1, newNode);
        mVisibleNodeList = TreeHelper.filterVisibleNode(mAllNodeList);
        notifyDataSetChanged();
    }

}
