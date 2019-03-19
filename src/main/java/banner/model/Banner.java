package banner.model;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Max;

public class Banner {

    private Integer id;
    private String imgSrc;
    private Integer width;
    private Integer height;
    private String targetUrl;
    private Integer langId;
    private Integer priority;
    private Boolean activity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getActivity() {
        return activity;
    }

    public void setActivity(Boolean activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", imgSrc='" + imgSrc + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", targetUrl='" + targetUrl + '\'' +
                ", langId=" + langId +
                ", priority=" + priority +
                ", activity=" + activity +
                '}';
    }
}
