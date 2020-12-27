package com.galaxy.cms.module.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_moment_like")
public class MomentLike {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否删除(0false未删除 1true已删除)
     */
    @Column(name = "is_delete")
    private Boolean isDelete;

    /**
     * 点赞主体id（博客id或者评论id）
     */
    @Column(name = "subject_id")
    private Long subjectId;

    /**
     * 点赞类型 1-博客 2-评论
     */
    private Integer type;

    /**
     * 业务状态：
     */
    private Integer status;

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
     * 获取创建人
     *
     * @return create_by - 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
     * 获取点赞主体id（博客id或者评论id）
     *
     * @return subject_id - 点赞主体id（博客id或者评论id）
     */
    public Long getSubjectId() {
        return subjectId;
    }

    /**
     * 设置点赞主体id（博客id或者评论id）
     *
     * @param subjectId 点赞主体id（博客id或者评论id）
     */
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取业务状态：
     *
     * @return status - 业务状态：
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置业务状态：
     *
     * @param status 业务状态：
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}