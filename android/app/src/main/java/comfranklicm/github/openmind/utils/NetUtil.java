package comfranklicm.github.openmind.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/8/31
 */
/**
 *网络部分封装  网络单例
 */
public class NetUtil {
    private String ipAddress;
    private String port;
    private String sessionId;
    private String token;

    //为了实现每次使用该类时不创建新的对象而创建的静态对象
    private static NetUtil NetUtilInstance;

    //构造方法私有化
    private NetUtil(){}

    //实例化一次
    public synchronized static NetUtil getInstance()
    {
        if (null == NetUtilInstance) {
            NetUtilInstance = new NetUtil();
        }
        return NetUtilInstance;
    }

    //判断网络是否连通
    public static boolean isNetworkConnectionActive(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
