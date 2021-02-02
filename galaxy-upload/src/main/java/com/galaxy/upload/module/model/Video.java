package com.galaxy.upload.module.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_video")
public class Video {
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
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 图片名称
     */
    @Column(name = "video_name")
    private String videoName;

    /**
     * 待定
     */
    private String thumbnail;

    /**
     * 类型
     */
    @Column(name = "content_type")
    private String contentType;

    /**
     * 图片大小
     */
    private Long size;

    /**
     * 级别
     */
    private String level;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 视频文件夹名称
     */
    @Column(name = "s3_bucket_name")
    private String s3BucketName;

    /**
     * 视频桶路径
     */
    @Column(name = "s3_key_480")
    private String s3Key480;

    /**
     * 视频桶路径
     */
    @Column(name = "s3_key_1080")
    private String s3Key1080;

    /**
     * 视频桶路径
     */
    @Column(name = "s3_key_2160")
    private String s3Key2160;

    /**
     * 240视频全拼路径
     */
    @Column(name = "object_url_480")
    private String objectUrl480;

    /**
     * 480视频全拼路径
     */
    @Column(name = "object_url_1080")
    private String objectUrl1080;

    /**
     * 1080视频全拼路径
     */
    @Column(name = "object_url_2160")
    private String objectUrl2160;

    /**
     * 视频首页图片
     */
    @Column(name = "frame_images")
    private String frameImages;

    /**
     * 视频首页图片
     */
    @Column(name = "status_name")
    private String statusName;

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
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取图片名称
     *
     * @return video_name - 图片名称
     */
    public String getVideoName() {
        return videoName;
    }

    /**
     * 设置图片名称
     *
     * @param videoName 图片名称
     */
    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    /**
     * 获取待定
     *
     * @return thumbnail - 待定
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * 设置待定
     *
     * @param thumbnail 待定
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * 获取类型
     *
     * @return content_type - 类型
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 设置类型
     *
     * @param contentType 类型
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 获取图片大小
     *
     * @return size - 图片大小
     */
    public Long getSize() {
        return size;
    }

    /**
     * 设置图片大小
     *
     * @param size 图片大小
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * 获取级别
     *
     * @return level - 级别
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置级别
     *
     * @param level 级别
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 获取后缀
     *
     * @return suffix - 后缀
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 设置后缀
     *
     * @param suffix 后缀
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 获取视频文件夹名称
     *
     * @return s3_bucket_name - 视频文件夹名称
     */
    public String getS3BucketName() {
        return s3BucketName;
    }

    /**
     * 设置视频文件夹名称
     *
     * @param s3BucketName 视频文件夹名称
     */
    public void setS3BucketName(String s3BucketName) {
        this.s3BucketName = s3BucketName;
    }

    /**
     * 获取视频桶路径
     *
     * @return s3_key_480 - 视频桶路径
     */
    public String getS3Key480() {
        return s3Key480;
    }

    /**
     * 设置视频桶路径
     *
     * @param s3Key480 视频桶路径
     */
    public void setS3Key480(String s3Key480) {
        this.s3Key480 = s3Key480;
    }

    /**
     * 获取视频桶路径
     *
     * @return s3_key_1080 - 视频桶路径
     */
    public String getS3Key1080() {
        return s3Key1080;
    }

    /**
     * 设置视频桶路径
     *
     * @param s3Key1080 视频桶路径
     */
    public void setS3Key1080(String s3Key1080) {
        this.s3Key1080 = s3Key1080;
    }

    /**
     * 获取视频桶路径
     *
     * @return s3_key_2160 - 视频桶路径
     */
    public String getS3Key2160() {
        return s3Key2160;
    }

    /**
     * 设置视频桶路径
     *
     * @param s3Key2160 视频桶路径
     */
    public void setS3Key2160(String s3Key2160) {
        this.s3Key2160 = s3Key2160;
    }

    /**
     * 获取240视频全拼路径
     *
     * @return object_url_480 - 240视频全拼路径
     */
    public String getObjectUrl480() {
        return objectUrl480;
    }

    /**
     * 设置240视频全拼路径
     *
     * @param objectUrl480 240视频全拼路径
     */
    public void setObjectUrl480(String objectUrl480) {
        this.objectUrl480 = objectUrl480;
    }

    /**
     * 获取480视频全拼路径
     *
     * @return object_url_1080 - 480视频全拼路径
     */
    public String getObjectUrl1080() {
        return objectUrl1080;
    }

    /**
     * 设置480视频全拼路径
     *
     * @param objectUrl1080 480视频全拼路径
     */
    public void setObjectUrl1080(String objectUrl1080) {
        this.objectUrl1080 = objectUrl1080;
    }

    /**
     * 获取1080视频全拼路径
     *
     * @return object_url_2160 - 1080视频全拼路径
     */
    public String getObjectUrl2160() {
        return objectUrl2160;
    }

    /**
     * 设置1080视频全拼路径
     *
     * @param objectUrl2160 1080视频全拼路径
     */
    public void setObjectUrl2160(String objectUrl2160) {
        this.objectUrl2160 = objectUrl2160;
    }

    public String getFrameImages() {
        return frameImages;
    }

    public void setFrameImages(String frameImages) {
        this.frameImages = frameImages;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
