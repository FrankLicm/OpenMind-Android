package comfranklicm.github.openmind;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

import comfranklicm.github.openmind.Httprequests.HttpPostRunnable;
import comfranklicm.github.openmind.JsonParsing.ViewProjectDetailJsonParser;
import comfranklicm.github.openmind.utils.User;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created and Modified by:LeiYuanYuan
 * Modified by:LiChangMao
 * Time:2016/9/8
 */
public class AboutMeRecyViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView
    Context context;
    //上拉加载更多状态-默认为0
    private int load_more_status = 0;

    public AboutMeRecyViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int j = position;
        if (holder instanceof AboutMeViewHolder) {
            ((AboutMeViewHolder) holder).head.setImageURI(User.getInstance().aboutMeList.get(j).getWhoHead());
            ((AboutMeViewHolder) holder).statement.setText(User.getInstance().aboutMeList.get(j).getWhoName() + " 在 ");
            ((AboutMeViewHolder) holder).project_name.setText(User.getInstance().aboutMeList.get(j).getProjectName());
            ((AboutMeViewHolder) holder).comment_content.setText(User.getInstance().aboutMeList.get(j).getContent());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String time = sdf.format(new Date(Long.parseLong(User.getInstance().aboutMeList.get(j).getTime()) * 1000));
            ((AboutMeViewHolder) holder).date.setText(time);//时间的处理？？？？
//            ((AboutMeViewHolder) holder).reply.setOnClickListener(new View.OnClickListener() {//回复后弹出下拉框
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
            ((AboutMeViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {//整个卡片的点击事件 进入项目中
                @Override
                public void onClick(View v) {
                    HttpPostRunnable r = new HttpPostRunnable();
                    r.setActionId(9);
                    r.setProjectId(User.getInstance().aboutMeList.get(j).getProjectId());
                    Thread t = new Thread(r);
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ((ViewProjectDetailJsonParser) User.getInstance().baseJsonParsers.get(8)).ViewProjectDetailJsonParsing(r.getStrResult());
                    MyActivity activity = User.getInstance().getMyActivity();
                    activity.transactiontoProjectDetail();
                }
            });
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    //footViewHolder.progressBar.setVisibility(View.VISIBLE);
                    footViewHolder.progressBar.setVisibility(View.GONE);
                    footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.progressBar.setVisibility(View.VISIBLE);
                    footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                    break;
                default:
                    footViewHolder.foot_view_item_tv.setText("已经没有更多数据了");
                    footViewHolder.progressBar.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(context).inflate(R.layout.about_me_item_layout, parent, false);
            AboutMeViewHolder viewHolder = new AboutMeViewHolder(v);
            return viewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View foot_view = LayoutInflater.from(context).inflate(R.layout.recycler_load_more_layout, parent, false);
            FootViewHolder footViewHolder = new FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return User.getInstance().aboutMeList.size() + 1;
    }

    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     *
     * @param status 加载状态
     */
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    static class AboutMeViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        SimpleDraweeView head;//头像
        TextView statement;//描述是谁在哪个项目中对你进行了回复
        TextView project_name;
        TextView comment_content;//评论数
        TextView reply;//"回复"的按钮
        TextView date;//日期

        public AboutMeViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.aboutme_cardview);//整个卡片
            head = (SimpleDraweeView) itemView.findViewById(R.id.aboutme_head_image_view);
            statement = (TextView) itemView.findViewById(R.id.aboutme_statement);
            project_name = (TextView) itemView.findViewById(R.id.aboutme_projname);
            comment_content = (TextView) itemView.findViewById(R.id.aboutme_comment_content);
            //reply=(TextView)itemView.findViewById(R.id.aboutme_reply);
            date = (TextView) itemView.findViewById(R.id.aboutme_comment_date);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView foot_view_item_tv;
        private PhotoDraweeView progressBar;

        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv = (TextView) view.findViewById(R.id.foot_view_item_tv);
            progressBar = (PhotoDraweeView) view.findViewById(R.id.progressBar);
            Uri uri=Uri.parse("asset:///image/loading.gif");
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setUri(uri);
            controller.setOldController(progressBar.getController());
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null || progressBar == null) {
                        return;
                    }
                    progressBar.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            });
            controller.setAutoPlayAnimations(true);
            progressBar.setController(controller.build());
        }
    }
}
