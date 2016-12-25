package comfranklicm.github.openmind.utils;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import comfranklicm.github.openmind.ActiveDegreeFragment;
import comfranklicm.github.openmind.JsonParsing.BaseJsonParser;
import comfranklicm.github.openmind.JsonParsing.CommentJsonParser;
import comfranklicm.github.openmind.JsonParsing.LoginJsonParser;
import comfranklicm.github.openmind.JsonParsing.LogoutJsonParser;
import comfranklicm.github.openmind.JsonParsing.RegisterJsonParser;
import comfranklicm.github.openmind.JsonParsing.SynchronousDataJsonParser;
import comfranklicm.github.openmind.JsonParsing.ViewAboutMeJsonParser;
import comfranklicm.github.openmind.JsonParsing.ViewActiveDataJsonParser;
import comfranklicm.github.openmind.JsonParsing.ViewAllProjectsJsonParser;
import comfranklicm.github.openmind.JsonParsing.ViewOwnProjectsJsonParser;
import comfranklicm.github.openmind.JsonParsing.ViewProjectDetailJsonParser;
import comfranklicm.github.openmind.JsonParsing.ViewVoteProjectsJsonParser;
import comfranklicm.github.openmind.JsonParsing.VoteJsonParser;
import comfranklicm.github.openmind.MyActivity;
import comfranklicm.github.openmind.OwnProjectsFragment;
import comfranklicm.github.openmind.ProjectListRecyViewAdapter;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/8/27
 */
/**
 * 用户信息封装，User单例
 */
public class User {
    //为了实现每次使用该类时不创建新的对象而创建的静态对象
    private static User userInstance;
    public List<ProjectInfo>allinfos=new ArrayList<ProjectInfo>();
    public List<ProjectInfo>voteinfos=new ArrayList<ProjectInfo>();
    public List<ProjectInfo>owninfos=new ArrayList<ProjectInfo>();
    public List<ActiveInfo>ownactives=new ArrayList<ActiveInfo>();
    public List<AboutMe>aboutMeList=new ArrayList<AboutMe>();
    public List<BaseJsonParser>baseJsonParsers=new ArrayList<BaseJsonParser>();
    public List<Comment> currentParentComments = new ArrayList<Comment>();
    public List<Comment> currentChildComments = new ArrayList<Comment>();
    public List<Integer> votenumbers = new ArrayList<Integer>();
    public ProjectListRecyViewAdapter projectListRecyViewAdapter;
    public int index = 0;
    private String userName;
    private String passWord;
    private String realName;
    private String department;
    private String registerTime;
    private String voteNumber;
    private boolean isLogin=false;
    private String pictureLink;
    private MyActivity myActivity;//add by lyy 2016.9.1
    private OwnProjectsFragment ownProjectsFragment;
    private ActiveDegreeFragment activeDegreeFragment;
    private View allView;
    private String loginResult;
    private String registerResult;
    private String loginError;
    private String registerError;
    private String logoutResult;
    private ProjectInfo currentProject;
    private String projectFindResult;
    private String synchronousResult;
    private String synchronousError;
    private String commentResult;
    private String commentError;
    private String voteResult;
    private String voteError;
    private Integer pageNumber;
    private String  minimumTime;
    private boolean isLastLogin=false;
    private Integer returnCount=0;
    private Comment currentParentComment;
    private String fileUrl;
    private Comment Commentadded;
    private Integer currentChildCount;
    private Integer currentVotenum;
    private String minimumMonth;
    private Integer activeReturnSize;
    private String aboutMeMiniTime;
    private Integer aboutMeReturnSize;
    private String  upLoadImageResult;
    private String  upLoadImageError;
    //构造方法私有化
    private User(){}

