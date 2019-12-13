package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import com.itheima.health.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/11/28 10:03
 * @Version V1.0
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberDao memberDao;


    @Override
    public Map getMemberReport_age() {
        // 存放年龄区间
        List<String> memberAge = new ArrayList<>();
        memberAge.add("0-10");
        memberAge.add("10-18");
        memberAge.add("18-30");
        memberAge.add("30-50");
        memberAge.add("50-80");
        memberAge.add("80之后");

        List<Date> date = memberDao.findMemberAge();


        long now = System.currentTimeMillis();
        int ycount = 0;
        int qcount = 0;
        int ccount = 0;
        int zcount = 0;
        int zlcount = 0;
        int lcount = 0;
        for (Date date1 : date) {
            long time = date1.getTime();
            long age = now - time;
            age = age / 1000 / 60 / 60 / 24 / 365;

            if (age >= 0 && age < 10) {
                ycount++;
            } else if (age >= 10 && age < 18) {
                qcount++;
            } else if (age >= 18 && age < 30) {
                ccount++;
            } else if (age >= 30 && age < 50) {
                zcount++;
            } else if (age >= 50 && age < 80) {
                zlcount++;
            } else {
                lcount++;
            }
        }
        List<Integer> countList = new ArrayList<>();
        countList.add(ycount);
        countList.add(qcount);
        countList.add(ccount);
        countList.add(zcount);
        countList.add(zlcount);
        countList.add(lcount);
        List<Map> memberCount =new ArrayList<>();
        for (int i = 0; i < memberAge.size(); i++) {
            Map map = new HashMap();
            map.put("name",memberAge.get(i));
            map.put("value",countList.get(i));
            memberCount.add(map);
        }

        Map memberMap = new HashMap();
        memberMap.put("memberAge", memberAge);
        memberMap.put("memberCount", memberCount);
        return memberMap;
    }

    @Override
    public Map getMemberReport_sex() {
        // 存放男女的名称
        List<String> memberSex = new ArrayList<>();
        memberSex.add("ladies");
        memberSex.add("gentlemen");
        // 存放性别的名称、对应性别的数量
        int ladiesCount = memberDao.findladiesCount();
        int gentlemenCount = memberDao.findgentlemenCount();
        // 构造Map集合
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "ladies");
        map1.put("value", ladiesCount);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "gentlemen");
        map2.put("value", gentlemenCount);
        List<Map> memberCount = new ArrayList<>();
        memberCount.add(map1);
        memberCount.add(map2);

        Map memberMap = new HashMap();
        memberMap.put("memberSex", memberSex);
        memberMap.put("memberCount", memberCount);

        return memberMap;
    }

    @Override
    public Member findMemberByTelephone(String telephone) {
        return memberDao.findMemberByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        // 如果密码不为空，使用md5进行加密
        if(member !=null && member.getPassword()!=null && !member.getPassword().equals("")){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByRegTime(List<String> months) {
        // 组织数据
        List<Integer> memberCounts = new ArrayList<>();
        // 遍历months
        if(months!=null && months.size()>0){
            // 格式：[2019-01, 2019-02, 2019-03, 2019-04, 2019-05, 2019-06, 2019-07, 2019-08, 2019-09, 2019-10, 2019-11, 2019-12]
            for (String month : months) {
                String sDate = month+"-31"; // 使用当前月，计算当前月的最后1天
                Integer count = memberDao.findMemberCountByRegTime(sDate);
                memberCounts.add(count);
            }
        }
        return memberCounts;
    }

    @Override
    public Integer findByCurrentMemberCount(Map countMap) {
        return memberDao.findByCurrentMemberCount(countMap);
    }
}
