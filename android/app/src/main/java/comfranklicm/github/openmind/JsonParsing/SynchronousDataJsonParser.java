package comfranklicm.github.openmind.JsonParsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import comfranklicm.github.openmind.utils.Active;
import comfranklicm.github.openmind.utils.ActiveInfo;
import comfranklicm.github.openmind.utils.NetUtil;
import comfranklicm.github.openmind.utils.ProjectInfo;
import comfranklicm.github.openmind.utils.User;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/6
 */
/**
 * 同步数据
 */
public class SynchronousDataJsonParser extends BaseJsonParser{
    public void SynchronousDataJsonParsing(String strResult)
    {
        try{
            JSONObject jarr=new JSONObject(strResult);
            String error="";
            User.getInstance().setSynchronousResult(jarr.getString("result"));
            if (User.getInstance().getSynchronousResult().equals("true")) {
                //个人所有项目数据
                JSONArray jsonArray=new JSONArray(jarr.getString("projects"));
                try {
                    User.getInstance().owninfos.clear();
                }catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject1=(JSONObject)jsonArray.get(i);
                    ProjectInfo projectInfo=new ProjectInfo();
                    projectInfo.setProjectId(jsonObject1.getString("_id"));
                    projectInfo.setProjectName(jsonObject1.getString("proj_name"));
                    projectInfo.setOwnUser(jsonObject1.getString("own_usr"));
                    projectInfo.setOwnName(jsonObject1.getString("own_name"));
                    projectInfo.setOwn_head(jsonObject1.getString("own_head"));
                    projectInfo.setPubTime(jsonObject1.getString("pub_time"));
                    projectInfo.setIntroduction(jsonObject1.getString("introduction"));


                    JSONArray jsonArray1=new JSONArray(jsonObject1.getString("labels"));
                    List<String> labelList=new ArrayList<String>();
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
                        labelList.add(jsonArray1.getString(j));
                    }
                    projectInfo.setLabellist(labelList);
                    User.getInstance().owninfos.add(projectInfo);
                }
                //我的所有活跃数据
                JSONArray jsonArray1=new JSONArray(jarr.getString("active_info"));
                try {
                    User.getInstance().ownactives.clear();
                }catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
                for(int i=0;i<jsonArray1.length();i++)
                {
                    JSONObject jsonObject=(JSONObject)jsonArray1.get(i);
                    ActiveInfo activeInfo=new ActiveInfo();
                    activeInfo.setActive(jsonObject.getString("active"));
                    activeInfo.setMonth(jsonObject.getString("month"));

                    JSONArray jsonArray2=new JSONArray(jsonObject.getString("active"));
                    List<Active>activeList=new ArrayList<Active>();
                    for (int j=0;j<jsonArray2.length();j++)
                    {
                        JSONObject jsonObject1=(JSONObject)jsonArray2.get(j);
                        Active active=new Active();
                        active.setDay(jsonObject1.getString("day"));
                        active.setDegree(jsonObject1.getString("degree"));
                        activeList.add(active);
                    }
                    activeInfo.setActiveList(activeList);
                    User.getInstance().ownactives.add(activeInfo);
                }
                NetUtil.getInstance().setToken(jarr.getString("token"));
            }
            else {
                switch (jarr.getString("reason"))
                {
                    case "1":error="未登陆";break;
                    case "2":error="令牌错误";break;
                }
                User.getInstance().setSynchronousError(error);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
