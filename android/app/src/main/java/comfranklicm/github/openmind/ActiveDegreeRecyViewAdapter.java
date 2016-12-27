package comfranklicm.github.openmind;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import comfranklicm.github.openmind.utils.User;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created and Modified by:LeiYuanYuan
 * Modified by:LiChangMao
 * Time:2016/9/6
 */
public class ActiveDegreeRecyViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView
    private Context context;
    private String year, month;
    private String date;
    //上拉加载更多状态-默认为0
    private int load_more_status = 0;

    public ActiveDegreeRecyViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int j = position;
        if (holder instanceof ActiveDegreeViewHolder) {
            date = User.getInstance().ownactives.get(j).getMonth();
            this.year = date.substring(0, 4);
            Log.d("activedate", date);
            Log.d("activeyear", year);
            this.month = date.substring(4);
            Log.d("activemonth", month);
        for (int i = 0; i < User.getInstance().ownactives.get(j).getActiveList().size(); i++) {
            if (Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDegree()) > 0 && Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDegree()) <= 10) {
                //根据活跃度的高度来更改方格的背景颜色
                ((ActiveDegreeViewHolder) holder).day[Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDay()) - 1].setBackgroundColor(Color.parseColor("#1e6823"));
            } else if (Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDegree()) <= 30 && Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDegree()) > 10) {
                ((ActiveDegreeViewHolder) holder).day[Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDay()) - 1].setBackgroundColor(Color.parseColor("#44a340"));
            } else if (Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDegree()) <= 60 && Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDegree()) > 30) {
                //......
                ((ActiveDegreeViewHolder) holder).day[Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDay()) - 1].setBackgroundColor(Color.parseColor("#8cc665"));
            } else if (Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDegree()) <= 100 && Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDegree()) > 60) {
                ((ActiveDegreeViewHolder) holder).day[Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDay()) - 1].setBackgroundColor(Color.parseColor("#d6e685"));
            } else if (Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDegree()) > 100) {
                ((ActiveDegreeViewHolder) holder).day[Integer.parseInt(User.getInstance().ownactives.get(j).getActiveList().get(i).getDay()) - 1].setBackgroundColor(Color.parseColor("#eeeeee"));
            }
        }
        switch (month) {
            case "01":
                ((ActiveDegreeViewHolder) holder).date.setText(year + " Jan.");
                break;
            case "03":
                ((ActiveDegreeViewHolder) holder).date.setText(year + " Mar.");
                break;
            case "05":
                ((ActiveDegreeViewHolder) holder).date.setText(year + " May.");
                break;
            case "07":
                ((ActiveDegreeViewHolder) holder).date.setText(year + " Jul.");
                break;
            case "08":
                ((ActiveDegreeViewHolder) holder).date.setText(year + " Aug.");
                break;
            case "10":
                ((ActiveDegreeViewHolder) holder).date.setText(year + " Oct.");
                break;
            case "12":
                ((ActiveDegreeViewHolder) holder).date.setText(year + " Dec.");
                break;
            case "04":
                ((ActiveDegreeViewHolder) holder).day[30].setVisibility(View.INVISIBLE);
                ((ActiveDegreeViewHolder) holder).date.setText(year + " Apr.");
                break;
            case "06":
                ((ActiveDegreeViewHolder) holder).day[30].setVisibility(View.INVISIBLE);
                ((ActiveDegreeViewHolder) holder).date.setText(year + " Jun.");
                break;
            case "09":
                ((ActiveDegreeViewHolder) holder).day[30].setVisibility(View.INVISIBLE);
                ((ActiveDegreeViewHolder) holder).date.setText(year + " Sep.");
                break;
            case "11":
                ((ActiveDegreeViewHolder) holder).day[30].setVisibility(View.INVISIBLE);
                ((ActiveDegreeViewHolder) holder).date.setText(year + " Nov.");
                break;

            case "02": {
                if (Integer.valueOf(year) % 4 == 0 && Integer.valueOf(year) % 100 != 0) {
                    ((ActiveDegreeViewHolder) holder).day[30].setVisibility(View.INVISIBLE);
                    ((ActiveDegreeViewHolder) holder).day[29].setVisibility(View.INVISIBLE);
                } else if (Integer.valueOf(year) % 400 == 0) {
                    ((ActiveDegreeViewHolder) holder).day[30].setVisibility(View.INVISIBLE);
                    ((ActiveDegreeViewHolder) holder).day[29].setVisibility(View.INVISIBLE);
                } else {
                    ((ActiveDegreeViewHolder) holder).day[30].setVisibility(View.INVISIBLE);
                    ((ActiveDegreeViewHolder) holder).day[29].setVisibility(View.INVISIBLE);
                    ((ActiveDegreeViewHolder) holder).day[28].setVisibility(View.INVISIBLE);
                }
                ((ActiveDegreeViewHolder) holder).date.setText(year + " Feb.");
                break;
            }
            default:
                break;
        }
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
            View v = LayoutInflater.from(context).inflate(R.layout.active_degree, parent, false);
            ActiveDegreeViewHolder viewHolder = new ActiveDegreeViewHolder(v);
            return viewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View foot_view = LayoutInflater.from(context).inflate(R.layout.recycler_load_more_layout, parent, false);
            FootViewHolder footViewHolder = new FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return User.getInstance().ownactives.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
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

    static class ActiveDegreeViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView[] day = new TextView[31];

        public ActiveDegreeViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            day[0] = (TextView) itemView.findViewById(R.id.day1);
            day[1] = (TextView) itemView.findViewById(R.id.day2);
            day[2] = (TextView) itemView.findViewById(R.id.day3);
            day[3] = (TextView) itemView.findViewById(R.id.day4);
            day[4] = (TextView) itemView.findViewById(R.id.day5);
            day[5] = (TextView) itemView.findViewById(R.id.day6);
            day[6] = (TextView) itemView.findViewById(R.id.day7);
            day[7] = (TextView) itemView.findViewById(R.id.day8);
            day[8] = (TextView) itemView.findViewById(R.id.day9);
            day[9] = (TextView) itemView.findViewById(R.id.day10);
            day[10] = (TextView) itemView.findViewById(R.id.day11);
            day[11] = (TextView) itemView.findViewById(R.id.day12);
            day[12] = (TextView) itemView.findViewById(R.id.day13);
            day[13] = (TextView) itemView.findViewById(R.id.day14);
            day[14] = (TextView) itemView.findViewById(R.id.day15);
            day[15] = (TextView) itemView.findViewById(R.id.day16);
            day[16] = (TextView) itemView.findViewById(R.id.day17);
            day[17] = (TextView) itemView.findViewById(R.id.day18);
            day[18] = (TextView) itemView.findViewById(R.id.day19);
            day[19] = (TextView) itemView.findViewById(R.id.day20);
            day[20] = (TextView) itemView.findViewById(R.id.day21);
            day[21] = (TextView) itemView.findViewById(R.id.day22);
            day[22] = (TextView) itemView.findViewById(R.id.day23);
            day[23] = (TextView) itemView.findViewById(R.id.day24);
            day[24] = (TextView) itemView.findViewById(R.id.day25);
            day[25] = (TextView) itemView.findViewById(R.id.day26);
            day[26] = (TextView) itemView.findViewById(R.id.day27);
            day[27] = (TextView) itemView.findViewById(R.id.day28);
            day[28] = (TextView) itemView.findViewById(R.id.day29);
            day[29] = (TextView) itemView.findViewById(R.id.day30);
            day[30] = (TextView) itemView.findViewById(R.id.day31);
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
