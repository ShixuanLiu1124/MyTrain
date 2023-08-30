package com.jiawa.mytrain.member.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.jiawa.mytrain.common.exception.BusinessException;
import com.jiawa.mytrain.common.exception.BusinessExceptionEnum;
import com.jiawa.mytrain.common.util.SnowUtil;
import com.jiawa.mytrain.member.domain.Member;
import com.jiawa.mytrain.member.domain.MemberExample;
import com.jiawa.mytrain.member.mapper.MemberMapper;
import com.jiawa.mytrain.member.req.MemberRegisterReq;
import com.jiawa.mytrain.member.req.MemberSendCodeReq;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);

    @Resource
    private MemberMapper memberMapper;

    public int count(){
        return Math.toIntExact(memberMapper.countByExample(null));
    }

    public long register(MemberRegisterReq req){
        String mobile = req.getMobile();
        // 查询是否有重复电话号码
        MemberExample memberExample = new MemberExample();
        // 创建条件
        memberExample.createCriteria().andMobileEqualTo(mobile);
        // 将数据库中与注册手机号相同的 member 对象放到一个 list 中
        List<Member> list = memberMapper.selectByExample(memberExample);

        // 如果 list 不为空,说明有重复手机号
        if(CollUtil.isNotEmpty(list)){
//            return list.get(0).getId();
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        // 注册
        Member member = new Member();
        // 雪花算法
        member.setId(SnowUtil.getSnowflakeNextId());
        // 设置手机号
        member.setMobile(mobile);

        memberMapper.insert(member);

        return member.getId();
    }

    // 发送手机验证码
    public void sendCode(MemberSendCodeReq req){
        String mobile = req.getMobile();
        // 查询是否有重复电话号码
        MemberExample memberExample = new MemberExample();
        // 创建条件
        memberExample.createCriteria().andMobileEqualTo(mobile);
        // 将数据库中与注册手机号相同的 member 对象放到一个 list 中
        List<Member> list = memberMapper.selectByExample(memberExample);

        // 如果 list 不为空,说明手机号已经注册
        if(CollUtil.isNotEmpty(list)){
            LOG.info("手机号不存在，插入一条记录");

            Member member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        }else {
            LOG.info("手机号存在，不插入记录");
        }

        // 生成验证码
        String code = RandomUtil.randomString(4);
        LOG.info("生成短信验证码：{}", code);

        // 保存短信记录表:
        // 包含手机号、短信验证码、有效期、是否已使用、业务类型、发送时间、使用时间
        LOG.info("保存短信验证码：{}", code);
        // 对接短信通道，发送短信
        LOG.info("对接短信通道");
    }
}
