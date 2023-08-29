package com.jiawa.mytrain.common.exception;

// 业务异常枚举类
public enum BusinessExceptionEnum {
    // 相当于构造函数
    MEMBER_MOBILE_EXIST("手机号已注册");

    private String desc;

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BusinessExceptionEnum{" +
                "desc='" + desc + '\'' +
                '}';
    }
}