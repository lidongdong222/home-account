package com.ldd.home.operate.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.utils.DateUtil;
import com.ldd.home.operate.entity.ImpInfo;
import com.ldd.home.operate.entity.req.ResourceHisReq;
import com.ldd.home.operate.entity.vo.ImportHisVo;
import com.ldd.home.operate.mapper.ImpInfoMapper;
import com.ldd.home.operate.service.IImpInfoService;
import com.ldd.home.operate.service.IImportExportService;
import com.ldd.home.operate.service.IResourceService;
import com.ldd.home.operate.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ImportExportServiceImpl implements IImportExportService {

    @Autowired
    private ImpInfoMapper impInfoMapper;

    @Autowired
    IResourceService resourceService;

    @Autowired
    IImpInfoService impInfoService;
    @Autowired
    ISysConfigService configService;

    /**
     * @param req
     * @return
     */
    @Override
    public Result getImportHisList(ResourceHisReq req) throws ParseException {
        Page<ImpInfo> page = impInfoMapper.selectPage(new Page<>(req.getPageNum(),req.getPageSize()),
                Wrappers.query(ImpInfo.class));
        List resultList = new ArrayList<>();
        for (ImpInfo imp : page.getRecords()) {
            ImportHisVo his = ImportHisVo.builder()
                    .impId(imp.getImpId())
                    .impTime(DateUtil.formatDatetime(imp.getImpTime()))
                    .status(imp.getStatus())
                    .resName(imp.getResName()).build();
            if((new Date().getTime() - imp.getImpTime().getTime())<1000*60){
                his.setImpTime("刚刚");
            }
            if(Objects.equals(imp.getStatus() , ImpInfo.ImpStatusEnum.WAIT.getCode())){
                his.setImpResult("排队中...");
            }else if(Objects.equals(imp.getStatus() , ImpInfo.ImpStatusEnum.HANDING.getCode())){
                his.setImpResult("导入中，请耐心等待！");
            }else if(Objects.equals(imp.getStatus() , ImpInfo.ImpStatusEnum.SUCCESS.getCode())){
                his.setImpResult("成功"+imp.getImpSucc()+"条,失败"+imp.getImpFail()+"条。");
            }else if(Objects.equals(imp.getStatus() , ImpInfo.ImpStatusEnum.FAIL.getCode())){
                his.setImpResult("导入失败，请联系管理员！");
            }
            resultList.add(his);
        }
        return Result.successPage("查询成功！",
                page.getTotal(),resultList);
    }

}
