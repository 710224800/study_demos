package rulerview.test.com.rulerview;

public class SDKLog {
    private static final String TAG_PRE = "";

    public static int v(String tag, String msg) {
        return android.util.Log.v(TAG_PRE + tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        return android.util.Log.v(TAG_PRE + tag, msg, tr);
    }

    public static int d(String tag, String msg) {
        return android.util.Log.d(TAG_PRE + tag, msg);
    }

    public static int d(String tag, String msg, Throwable tr) {
        return android.util.Log.d(TAG_PRE + tag, msg, tr);
    }

    public static int i(String tag, String msg) {
        return android.util.Log.i(TAG_PRE + tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        return android.util.Log.i(TAG_PRE + tag, msg, tr);
    }

    public static int e(String tag, String msg) {
        return android.util.Log.e(TAG_PRE + tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        return android.util.Log.e(TAG_PRE + tag, msg, tr);
    }

//    public static int w(String tag, String msg) {
//        if (!AppConfig.getDebug()) {
//            return 0;
//        }
//        return android.util.Log.w(TAG_PRE + tag, msg);
//    }
//
//    public static int w(String tag, String msg, Throwable tr) {
//        if (!AppConfig.getDebug())
//            return 0;
//        return android.util.Log.w(TAG_PRE + tag, msg, tr);
//    }

//    private static FileLog sFileLoger;
}
