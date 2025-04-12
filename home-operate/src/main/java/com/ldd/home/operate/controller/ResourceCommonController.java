package com.ldd.home.operate.controller;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.ext.Page;
import com.ldd.home.operate.entity.req.ResourceHisReq;
import com.ldd.home.operate.service.IImportExportService;
import com.ldd.home.operate.service.IReportService;
import com.ldd.home.operate.service.IResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/common/resource")
public class ResourceCommonController {

    @Autowired
    IReportService reportService;

    @Autowired
    IImportExportService importExportService;

    @Autowired
    IResourceService resourceService;



    /**
     * 获取历史列表
     */
    @PostMapping("/getImportHisList")
    public Result getImportHisList(@RequestBody @Validated(Page.class) ResourceHisReq req) throws ParseException {
        return importExportService.getImportHisList(req);
    }

}
