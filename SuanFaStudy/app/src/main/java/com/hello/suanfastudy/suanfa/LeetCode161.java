package com.hello.suanfastudy.suanfa;

/**
 * Created by lyhao on 2021/12/27.
 *
 * 给定两个字符串 s 和 t，判断他们的编辑距离是否为 1。
 *
 * 注意：
 *  满足编辑距离等于 1 有三种可能的情形：
 *
 * 往 s 中插入一个字符得到 t
 * 从 s 中删除一个字符得到 t
 * 在 s 中替换一个字符得到 t
 */
public class LeetCode161 {
    public static void main(String[] args) {
        System.out.println(isOneEditDistance2("123", "1213"));
    }

    public static boolean isOneEditDistance2(String s, String t) {
        if (s.length() > t.length()) { // 保证 s长度是小于等于t，主要为了方便区分哪个是短的
            return isOneEditDistance2(t, s);
        }
        int distance = t.length() - s.length();
        if (distance > 1) { //长度差超过1
            return false;
        }
        int minLen = s.length();
        for(int i = 0; i < minLen; i++) {
            if (s.charAt(i) != t.charAt(i)) {
                //如长度相同，那么两字符串后面应该相同
                if (distance == 0) {
                    return s.substring(i+1).equals(t.substring(i+1));
                } else { //如长度不同，那么必须在s[i]插入t[i]，所以要保证s.substring(i).equals(t.substring(i + 1))
                    return s.substring(i).equals(t.substring(i+1));
                }
            }
        }
        return distance == 1;
    }

    // 下边这个也对，是我自己想的，但是每个字符这样比较，性能肯定不是最好的方法

    public static boolean isOneEditDistance(String s, String t) {
        int distance = 0;
        if (Math.abs(s.length() - t.length()) > 1) {
            return false;
        }
        if (s.length() == 0 || t.length() == 0) {
            return true;
        }
        int indexS = 0, indexT = 0;
        while (indexS < s.length() && indexT < t.length()) {
            if (s.charAt(indexS) != t.charAt(indexT)) {
                if (distance > 0) {
                    return false;
                }
                distance++;
                if (indexS + 1 < s.length() && s.charAt(indexS +1) == t.charAt(indexT)) {
                    indexS ++;
                    continue;
                } else if (indexT + 1 < t.length() && s.charAt(indexS) == t.charAt(indexT + 1)) {
                    indexT ++;
                    continue;
                }
            }
            indexS++;
            indexT++;
        }
        if (distance == 0) {
            return indexS == indexT && indexS == Math.max(s.length(), t.length()) - 1;
        }
        return distance == 1 && indexS == s.length() && indexT == t.length();
    }
}
