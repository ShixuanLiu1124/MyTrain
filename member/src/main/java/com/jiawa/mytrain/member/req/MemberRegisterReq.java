package com.jiawa.mytrain.member.req;

import jakarta.validation.constraints.NotBlank;

public class MemberRegisterReq {
    private String mobile;

    @NotBlank(message = "手机号不能为空")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "MemberRegisterReq{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
