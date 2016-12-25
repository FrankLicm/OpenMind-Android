package comfranklicm.github.openmind.utils;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/8/31
 */
/**
 * 评论封装
 */
public class Comment{
    private String commentId;
    private String parentId;
    private String sendUser;
    private String sendName;
    private String sendHead;
    private String receiveUser;
    private String receiveName;
    private String time;
    private String content;
    public Integer childCommentCount=0;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendHead() {
        return sendHead;
    }

    public void setSendHead(String sendHead) {
        this.sendHead = sendHead;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}