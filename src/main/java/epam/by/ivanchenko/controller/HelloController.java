package epam.by.ivanchenko.controller;

import epam.by.ivanchenko.security.PersonDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/showUserInfo")
        public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();         // Достаем объект authentification, который был положен в поток после аутентиф. пользователя
        PersonDetail personDetail = (PersonDetail) authentication.getPrincipal();                       // Даункастинг
        System.out.println(personDetail.getPerson());                                                   // Получае person

        return "hello";
    }
}
