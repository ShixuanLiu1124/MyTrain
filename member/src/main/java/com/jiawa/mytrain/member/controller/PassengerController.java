package com.jiawa.mytrain.member.controller;

import com.jiawa.mytrain.common.resp.CommonResp;
import com.jiawa.mytrain.member.req.PassengerSaveReq;
import com.jiawa.mytrain.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Resource
    private PassengerService passengerService;

    @PostMapping("/save")
    // @Valid 让 req 中的验证注解生效
    public CommonResp<Object> save(@Valid @RequestBody PassengerSaveReq req){
        passengerService.save(req);

        return new CommonResp<>();
    }
}
