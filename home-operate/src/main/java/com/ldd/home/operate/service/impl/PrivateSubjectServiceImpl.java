package com.ldd.home.operate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.StrUtil;
import com.ldd.home.operate.entity.PrivateSubInfo;
import com.ldd.home.operate.entity.req.SubQueryReq;
import com.ldd.home.operate.mapper.PrivateSubInfoMapper;
import com.ldd.home.operate.service.IPrivateSubjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class PrivateSubjectServiceImpl implements IPrivateSubjectService {

    @Autowired
    PrivateSubInfoMapper subInfoMapper;

    @Override
    public Result getSubjectList(SubQueryReq req) {
        if(StrUtil.isEmpty(req.getMhcx())){
            List<PrivateSubInfo> subInfos = subInfoMapper.selectList(
                    new LambdaQueryWrapper<PrivateSubInfo>()
                    .eq(PrivateSubInfo::getParentId,StrUtil.isEmpty(req.getSubId())?0L:req.getSubId())
                    .eq(PrivateSubInfo::getStatus,"1"));
            List<PrivateSubInfo> hasChildren = subInfos.size()>0?
                    subInfoMapper.queryListCareChildren(subInfos.stream().map(t->t.getSubId()).toList()): Collections.emptyList();
            subInfos.forEach(t->{
                for (PrivateSubInfo hasChild : hasChildren) {
                    if(t.getSubId().equals(hasChild.getParentId())){
                        t.setHasChildren(hasChild.hasChildren);
                        break;
                    }
                }
            });
            return Result.successList("查询成功",subInfos);
        }else{
            List resultList = new ArrayList();
            List<PrivateSubInfo> subInfos = subInfoMapper.getSubjectList(req);
            subInfos.forEach(t->{
                if(t.getParentId().equals(0L))resultList.add(t);
                for (PrivateSubInfo child : subInfos) {
                    if(t.getSubId().equals(child.getParentId())){
                        if(Objects.isNull(t.getChildren())) t.setChildren(new ArrayList<>());
                        t.getChildren().add(child);
                        break;
                    }
                }
            });
            return Result.successList("查询成功",resultList);
        }
    }

    @Override
    public Result addSubject(PrivateSubInfo PrivateSubInfo) throws BusinessException {
        PrivateSubInfo.setSubId(null);
        PrivateSubInfo.setStatus("1");
        PrivateSubInfo.setSubLevel(0);
        int r = subInfoMapper.insert(PrivateSubInfo);
        if (!Objects.equals(r , 1)) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("新增成功！");
    }

    @Override
    public Result updSubject(PrivateSubInfo PrivateSubInfo) throws BusinessException {
        PrivateSubInfo.setSubCode(null);
        int r = subInfoMapper.updateById(PrivateSubInfo);
        if (!Objects.equals(r , 1)) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("修改成功！");
    }

    @Override
    public Result delSubject(String id) throws BusinessException {
        checkHasChild(id);
        int r = subInfoMapper.updateById(PrivateSubInfo.builder().subId(StrUtil.toLong(id)).status("0").build());
        if (!Objects.equals(r , 1)) throw new BusinessException(ErrorMsgConst.DATA_ERROR);
        return Result.success("删除成功！");
    }

    /**
     * 删除时检查是否有下级科目，有责不能删除
     */
    private void checkHasChild(String id) throws BusinessException {
        List child = subInfoMapper.selectList(new LambdaQueryWrapper<PrivateSubInfo>().eq(PrivateSubInfo::getParentId,id));
        if(child.size()>0) throw new BusinessException("删除科目含有子级科目，请先删除子级科目！");
    }

    /**
     * 获取下一个自然排序的科目代码
     * @return
     */
    @Override
    public Result getNextSubCode(String id) {
        String subCode = subInfoMapper.selectMaxByParentId(id);
        Result  result = Result.successData("成功",
                StrUtil.isEmpty(id)?
                        "":subCode.substring(0,subCode.length()-3)+StringUtils.leftPad(
                                StrUtil.toInt(subCode.substring(subCode.length()-3))+1+"",
                        3,
                        "0"));
        return result;
    }
}
