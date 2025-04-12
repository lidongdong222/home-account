package com.ldd.home.operate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldd.home.operate.entity.PrivateSubInfo;
import com.ldd.home.operate.entity.req.SubQueryReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 科目信息表 Mapper 接口
 * </p>
 *
 * @author ldd
 * @since 2024-03-21
 */
@Mapper
public interface PrivateSubInfoMapper extends BaseMapper<PrivateSubInfo> {

    List<PrivateSubInfo> queryListCareChildren(@Param("ids") List<Long> list);

    String selectMaxByParentId(@Param("parentId")String id);

    /**
     * 模糊查询 由子节点查询所有父节点
     * @param req
     * @return
     */
    List<PrivateSubInfo> getSubjectList(SubQueryReq req);
}
