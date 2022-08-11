package com.hello.suanfastudy.suanfa;

import java.util.Collections;
import java.util.HashMap;

/**
 * Created by lyhao on 2021/12/22.
 *
 * 159. 至多包含两个不同字符的最长子串
 *
 * 如果输入字符串 s 的长度小于等于 2，那么它自己就是“至多包含两个不同字符的最长子串”，返回 s.length()；
 * 定义左右两个指针：left、right；分别表示当前字串的左右边界索引。
 * 初始时，left 和 right 都指向 0。
 * right 每往右走一步，都判断 left 到 right 区间字符种类有没有超过 2 种
 * 1、如果没有，说明当前 left 到 right 区间的字串是符合要求的，更新长度 maxLen = Math.max(maxLen, right - left)
 * 2、如果超过了 2 种，则 left 往右移动，直到区间字符种类不超过 2 种为止，更新长度 maxLen = Math.max(maxLen, right - left)。
 * 现在就有一个棘手的问题：如何判断 left 到 right 区间内字符是不是超过了 2 种？
 *
 * 解决思路如下：
 * 定义一个 HashMap，key 为字符，value 为该字符在区间 left 到 right 中最靠右的那个索引。
 * 于是，right 每往右走一步，都进行以下操作：
 * 1、map.put(s.charAt(right), right)，如果 s.charAt(right) 已存在，刚好顺便把它更新到最靠右；不存在的话，添加后就是唯一的存在，value 也是最靠右的。
 * 2、判断 map 里面的元素是不是等于三个，如果是，说明得删除一个，找出三个元素 value 最小的那个进行删除；
 */
public class LeetCode159 {
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstringTwoDistinct("eceba")); // 3
        System.out.println(lengthOfLongestSubstringTwoDistinct("ccaabbb")); // 5
        System.out.println(lengthOfLongestSubstringTwoDistinct("")); // 0
    }

    public static int lengthOfLongestSubstringTwoDistinct(String s) {
        if(s == null) {
            return 0;
        }
        if(s.length() <= 2) {
            return s.length();
        }
        HashMap<Character, Integer> hashMap = new HashMap<>();
        int left = 0, right = 0;
        int maxLen = 0;
        while(right < s.length()){
            char rC = s.charAt(right);
            if (hashMap.size() >= 2) {
                if(hashMap.containsKey(rC)){
                    hashMap.put(rC, right);
                    right ++;
                    maxLen = Math.max(maxLen, right - left);
                } else {
                    int minIndex = Collections.min(hashMap.values());
                    hashMap.remove(s.charAt(minIndex));
                    left = minIndex + 1;
                }
            } else {
                hashMap.put(rC, right);
                right ++;
                maxLen = Math.max(maxLen, right - left);
            }
        }
        return maxLen;
    }

    // 这样写也是一样的
    public int lengthOfLongestSubstringTwoDistinct2(String s) {
        int strLength = s.length();
        if (strLength < 3) return strLength;
        int left = 0;
        int right = 0;
        HashMap<Character, Integer> map = new HashMap<>();

        int maxLen = 2;

        while (right < strLength) {
            map.put(s.charAt(right), right);
            right++;
            if (map.size() == 3) {
                int delIdx = Collections.min(map.values());
                map.remove(s.charAt(delIdx));
                left = delIdx + 1;
            }
            maxLen = Math.max(maxLen, right - left);
        }
        return maxLen;
    }
}
