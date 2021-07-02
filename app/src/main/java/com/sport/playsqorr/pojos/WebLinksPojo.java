package com.sport.playsqorr.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kalyan on 22-07-2019.
 */

public class WebLinksPojo {

    @SerializedName("pageName")
    @Expose
    private String pageName;
    @SerializedName("pageUrl")
    @Expose
    private String pageUrl;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }
}
