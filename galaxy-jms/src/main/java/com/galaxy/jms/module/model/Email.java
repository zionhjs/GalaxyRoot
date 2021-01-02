package com.galaxy.jms.module.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_email")
public class Email {
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
     * 业务状态
     */
    private Integer status;

    /**
     * 接收者邮箱
     */
    @Column(name = "user_email")
    private String userEmail;

    /**
     * 管理员接收内容
     */
    @Column(name = "message_inside")
    private String messageInside;

    /**
     * 客户接收内容
     */
    @Column(name = "message_outside")
    private String messageOutside;

    /**
     * 结果集
     */
    private String result;

    /**
     * 随机url
     */
    @Column(name = "generate_url")
    private String generateUrl;

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
     * 获取业务状态
     *
     * @return status - 业务状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置业务状态
     *
     * @param status 业务状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取接收者邮箱
     *
     * @return user_email - 接收者邮箱
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * 设置接收者邮箱
     *
     * @param userEmail 接收者邮箱
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * 获取管理员接收内容
     *
     * @return message_inside - 管理员接收内容
     */
    public String getMessageInside() {
        return messageInside;
    }

    /**
     * 设置管理员接收内容
     *
     * @param messageInside 管理员接收内容
     */
    public void setMessageInside(String messageInside) {
        this.messageInside = messageInside;
    }

    /**
     * 获取客户接收内容
     *
     * @return message_outside - 客户接收内容
     */
    public String getMessageOutside() {
        return messageOutside;
    }

    /**
     * 设置客户接收内容
     *
     * @param messageOutside 客户接收内容
     */
    public void setMessageOutside(String messageOutside) {
        this.messageOutside = messageOutside;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGenerateUrl() {
        return generateUrl;
    }

    public void setGenerateUrl(String generateUrl) {
        this.generateUrl = generateUrl;
    }
}