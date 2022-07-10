package test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.R.Result;
import com.shop.ShoppingSystemApplication;
import com.shop.dao.MenuMapper;
import com.shop.dao.TypeMapper;
import com.shop.dao.UserMapper;
import com.shop.domain.pojo.Menu;
import com.shop.domain.pojo.Type;
import com.shop.domain.pojo.User;
import com.shop.service.TypeService;
import com.shop.service.UserService;
import com.shop.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;

import static cn.hutool.core.util.RandomUtil.getRandom;

@SpringBootTest(classes = ShoppingSystemApplication.class)

public class Test {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private TypeService typeService;


    @org.junit.jupiter.api.Test
    public void test() {
    /*    String s = "test001";
        s = IdUtils.DayCode()+IdUtils.pad(3, Long.parseLong(s.substring(s.length() -2,s.length())) + 1);
        System.out.println(s);*/
  /*      Result type01 = typeService.getTypeById("type01");
        System.out.println(type01.getData());
        Result list = typeService.getTypeList();
        System.out.println(list.getData());*/
        String[] strings = stringetRandom(10, 10);
        for (String s : strings) {
            System.out.println(s);
        }
        char s []={1,2,3,4,5,6,7,8,9,0,'a'};

    }

    public String[] stringetRandom(int count, int StringLen) {
        char arr[] = new char[56+ 11];
        int j = 0;
        for (int i = 65; i <= 90; i++) {
            arr[j] = (char) i;
            j++;
        }
        for (int i = 48; i <= 57; i++) {
            arr[j] = (char) i;
            j++;
        }
        String strings = "";
        String[] sumcount = new String[count];
        HashSet<String> hashSet = new HashSet<>();
        for (int i = 0; i < sumcount.length; ) {
            if (hashSet.size() < count) {
                strings = "";
                int v;
                char s;
                for (int z = 0; z < StringLen; z++) {
                    v = (int) (Math.random() * (90-65)+11);
                    s = arr[v];
                    strings += s;
                }
                hashSet.add(strings);
                i++;
            }
        }
        int i = 0;

        for (String s : hashSet) {
            sumcount[i] = s;
            i++;
        }
        return sumcount;
    }


}
