package com.galaxy.jms.module.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_short_conn_result")
public class ShortConnResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_at")
    private Date createAt;

    /**
     * 是否删除(0 false 未删除,1 true 已删除)
     */
    @Column(name = "is_delete")
    private Boolean isDelete;

    /**
     * 处理结果：‘0’代表成功，‘1’代表失败
     */
    private String code;

    /**
     * 生成的短网址，如果生成失败，则返回原链接
     */
    private String url;

    /**
     * 异常描述
     */
    private String err;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return create_at
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * @param createAt
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取是否删除(0 false 未删除,1 true 已删除)
     *
     * @return is_delete - 是否删除(0 false 未删除,1 true 已删除)
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除(0 false 未删除,1 true 已删除)
     *
     * @param isDelete 是否删除(0 false 未删除,1 true 已删除)
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取处理结果：‘0’代表成功，‘1’代表失败
     *
     * @return code - 处理结果：‘0’代表成功，‘1’代表失败
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置处理结果：‘0’代表成功，‘1’代表失败
     *
     * @param code 处理结果：‘0’代表成功，‘1’代表失败
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取生成的短网址，如果生成失败，则返回原链接
     *
     * @return url - 生成的短网址，如果生成失败，则返回原链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置生成的短网址，如果生成失败，则返回原链接
     *
     * @param url 生成的短网址，如果生成失败，则返回原链接
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取异常描述
     *
     * @return err - 异常描述
     */
    public String getErr() {
        return err;
    }

    /**
     * 设置异常描述
     *
     * @param err 异常描述
     */
    public void setErr(String err) {
        this.err = err;
    }
}