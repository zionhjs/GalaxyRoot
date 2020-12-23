package com.galaxy.cms.module.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_blog")
public class Blog {
    /**
     * 记录ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 修改人
     */
    @Column(name = "updated_by")
    private String updatedBy;

    /**
     * 是否删除(0false未删除 1true已删除)
     */
    @Column(name = "is_delete")
    private Boolean isDelete;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 业务状态(1启用:2封禁)
     */
    private Integer status;

    /**
     * 文字内容
     */
    private String content;

    /**
     * 点赞数
     */
    @Column(name = "like_num")
    private Integer likeNum;

    /**
     * 图片地址，多个逗号分隔
     */
    @Column(name = "image_urls")
    private String imageUrls;

    /**
     * 获取记录ID
     *
     * @return id - 记录ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置记录ID
     *
     * @param id 记录ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取创建人
     *
     * @return created_by - 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置创建人
     *
     * @param createdBy 创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取更新时间
     *
     * @return updated_at - 更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置更新时间
     *
     * @param updatedAt 更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取修改人
     *
     * @return updated_by - 修改人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 设置修改人
     *
     * @param updatedBy 修改人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * 获取是否删除(0false未删除 1true已删除)
     *
     * @return is_delete - 是否删除(0false未删除 1true已删除)
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除(0false未删除 1true已删除)
     *
     * @param isDelete 是否删除(0false未删除 1true已删除)
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取版本号
     *
     * @return version - 版本号
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置版本号
     *
     * @param version 版本号
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取业务状态(1启用:2封禁)
     *
     * @return status - 业务状态(1启用:2封禁)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置业务状态(1启用:2封禁)
     *
     * @param status 业务状态(1启用:2封禁)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取文字内容
     *
     * @return content - 文字内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置文字内容
     *
     * @param content 文字内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取点赞数
     *
     * @return like_num - 点赞数
     */
    public Integer getLikeNum() {
        return likeNum;
    }

    /**
     * 设置点赞数
     *
     * @param likeNum 点赞数
     */
    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    /**
     * 获取图片地址，多个逗号分隔
     *
     * @return image_urls - 图片地址，多个逗号分隔
     */
    public String getImageUrls() {
        return imageUrls;
    }

    /**
     * 设置图片地址，多个逗号分隔
     *
     * @param imageUrls 图片地址，多个逗号分隔
     */
    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }
}