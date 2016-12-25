package comfranklicm.github.openmind.utils;

/**
 * Created and Modified by:LiChangMao
 * Time:2016/8/31
 */
import java.util.List;

/**
 * 项目数据封装
 */
public class ProjectInfo {
    private String projectId;
    private String projectName;
    private String ownUser;
    private String ownName;
    private String ownTime;
    private String own_head;
    private String pubTime;
    private String Labels;
    private String links;
    private String introduction;
    private String shares;
    private String comments;
    private String label1;
    private String label2;
    private String score;
    private String everVoted;
    private List<String>labellist;
    private List<Link>linkList;
    private List<Comment>commentList;
    private List<Share>shareList;

    public ProjectInfo(){}

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

    public String getOwnUser() {
        return ownUser;
    }

    public void setOwnUser(String ownUser) {
        this.ownUser = ownUser;
    }

    public String getOwnTime() {
        return ownTime;
    }

    public void setOwnTime(String ownTime) {
        this.ownTime = ownTime;
    }

    public String getLabels() {
        return Labels;
    }

    public void setLabels(String labels) {
        Labels = labels;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getLink() {
        return links;
    }

    public void setLink(String links) {
        this.links = links;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getOwnName() {
        return ownName;
    }

    public void setOwnName(String ownName) {
        this.ownName = ownName;
    }

    public List<String> getLabellist() {
        return labellist;
    }

    public void setLabellist(List<String> labellist) {
        this.labellist = labellist;
    }

    public String getOwn_head() {
        return own_head;
    }

    public void setOwn_head(String own_head) {
        this.own_head = own_head;
    }

    public List<Link> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<Link> linkList) {
        this.linkList = linkList;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Share> getShareList() {
        return shareList;
    }

    public void setShareList(List<Share> shareList) {
        this.shareList = shareList;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getEverVoted() {
        return everVoted;
    }

    public void setEverVoted(String everVoted) {
        this.everVoted = everVoted;
    }
}


