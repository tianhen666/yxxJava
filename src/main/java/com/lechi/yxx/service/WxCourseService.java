package com.lechi.yxx.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface WxCourseService {

    Map<String, Object> getPrePayInfo(HttpServletRequest request) throws Exception;

}
