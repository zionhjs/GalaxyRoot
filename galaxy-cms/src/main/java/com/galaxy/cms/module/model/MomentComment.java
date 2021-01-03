package com.galaxy.cms.module.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_moment_comment")
public class MomentComment {
    /**
     * 自增id
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
     * 业务状态：
     */
    private Boolean status;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 关联comment id
     */
    @Column(name = "moment_id")
    private Long momentId;

    /**
     * 父级评论ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 回复详情
     */
    private String comment;

    /**
     * 点赞数量
     */
    @Column(name = "like_num")
    private Integer likeNum;

    /**
     * 图片
     */
    @Column(name = "image_urls")
    private String imageUrls;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 获取自增id
     *
     * @return id - 自增id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置自增id
     *
     * @param id 自增id
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
     * 获取业务状态：
     *
     * @return status - 业务状态：
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置业务状态：
     *
     * @param status 业务状态：
     */
    public void setStatus(Boolean status) {
        this.status = status;
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
     * 获取关联comment id
     *
     * @return moment_id - 关联comment id
     */
    public Long getMomentId() {
        return momentId;
    }

    /**
     * 设置关联comment id
     *
     * @param momentId 关联comment id
     */
    public void setMomentId(Long momentId) {
        this.momentId = momentId;
    }

    /**
     * 获取父级评论ID
     *
     * @return parent_id - 父级评论ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父级评论ID
     *
     * @param parentId 父级评论ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取回复详情
     *
     * @return comment - 回复详情
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置回复详情
     *
     * @param comment 回复详情
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取点赞数量
     *
     * @return like_num - 点赞数量
     */
    public Integer getLikeNum() {
        return likeNum;
    }

    /**
     * 设置点赞数量
     *
     * @param likeNum 点赞数量
     */
    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    /**
     * 获取图片
     *
     * @return image_urls - 图片
     */
    public String getImageUrls() {
        return imageUrls;
    }

    /**
     * 设置图片
     *
     * @param imageUrls 图片
     */
    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}