package tech.yibeiliu.retrofitrxjavamvpdemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by YiBeiLiu on 2017/04/25.
 */

public class Movies implements Serializable {
    private int count;//返回的数量
    private int start;//开始的位置
    private int total;//服务器条目总数
    private List<Subjects> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
    }
}
