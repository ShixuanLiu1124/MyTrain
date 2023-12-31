package com.jiawa.mytrain.member.controller;

import com.jiawa.mytrain.common.resp.CommonResp;
import com.jiawa.mytrain.member.req.MemberLoginReq;
import com.jiawa.mytrain.member.req.MemberRegisterReq;
import com.jiawa.mytrain.member.req.MemberSendCodeReq;
import com.jiawa.mytrain.member.resp.MemberLoginResp;
import com.jiawa.mytrain.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
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
    // @Valid 让 req 中的验证注解生效
    public CommonResp<Long> register(@Valid @RequestBody MemberRegisterReq req){
        long register = memberService.register(req);
//        CommonResp<Long> commonResp = new CommonResp<>();
//
//        commonResp.setContent(register);
//
//        return commonResp;

        return new CommonResp<>(register);
    }

    @PostMapping("/send-code")
    // @Valid 让 req 中的验证注解生效
    public CommonResp<Long> sendCode(@Valid @RequestBody MemberSendCodeReq req){

        memberService.sendCode(req);

        return new CommonResp<>();
    }

    @PostMapping("/login")
    // @Valid 让 req 中的验证注解生效
    public CommonResp<MemberLoginResp> login(@Valid @RequestBody MemberLoginReq req){

        MemberLoginResp resp = memberService.login(req);

        return new CommonResp<>(resp);
    }
}
