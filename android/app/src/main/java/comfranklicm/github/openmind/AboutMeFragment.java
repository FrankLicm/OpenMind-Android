package comfranklicm.github.openmind;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
public class AboutMeFragment extends Fragment {
    private static int num = User.getInstance().aboutMeList.size();
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AboutMeRecyViewAdapter adapter;
    private int lastVisibleItem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.about_me_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final Context context = getContext();
        adapter = new AboutMeRecyViewAdapter(getActivity());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        if (User.getInstance().aboutMeList.size() > 0) {
            User.getInstance().aboutMeList.clear();
        }
        num = 0;
        if (NetUtil.isNetworkConnectionActive(getActivity())) {
            HttpPostRunnable runnable = new HttpPostRunnable();
            runnable.setActionId(13);
            runnable.setPageSize("5");
            runnable.setTime_max("" + System.currentTimeMillis() / 1000L);
            Thread thread = new Thread(runnable);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            JsonParser.ParseJson(13, runnable.getStrResult());
            num = num + 5;
            adapter.changeMoreStatus(ProjectListRecyViewAdapter.PULLUP_LOAD_MORE);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "网络连接失败，请检查网络", Toast.LENGTH_LONG).show();
        }

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (adapter.getItemCount() != 0 && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    adapter.changeMoreStatus(ProjectListRecyViewAdapter.LOADING_MORE);
                    adapter.notifyDataSetChanged();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (NetUtil.isNetworkConnectionActive(getActivity())) {
                                HttpPostRunnable runnable = new HttpPostRunnable();
                                runnable.setActionId(13);
                                runnable.setPageSize("5");
                                runnable.setTime_max(User.getInstance().getAboutMeMiniTime());
                                Thread thread = new Thread(runnable);
                                thread.start();
                                try {
                                    thread.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                JsonParser.ParseJson(13, runnable.getStrResult());
                                Log.d("return4", "" + adapter.getItemCount());
                                num = num + User.getInstance().getReturnCount();
                                Log.d("return2", "" + User.getInstance().getReturnCount());
                                Log.d("return3", "" + num);
                            } else {
                                Toast.makeText(getActivity(), "网络连接失败，请检查网络", Toast.LENGTH_LONG).show();
                            }
                            if (User.getInstance().getAboutMeReturnSize() >= 5) {
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
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.demo_swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        User.getInstance().aboutMeList.clear();
                        num = 0;
                        if (NetUtil.isNetworkConnectionActive(getActivity())) {
                            HttpPostRunnable runnable = new HttpPostRunnable();
                            runnable.setActionId(13);
                            runnable.setPageSize("5");
                            runnable.setTime_max("" + System.currentTimeMillis() / 1000L);
                            Thread thread = new Thread(runnable);
                            thread.start();
                            try {
                                thread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            JsonParser.ParseJson(13, runnable.getStrResult());
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
