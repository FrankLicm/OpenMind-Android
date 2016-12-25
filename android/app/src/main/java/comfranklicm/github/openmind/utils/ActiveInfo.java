package comfranklicm.github.openmind.utils;

import java.util.List;
/**
 * Created and Modified by:LiChangMao
 * Time:2016/8/27
 */
/**
 * 用户活跃信息封装，用户活跃信息单例
 */
public class ActiveInfo {
    private String username;
    private String year;
    private String month;
    private String active;
    private List<Active>activeList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public List<Active> getActiveList() {
        return activeList;
    }

    public void setActiveList(List<Active> activeList) {
        this.activeList = activeList;
    }
}

