package com.shop.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * * 订单编码生成器，生成32位数字编码，
 * * @生成规则 1位单号类型+17位时间戳+14位(用户id加密&随机数)
 */
public class IdUtils {
    

    private static final String USER_CODE = "RY";


    /**
     * 订单类别头
     */

    private static final String ORDER_CODE = "OR";
    /**
     * 退货类别头
     */

    private static final String RETURN_ORDER = "TH";
    /**
     * 退款类别头
     */

    private static final String REFUND_ORDER = "TK";


    /**
     * 随即编码
     */

    private static final int[] r = new int[]{7, 9, 6, 2, 8, 1, 3, 0, 5, 4};
    /**
     * 用户id和随机数总长度
     */

    private static final int maxLength = 14;

    /**
     * 根据id进行加密+加随机数组成固定长度编码
     */
    private static String toCode(Integer userId) {
        String idStr = userId.toString();
        StringBuilder idsbs = new StringBuilder();
        for (int i = idStr.length() - 1; i >= 0; i--) {
            idsbs.append(r[idStr.charAt(i) - '0']);
        }
        return idsbs.append(getRandom(maxLength - idStr.length())).toString();
    }

    /**
     * 生成时间戳
     */
    public static String getDateTime() {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }



    /*
    *
    *
    * */

    public static String pad(int length,long num){
        return String.format("%0".concat(String.valueOf(length)).concat("d"), num);
    }
/*
*
* 生成时间戳---到日
* */
    public static String DayCode() {
        DateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.format(new Date());
    }

    /**
     * 生成固定长度随机码
     *
     * @param n 长度
     */

    private static long getRandom(long n) {
        long min = 1, max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        long rangeLong = (((long) (new Random().nextDouble() * (max - min)))) + min;
        return rangeLong;
    }


    /**
     * 生成不带类别标头的编码
     *
     * @param userId
     */
    private static synchronized String getCode(Integer userId) {
        userId = userId == null ? 10000 : userId;
        return getDateTime() + toCode(userId);
    }


    /**
     * 生成订单单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */

    public static String getOrderCode(Integer userId) {
        return ORDER_CODE + getCode(userId);
    }


    /**
     * 生成退货单号编码（调用方法）
     * @param userId 网站中该用户唯一ID 防止重复
     */
    public static String getReturnCode(Integer userId) {
        return RETURN_ORDER + getCode(userId);
    }

    /**
     * 生成退款单号编码(调用方法)
     * @param userId  网站中该用户唯一ID 防止重复
     */
    public static String getRefundCode(Integer userId) {
        return REFUND_ORDER + getCode(userId);
    }


    /*
    *
    * 用户ID生成
    *
    * */
    public static String getUserId(StringRedisTemplate stringRedisTemplate){
        String userCode ="RY"+DayCode();
        String maxId = stringRedisTemplate.opsForValue().get(userCode+"maxId");
        if (maxId==null){
            stringRedisTemplate.opsForValue().set(userCode+"maxId","001",1, TimeUnit.DAYS);
            maxId="001";
        }else {
            maxId = IdUtils.pad(3, Long.parseLong(maxId) + 1);
            stringRedisTemplate.opsForValue().set(userCode+"maxId",maxId,1, TimeUnit.DAYS);
        }
        String userId = userCode + maxId;
        return userId;
    }


    /*
    *
    * 商品ID生成
    *
    * */

    public static String getGoodsId(StringRedisTemplate stringRedisTemplate){
        String maxId = stringRedisTemplate.opsForValue().get("GoodsMaxId");
        if (maxId==null){
            stringRedisTemplate.opsForValue().set("GoodsMaxId","001");
            maxId="001";
        }else {
            maxId = IdUtils.pad(3, Long.parseLong(maxId) + 1);
            stringRedisTemplate.opsForValue().set("GoodsMaxId",maxId);
        }
        String userId = "GOODS" + maxId;
        return userId;
    }



    /*
     *
     * 商品类别ID生成
     *
     * */

    public static String getTypeId(StringRedisTemplate stringRedisTemplate){
        String maxId = stringRedisTemplate.opsForValue().get("TypeMaxId");
        if (maxId==null){
            stringRedisTemplate.opsForValue().set("TypeMaxId","001");
            maxId="001";
        }else {
            maxId = IdUtils.pad(3, Long.parseLong(maxId) + 1);
            stringRedisTemplate.opsForValue().set("TypeMaxId",maxId);
        }
        String userId = "TYPE" + maxId;
        return userId;
    }


    /*
     *
     * 收货地址ID生成
     *
     * */

    public static String getConsigneeId(StringRedisTemplate stringRedisTemplate){
        String maxId = stringRedisTemplate.opsForValue().get("ConsigneeMaxId");
        if (maxId==null){
            stringRedisTemplate.opsForValue().set("ConsigneeMaxId","001");
            maxId="001";
        }else {
            maxId = IdUtils.pad(3, Long.parseLong(maxId) + 1);
            stringRedisTemplate.opsForValue().set("ConsigneeMaxId",maxId);
        }
        String userId = "CON" + maxId;
        return userId;
    }

    public static String getOrderId(){
        String orderCode ="OR"+getDateTime()+getRandom(6);
        return orderCode;
    }

}