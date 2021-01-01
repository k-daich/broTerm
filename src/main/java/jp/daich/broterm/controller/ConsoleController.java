package jp.daich.broterm.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.daich.broterm.form.ConsoleRequestForm;
import jp.daich.broterm.form.LoginRequestTextForm;
import jp.daich.broterm.jsch.session.SshSessionHolder;
import jp.daich.broterm.util.LogUtil;

public class ConsoleController {

    @RequestMapping(value = "/console/login", method = RequestMethod.POST)
    public String login(@ModelAttribute LoginRequestTextForm request, Model model) {
        LogUtil.startLog(request.getConsoleNickname());
        // 入力フォームで取り扱うオブジェクトを設定
        model.addAttribute("loginRequest", new LoginRequestTextForm());
        int sessionSeq = SshSessionHolder.connect(request.getIp(), request.getPort(), request.getHostName(), request.getPasswd());
        model.addAttribute("sessionIdSeq", sessionSeq);
        LogUtil.endLog();
        return "console/login";
    }

    @RequestMapping(value = "/console/sendCommand", method = RequestMethod.POST)
    public String sendCommand(@ModelAttribute ConsoleRequestForm request, Model model) {
        LogUtil.startLog(request.getCommandLines());
        SshSessionHolder.get(request.getSessionSeq()).sendCommand(request.getCommandLines(), model);
        LogUtil.endLog();
        return "console/sendCommand";
    }
}
