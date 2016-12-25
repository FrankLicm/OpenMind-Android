package comfranklicm.github.openmind.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/5
 */
public class ManyNodeTree {

    /** 树根*/
    private ManyTreeNode root;
    private ManyTreeNode curNode;
    private String curpath;
    /**
     * 构造函数
     */
    public ManyNodeTree()
    {
        root = new ManyTreeNode(new String("..."));
        root.setParentManyTreeNode(null);
        curNode=root;
        curpath="...";
    }

    public void addPath(String path)
    {
        ManyTreeNode indexNode=root;
        int flag=0;
        int j;
        if(path.contains("/"))
        {
            String[] spit=path.split("/") ;
            for(int i=0;i<spit.length;i++)
            {
                j=0;
                flag=0;
                while(!indexNode.getChildList().isEmpty()&&j<indexNode.getChildList().size())
                {
                    if(indexNode.getChildList().get(j).getFileName().equals(spit[i]))
                    {
                        //System.out.println(j);
                        flag=1;
                        break;
                    }
                    j++;
                }
                if(flag==1)
                {
                    //System.out.println(j);
                    indexNode=indexNode.getChildList().get(j);
                    //System.out.println(curNode.getChildList().get(j).getFileName());
                }
                else
                {
                    indexNode.getChildList().add(new ManyTreeNode(spit[i]));
                    indexNode.getChildList().get(indexNode.getChildList().size()-1).setParentManyTreeNode(indexNode);
                    //System.out.println(indexNode.getChildList().get(indexNode.getChildList().size()-1).getFileName());
                    indexNode=indexNode.getChildList().get(indexNode.getChildList().size()-1);
                }
            }
        }else
        {
            root.getChildList().add(new ManyTreeNode(path));
            root.getChildList().get(root.getChildList().size()-1).setParentManyTreeNode(root);
        }
    }
    public String getCurPath()
    {
        return curpath;
    }
    public void  enter(String dir)
    {
        curpath="...";
        curNode=root;
        int flag=0;
        if(dir.contains("/"))
        {
            String[] spit=dir.split("/");
            for(int i=0;i<spit.length;i++)
            {
                int j=0;
                flag=0;
                while(!curNode.getChildList().isEmpty()&&j<curNode.getChildList().size())
                {
                    if(curNode.getChildList().get(j).getFileName().equals(spit[i]))
                    {
                        flag=1;
                        break;
                    }
                    j++;
                }
                if(flag==1)
                {
                    curNode=curNode.getChildList().get(j);
                    curpath=curpath+"/"+spit[i];
                }
                else{
                    System.out.println("没有此路径1");
                }
            }
        }
        else
        {
            int flag1=0;
            int k=0;
            while(!root.getChildList().isEmpty()&&k<root.getChildList().size())
            {
                if(root.getChildList().get(k).getFileName().equals(dir))
                {
                    flag1=1;
                    break;
                }
                k++;
            }
            if(flag1==1)
            {
                curNode=root.getChildList().get(k);
                curpath=curpath+"/"+dir;
            }
            else{
                System.out.println("没有此路径2");
            }
        }
    }
    public void back()
    {
        if(curNode.getParentManyTreeNode()!=null){
            curNode=curNode.getParentManyTreeNode();
            String[] spit=curpath.split("/");
            curpath="...";
            for(int i=1;i<spit.length-1;i++)
            {
                curpath=curpath+"/"+spit[i];
            }
        }else
        {
            curpath="...";
            curNode=root;
            System.out.println("已到根节点");
        }
    }

    public String iteratorTree(ManyTreeNode manyTreeNode)
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\n");

        if(manyTreeNode != null)
        {
            for (ManyTreeNode index : manyTreeNode.getChildList())
            {
                buffer.append(index.getFileName()+ "/");

                if (index.getChildList() != null && index.getChildList().size() > 0 )
                {
                    buffer.append(iteratorTree(index));
                }
            }
        }
        buffer.append("\n");
        return buffer.toString();
    }
    public ManyTreeNode getRoot() {
        return root;
    }
    public ManyTreeNode getCurNode(){
        return curNode;
    }
    public void setCurNode(ManyTreeNode node)
    {
        curNode=node;
    }
    public List<String> getCurFileList()
    {
        List<String>curFileList = new ArrayList<String>();
        for(int i=0;i<curNode.getChildList().size();i++)
        {
            curFileList.add(curNode.getChildList().get(i).getFileName());
        }
        return curFileList;
    }
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        ManyNodeTree manytree=new ManyNodeTree();
//        manytree.addPath("zhongguo/jiangsu/nanjing/jiangning.txt");
//        manytree.addPath("zhongguo/shanxi/jingzhong/yuci.md");
//        manytree.addPath("zhongguo/jiangsu/yangzhou.km");
//        manytree.addPath("jsdkf.c");
//        //System.out.println(manytree.iteratorTree(manytree.getRoot()));
//        //manytree.enter("zhongguo/jiangsu/nanjing/");
//        //System.out.println(manytree.getCurPath());
//        //System.out.println(manytree.getCurNode().getChildList().get(0).filename);
//        //manytree.back();
//        //System.out.println(manytree.getCurPath());
//        //System.out.println(manytree.getCurNode().getChildList().get(0).filename);
//        manytree.enter("zhongguo");
//        for(int i=0;i<manytree.getCurFileList().size();i++)
//        {
//            System.out.println(manytree.getCurFileList().get(i));
//        }
//    }
}

