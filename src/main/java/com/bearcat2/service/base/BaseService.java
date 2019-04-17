package com.bearcat2.service.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: 通用service接口</p>
 * <p>Title: BaseService</p>
 * <p>Create Time: 2018/8/15 22:01</p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
public interface BaseService<Record, Example, ID> {

    Record selectByPrimaryKey(ID id);

    boolean deleteByPrimaryKey(ID id);

    boolean deleteBatchByPrimaryKey(List<ID> ids);

    boolean insert(Record record);

    boolean insertSelective(Record record);

    boolean insertBatch(List<Record> records);

    boolean updateByPrimaryKey(Record record);

    boolean updateByPrimaryKeySelective(Record record);

    boolean updateBatch(List<Record> records);

    boolean countByExample(Example example);

    boolean deleteByExample(Example example);

    List<Record> selectByExample(Example example);

    boolean updateByExampleSelective(@Param("record") Record record, @Param("example") Example example);

    boolean updateByExample(@Param("record") Record record, @Param("example") Example example);

}