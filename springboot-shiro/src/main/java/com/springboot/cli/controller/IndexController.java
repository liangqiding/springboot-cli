package com.springboot.cli.controller;


import com.springboot.cli.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ding
 */
@Controller
public class IndexController {

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("msg", "hello,shiro");
        return "/index";
    }

    @RequestMapping("/userInfo")
    public String table1(Model model) {
        return "userInfo";
    }

    @RequestMapping("/table")
    public String table(Model model) {
        return "table";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "/doLogin")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        //用来存放错误信息
        String msg = "";
        //如果未认证
        if (!subject.isAuthenticated()) {
            //将用户名和密码封装到shiro中
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                // 执行登陆方法
                subject.login(token);
            } catch (Exception e) {
                e.printStackTrace();
                msg = "账号或密码错误";
            }
            //如果msg为空，说明没有异常，就返回到主页
            if (msg.isEmpty()) {
                return "redirect:/index";
            } else {
                model.addAttribute("errorMsg", msg);
                return "login";
            }
        }
        return "/login";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "index";
    }

    @GetMapping("/unAuth")
    public String unAuth() {
        return "unAuth";
    }
}
