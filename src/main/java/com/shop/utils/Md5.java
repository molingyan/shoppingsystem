package com.shop.utils;

public class Md5 {
    public static String getRandomSalt() {
        String model = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuffer salt = new StringBuffer();
        char[] m = model.toCharArray();
        for (int i = 0; i < 6; i++) {
            char c = m[(int) (Math.random() * 36)];
// 保证六位随机数之间没有重复的
// if (randomcode.contains(String.valueOf©)) {
// i–;
// continue;
// }
            salt = salt.append(c);
        }
        return salt.toString();
    }
}
