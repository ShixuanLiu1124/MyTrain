package com.jiawa.mytrain.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiawa.mytrain.common.context.LoginMemberContext;
import com.jiawa.mytrain.common.util.SnowUtil;
import com.jiawa.mytrain.member.domain.Passenger;
import com.jiawa.mytrain.member.domain.PassengerExample;
import com.jiawa.mytrain.member.mapper.PassengerMapper;
import com.jiawa.mytrain.member.req.PassengerQueryReq;
import com.jiawa.mytrain.member.req.PassengerSaveReq;
import com.jiawa.mytrain.member.resp.PageResp;
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

        // 根据 id 是否为空判断是编辑还是新增
        if(ObjectUtil.isNull(passenger.getId())){
            passenger.setMemberId(LoginMemberContext.getId());
            passenger.setId(SnowUtil.getSnowflakeNextId());
            passenger.setCreateTime(now);
            passenger.setUpdateTime(now);
            passengerMapper.insert(passenger);
        }else{
            // 编辑成员
            passenger.setUpdateTime(now);
            passengerMapper.updateByPrimaryKey(passenger);
        }
    }

    public PageResp<PassengerQueryResp> queryList(PassengerQueryReq req) {
        PassengerExample passengerExample = new PassengerExample();
        // 根据 id 倒序
        passengerExample.setOrderByClause("id desc");
        PassengerExample.Criteria criteria = passengerExample.createCriteria();

        if(ObjectUtil.isNotNull(req.getMemberId())){
            criteria.andMemberIdEqualTo(req.getMemberId());
        }

        // 开始分页
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());

        List<Passenger> passengerList = passengerMapper.selectByExample(passengerExample);

        PageInfo<Passenger> pageInfo = new PageInfo<>(passengerList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<PassengerQueryResp> list = BeanUtil.copyToList(passengerList, PassengerQueryResp.class);
        PageResp<PassengerQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    public void delete(Long id) {
        passengerMapper.deleteByPrimaryKey(id);
    }
}
