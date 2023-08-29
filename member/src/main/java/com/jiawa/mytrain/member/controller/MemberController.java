package com.jiawa.mytrain.member.controller;

import com.jiawa.mytrain.common.resp.CommonResp;
import com.jiawa.mytrain.member.req.MemberRegisterReq;
import com.jiawa.mytrain.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @GetMapping("/count")
    public CommonResp<Integer> count(){
        int count = memberService.count();
//        CommonResp<Integer> commonResp = new CommonResp<>();
//
//        commonResp.setContent(count);
//
//        return commonResp;

        return new CommonResp<>(count);
    }

    @PostMapping("/register")
    public CommonResp<Long> register(MemberRegisterReq req){
        long register = memberService.register(req);
//        CommonResp<Long> commonResp = new CommonResp<>();
//
//        commonResp.setContent(register);
//
//        return commonResp;

        return new CommonResp<>(register);
    }
}
