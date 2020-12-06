package com.galaxy.common.core.service;

import com.galaxy.common.core.vo.Page;
import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 */
public interface Service<T> {

    void save(T model);//持久化
    int saveList(List<T> model);
    void save(List<T> models);//批量持久化
    void deleteById(Long id);//通过主鍵刪除
    void deleteByIds(String ids);//批量刪除 eg：ids -> “1,2,3,4”
    void update(T model);//更新
    void changeStateById(Long id,Boolean isDelete,Long userId); //更新状态
    T findById(Long id);//通过ID查找
    T findDetail(Long id);//通过ID查找状态有效的数据
    T findBy(String fieldName, Object value) throws TooManyResultsException; //通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
    T findBy(T model) throws TooManyResultsException; //通过Model查找
    List<T> findValidAll()  throws TooManyResultsException;//查询state为1的数据
    List<T> findByModel(T model);
    List<T> findValidDeleteAll(Page<T> page);
    //List<T> findValidDeleteAll(Page<T> page, UserVo user);
    List<T> findByIds(String ids);//通过多个ID查找//eg：ids -> “1,2,3,4”
    List<T> findByCondition(Condition condition);//根据条件查找
    List<T> findAll();//获取所有

}
