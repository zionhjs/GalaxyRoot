package com.galaxy.cms.module.vo;

import com.github.pagehelper.PageInfo;

public class HomeListVo {

    private PageInfo blogPageInfo;

    private PageInfo imagesPageInfo;

    private PageInfo videoPageInfo;

    public PageInfo getBlogPageInfo() {
        return blogPageInfo;
    }

    public void setBlogPageInfo(PageInfo blogPageInfo) {
        this.blogPageInfo = blogPageInfo;
    }

    public PageInfo getImagesPageInfo() {
        return imagesPageInfo;
    }

    public void setImagesPageInfo(PageInfo imagesPageInfo) {
        this.imagesPageInfo = imagesPageInfo;
    }

    public PageInfo getVideoPageInfo() {
        return videoPageInfo;
    }

    public void setVideoPageInfo(PageInfo videoPageInfo) {
        this.videoPageInfo = videoPageInfo;
    }
}
