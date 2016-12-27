package comfranklicm.github.openmind;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comfranklicm.github.openmind.utils.ManyNodeTree;
import comfranklicm.github.openmind.utils.ManyTreeNode;
import comfranklicm.github.openmind.utils.User;


/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/5
 */
public class FileViewFragment extends Fragment{
    View view;
    TextView route;
    TextView backtoprojectdetail;
    ListView fileListView;
    FileListViewAdapter fileListViewAdapter;
    List<Map<String, Object>> fileListItems;
    List<String>filePathList=new ArrayList<String>();
    Integer layerNumber;
    ManyTreeNode manyTreeNode;

    String pictureType = ".png.jpg.webp.gif.bmp.jpeg";
    String textType = ".txt.c.cpp.php.java.asp.net.jsp.js.css.html.cc.m.mm.h.xml.hlp.conf.sh.bat.ini.py.rb.cs";
    String markDown = ".md";
    ManyNodeTree tree=new ManyNodeTree();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.file_view_layout,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        route=(TextView)view.findViewById(R.id.route);

        route.setText("...");
        backtoprojectdetail=(TextView)view.findViewById(R.id.backbtn);
        backtoprojectdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.getInstance().currentChildComments.size() != 0) {
                    User.getInstance().currentChildComments.clear();
                }
                if (User.getInstance().currentParentComments.size() != 0) {
                    User.getInstance().currentParentComments.clear();
                }
                MyActivity activity = (MyActivity) getActivity();
                activity.transactiontoProjectDetail();
            }
        });

        getFilePathTree();
        fileListView=(ListView)view.findViewById(R.id.list_view);
        fileListItems=getListItems();
        fileListViewAdapter=new FileListViewAdapter(this.getContext(),fileListItems);
        fileListView.setAdapter(fileListViewAdapter);
        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        if (tree.getCurNode().getParentManyTreeNode() != null) {
                            //tree.setCurNode(tree.getCurNode().getParentManyTreeNode());
                            tree.back();
                            fileListItems = getListItems();
                            fileListViewAdapter.listItems = getListItems();
                            //fileListView.setAdapter(fileListViewAdapter);
                            fileListViewAdapter.notifyDataSetChanged();
                            Log.d("curpath", tree.getCurPath());
                            route.setText(tree.getCurPath());
                        }
                        break;
                    }
                    default: {
                        if (tree.getCurNode().getChildList().size() != 0) {
                            //tree.setCurNode(tree.getCurNode().getChildList().get(position - 1));
                            if (!tree.getCurNode().getChildList().get(position - 1).getFileName().contains(".")) {
                                tree.enter(tree.getCurPath() + "/" + tree.getCurNode().getChildList().get(position - 1).getFileName());
                                fileListItems = getListItems();
                                fileListViewAdapter.listItems = getListItems();
                                fileListViewAdapter.notifyDataSetChanged();
                                Log.d("curpath", tree.getCurPath());
                                route.setText(tree.getCurPath());
                            } else {
                                String route = null;
                                if (!tree.getCurPath().equals("...")) {
                                    route = tree.getCurPath().substring(4) + "/" + tree.getCurNode().getChildList().get(position - 1).getFileName();
                                } else {
                                    route = tree.getCurNode().getChildList().get(position - 1).getFileName();
                                }
                                Log.d("route", route);
                                String[] s = route.split("\\.");
                                Log.d("s", Arrays.toString(s));
                                String url = null;
                                for (int j = 0; j < User.getInstance().getCurrentProject().getShareList().size(); j++) {
                                    if (User.getInstance().getCurrentProject().getShareList().get(j).getName().contains(route)) {
                                        url = User.getInstance().getCurrentProject().getShareList().get(j).getUrl();
                                        break;
                                    }
                                }
                                User.getInstance().setFileUrl(url);
                                Log.d("routeurl", url);
                                if (pictureType.contains(s[1])) {
                                    Log.d("routetype", "picture");
                                    Intent intent = new Intent(getActivity(), PictureViewActivity.class);
                                    startActivity(intent);
                                } else if (textType.contains(s[1])) {
                                    Log.d("routeurl", "text");
                                    Intent intent = new Intent(getActivity(), TextFileViewActivity.class);
                                    startActivity(intent);
                                } else if (markDown.contains(s[1])) {
                                    Log.d("routeurl", "markdown");
                                    Intent intent = new Intent(getActivity(), MarkdownViewActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    Uri content_url = Uri.parse(User.getInstance().getFileUrl());
                                    intent.setData(content_url);
                                    startActivity(intent);
                                }
                            }
                        }
                        break;
                    }
                }
            }
        });

        return view;
    }
    public void getFilePathTree()
    {
        for(int i=0;i<User.getInstance().getCurrentProject().getShareList().size();i++) {
            tree.addPath(User.getInstance().getCurrentProject().getShareList().get(i).getName());
        }
    }
   private  List<Map<String, Object>> getListItems()
   {
       List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
       Map<String, Object> rootmap = new HashMap<String, Object>();
       rootmap.put("filetype",0);
       rootmap.put("filename","...");
       listItems.add(rootmap);
       for(int i=0;i<tree.getCurNode().getChildList().size();i++)
       {
           Map<String, Object> map = new HashMap<String, Object>();
           Integer type=0;
           if(!tree.getCurNode().getChildList().get(i).getFileName().contains("."))
           {
               type=0;
           }else {
               type=1;
               for(int j=0;j<User.getInstance().getCurrentProject().getShareList().size();j++)
               {
                   if (User.getInstance().getCurrentProject().getShareList().get(j).getName().contains(tree.getCurNode().getChildList().get(i).getFileName()))
                   {
                       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                       String time = sdf.format(new Date(Long.parseLong(User.getInstance().getCurrentProject().getShareList().get(j).getTime())*1000));
                       map.put("filedate",time);
                       break;
                   }
               }
           }
           map.put("filetype",type);
           map.put("filename", tree.getCurNode().getChildList().get(i).getFileName());
           //Log.d("filename",manyTreeNode.getChildList().get(i).getData().getNodeId());
           listItems.add(map);
       }
       return listItems;
   }
}
