package com.feicui.contacts.android8_30.entity;
/**
 * 电话类型实体类
 */

public class PhoneTypeEntity {

    //电话类型名称
    private String typeName;
    private  String subTable;

    public String getSubTable() {
        return subTable;
    }

    public void setSubTable(String subTable) {
        this.subTable = subTable;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
