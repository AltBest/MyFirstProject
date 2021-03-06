package com.feicui.contacts.android8_30;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.feicui.contacts.android8_30.adapter.PhoneNumberAdapter;
import com.feicui.contacts.android8_30.entity.NumberEntity;
import com.feicui.contacts.android8_30.entity.PhoneNumberEnerty;
import com.feicui.contacts.android8_30.entity.TypeEntry;

import java.util.ArrayList;

/**
 * @author cpp 2016-09-01
 * @description 展示电话号码列表的页面
 */
public class PhoneNumberActivity extends BaseActivity {
    //显示电话号码的列表
    ListView lv_phoneNumber;
    //电话号码列表的适配器
    PhoneNumberAdapter pnAdapter;
    //loading界面
    LinearLayout ll_loading;

    @Override
    protected int setContent() {
        return R.layout.activity_phone_number;
    }

    @Override
    protected void initView() {
    lv_phoneNumber = (ListView) findViewById(R.id.lv_phoneNumber);
    ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
        //开始装载界面资源
        new InitTask().execute();
    }

    @Override
    protected void setListener() {

    }
    //异步初始化操作任务类
    class InitTask extends AsyncTask<Void, Void, Void>{
    //任务启动后在异步线程中执行的代码，不可操作UI
        @Override
        protected Void doInBackground(Void... params) {
        //装载ListView
            initList();
            return null;
        }
        //任务启动之前执行的代码，可操作UI
        @Override
        protected void onPreExecute(){
            //让loading界面显示
            ll_loading.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(Void aVoid){
            //隐藏loading界面
            ll_loading.setVisibility(View.GONE);
            //给列表设置适配器
            lv_phoneNumber.setAdapter(pnAdapter);
        }
    }
    /**
     * @description 装载ListView
     */
    private void initList(){
        //获取可读的database对象，通过打开固定路径的方式
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                TypeEntry.DATABASE_PATH + "/phone.db", null
        );
        //获得当前应加载的数据库表名
        String subTable =
                getIntent().getStringExtra(TypeEntry.COLUMMNS_NAME_SUBTABLE);
        //查询数据库，返回一个游标
        Cursor cursor = db.query(subTable,//表名
                new String[] {"*"},
                null,
                null,
                null,
                null,
                null
                );
        //将游标移动到第一行
        cursor.moveToFirst();
        //准备好实体数据组
        final ArrayList<PhoneNumberEnerty> enerties = new ArrayList<PhoneNumberEnerty>();
        do{
            //将游标中的数据遍历出
            String phoneName = cursor.getString(
                    cursor.getColumnIndexOrThrow(NumberEntity.COLUMNS_NAME_NAME)
            );
            String phoneNumber = cursor.getString(
                cursor.getColumnIndexOrThrow(NumberEntity.COLUMNS_NAME_NUMBER)
            );
            //装载进实体类中
            PhoneNumberEnerty enerty = new PhoneNumberEnerty();
            enerty.setPhoneName(phoneName);
            enerty.setPhoneNumber(phoneNumber);
            enerties.add(enerty);
        }while (cursor.moveToNext());
        //初始化适配器
        pnAdapter = new PhoneNumberAdapter(this,enerties);
        //给列表设置点击监听
        lv_phoneNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //创建询问是否拨打电话的对话框
                new AlertDialog.Builder(PhoneNumberActivity.this)
                        .setTitle("警告")
                        .setMessage(
                                "是否开始拨打"
                                +enerties.get(position).getPhoneName()
                                + "电话"
                                + "\n"
                                + "\n"
                                + "TEL:" + enerties.get(position).getPhoneNumber()
                        ).setPositiveButton(
                        "拨号",
                        //点击拨号后的监听
                        new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(
                                        "android.intent.action.CALL",
                                        Uri.parse("tel:" + enerties.get(position).getPhoneNumber())
                                );
                                startActivity(intent);
                            }
                        }
                )//确认按钮
                .setNegativeButton("取消", null)//取消按钮
                .show();//显示警告框
            }
        });
    }
}
