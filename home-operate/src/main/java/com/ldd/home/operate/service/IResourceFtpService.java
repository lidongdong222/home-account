package com.ldd.home.operate.service;

import com.ldd.home.operate.entity.req.ResourceBusiReq;

public interface IResourceFtpService {
    public <T extends ResourceBusiReq>  void saveFile(T t);
}
