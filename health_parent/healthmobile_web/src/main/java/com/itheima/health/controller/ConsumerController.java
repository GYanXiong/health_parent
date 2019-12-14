package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.itheima.health.entity.ConsumerEncryptData;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Consumer;
import com.itheima.health.service.ConsumerService;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Reference
    private ConsumerService consumerService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/getopenId")
    public Result getOpenId(@RequestParam Integer id, @RequestParam String randPass){
        Jedis resource = jedisPool.getResource();
        String openid = resource.hget(randPass, id+"");
        if(StringUtils.isEmpty(openid)){
            openid = consumerService.findOpenid(id, randPass);
        }
        if(StringUtils.isEmpty(openid)){
            return new Result(false,"failed");
        }
        resource.hset(randPass,id+"",openid);
        return new Result(true,"success",openid);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result getInfoDecoding(@RequestBody ConsumerEncryptData consumerEncryptData) {
        Security.addProvider(new BouncyCastleProvider());
        byte[] sessionKeyByte = Base64.decodeBase64(consumerEncryptData.getSession_key());
        byte[] ivByte = Base64.decodeBase64(consumerEncryptData.getIv());
        byte[] encryptDataByte = Base64.decodeBase64(consumerEncryptData.getEncryptedData());
        String info = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            Key key = new SecretKeySpec(sessionKeyByte, "AES");
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("AES");
            algorithmParameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, key, algorithmParameters);
            byte[] bytes = cipher.doFinal(encryptDataByte);
            info=new String(bytes);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(StringUtils.isEmpty(info)){
            return new Result(false, "json转换失败");
        }
        Map map = null;
        try {
            map = JSONObject.parseObject(info, Map.class);
            //是否已经注册？
            Consumer byOpenId = consumerService.findByOpenId((String) map.get("openId"));
            if (byOpenId != null) {
                return new Result(true, "已经注册过的用户", byOpenId);
            }
            //未注册 转化成consumer数据，写入数据库
            Consumer consumer = new Consumer((String) map.get("nickName"), (String) map.get("avatarUrl"), (Integer) map.get("gender"), (String) map.get("openId"), UUID.randomUUID() + "");
            consumerService.add(consumer);
            HashMap<String, Object> ret = new HashMap<String, Object>();
            ret.put("id", consumer.getId());
            ret.put("randPass", consumer.getRandPass());
            return new Result(true, "注册会员成功", ret);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"注册会员失败");
        }
    }
}
