package epam.by.ivanchenko.controller;

import epam.by.ivanchenko.security.PersonDetail;
import epam.by.ivanchenko.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    private final AdminService adminService;

    @Autowired
    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/showUserInfo")
    @ResponseBody
        public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();         // Достаем объект authentication, который был положен в поток после аутентиф. пользователя
        PersonDetail personDetail = (PersonDetail) authentication.getPrincipal();                       // Даункастинг
        System.out.println(personDetail.getPerson());                                                   // Получае person

        return personDetail.getPerson().getUsername();
    }

    @GetMapping("/admin")
    public String adminPage() {
        adminService.doAdminStuff();
        return "admin";
    }
}
