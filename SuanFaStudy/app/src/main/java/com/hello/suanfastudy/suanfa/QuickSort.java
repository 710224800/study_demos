package com.hello.suanfastudy.suanfa;

import java.util.Stack;

/**
 * Created by lyhao on 2021/12/1.
 */
public class QuickSort { // 快速排序
    public static void main(String[] args) {
        int[] array = {0,10,7,2,4,7,62,3,4,2,1,8,9,19};
        sort2(array, 0, array.length - 1);
        for (int item : array) {
            System.out.print("  ");
            System.out.print(item);
        }
    }

    // ====================================递归  递归  递归====================================
    public static void sort(int[] array, int start, int end) {
        if (array == null || array.length <= 0) {
            return;
        }
        if (start > end) {
            return;
        }
        int targetIndex = quickSort(array, start, end);

        sort(array, start, targetIndex - 1);
        sort(array, targetIndex + 1, end);
    }


    public static int quickSort(int[] array, int left, int right){
        int targetIndex = left;
        int target = array[left];
        while (left < right) {
            while (left < right && target >= array[right]) {
                right --;
            }
            while (left < right && target <= array[left]) {
                left ++;
            }
            if (left < right) {
                int temp = array[left];
                array[left] = array[right];
                array[right] = temp;
            }
        }
        //最后将基准 与 left right 相等的位置交换 ，这样就以基准分为两部分
        array[targetIndex] = array[left];
        array[left] = target;
        targetIndex = left;
        return targetIndex;
    }

    // ====================================非递归  非递归  非递归====================================
    public static void sort2(int[] array, int left, int right){
        Stack<Integer> stack = new Stack<>();
        int start, end, targetIndex;
        stack.push(left);
        stack.push(right);

        while (!stack.empty()) {
            end = stack.pop();
            start = stack.pop();
            targetIndex = quickSort(array, start, end);
            if (targetIndex > start + 1) {
                stack.push(start);
                stack.push(targetIndex - 1);
            }
            if (targetIndex < end - 1) {
                stack.push(targetIndex + 1);
                stack.push(end);
            }
        }
    }
}
