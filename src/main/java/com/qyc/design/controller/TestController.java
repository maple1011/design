package com.qyc.design.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TestController {

    /**
     * SSE 框架
     * @param request
     * @param response
     * @param str
     */
    @RequestMapping("/chat/message/{str}")
    public void chat(HttpServletRequest request, HttpServletResponse response,@PathVariable(value = "str") String str){
        response.setHeader("Content-Type", "text/event-stream");
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Pragma", "no-cache");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            if(!StringUtils.isEmpty(str)){
                out.write(String.valueOf(str+"回答内容:\n").getBytes());
                out.flush();
            }
            out.write("{}".getBytes());
            out.flush();
            Thread.sleep(10000);
            out.write("短句2\n".getBytes());
            out.flush();
            Thread.sleep(1000);
            out.write("短句3\n".getBytes());
            out.flush();
            Thread.sleep(1000);
            out.write("短句4\n".getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("结束");
        }
    }
}
