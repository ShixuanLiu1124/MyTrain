package com.jiawa.mytrain.member.service;

import cn.hutool.core.collection.CollUtil;
import com.jiawa.mytrain.common.exception.BusinessException;
import com.jiawa.mytrain.common.exception.BusinessExceptionEnum;
import com.jiawa.mytrain.common.util.SnowUtil;
import com.jiawa.mytrain.member.domain.Member;
import com.jiawa.mytrain.member.domain.MemberExample;
import com.jiawa.mytrain.member.mapper.MemberMapper;
import com.jiawa.mytrain.member.req.MemberRegisterReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

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
}
