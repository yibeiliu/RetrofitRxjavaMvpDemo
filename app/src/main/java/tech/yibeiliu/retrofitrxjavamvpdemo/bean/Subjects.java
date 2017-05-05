package tech.yibeiliu.retrofitrxjavamvpdemo.bean;

import java.util.List;

/**
 * Created by YiBeiLiu on 2017/04/25.
 */

/**
 * 为了我方便简单，从网络上获取的 json 串，只取如下部分来进行显示
 */
public class Subjects {

    private String title;
    private String mainland_pubdate;
    private Images images;
    private List<String> durations;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainland_pubdate() {
        return mainland_pubdate;
    }

    public void setMainland_pubdate(String mainland_pubdate) {
        this.mainland_pubdate = mainland_pubdate;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public List<String> getDurations() {
        return durations;
    }

    public void setDurations(List<String> durations) {
        this.durations = durations;
    }
}
