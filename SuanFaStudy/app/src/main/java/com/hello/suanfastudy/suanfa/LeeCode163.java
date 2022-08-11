package com.hello.suanfastudy.suanfa;

/**
 * Created by lyhao on 2021/12/28.
 *
 * 给定一个排序的整数数组 nums ，其中元素的范围在 闭区间 [lower, upper] 当中，返回不包含在数组中的缺失区间。
 *
 * 示例：
 * 输入: nums = [0, 1, 3, 50, 75], lower = 0 和 upper = 99,
 * 输出: [“2”, “4->49”, “51->74”, “76->99”]
 */
public class LeeCode163 {
    public static void main(String[] args) {
        System.out.println(findMissingRanges(new int[]{0, 1, 3, 50, 75}, 0, 99));
    }

    public static String findMissingRanges(int[] nums, int lower, int upper) {
        StringBuilder sb = new StringBuilder();
        int left = lower;
        for (int num : nums) {
            if (left == num) {
                left++;
            } else if (num > left) {
                if (num - left > 1) {
                    sb.append("\"").append(left).append("->").append(num - 1).append("\"").append(", ");
                } else {
                    sb.append("\"").append(left).append("\"").append(", ");
                }
                left = num + 1;
            }
        }
        String result = "";
        if (left == upper) {
            sb.append("\"").append(left).append("\"");
            result = sb.toString();
        } else if (upper > left) {
            sb.append("\"").append(left).append("->").append(upper).append("\"");
            result = sb.toString();
        } else {
            result = sb.toString();
            result = result.substring(0, result.length() - 2); // 去除最后的 ", "
        }
        return "[" + result + "]";
    }
}
