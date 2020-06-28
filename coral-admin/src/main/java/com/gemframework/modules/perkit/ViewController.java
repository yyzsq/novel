
package com.gemframework.modules.perkit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Title: ViewController
 * @Package: com.gemframework.controller
 * @Date: 2020-03-15 20:38:15
 * @Version: v1.0
 * @Description: 获取页面路径控制器
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@Controller
public class ViewController {

	//===============内置模块路径=======================

	@RequestMapping("prekit/{url}.html")
	public String prekit(@PathVariable("url") String url){
		return "modules/prekit/" + url;
	}

	@RequestMapping("prekit/{module}/{url}.html")
	public String prekit(@PathVariable("module") String module,@PathVariable("url") String url){
		return "modules/prekit/"+module+"/" + url;
	}

	//===============扩展模块路径=======================

	@RequestMapping("extend/{url}.html")
	public String extend(@PathVariable("url") String url){
		return "modules/extend/" + url;
	}

	@RequestMapping("extend/{module}/{url}.html")
	public String extend(@PathVariable("module") String module,@PathVariable("url") String url){
		return "modules/extend/"+module+"/" + url;
	}

	//===============错误相关=======================
	@GetMapping(value = "error/{url}.html")
	public String error(@PathVariable("url") String url) {
		return "error/"+url;
	}


	//===============登录相关=======================

	@GetMapping("login")
	public String login(){
		return "login";
	}

	@RequestMapping("login{suffix}")
	public String login1(@PathVariable("suffix") String suffix){
		return "login"+suffix;
	}

	@GetMapping({"/", "index"})
	public String index(){
		return "index";
	}

	@GetMapping(value = "/home")
	public String home() {
		return "home";
	}


}
