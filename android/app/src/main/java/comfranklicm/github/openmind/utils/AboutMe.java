package comfranklicm.github.openmind.utils;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/8/27
 */
/**
 * 与我相关数据封装
 */
public class AboutMe {
    private String whoUser;
    private String whoName;
    private String whoHead;
    private String time;
    private String projectId;
    private String projectName;
    private String actionId;//消息类型,0  表示评论或回复  1  表示收藏
    private String content;//当 action_id==0，此值才有实际意义

    public String getWhoUser() {
        return whoUser;
    }

    public void setWhoUser(String whoUser) {
        this.whoUser = whoUser;
    }

    public String getWhoName() {
        return whoName;
    }

    public void setWhoName(String whoName) {
        this.whoName = whoName;
    }

    public String getWhoHead() {
        return whoHead;
    }

    public void setWhoHead(String whoHead) {
        this.whoHead = whoHead;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
