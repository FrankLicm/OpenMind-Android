package comfranklicm.github.openmind.JsonParsing;

import org.json.JSONException;
import org.json.JSONObject;

import comfranklicm.github.openmind.utils.User;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/6
 */
/**
 * 为喜爱的项目投票
 */
public class VoteJsonParser extends BaseJsonParser{
    public void VoteJsonParsing(String strResult)
    {
        try {
            JSONObject jarr=new JSONObject(strResult);
            String error="";
            User.getInstance().setVoteResult(jarr.getString("result"));
            if(User.getInstance().getVoteResult().equals("true"))
            {

            }
            else
            {
                switch (jarr.getString("reason"))
                {
                    case "1":error="未登陆";break;
                    case "2":
                        error = "2";
                        break;
                    case "3":
                        error = "该项目此时不处于投票状态";
                        break;
                    case "4":
                        error = "你的投票权利用光了";
                        break;
                    default:error="其他错误";break;
                }
                User.getInstance().setVoteError(error);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
