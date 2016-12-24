package comfranklicm.github.openmind.JsonParsing;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/6
 */
import org.json.JSONException;
import org.json.JSONObject;

import comfranklicm.github.openmind.utils.Comment;
import comfranklicm.github.openmind.utils.User;

/**
 * 发表评论和建议
 */
public class CommentJsonParser extends BaseJsonParser{

    public void CommentJsonParsing(String strResult)
    {
        try {
            JSONObject jarr=new JSONObject(strResult);
            String error="";
            User.getInstance().setCommentResult(jarr.getString("result"));
            if(User.getInstance().getCommentResult().equals("true"))
            {
                JSONObject jsonObject=new JSONObject(jarr.getString("comment"));
                Comment comment=new Comment();
                comment.setCommentId(jsonObject.getString("id"));
                comment.setParentId(jsonObject.getString("parent_id"));
                comment.setSendUser(jsonObject.getString("send_usr"));
                comment.setSendName(jsonObject.getString("send_name"));
                comment.setSendHead(jsonObject.getString("send_head"));
                comment.setReceiveUser(jsonObject.getString("recv_usr"));
                comment.setReceiveName(jsonObject.getString("recv_name"));
                comment.setTime(jsonObject.getString("time"));
                comment.setContent(jsonObject.getString("content"));
                User.getInstance().setCommentadded(comment);
            }
            else
            {
                switch (jarr.getString("reason"))
                {
                    case "1":error="未登陆";break;
                    case "2":error="不存在该项目";break;
                    case "3":error="评论内容为空";break;
                    default:error="其他错误";break;                        }
                User.getInstance().setCommentError(error);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
