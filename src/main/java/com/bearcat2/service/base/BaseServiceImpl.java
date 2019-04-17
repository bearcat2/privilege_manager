package com.bearcat2.service.base;

import com.bearcat2.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>Description: 通用service实现类 </p>
 * <p>Title: BaseServiceImpl </p>
 * <p>Create Time: 2018/8/15 22:59 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
public class BaseServiceImpl<Record, Example, ID> implements BaseService<Record, Example, ID> {

    @Autowired
    public BaseMapper<Record, Example, ID> baseMapper;

    @Override
    public Record selectByPrimaryKey(ID id) {
        return this.baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean deleteByPrimaryKey(ID id) {
        return verifyUpdateIsSuccess(this.baseMapper.deleteByPrimaryKey(id));
    }

    @Override
    public boolean deleteBatchByPrimaryKey(List<ID> ids) {
        return verifyUpdateIsSuccess(this.baseMapper.deleteBatchByPrimaryKey(ids));
    }

    @Override
    public boolean insert(Record record) {
        return verifyUpdateIsSuccess(this.baseMapper.insert(record));
    }

    @Override
    public boolean insertSelective(Record record) {
        return verifyUpdateIsSuccess(this.baseMapper.insertSelective(record));
    }

    @Override
    public boolean insertBatch(List<Record> records) {
        return verifyUpdateIsSuccess(this.baseMapper.insertBatch(records));
    }

    @Override
    public boolean updateByPrimaryKey(Record record) {
        return verifyUpdateIsSuccess(this.baseMapper.updateByPrimaryKey(record));
    }

    @Override
    public boolean updateByPrimaryKeySelective(Record record) {
        return verifyUpdateIsSuccess(this.baseMapper.updateByPrimaryKeySelective(record));
    }

    @Override
    public boolean updateBatch(List<Record> records) {
        return verifyUpdateIsSuccess(this.baseMapper.updateBatch(records));
    }

    @Override
    public boolean countByExample(Example example) {
        return verifyUpdateIsSuccess(this.baseMapper.countByExample(example));
    }

    @Override
    public boolean deleteByExample(Example example) {
        return verifyUpdateIsSuccess(this.baseMapper.deleteByExample(example));
    }

    @Override
    public List<Record> selectByExample(Example example) {
        return this.baseMapper.selectByExample(example);
    }

    @Override
    public boolean updateByExampleSelective(@Param("record") Record record, @Param("example") Example example) {
        return verifyUpdateIsSuccess(this.baseMapper.updateByExampleSelective(record, example));
    }

    @Override
    public boolean updateByExample(@Param("record") Record record, @Param("example") Example example) {
        return verifyUpdateIsSuccess(this.baseMapper.updateByExample(record, example));
    }

    /**
     * 验证更新【增加、修改、删除】数据库是否成功,注这里的成功指的是物理意义上的成功即实际影响了数据库表几行记录
     *
     * @param rows 影响数据行数
     * @return true - 更新操作成功 false - 更新操作失败
     */
    private boolean verifyUpdateIsSuccess(Integer rows) {
        return rows != null && rows >= 1;
    }
}
