package comfranklicm.github.openmind.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/5
 */
public class ManyTreeNode {
    /** 子树集合*/
    private List<ManyTreeNode> childList;

    public String filename;

    private ManyTreeNode ParentManyTreeNode=null;
    /**
     * 构造函数
     *
     * @param data 树节点
     */
    public ManyTreeNode(String data)
    {
        this.filename = data;
        this.childList = new ArrayList<ManyTreeNode>();
    }

    /**
     * 构造函数
     *
     * @param data 树节点
     * @param childList 子树集合
     */
    public ManyTreeNode(String data, List<ManyTreeNode> childList)
    {
        this.filename = data;
        this.childList = childList;
    }

    public String getFileName() {
        return filename;
    }

    public void setFileName(String data) {
        this.filename = data;
    }

    public List<ManyTreeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<ManyTreeNode> childList) {
        this.childList = childList;
    }

    public ManyTreeNode getParentManyTreeNode() {
        return ParentManyTreeNode;
    }

    public void setParentManyTreeNode(ManyTreeNode parentManyTreeNode) {
        ParentManyTreeNode = parentManyTreeNode;
    }
}
