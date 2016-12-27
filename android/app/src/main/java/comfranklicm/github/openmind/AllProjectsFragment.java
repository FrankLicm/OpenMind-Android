package comfranklicm.github.openmind;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import comfranklicm.github.openmind.Httprequests.HttpPostRunnable;
import comfranklicm.github.openmind.JsonParsing.JsonParser;
import comfranklicm.github.openmind.utils.NetUtil;
import comfranklicm.github.openmind.utils.User;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/9/6
 */
public class AllProjectsFragment extends Fragment {
    private static int num = User.getInstance().allinfos.size();
    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProjectListRecyViewAdapter adapter;
    private int lastVisibleItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fg4, container, false);
        User.getInstance().index = 0;
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        final Context context=getContext();
        adapter=new ProjectListRecyViewAdapter(context,0);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("return0", "" + num);
                Log.d("return1",""+User.getInstance().getReturnCount());
                Log.d("returnlastvisible",""+lastVisibleItem);
                Log.d("returnadapter",""+adapter.getItemCount());
                if (adapter.getItemCount() != 0 && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    adapter.changeMoreStatus(ProjectListRecyViewAdapter.LOADING_MORE);
                    adapter.notifyDataSetChanged();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (NetUtil.isNetworkConnectionActive(getActivity())) {
                                HttpPostRunnable runnable = new HttpPostRunnable();
                                runnable.setActionId(7);
                                runnable.setPageSize("5");
                                runnable.setTime_max(User.getInstance().getMinimumTime());
                                Thread thread = new Thread(runnable);
                                thread.start();
                                try {
                                    thread.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                JsonParser.ParseJson(7, runnable.getStrResult());
                                Log.d("return4", "" + adapter.getItemCount());
                                num = num + User.getInstance().getReturnCount();
                                Log.d("return2", "" + User.getInstance().getReturnCount());
                                Log.d("return3", "" + num);
                            } else {
                                Toast.makeText(getActivity(), "网络连接失败，请检查网络", Toast.LENGTH_LONG).show();
                            }
                            if (User.getInstance().getReturnCount() >= 5) {
                                adapter.changeMoreStatus(ProjectListRecyViewAdapter.PULLUP_LOAD_MORE);
                            } else {
                                adapter.changeMoreStatus(2);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }, 1000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.demo_swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        User.getInstance().allinfos.clear();
                        num = 0;
                        if (NetUtil.isNetworkConnectionActive(getActivity())) {
                            HttpPostRunnable runnable = new HttpPostRunnable();
                            runnable.setActionId(7);
                            runnable.setPageSize("5");
                            runnable.setTime_max("" + System.currentTimeMillis() / 1000L);
                            Thread thread = new Thread(runnable);
                            thread.start();
                            try {
                                thread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            JsonParser.ParseJson(7, runnable.getStrResult());
                            num = num + 5;
                            adapter.changeMoreStatus(ProjectListRecyViewAdapter.PULLUP_LOAD_MORE);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "网络连接失败，请检查网络", Toast.LENGTH_LONG).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        return view;
    }
}
