package com.ldd.home.operate.service;


import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.entity.AccBalance;
import com.ldd.home.operate.entity.AccInfo;
import com.ldd.home.operate.entity.SubInfo;
import com.ldd.home.operate.entity.req.AccInfoReq;
import com.ldd.home.operate.entity.req.IdReq;
import com.ldd.home.operate.entity.req.SubInfoReq;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IAccInfoService {
    Result getSubList(SubInfoReq req);

    Result addSubInfo(SubInfo req) throws BusinessException, InterruptedException;

    Result updSubinfo(SubInfo req) throws BusinessException;

    Result delSubinfo(IdReq req) throws BusinessException;

    Result getAccountList(AccInfoReq req);

    Result addAccount(AccInfo req) throws BusinessException;

    Result updAccount(AccInfo req) throws BusinessException;

    Result delAccount(String id) throws BusinessException;

    Result addAccountConfirm(AccInfo req);

    List<AccBalance> getAccMonthSummaryInfo(String s);

    Result importWxBill(MultipartFile file, Map params) throws IOException, Exception;

    Result importWxBillData(List<Map> wxBillList) throws Exception;

    void exportAccountList(AccInfoReq params, HttpServletResponse response) throws Exception;

    void exporSubjectList(SubInfoReq params, HttpServletResponse response) throws Exception;
}
