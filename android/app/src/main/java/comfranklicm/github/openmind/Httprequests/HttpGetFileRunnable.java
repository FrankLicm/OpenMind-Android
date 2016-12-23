package comfranklicm.github.openmind.Httprequests;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/6
 */
public class HttpGetFileRunnable implements Runnable {
    String path;
    String result;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public void run() {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(path);
            // replace with your url
            HttpResponse response;
            try {
                response = client.execute(request);
                //HttpEntity entity = respons
                result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("wrong:", "wrong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