    //实例化一次
    public synchronized static User getInstance()
    {
        if (null == userInstance) {
            userInstance = new User();
            userInstance.setVoteNumber("0");
        }
        return userInstance;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(String voteNumber) {
        this.voteNumber = voteNumber;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    public String getRegisterResult() {
        return registerResult;
    }

    public void setRegisterResult(String registerResult) {
        this.registerResult = registerResult;
    }

    public String getLoginError() {
        return loginError;
    }

    public void setLoginError(String loginError) {
        this.loginError = loginError;
    }

    public String getRegisterError() {
        return registerError;
    }

    public void setRegisterError(String registerError) {
        this.registerError = registerError;
    }

    public String getLogoutResult() {
        return logoutResult;
    }

    public void setLogoutResult(String logoutResult) {
        this.logoutResult = logoutResult;
    }

    public ProjectInfo getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(ProjectInfo currentProject) {
        this.currentProject = currentProject;
    }

    public String getProjectFindResult() {
        return projectFindResult;
    }

    public void setProjectFindResult(String projectFindResult) {
        this.projectFindResult = projectFindResult;
    }

    public String getSynchronousResult() {
        return synchronousResult;
    }

    public void setSynchronousResult(String synchronousResult) {
        this.synchronousResult = synchronousResult;
    }

    public String getSynchronousError() {
        return synchronousError;
    }

    public void setSynchronousError(String synchronousError) {
        this.synchronousError = synchronousError;
    }

    public String getCommentError() {
        return commentError;
    }

    public void setCommentError(String commentError) {
        this.commentError = commentError;
    }

    public String getCommentResult() {
        return commentResult;
    }

    public void setCommentResult(String commentResult) {
        this.commentResult = commentResult;
    }

    public String getVoteResult() {
        return voteResult;
    }

    public void setVoteResult(String voteResult) {
        this.voteResult = voteResult;
    }

    public String getVoteError() {
        return voteError;
    }

    public void setVoteError(String voteError) {
        this.voteError = voteError;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getMinimumTime() {
        return minimumTime;
    }

    public void setMinimumTime(String minimumTime) {
        this.minimumTime = minimumTime;
    }

    public void addAllJsonParse() {
        baseJsonParsers.add(new RegisterJsonParser());
        baseJsonParsers.add(new LoginJsonParser());
        baseJsonParsers.add(null);
        baseJsonParsers.add(new LogoutJsonParser());
        baseJsonParsers.add(null);
        baseJsonParsers.add(null);
        baseJsonParsers.add(new ViewAllProjectsJsonParser());
        baseJsonParsers.add(new ViewOwnProjectsJsonParser());
        baseJsonParsers.add(new ViewProjectDetailJsonParser());
        baseJsonParsers.add(new SynchronousDataJsonParser());
        baseJsonParsers.add(new ViewActiveDataJsonParser());
        baseJsonParsers.add(new CommentJsonParser());
        baseJsonParsers.add(new ViewAboutMeJsonParser());
        baseJsonParsers.add(new ViewVoteProjectsJsonParser());
        baseJsonParsers.add(new VoteJsonParser());
    }
    public MyActivity getMyActivity() {
        return myActivity;
    }//add by lyy 2016.9.1

    public void setMyActivity(MyActivity myActivity) {
        this.myActivity = myActivity;
    }//add by lyy 2016.9.1

    public boolean isLastLogin() {
        return isLastLogin;
    }

    public void setIsLastLogin(boolean isLastLogin) {
        this.isLastLogin = isLastLogin;
    }

    public View getAllView() {
        return allView;
    }

    public void setAllView(View allView) {
        this.allView = allView;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public Comment getCurrentParentComment() {
        return currentParentComment;
    }

    public void setCurrentParentComment(Comment currentParentComment) {
        this.currentParentComment = currentParentComment;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Comment getCommentadded() {
        return Commentadded;
    }

    public void setCommentadded(Comment commentadded) {
        Commentadded = commentadded;
    }

    public Integer getCurrentChildCount() {
        return currentChildCount;
    }

    public void setCurrentChildCount(Integer currentChildCount) {
        this.currentChildCount = currentChildCount;
    }

    public Integer getCurrentVotenum() {
        return currentVotenum;
    }

    public void setCurrentVotenum(Integer currentVotenum) {
        this.currentVotenum = currentVotenum;
    }

    public String getMinimumMonth() {
        return minimumMonth;
    }

    public void setMinimumMonth(String minimumMonth) {
        this.minimumMonth = minimumMonth;
    }

    public Integer getActiveReturnSize() {
        return activeReturnSize;
    }

    public void setActiveReturnSize(Integer activeReturnSize) {
        this.activeReturnSize = activeReturnSize;
    }

    public Integer getAboutMeReturnSize() {
        return aboutMeReturnSize;
    }

    public void setAboutMeReturnSize(Integer aboutMeReturnSize) {
        this.aboutMeReturnSize = aboutMeReturnSize;
    }

    public String getAboutMeMiniTime() {
        return aboutMeMiniTime;
    }

    public void setAboutMeMiniTime(String aboutMeMiniTime) {
        this.aboutMeMiniTime = aboutMeMiniTime;
    }

    public String getUpLoadImageResult() {
        return upLoadImageResult;
    }

    public void setUpLoadImageResult(String upLoadImageResult) {
        this.upLoadImageResult = upLoadImageResult;
    }

    public String getUpLoadImageError() {
        return upLoadImageError;
    }

    public void setUpLoadImageError(String upLoadImageError) {
        this.upLoadImageError = upLoadImageError;
    }

    public OwnProjectsFragment getOwnProjectsFragment() {
        return ownProjectsFragment;
    }

    public void setOwnProjectsFragment(OwnProjectsFragment ownProjectsFragment) {
        this.ownProjectsFragment = ownProjectsFragment;
    }

    public ActiveDegreeFragment getActiveDegreeFragment() {
        return activeDegreeFragment;
    }

    public void setActiveDegreeFragment(ActiveDegreeFragment activeDegreeFragment) {
        this.activeDegreeFragment = activeDegreeFragment;
    }
}

