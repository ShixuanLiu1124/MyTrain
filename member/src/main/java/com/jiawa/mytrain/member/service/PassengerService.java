package com.jiawa.mytrain.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.jiawa.mytrain.common.util.SnowUtil;
import com.jiawa.mytrain.member.domain.Passenger;
import com.jiawa.mytrain.member.mapper.PassengerMapper;
import com.jiawa.mytrain.member.req.PassengerSaveReq;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;

    public void save(@Valid @RequestBody PassengerSaveReq req){
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }
}
