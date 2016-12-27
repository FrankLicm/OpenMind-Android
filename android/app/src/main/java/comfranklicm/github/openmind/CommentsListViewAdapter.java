package comfranklicm.github.openmind;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created and Modified by:LeiYuanYuan
 * Modified by:LiChangMao
 * Time:2016/9/1
 */
public class CommentsListViewAdapter extends BaseAdapter{
    public List<Map<String, Object>> listItems;    //商品信息集合
    private Context context;                        //运行上下文
    private LayoutInflater listContainer;           //视图容器
    public CommentsListViewAdapter(Context context, List<Map<String, Object>> listItems) {
        this.context = context;
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文
        this.listItems = listItems;
        //hasChecked = new boolean[getCount()];
    }

    public int getCount() {
        return listItems.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    /**
     * ListView Item设置
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Log.e("method", "getView");
        //final int selectID = position;
        //自定义视图
        CommentsListView  commentsListView = null;
        if (convertView == null) {
            commentsListView = new CommentsListView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.comments_list_view, null);
            //获取控件对象
            //commentsListView.linearLayout1=(RelativeLayout)convertView.findViewById(R.id.RelativeLayout1);//改变这个的高度来适配不同长度的评论
            commentsListView.head_image_view= (SimpleDraweeView)convertView.findViewById(R.id.head_image_view);
            commentsListView.comment_floor=(TextView)convertView.findViewById(R.id.comment_floor);
            commentsListView.user_name=(TextView)convertView.findViewById(R.id.user_name);
            commentsListView.comment_date=(TextView)convertView.findViewById(R.id.comment_date);
            commentsListView.comment_content=(TextView)convertView.findViewById(R.id.comment_content);
            commentsListView.go_to_detail_btn=(TextView)convertView.findViewById(R.id.fa_angle_double_right);
            commentsListView.go_to_detail_btn.setTypeface(FontManager.getTypeface(context,FontManager.FONTAWESOME));
            commentsListView.comment_icon=(TextView)convertView.findViewById(R.id.fa_commenting_o);
            commentsListView.comment_icon.setTypeface(FontManager.getTypeface(context,FontManager.FONTAWESOME));
            commentsListView.comment_num=(TextView)convertView.findViewById(R.id.comments_num);
            commentsListView.line = convertView.findViewById(R.id.view);
            //设置控件集到convertView
            convertView.setTag(commentsListView);
        }else {
            commentsListView = (CommentsListView)convertView.getTag();
        }
//      Log.e("image", (String) listItems.get(position).get("title"));  //测试
//      Log.e("image", (String) listItems.get(position).get("info"));

        //设置文字和图片
        commentsListView.head_image_view.setImageURI((Uri) listItems.get(position).get("head_image_view"));
        commentsListView.comment_floor.setText("" + listItems.get(position).get("comment_floor"));
        commentsListView.user_name.setText((String) listItems.get(position).get("user_name"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (!listItems.get(position).get("comment_date").equals("暂无评论")) {
            String time = sdf.format(new Date(Long.parseLong((String) listItems.get(position).get("comment_date")) * 1000));
            commentsListView.comment_date.setText(time);
        }else {
            commentsListView.comment_date.setText((String) listItems.get(position).get("comment_date"));
        }
        commentsListView.comment_content.setText((String)listItems.get(position).get("comment_content"));
        commentsListView.comment_num.setText(""+listItems.get(position).get("comment_num"));
        return convertView;
    }

//    /**
//     * 记录勾选了哪个物品
//     * @param checkedID 选中的物品序号
//     */
//    private void checkedChange(int checkedID) {
//        hasChecked[checkedID] = !hasChecked[checkedID];
//    }
//
//    /**
//     * 判断物品是否选择
//     * @param checkedID 物品序号
//     * @return 返回是否选中状态
//     */
//    public boolean hasChecked(int checkedID) {
//        return hasChecked[checkedID];
//    }
//
//    /**
//     * 显示物品详情
//     * @param clickID
//     */
//    private void showDetailInfo(int clickID) {
//        new AlertDialog.Builder(context)
//                .setTitle("物品详情：" + listItems.get(clickID).get("info"))
//                .setMessage(listItems.get(clickID).get("detail").toString())
//                .setPositiveButton("确定", null)
//                .show();
//    }
//

    //private boolean[] hasChecked;                   //记录商品选中状态
    final class CommentsListView {                //自定义控件集合
        public LinearLayout linearLayout1;
        public ImageView head_image_view;
        public TextView comment_floor;
        public TextView user_name;
        public TextView comment_date;
        public TextView comment_content;
        public TextView go_to_detail_btn;//右边的箭头，点击进入更多详情
        public TextView comment_icon;
        public TextView comment_num;
        public View line;
    }
}
