package com.springapp.controller.classic;

import com.springapp.IncludeAPI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by Gushchin-AA1 on 08.09.2017.
 */
@Controller
public class LoginController {
    @RequestMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/login/error", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public final String displayLoginform(Model model, HttpServletResponse response, @RequestParam(value = "type") String type
            , @RequestParam(value = "text", required = false) String text
            , @RequestParam(value = "link", required = false) String link) throws UnsupportedEncodingException {

        switch (type) {
            case "badCredentials":
                model.addAttribute("error", new String("Логин или пароль введены не верно!<br/>".getBytes(), "UTF-8"));
                break;
            case "accountDisabled":
                model.addAttribute("error", "Ваша учетная запись отключена! Перейдите по ссылке для восстановления доступа: <a href='" + link + "'>Продлить доступ</a><br/>");
                break;
            default:
                model.addAttribute("error", "Авторизация не удалась, повторите попытку позже...<br/>");
                break;
        }

        return "login";
    }

    @RequestMapping(value = "/403")
    public String err403(ModelMap modelMap) {
        return "403";
    }
}
