package comfranklicm.github.openmind.Httprequests;

/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/6
 */
import android.util.Log;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;
import comfranklicm.github.openmind.utils.NetUtil;
import comfranklicm.github.openmind.utils.User;

public class HttpPostImageRunnable implements Runnable{
    private String data;
    @Override
    public void run() {
        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
        NameValuePair pair1 = new BasicNameValuePair("action_id","3");
        NameValuePair pair2 = new BasicNameValuePair("token", NetUtil.getInstance().getToken());
        Log.d("imgtoken",NetUtil.getInstance().getToken());
        NameValuePair pair3 = new BasicNameValuePair("head", data);
        pairList.add(pair1);
        pairList.add(pair2);
        pairList.add(pair3);
        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost("http://"+ NetUtil.getInstance().getIpAddress()+":"+NetUtil.getInstance().getPort()+"/action");
        httpPost.setHeader("Cookie", NetUtil.getInstance().getSessionId());
        Log.d("imgcookie",NetUtil.getInstance().getSessionId());
        try {
            HttpEntity requestHttpEntity = new UrlEncodedFormEntity(pairList, HTTP.UTF_8);
            httpPost.setEntity(requestHttpEntity);
            HttpResponse httpResponse=httpClient.execute(httpPost);
            int code=httpResponse.getStatusLine().getStatusCode();
            if(code==200)
            {
                Header it = httpResponse.getFirstHeader("Set-Cookie");
                String session = it.toString();
                String[] heads = session.split(";");
                String [] split = heads[0].split(":");
                session=split[1];
                NetUtil.getInstance().setSessionId(session);
                Log.d("savesessionid", NetUtil.getInstance().getSessionId());
               String result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
                Log.d("result",result);
                JSONObject jsonObject =new JSONObject(result);
                User.getInstance().setUpLoadImageResult(jsonObject.getString("result"));
                if(User.getInstance().getUpLoadImageResult().equals("true"))
                {
                    NetUtil.getInstance().setToken(jsonObject.getString("token"));
                    User.getInstance().setPictureLink(jsonObject.getString("head"));
                }else
                {
                    String error;
                    switch (jsonObject.getString("reason"))
                    {
                        case "1":error="未登陆";break;
                        case "2":error="令牌错误";break;
                        default:error="其他错误";break;
                    }
                    User.getInstance().setUpLoadImageError(error);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
