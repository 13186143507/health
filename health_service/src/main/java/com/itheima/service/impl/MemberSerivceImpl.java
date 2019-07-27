package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberSerivce;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 会员业务服务实现类
 */
@Service(interfaceClass = MemberSerivce.class)
@Transactional
public class MemberSerivceImpl implements MemberSerivce {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }


    /**
     *  指定时间内查询会员数量
     * 查询每月的会员数据量统计
     * @return
     */
    @Override
    public Map<String, Object> findMemberCount() {
        Map<String,Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        // 获取当前时间之前的前12月
        calendar.add(Calendar.MONTH,-12);
        // 从2018-8   到 2019-7 每个月份
        List<String> datelist = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1); // +1  2018-8
            try {
                datelist.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("months",datelist);

        // 使用月份查询每个月份（截止到月底）会员的数量
        List<Integer> countList = new ArrayList<>();
        for (String s : datelist) {
            String date = s+"-31"; // 2019-07-31
            Integer count = memberDao.findCountByDate(date);
            countList.add(count);
        }
        map.put("memberCount",countList);
        return map;
    }


    /**
     * 指定时间内查询会员数量
     * 查询一个时间段的会员变化量
     * @param start
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> findMemberCountBetweenDate(String start, String end) throws Exception {
        //如果有一个参数为空,则默认设置为今日
        if ("".equals(start)){
            start = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        if ("".equals(end)){
            end = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }

        //获取两个日期的毫秒值
        long startTime = DateUtils.parseString2Date(start).getTime();
        long endTime = DateUtils.parseString2Date(end).getTime();

        //判断日期是否符合规范
        if (endTime<=startTime){
            throw new RuntimeException("日期有误!结束日期不能在开始日期之后!");
        }

        HashMap<String, Object> map = new HashMap<>();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<Integer> countList = new ArrayList<>();

        //判断两个日期间是否相差超过31天
        //long oneMonthTime = 31*24*60*60*1000L;
        //long betweenTime = endTime-startTime;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parseString2Date(start));

        while (calendar.getTime().getTime() <= endTime){
            calendar.add(Calendar.DATE,1);
            dateList.add(DateUtils.parseDate2String(calendar.getTime()));
        }

        /*if (betweenTime>oneMonthTime) {
            //日期相差超过31天,按月分
        }else {
            //日期相差不超过31天,按天分
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.parseString2Date(start));

            while (calendar.getTime().getTime() <= endTime){
                calendar.add(Calendar.DATE,1);
                dateList.add(DateUtils.parseDate2String(calendar.getTime()));
            }
        }*/

        for (String date : dateList) {
            Integer countByDate = memberDao.findCountByDate(date);
            countList.add(countByDate);
        }

        map.put("months",dateList);
        map.put("memberCount",countList);

        return map;
    }










   /* *//**
     * 根据月份查询 会员数量
     * @param months
     * @return
     *//*
    @Override
    public List<Integer> findMemberCountByMonth(List<String> months) {
        //定义一个集合返回统计会员数量
        List<Integer> rsCount = new ArrayList<>();
        for (String month : months) {
            month = month + "-31";
            Integer count = memberDao.findMemberCountBeforeDate(month);
            rsCount.add(count);
        }
        return rsCount;
    }*/
}
