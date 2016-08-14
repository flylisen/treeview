package com.lisen.android.treeview.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lisen.android.treeview.R;
import com.lisen.android.treeview.adapter.SimpleTreeViewAdapter;
import com.lisen.android.treeview.bean.FileBean;
import com.lisen.android.treeview.bean.Node;
import com.lisen.android.treeview.util.BaseTreeViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mTree;
    private List<FileBean> mDatas = new ArrayList<>();
    private SimpleTreeViewAdapter<FileBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTree = (ListView) findViewById(R.id.lv_main_activity);
        initDatas();
        try {
            mAdapter = new SimpleTreeViewAdapter<FileBean>(mDatas, MainActivity.this,
                    mTree, 0);
            mTree.setAdapter(mAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mAdapter.setOnTreeNodeClickListener(new BaseTreeViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onTreeNodeClick(Node node, int position) {
                if (!node.isLeaf()) {
                    //不是叶子节点，不响应单击事件
                    return;
                }
                Toast.makeText(MainActivity.this, node.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        //设置长点击添加node
        mTree.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final EditText et = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("添加节点")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (TextUtils.isEmpty(et.getText().toString())) {
                                    return;
                                }
                                mAdapter.addNode(position, et.getText().toString());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return true;
            }
        });
    }

    private void initDatas() {

        FileBean bean = new FileBean(1, 0, "根目录1");
        mDatas.add(bean);
        bean = new FileBean(2, 0, "根目录2");
        mDatas.add(bean);
        bean = new FileBean(3, 0, "根目录3");
        mDatas.add(bean);
        bean = new FileBean(4, 1, "节点1-1");
        mDatas.add(bean);
        bean = new FileBean(5, 1, "节点1-2");
        mDatas.add(bean);
        bean = new FileBean(6, 1, "节点1-3");
        mDatas.add(bean);
        bean = new FileBean(7, 4, "节点1-1-1");
        mDatas.add(bean);
        bean = new FileBean(8, 2, "节点2-1");
        mDatas.add(bean);
        bean = new FileBean(9, 3, "节点3-1");
        mDatas.add(bean);
        bean = new FileBean(10, 0, "根目录4");
        mDatas.add(bean);
        bean = new FileBean(11, 10, "节点4-1");
        mDatas.add(bean);
        bean = new FileBean(12, 9, "节点3-1-1");
        mDatas.add(bean);

    }
}
