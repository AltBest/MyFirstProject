package com.feicui.contacts.android8_30;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.feicui.contacts.android8_30.adapter.PhoneTypeAdapter;
import com.feicui.contacts.android8_30.entity.PhoneTypeEntity;
import com.feicui.contacts.android8_30.entity.TypeEntry;
import com.feicui.contacts.android8_30.sqlite.MyOpenHelder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * @author cpp  2016-09-02
 * @description 通讯大全主页
 */
public class MainActivity extends BaseActivity {
    //存放数据库的目录
    private final String DATABSE_PATH = "/data/data/com.feicui.contacts.android8_30/databases";
    //电话类型列表
    ListView lv_phoneType;
    //是否进入双击退出计时状态
    boolean is_exit = false;
    //第一次点击退出的时间戳
    long l_firstClickTime;
    //第二次点击退出的时间戳
    long l_secondClickTime;
    //loading界面
    LinearLayout ll_loding;
    //电话类型适配器
    PhoneTypeAdapter ptAdapter;
    //SQLite自定义帮助类
    MyOpenHelder mHelder;

    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mHelder = new MyOpenHelder(this);
        lv_phoneType = (ListView) findViewById(R.id.lv_phoneType);
        ll_loding = (LinearLayout) findViewById(R.id.ll_loading);
        //启动初始化异步任务
        new InitTask().execute();

    }
    @Override
    protected void setListener() {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //双击退出
        doubleColickExit(keyCode, event);
        return true;
    }

    /**
     * 初始化电话类型数据列表
     */
    private void initList() {
        //获取可读的database对象，通过打开固定路径的方式
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                DATABSE_PATH + "/phone.db", null
        );
        //查询数据库，返回一个游标
        Cursor cursor = db.query(TypeEntry.TABLE_NAME,//表名
                new String[]{TypeEntry.COLUMMNS_NAME_TYPE, TypeEntry.COLUMMNS_NAME_SUBTABLE},//COLUMN NAME　
                null,//WHERE
                null,//WHERE args
                null,//GROUP BY
                null,//HAVING
                null//ORDER
                );
        //将游标移动到第一行
        cursor.moveToFirst();
        //准备好实体数据组
        final ArrayList<PhoneTypeEntity> entities = new ArrayList<PhoneTypeEntity>();
        do {
            //将游标中的数据遍历取出
            String typeName = cursor.getString(
                    cursor.getColumnIndexOrThrow(TypeEntry.COLUMMNS_NAME_TYPE)
            );
            String subTable = cursor.getString(
                    cursor.getColumnIndexOrThrow(TypeEntry.COLUMMNS_NAME_SUBTABLE)
            );
            //装载进实体类中
            PhoneTypeEntity entity = new PhoneTypeEntity();
            entity.setTypeName(typeName);
            entity.setSubTable(subTable);

            //装载进实体类组中
            entities.add(entity);
        }while (cursor.moveToNext());
        //初始化适配器
        ptAdapter = new PhoneTypeAdapter(this, entities);
        //给列表设置监听
        lv_phoneType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //用来启动新界面的intent
                Intent intent;
                //判断从数据库中获取到的TypeName是什么字符
                switch (entities.get(position).getSubTable()){
                    //隐式启动拨号界面
                    case TypeEntry.SUB_LOCAL:
                    intent = new Intent(Intent.ACTION_DIAL);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_CATERING:
                        //将子表的表名放入到intent中传到下个Activity
                        intent = new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMMNS_NAME_SUBTABLE, TypeEntry.SUB_CATERING);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_PUBLIC_SERVICE:
                        //将子表的表名放入到intent中传到下个Activity
                        intent = new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMMNS_NAME_SUBTABLE, TypeEntry.SUB_PUBLIC_SERVICE);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_OPERATOR :
                        //将子表的表名放入到intent中传到下个Activity
                        intent = new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMMNS_NAME_SUBTABLE, TypeEntry.SUB_OPERATOR);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_EXPRESSAGE:
                        //将子表的表名放入到intent中传到下个Activity
                        intent = new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMMNS_NAME_SUBTABLE, TypeEntry.SUB_EXPRESSAGE);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_TRAVEL:
                        //将子表的表名放入到intent中传到下个Activity
                        intent = new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMMNS_NAME_SUBTABLE, TypeEntry.SUB_TRAVEL);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_Bank:
                        //将子表的表名放入到intent中传到下个Activity
                        intent = new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMMNS_NAME_SUBTABLE, TypeEntry.SUB_Bank);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_INSURANCE:
                        //将子表的表名放入到intent中传到下个Activity
                        intent = new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMMNS_NAME_SUBTABLE, TypeEntry.SUB_INSURANCE);
                        startActivity(intent);
                        break;
                    case TypeEntry.SUB_AFTER_SALE:
                        //将子表的表名放入到intent中传到下个Activity
                        intent = new Intent(MainActivity.this, PhoneNumberActivity.class);
                        intent.putExtra(TypeEntry.COLUMMNS_NAME_SUBTABLE, TypeEntry.SUB_AFTER_SALE);
                        startActivity(intent);
                        break;

                }
            }
        });
    }
    /**
     * 双击退出函数
     */
    private void doubleColickExit(int keyCode, KeyEvent event) {
        //当用户第一次点击返回钮时
        if (keyCode == KeyEvent.KEYCODE_BACK && is_exit == false) {
            is_exit = true;//设置记录标志为true
            l_firstClickTime = System.currentTimeMillis();//获得第一次点击的时间戳
            //显示再次点击退出提示
            Toast.makeText(this, getString(R.string.exit_toast), Toast.LENGTH_SHORT).show();
        }
        //用户第二次点击返回钮
        else if (keyCode == KeyEvent.KEYCODE_BACK && is_exit == true) {
            l_secondClickTime = System.currentTimeMillis();//记录下第二次点击退出的时间
            //时间差在两秒之内，退出程序
            if (l_secondClickTime - l_firstClickTime < 2000) {
                finish();
            } else {
                is_exit = false;//重置记录退出时间标志
                doubleColickExit(keyCode, event);//超出2000毫秒时，重新开始本函数逻辑
            }
        }

    }
    /**
     * @description 从raw文件夹中导入随APK发布的数据库
     */
    private void importDatabase(){
        try{
            //创建数据库目录，若数据库目录不存在，创建单层目录
            File dirFile = new File(DATABSE_PATH);
            if (!dirFile.exists()){
                dirFile.mkdir();
            }
            //创建将被导入的数据库File对象
            File file = new File(DATABSE_PATH, "phone.db");
            //判断文件是否存在，如不存在则创建该文件，存在就直接返回
            if (!file.exists()){
                file.createNewFile();
            }else{
                return;
            }
            //获得自带数据库的输入流
            InputStream ip = getResources().openRawResource(R.raw.phone);
            //创建将被导入的数据库输出流
            FileOutputStream fop = new FileOutputStream(file);
            //创建缓冲区
            byte[] buffer = new byte[1024];
            //将数据读入缓冲区，并写入输出流
            while (ip.read(buffer) != -1){
                //将缓冲区中的数据写入输出流
                fop.write(buffer);
                //重置缓冲区
                buffer = new byte[1024];
            }
            //关闭输入输出流
            ip.close();
            fop.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //异步初始化操作任务类
    class InitTask extends AsyncTask<Void, Void, Void>{
    //任务启动后在异步线程中执行的代码，不可操作UI
        @Override
        protected Void doInBackground(Void... params) {
            //转移数据库文件
            importDatabase();
            //装载ListView
            initList();
            return null;
        }
        //任务启动之前执行的代码
        @Override
        protected void onPreExecute(){
            //让loding界面显示
            ll_loding.setVisibility(View.VISIBLE);
        }
        //任务完成之后执行的代码，可操作UI
        @Override
        protected void onPostExecute(Void aVoid){
            //隐藏loding界面
            ll_loding.setVisibility(View.GONE);
            //给列表设置适配器
            lv_phoneType.setAdapter(ptAdapter);
        }
    }

}
