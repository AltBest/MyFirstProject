package com.feicui.contacts.android8_30.entity;

import android.provider.BaseColumns;

public class TypeEntry implements BaseColumns{
    //存放数据库的目录
    public static final String DATABASE_PATH = "/data/data/com.feicui.contacts.android8_30/databases";
    public static final String TABLE_NAME = "PhoneType";
    public static final String COLUMMNS_NAME_TYPE = "type";
    public static final String COLUMMNS_NAME_SUBTABLE = "subtable";
    //创建表格的SQL语句
    public static final String SQL_CREATE_TABLE =
            "create table " + TABLE_NAME + " (" +
                    _ID + " integer primary key," +
                    COLUMMNS_NAME_TYPE + " text," +
                    COLUMMNS_NAME_SUBTABLE + " text" + ")";
    //删除表格的SQL语句
    public static final String SQL_DELETE_TABLE =
            "drop table if exists " + TABLE_NAME;
    //子表表名
    public static final  String SUB_LOCAL = "Local";
    public static final  String SUB_CATERING = "Catering";
    public static final  String SUB_PUBLIC_SERVICE = "PublicServer";
    public static final  String SUB_OPERATOR = "Operator";
    public static final  String SUB_EXPRESSAGE = "Expressage";
    public static final  String SUB_TRAVEL = "Travel";
    public static final  String SUB_Bank = "Bank";
    public static final  String SUB_INSURANCE = "Insurance";
    public static final  String SUB_AFTER_SALE = "AfterSale";


}
