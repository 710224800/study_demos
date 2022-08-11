package com.hello.suanfastudy.suanfa;

/**
 * Created by lyhao on 2021/12/7.
 */
public class Read4 {
    static final String fileData = "1234567890";
    static int index = 0;
    public static void main(String[] args) {
        char[] buf = new char[1];
        read(buf, buf.length);
        System.out.println(buf);
    }

    public static int read(char[] buf, int n) {
        int total = 0;
        char[] buf4 = new char[4];
        while(total < n) {
            int ret = read4(buf4);
            if (n - total > ret) {
                System.arraycopy(buf4, 0, buf, total, ret);
                total += ret;
                if (ret < 4) {
                    break;
                }
            } else {
                System.arraycopy(buf4, 0, buf, total, n - total);
                total += (n - total);
            }

        }
        return total;
    }

    public static int read4(char[] buf) {
        int i = 0;
        for(i = 0; i < buf.length; i++) {
            buf[i] = fileData.charAt(index ++);
        }
        return index;
    }

/** C 代码用指针，效率会更高 **/
//    // Forward declaration of the read4 API.
//    int read4(char *buf);
//
//    class Solution {
//        public:
//        /**
//         * @param buf Destination buffer
//         * @param n   Number of characters to read
//         * @return    The number of actual characters read
//         */
//        int read(char *buf, int n) {
//            int total=0;
//            while(1)
//            {
//                int ret=read4(buf+total);
//                total+=ret;
//                if(ret<4||total>=n)break;
//            }
//            if(total>n)total=n;
//            buf[total]='\0';
//            return total;
//        }
//    }
}
