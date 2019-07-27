package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Member;

import java.util.List;

public interface MemberDao {
    public List<Member> findAll();
    public Page<Member> selectByCondition(String queryString);
    public void add(Member member);
    public void deleteById(Integer id);
    public Member findById(Integer id);
    public Member findByTelephone(String telephone);
    public void edit(Member member);
    public Integer findMemberCountBeforeDate(String date);

    // 根据当前时间，统计当前时间注册的会员
    public Integer findMemberCountByDate(String date);
    // 根据当前时间，统计当前时间之后注册的会员数量
    public Integer findMemberCountAfterDate(String date);
    // 总会员数
    public Integer findMemberTotalCount();

    Integer findCountByDate(String date);
}
