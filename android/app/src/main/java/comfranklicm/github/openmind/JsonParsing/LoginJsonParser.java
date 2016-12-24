package comfranklicm.github.openmind.JsonParsing;

import org.json.JSONException;
import org.json.JSONObject;

import comfranklicm.github.openmind.utils.NetUtil;
import comfranklicm.github.openmind.utils.User;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/6
 */
/**
 * 登录 Json数据解析
 */
public class LoginJsonParser extends BaseJsonParser {

    public void LoginJsonParsing(String strResult)
    {
        try {
            JSONObject jarr=new JSONObject(strResult);
            String error="";
            User.getInstance().setLoginResult(jarr.getString("result"));
            if(User.getInstance().getLoginResult().equals("true"))
            {
                User.getInstance().setRealName(jarr.getString("realname"));
                User.getInstance().setDepartment(jarr.getString("department"));
                User.getInstance().setRegisterTime(jarr.getString("signup_time"));
                User.getInstance().setPictureLink(jarr.getString("head"));
                NetUtil.getInstance().setToken(jarr.getString("token"));
                User.getInstance().setIsLogin(true);
            }
            else
            {
                switch (jarr.getString("reason"))
                {
                    case "1":error="用户名或密码格式错误";break;
                    case "2":error="用户名密码错误";break;
                    default:error="其他错误";break;                        }
                User.getInstance().setLoginError(error);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}

