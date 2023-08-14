package com.jiawa.mytrain.member.service;

import cn.hutool.core.collection.CollUtil;
import com.jiawa.mytrain.member.domain.Member;
import com.jiawa.mytrain.member.domain.MemberExample;
import com.jiawa.mytrain.member.mapper.MemberMapper;
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

    public long register(String mobile){
        // 查询是否有重复电话号码
        MemberExample memberExample = new MemberExample();
        // 创建条件
        memberExample.createCriteria().andMobileEqualTo(mobile);
        // 将数据库中与注册手机号相同的 member 对象放到一个 list 中
        List<Member> list = memberMapper.selectByExample(memberExample);

        // 如果 list 不为空,说明有重复手机号
        if(CollUtil.isNotEmpty(list)){
//            return list.get(0).getId();
            throw new RuntimeException("手机号已注册");
        }

        // 注册
        Member member = new Member();
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);

        memberMapper.insert(member);

        return member.getId();
    }
}
