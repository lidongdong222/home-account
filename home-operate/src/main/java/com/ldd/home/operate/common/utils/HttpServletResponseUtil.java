package com.ldd.home.operate.common.utils;

import com.alibaba.fastjson2.JSON;
import com.ldd.home.operate.common.entity.Result;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpServletResponseUtil {
    public static void excelResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
    }

    public static void writeJson(HttpServletResponse resp, Result result) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(JSON.toJSONString(result));
    }
}
