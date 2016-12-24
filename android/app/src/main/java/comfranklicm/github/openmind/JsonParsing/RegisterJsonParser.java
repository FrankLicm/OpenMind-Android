package comfranklicm.github.openmind.JsonParsing;

import org.json.JSONException;
import org.json.JSONObject;

import comfranklicm.github.openmind.utils.User;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/6
 */
/**
 * 注册 Json数据解析
 */
public class RegisterJsonParser extends BaseJsonParser{
    public void RegisterJsonParsing(String strResult){
        try {
            JSONObject jarr=new JSONObject(strResult);
            String error="";
            User.getInstance().setRegisterResult(jarr.getString("result"));
            if(User.getInstance().getRegisterResult().equals("true"))
            {

            }
            else
            {
                switch (jarr.getString("reason"))
                {
                    case "1":error="用户名或密码格式错误";break;
                    case "2":error="信息不完整";break;
                    case "3":error="用户名已经存在";break;
                    default:error="其他错误";break;                        }
                User.getInstance().setRegisterError(error);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
