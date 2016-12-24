package comfranklicm.github.openmind.JsonParsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import comfranklicm.github.openmind.utils.ProjectInfo;
import comfranklicm.github.openmind.utils.User;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/6
 */
/**
 * 浏览所有项目的概要信息 Json解析
 */
public class ViewAllProjectsJsonParser extends BaseJsonParser{
    public void ViewAllProjectsJsonParsing(String strResult)
    {
        try{
            JSONArray jsonArray=new JSONArray(strResult);
            int i;
            for(i=0;i<jsonArray.length();i++)
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
                JSONArray jsonArray1=new JSONArray(jarr.getString("labels"));
                List<String> stringList=new ArrayList<String>();
                for (int j=0;j<jsonArray1.length();j++)
                {
                    if (j==0)
                    {
                        projectInfo.setLabel1(jsonArray1.getString(j));
                    }
                    try {
                        if (j==1)
                        {
                            projectInfo.setLabel2(jsonArray1.getString(j));
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    stringList.add(jsonArray1.getString(j));
                }
                projectInfo.setLabellist(stringList);
                User.getInstance().allinfos.add(projectInfo);
            }
            JSONObject jarr=(JSONObject)jsonArray.get(jsonArray.length()-1);
            User.getInstance().setMinimumTime(jarr.getString("pub_time"));
            User.getInstance().setReturnCount(jsonArray.length());
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
