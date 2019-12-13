package com.itheima.health.dao;

import com.itheima.health.pojo.Member;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberDao {

    Member findMemberByTelephone(String telephone);

    void add(Member member);

    Integer findMemberCountByRegTime(String sDate);

    Integer findTodayNewMember(String reportDate);

    Integer findTotalMember();

    Integer findThisNewMember(String date);

    Integer findByCurrentMemberCount(Map countMap);

    int findladiesCount();

    int findgentlemenCount();

    List<Date> findMemberAge();
}
