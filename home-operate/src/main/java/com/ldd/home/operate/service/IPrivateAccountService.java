package com.ldd.home.operate.service;

import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.entity.PrivateAccInfo;
import com.ldd.home.operate.entity.req.AccInfoReq;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPrivateAccountService {
    Result getAccountList(AccInfoReq req);

    Result addAccount(PrivateAccInfo PrivateAccInfo);

    Result updAccount(PrivateAccInfo PrivateAccInfo);

    Result delAccount(String id);

    Result importTemplate(MultipartFile file, String repId) throws IOException;
}
