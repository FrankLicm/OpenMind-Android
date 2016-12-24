package comfranklicm.github.openmind.JsonParsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import comfranklicm.github.openmind.utils.Link;
import comfranklicm.github.openmind.utils.ProjectInfo;
import comfranklicm.github.openmind.utils.User;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/6
 */
/**
 * 查看投票栏
 */
public class ViewVoteProjectsJsonParser extends BaseJsonParser{

    public void ViewVoteProjectsJsonParsing(String strResult)
    {
        try{
            JSONArray jsonArray=new JSONArray(strResult);
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jarr=(JSONObject)jsonArray.get(i);
                ProjectInfo projectInfo=new ProjectInfo();
                projectInfo.setProjectId(jarr.getString("_id"));
                projectInfo.setProjectName(jarr.getString("proj_name"));
                projectInfo.setOwnUser(jarr.getString("own_usr"));
                projectInfo.setOwnName(jarr.getString("own_name"));
                projectInfo.setOwn_head(jarr.getString("own_head"));
                projectInfo.setPubTime(jarr.getString("pub_time"));
                projectInfo.setIntroduction(jarr.getString("introduction"));
                projectInfo.setLabels(jarr.getString("labels"));
                projectInfo.setEverVoted(jarr.getString("ever_voted"));
                projectInfo.setScore(jarr.getString("score"));
                Integer s = Integer.parseInt(jarr.getString("score"));
                User.getInstance().votenumbers.add(s);
                JSONArray jsonArray1=new JSONArray(jarr.getString("labels"));
                List<String> stringList=new ArrayList<String>();
                for (int j=0;j<jsonArray1.length();j++)
                {
                    if (j==0)
                    {
                        projectInfo.setLabel1(jsonArray1.getString(j));
                    }
                    if (j==1)
                    {
                        projectInfo.setLabel2(jsonArray1.getString(j));
                    }
                    stringList.add(jsonArray1.getString(j));
                }
                projectInfo.setLabellist(stringList);
                JSONArray jsonArray2=new JSONArray(jarr.getString("links"));
                List<Link>linkList=new ArrayList<Link>();
                for(int k=0;k<jsonArray2.length();k++)
                {
                    JSONObject jsonObject=(JSONObject)jsonArray2.get(k);
                    Link link=new Link();
                    link.setAdress(jsonObject.getString("address"));
                    link.setDescription(jsonObject.getString("description"));
                    linkList.add(link);
                }
                projectInfo.setLinkList(linkList);
                User.getInstance().voteinfos.add(projectInfo);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
