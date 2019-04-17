package com.bearcat2.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Description: 通用mapper接口 </p>
 * <p>Title: BaseMapper </p>
 * <p>Create Time: 2018/8/15 22:56 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
public interface BaseMapper<Record, Example, ID> {
    /**
     * 根据指定主键获取一条数据库记录
     *
     * @param id
     * @return
     */
    Record selectByPrimaryKey(ID id);

    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(ID id);

    /**
     * 根据主键批量删除数据库的记录
     *
     * @param ids
     * @return
     */
    int deleteBatchByPrimaryKey(List<ID> ids);

    /**
     * 新写入数据库记录
     *
     * @param record
     * @return
     */
    int insert(Record record);

    /**
     * 动态字段,写入数据库记录
     *
     * @param record
     * @return
     */
    int insertSelective(Record record);

    /**
     * 动态字段,批量写入数据库记录
     *
     * @param records
     * @return
     */
    int insertBatch(List<Record> records);

    /**
     * 根据主键来更新符合条件的数据库记录
     *
     * @param record
     * @retur
     */
    int updateByPrimaryKey(Record record);

    /**
     * 动态字段,根据主键来更新符合条件的数据库记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Record record);

    /**
     * 动态字段,批量更新数据库记录
     *
     * @param records
     * @return
     */
    int updateBatch(List<Record> records);

    /**
     * 根据指定的条件获取数据库记录数
     *
     * @param example
     */
    int countByExample(Example example);

    /**
     * 根据指定的条件删除数据库符合条件的记录
     *
     * @param example
     */
    int deleteByExample(Example example);

    /**
     * 根据指定的条件查询符合条件的数据库记录
     *
     * @param example
     */
    List<Record> selectByExample(Example example);

    /**
     * 动态根据指定的条件来更新符合条件的数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Record record, @Param("example") Example example);

    /**
     * 根据指定的条件来更新符合条件的数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Record record, @Param("example") Example example);
}