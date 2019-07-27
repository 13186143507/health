package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会员服务接口层
 */
public interface MemberSerivce {
    /**
     * 根据手机号码查询会员信息
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 注册会员
     * @param member
     */
    void add(Member member);

/*    *//**
     * 根据月份查询 会员数量
     * @param
     * @return
     *//*
    List<Integer> findMemberCountByMonth(List<String> months);*/


    /**
     * 指定时间内查询会员数量
     * 没有选择时间  默认按过去一年时间查询
     * @return
     */
    Map<String,Object> findMemberCount();


    /**
     * 指定时间内查询会员数量
     * 选择指定时间  按天数查询会员数量
     * @param start   查询开始时间
     * @param end     查询结束时间
     * @return
     * @throws Exception
     */
    Map<String,Object> findMemberCountBetweenDate(String start, String end) throws Exception;
}
