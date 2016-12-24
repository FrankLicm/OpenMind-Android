package comfranklicm.github.openmind.JsonParsing;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import comfranklicm.github.openmind.utils.Comment;
import comfranklicm.github.openmind.utils.Link;
import comfranklicm.github.openmind.utils.ProjectInfo;
import comfranklicm.github.openmind.utils.Share;
import comfranklicm.github.openmind.utils.User;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/6
 */
/**
 * 浏览具体的一个项目的详细信息
 */
public class ViewProjectDetailJsonParser extends BaseJsonParser{

    public void ViewProjectDetailJsonParsing(String strResult)
    {
        try {
            JSONObject jarr=new JSONObject(strResult);
            ProjectInfo projectInfo=new ProjectInfo();
            User.getInstance().setProjectFindResult(jarr.getString("result"));
            if (User.getInstance().getProjectFindResult().equals("true"))
            {
                projectInfo.setProjectId(jarr.getString("_id"));
                projectInfo.setProjectName(jarr.getString("proj_name"));
                projectInfo.setOwnUser(jarr.getString("own_usr"));
                projectInfo.setOwnName(jarr.getString("own_name"));
                projectInfo.setOwn_head(jarr.getString("own_head"));
                projectInfo.setPubTime(jarr.getString("pub_time"));
                projectInfo.setIntroduction(jarr.getString("introduction"));


                JSONArray jsonArray=new JSONArray(jarr.getString("labels"));
                List<String> labelList=new ArrayList<String>();
                for (int j=0;j<jsonArray.length();j++)
                {
                    if (j==0)
                    {
                        projectInfo.setLabel1(jsonArray.getString(j));
                    }
                    if (j==1)
                    {
                        projectInfo.setLabel2(jsonArray.getString(j));
                    }
                    labelList.add(jsonArray.getString(j));
                }
                projectInfo.setLabellist(labelList);


                JSONArray jsonArray1=new JSONArray(jarr.getString("links"));
                List<Link>linkList=new ArrayList<Link>();
                for(int k=0;k<jsonArray1.length();k++)
                {
                    JSONObject jsonObject=(JSONObject)jsonArray1.get(k);
                    Link link=new Link();
                    link.setAdress(jsonObject.getString("address"));
                    link.setDescription(jsonObject.getString("description"));
                    linkList.add(link);
                }
                projectInfo.setLinkList(linkList);

                projectInfo.setShares(jarr.getString("shares"));
                Log.d("shares",projectInfo.getShares());
                JSONArray jsonArray2=new JSONArray(jarr.getString("shares"));
                List<Share>shareList=new ArrayList<Share>();
                for (int l=0;l<jsonArray2.length();l++)
                {
                    JSONObject jsonObject=(JSONObject)jsonArray2.get(l);
                    Share share=new Share();
                    share.setName(jsonObject.getString("name"));
                    share.setTime(jsonObject.getString("time"));
                    share.setUrl(jsonObject.getString("url"));
                    shareList.add(share);
                }

                projectInfo.setShareList(shareList);
               // Log.d("shareslist",shareList.get(0).getName());

                JSONArray jsonArray3=new JSONArray(jarr.getString("comments"));
                List<Comment>commentList=new ArrayList<Comment>();
                for (int i=0;i<jsonArray3.length();i++)
                {
                    JSONObject jsonObject=(JSONObject)jsonArray3.get(i);
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
                    commentList.add(comment);
                }
                projectInfo.setCommentList(commentList);
                User.getInstance().setCurrentProject(projectInfo);
            }
            else
            {

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
