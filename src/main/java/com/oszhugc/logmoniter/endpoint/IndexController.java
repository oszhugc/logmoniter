package com.oszhugc.logmoniter.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 路由到index.html
 * @author oszhugc
 * @Date 2019\4\25 0025 22:03
 **/
@Controller
public class IndexController {

    @GetMapping("index")
    public String index(){
        return "/index";
    }

}
