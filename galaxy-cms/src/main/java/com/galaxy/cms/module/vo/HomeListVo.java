package com.galaxy.cms.module.vo;

import com.github.pagehelper.PageInfo;

public class HomeListVo<T> {

    private PageInfo blogPageInfo;

    private T imagesPageInfo;

    private T videoPageInfo;

    public PageInfo getBlogPageInfo() {
        return blogPageInfo;
    }

    public void setBlogPageInfo(PageInfo blogPageInfo) {
        this.blogPageInfo = blogPageInfo;
    }

    public T getImagesPageInfo() {
        return imagesPageInfo;
    }

    public void setImagesPageInfo(T imagesPageInfo) {
        this.imagesPageInfo = imagesPageInfo;
    }

    public T getVideoPageInfo() {
        return videoPageInfo;
    }

    public void setVideoPageInfo(T videoPageInfo) {
        this.videoPageInfo = videoPageInfo;
    }
}
