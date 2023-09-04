package com.jiawa.mytrain.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.jwt.JWTUtil;
import com.jiawa.mytrain.common.exception.BusinessException;
import com.jiawa.mytrain.common.exception.BusinessExceptionEnum;
import com.jiawa.mytrain.common.util.SnowUtil;
import com.jiawa.mytrain.member.domain.Member;
import com.jiawa.mytrain.member.domain.MemberExample;
import com.jiawa.mytrain.member.mapper.MemberMapper;
import com.jiawa.mytrain.member.req.MemberLoginReq;
import com.jiawa.mytrain.member.req.MemberRegisterReq;
import com.jiawa.mytrain.member.req.MemberSendCodeReq;
import com.jiawa.mytrain.member.resp.MemberLoginResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        Member memberDB = selectByMobile(mobile);

        // 如果 memberDB 不为空,说明有重复手机号
        if(ObjectUtil.isNotEmpty(memberDB)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        // 注册
        Member member = new Member();
        // 雪花算法
        member.setId(SnowUtil.getSnowflakeNextId());
        // 设置手机号
        member.setMobile(mobile);
        // 插入用户
        memberMapper.insert(member);

        return member.getId();
    }

    // 发送手机验证码
    public void sendCode(MemberSendCodeReq req){
        String mobile = req.getMobile();
        // 查询是否有重复电话号码
        Member memberDB = selectByMobile(mobile);

        // 如果 memberDB 为空,说明手机号不存在
        if(ObjectUtil.isNull(memberDB)){
            LOG.info("手机号不存在，插入一条记录");

            Member member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        }else {
            LOG.info("手机号存在，不插入记录");
        }

        // 生成验证码
//        String code = RandomUtil.randomString(4);
        String code = "8888";
        LOG.info("生成短信验证码：{}", code);

        // 保存短信记录表:
        // 包含手机号、短信验证码、有效期、是否已使用、业务类型、发送时间、使用时间
        LOG.info("保存短信验证码：{}", code);
        // 对接短信通道，发送短信
        LOG.info("对接短信通道");
    }

    // 发送手机验证码
    public MemberLoginResp login(MemberLoginReq req){
        String mobile = req.getMobile();
        String code = req.getCode();
        Member memberDB = selectByMobile(mobile);

        // 如果 memberDB 为空,说明手机号不存在
        if(ObjectUtil.isNull(memberDB)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }

        // 校验短信验证码
        if(!"8888".equals(code)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }

        // 将查询到的 member 赋值给 memberLoginResp
        MemberLoginResp memberLoginResp = BeanUtil.copyProperties(memberDB, MemberLoginResp.class);
        // JWT token
        Map<String, Object> map = BeanUtil.beanToMap(memberLoginResp);
        String key = "Jiawa12306";
        String token = JWTUtil.createToken(map, key.getBytes());
        memberLoginResp.setToken(token);

        return memberLoginResp;
    }

    private Member selectByMobile(String mobile) {
        // 查询是否有重复电话号码
        MemberExample memberExample = new MemberExample();
        // 创建条件
        memberExample.createCriteria().andMobileEqualTo(mobile);
        // 将数据库中与注册手机号相同的 member 对象放到一个 list 中
        List<Member> list = memberMapper.selectByExample(memberExample);

        // 如果 list 为空,说明手机号已经注册
        if(CollUtil.isEmpty(list)){
            return null;
        }else {
            return list.get(0);
        }
    }
}
