package com.ldd.home.operate.service;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.entity.PrivateSubInfo;
import com.ldd.home.operate.entity.req.SubQueryReq;

public interface IPrivateSubjectService {
    Result getSubjectList(SubQueryReq req);

    Result addSubject(PrivateSubInfo PrivateSubInfo) throws BusinessException;

    Result updSubject(PrivateSubInfo PrivateSubInfo) throws BusinessException;

    Result delSubject(String id) throws BusinessException;

    Result getNextSubCode(String id);
}
