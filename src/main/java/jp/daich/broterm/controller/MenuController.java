package jp.daich.broterm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.daich.broterm.dto.LoginRequestDto;
import jp.daich.broterm.util.LogUtil;

@Controller
public class MenuController {

    public MenuController() {
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String init(Model model) {
        LogUtil.startLog();
        // 入力フォームで取り扱うオブジェクトを設定
        model.addAttribute("loginRequest", new LoginRequestDto());
        LogUtil.endLog();
        return "menu";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model) {
        LogUtil.startLog();
        // 入力フォームで取り扱うオブジェクトを設定
        model.addAttribute("loginRequest", new LoginRequestDto());
        LogUtil.endLog();
        return "login";
    }

}