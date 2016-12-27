package comfranklicm.github.openmind;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comfranklicm.github.openmind.Httprequests.HttpPostRunnable;
import comfranklicm.github.openmind.JsonParsing.CommentJsonParser;
import comfranklicm.github.openmind.utils.Comment;
import comfranklicm.github.openmind.utils.User;

/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/4
 */
public class ChildCommentListFragment extends Fragment{
    View view;
    TextView parentname;
    TextView backbtn;
    TextView parentdate;
    TextView parentcontent;
    TextView childNum;
    SimpleDraweeView parenthead;
    Button submitComment;
    EditText disInputText ;
    ListView childCommentListView;
    ChildCommentListViewAdapter commentsListViewAdapter;
    List<Map<String, Object>> commentsListItems;
    RelativeLayout relativeLayout,r1_bottom;
    List<Comment> currentChildComment = new ArrayList<Comment>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.child_comment_layout,container,false);
        parentname=(TextView)view.findViewById(R.id.user_name);
        parentdate=(TextView)view.findViewById(R.id.comment_date);
        parentcontent=(TextView)view.findViewById(R.id.comment_content);
        parenthead=(SimpleDraweeView)view.findViewById(R.id.head_image_view);
        childCommentListView=(ListView)view.findViewById(R.id.child_listview);
        backbtn=(TextView)view.findViewById(R.id.backbtn);
        childNum=(TextView)view.findViewById(R.id.childnum);
        relativeLayout=(RelativeLayout)view.findViewById(R.id.RelativeLayout1);
        r1_bottom=(RelativeLayout)view.findViewById(R.id.rl_bottom);
        r1_bottom.setVisibility(View.GONE);
        disInputText=(EditText)view.findViewById(R.id.group_discuss);
        submitComment=(Button)view.findViewById(R.id.group_discuss_submit);
        parentname.setText(User.getInstance().getCurrentParentComment().getSendName());
        parentcontent.setText(User.getInstance().getCurrentParentComment().getContent());
        if (!User.getInstance().getCurrentParentComment().getSendHead().equals("0")) {
            Uri uri = Uri.parse(User.getInstance().getCurrentParentComment().getSendHead());
            parenthead.setImageURI(uri);
        }
        childNum.setText("相关回复 共" + User.getInstance().getCurrentParentComment().childCommentCount + "条");
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.getInstance().currentChildComments.clear();
                User.getInstance().currentParentComments.clear();
                MyActivity activity = (MyActivity) getActivity();
                activity.transactiontoProjectDetail();
            }
        });
        commentsListItems=getListItems();
        commentsListViewAdapter=new ChildCommentListViewAdapter(this.getContext(),commentsListItems);
        childCommentListView.setAdapter(commentsListViewAdapter);
        setListViewHeightBasedOnChildren(childCommentListView);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1_bottom.setVisibility(View.VISIBLE);
                disInputText.setHint("回复 " + User.getInstance().getCurrentParentComment().getSendName());
                submitComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!disInputText.getText().toString().equals("")) {
                            HttpPostRunnable r = new HttpPostRunnable();
                            r.setActionId(12);
                            r.setProjectId(User.getInstance().getCurrentProject().getProjectId());
                            r.setProjectName(User.getInstance().getCurrentProject().getProjectName());
                            r.setProjectOwnerUser(User.getInstance().getCurrentProject().getOwnUser());
                            r.setProjectOwnerName(User.getInstance().getCurrentProject().getOwnName());
                            r.setReceiveuser(User.getInstance().getCurrentParentComment().getSendUser());
                            r.setReceivename(User.getInstance().getCurrentParentComment().getSendName());
                            r.setParentId(User.getInstance().getCurrentParentComment().getCommentId());
                            r.setContent(disInputText.getText().toString());
                            Thread t = new Thread(r);
                            t.start();
                            try {
                                t.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ((CommentJsonParser) User.getInstance().baseJsonParsers.get(11)).CommentJsonParsing(r.getStrResult());
                            if (User.getInstance().getCommentResult().equals("true")) {
                                Map<String, Object> map = new HashMap<String, Object>();
                                if (!User.getInstance().getCommentadded().getSendHead().equals("0")) {
                                    Uri imgUri = Uri.parse((User.getInstance().getCommentadded().getSendHead()));
                                    map.put("head_image_view", imgUri);
                                } else {
                                    Uri uri = Uri.parse("file:///android_asset/image/head.jpg");
                                    map.put("head_image_view", uri);
                                }
                                map.put("user_name", User.getInstance().getCommentadded().getSendName() + " 回复 " + User.getInstance().getCommentadded().getReceiveName());
                                map.put("comment_floor", "");
                                map.put("comment_date", User.getInstance().getCommentadded().getTime());
                                map.put("comment_content", User.getInstance().getCommentadded().getContent());
                                map.put("comment_num", "");
                                commentsListViewAdapter.listItems.add(map);
                                childCommentListView.setAdapter(commentsListViewAdapter);
                                commentsListViewAdapter.notifyDataSetChanged();
                                setListViewHeightBasedOnChildren(childCommentListView);
                                r1_bottom.setVisibility(View.GONE);
                                User.getInstance().currentChildComments.add(User.getInstance().getCommentadded());
                                currentChildComment.add(User.getInstance().getCommentadded());
                                User.getInstance().getCurrentParentComment().childCommentCount++;
                                childNum.setText("相关回复 共" + User.getInstance().getCurrentParentComment().childCommentCount + "条");
                                disInputText.setText("");
                            } else {
                                Toast.makeText(getActivity(), "评论失败:" + User.getInstance().getCommentError(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });
        childCommentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                r1_bottom.setVisibility(View.VISIBLE);
                disInputText.setHint("回复 " + currentChildComment.get(position).getSendName());
                submitComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!disInputText.getText().toString().equals("")) {
                            HttpPostRunnable r = new HttpPostRunnable();
                            r.setActionId(12);
                            r.setProjectId(User.getInstance().getCurrentProject().getProjectId());
                            r.setProjectName(User.getInstance().getCurrentProject().getProjectName());
                            r.setProjectOwnerUser(User.getInstance().getCurrentProject().getOwnUser());
                            r.setProjectOwnerName(User.getInstance().getCurrentProject().getOwnName());
                            r.setReceiveuser(currentChildComment.get(position).getSendUser());
                            r.setReceivename(currentChildComment.get(position).getSendName());
                            r.setParentId(currentChildComment.get(position).getParentId());
                            r.setContent(disInputText.getText().toString());
                            Thread t = new Thread(r);
                            t.start();
                            try {
                                t.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ((CommentJsonParser) User.getInstance().baseJsonParsers.get(11)).CommentJsonParsing(r.getStrResult());
                            if (User.getInstance().getCommentResult().equals("true")) {
                                Map<String, Object> map = new HashMap<String, Object>();
                                if (!User.getInstance().getCommentadded().getSendHead().equals("0")) {
                                    Uri imgUri = Uri.parse((User.getInstance().getCommentadded().getSendHead()));
                                    map.put("head_image_view", imgUri);
                                    Log.d("url1", imgUri.toString());
                                } else {
                                    Uri uri = Uri.parse("file:///android_asset/image/head.jpg");
                                    map.put("head_image_view", uri);
                                    Log.d("url2", uri.toString());
                                }
                                map.put("user_name", User.getInstance().getCommentadded().getSendName() + " 回复 " + User.getInstance().getCommentadded().getReceiveName());
                                map.put("comment_floor", "");
                                map.put("comment_date", User.getInstance().getCommentadded().getTime());
                                map.put("comment_content", User.getInstance().getCommentadded().getContent());
                                map.put("comment_num", "");
                                commentsListViewAdapter.listItems.add(map);
                                childCommentListView.setAdapter(commentsListViewAdapter);
                                commentsListViewAdapter.notifyDataSetChanged();
                                setListViewHeightBasedOnChildren(childCommentListView);
                                r1_bottom.setVisibility(View.GONE);
                                User.getInstance().currentChildComments.add(User.getInstance().getCommentadded());
                                currentChildComment.add(User.getInstance().getCommentadded());
                                User.getInstance().getCurrentParentComment().childCommentCount++;
                                childNum.setText("相关回复 共" + User.getInstance().getCurrentParentComment().childCommentCount + "条");
                                disInputText.setText("");
                            } else {
                                Toast.makeText(getActivity(), "评论失败:" + User.getInstance().getCommentError(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });
        return view;
    }
    private List<Map<String, Object>> getListItems() {
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        if(User.getInstance().currentChildComments.size()>0)
        {
            for(int i = 0; i <User.getInstance().currentChildComments.size(); i++) {
                if(User.getInstance().currentChildComments.get(i).getParentId().equals(User.getInstance().getCurrentParentComment().getCommentId())){
                Map<String, Object> map = new HashMap<String, Object>();
                if(!User.getInstance().currentChildComments.get(i).getSendHead().equals("0")) {
                    Uri imgUri=Uri.parse((User.getInstance().currentChildComments.get(i).getSendHead()));
                    map.put("head_image_view",imgUri);
                }else {
                    Uri uri=Uri.parse("file:///android_asset/image/head.jpg");
                    map.put("head_image_view",uri);
                }
                    map.put("user_name", User.getInstance().currentChildComments.get(i).getSendName() + " 回复 " + User.getInstance().currentChildComments.get(i).getReceiveName());
                map.put("comment_floor","");
                map.put("comment_date", User.getInstance().currentChildComments.get(i).getTime());
                map.put("comment_content", User.getInstance().currentChildComments.get(i).getContent());
                map.put("comment_num", "");
                listItems.add(map);
                    currentChildComment.add(User.getInstance().currentChildComments.get(i));
            }
            }
        }
        return listItems;
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {

        // 获取ListView对应的Adapter

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {

            return;

        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目

            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0); // 计算子项View 的宽高

            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        // listView.getDividerHeight()获取子项间分隔符占用的高度

        // params.height最后得到整个ListView完整显示需要的高度

        listView.setLayoutParams(params);

    }
}
