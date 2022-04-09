package com.example.nettyserver.serverstart;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月02日 14:49:00
 */
@RestController
@RequestMapping("/start")
public class StartController {
    @GetMapping("get")
    public String getStartInfo(){
        return "开始了";
    }
}
