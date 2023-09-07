package com.jiawa.mytrain.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.jiawa.mytrain.common.context.LoginMemberContext;
import com.jiawa.mytrain.common.util.SnowUtil;
import com.jiawa.mytrain.member.domain.Passenger;
import com.jiawa.mytrain.member.domain.PassengerExample;
import com.jiawa.mytrain.member.mapper.PassengerMapper;
import com.jiawa.mytrain.member.req.PassengerQueryReq;
import com.jiawa.mytrain.member.req.PassengerSaveReq;
import com.jiawa.mytrain.member.resp.PassengerQueryResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;

    private static final Logger LOG = LoggerFactory.getLogger(PassengerService.class);

    public void save(@Valid @RequestBody PassengerSaveReq req) {
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setMemberId(LoginMemberContext.getId());
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }

    public List<PassengerQueryResp> queryList(PassengerQueryReq req) {

        LOG.info("req.getsize() = " + req.getSize());

        PassengerExample passengerExample = new PassengerExample();
        PassengerExample.Criteria criteria = passengerExample.createCriteria();

        if(ObjectUtil.isNotNull(req.getMemberId())){
            criteria.andMemberIdEqualTo(req.getMemberId());
        }

        // 开始分页
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Passenger> passengerList = passengerMapper.selectByExample(passengerExample);

        return BeanUtil.copyToList(passengerList, PassengerQueryResp.class);
    }
}
