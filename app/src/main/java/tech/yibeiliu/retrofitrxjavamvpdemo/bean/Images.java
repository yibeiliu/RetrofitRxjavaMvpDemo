package tech.yibeiliu.retrofitrxjavamvpdemo.bean;

/**
 * Created by YiBeiLiu on 2017/04/25.
 */

public class Images {
    /**
     * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p2448676053.webp
     * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2448676053.webp
     * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p2448676053.webp
     */
    private String small;
    private String large;
    private String medium;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}
