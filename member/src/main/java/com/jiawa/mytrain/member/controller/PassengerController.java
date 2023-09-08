package com.jiawa.mytrain.member.controller;

import com.jiawa.mytrain.common.context.LoginMemberContext;
import com.jiawa.mytrain.common.resp.CommonResp;
import com.jiawa.mytrain.member.req.PassengerQueryReq;
import com.jiawa.mytrain.member.req.PassengerSaveReq;
import com.jiawa.mytrain.member.resp.PageResp;
import com.jiawa.mytrain.member.resp.PassengerQueryResp;
import com.jiawa.mytrain.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

// @CrossOrigin
@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Resource
    private PassengerService passengerService;

    // 增加和修改用 Post
    @PostMapping("/save")
    // @Valid 让 req 中的验证注解生效
    public CommonResp<Object> save(@Valid @RequestBody PassengerSaveReq req){
        passengerService.save(req);

        return new CommonResp<>();
    }

    // 查询用 Get
    @GetMapping("/query-list")
    // @Valid 让 req 中的验证注解生效
    public CommonResp<PageResp<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req){
        req.setMemberId(LoginMemberContext.getId());

        PageResp<PassengerQueryResp> passengerQueryRespList = passengerService.queryList(req);

        return new CommonResp<>(passengerQueryRespList);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        passengerService.delete(id);
        return new CommonResp<>();
    }
}
