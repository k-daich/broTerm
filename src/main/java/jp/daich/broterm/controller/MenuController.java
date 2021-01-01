package jp.daich.broterm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.daich.broterm.controller.constant.ModelAttributeName;
import jp.daich.broterm.form.LoginRequestSelectForm;
import jp.daich.broterm.form.LoginRequestTextForm;
import jp.daich.broterm.util.LogUtil;

@Controller
public class MenuController {

    public MenuController() {
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String init(Model model) {
        LogUtil.startLog();
        // 入力フォームで取り扱うオブジェクトを設定（テキスト方式）
        model.addAttribute(ModelAttributeName.LOGIN_REQUEST_TEXT_FORM, new LoginRequestTextForm());
        // 入力フォームで取り扱うオブジェクトを設定（選択方式）
        model.addAttribute(ModelAttributeName.LOGIN_REQUEST_SELECT_FORM, new LoginRequestSelectForm());
        LogUtil.endLog();
        return "menu";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginFromTextForm(Model model) {
        LogUtil.startLog();
        // 入力フォームで取り扱うオブジェクトを取得
        LoginRequestTextForm dto = (LoginRequestTextForm)model.getAttribute(ModelAttributeName.LOGIN_REQUEST_TEXT_FORM);
        LogUtil.debug(dto.toString());
        Map<String, String> body = new HashMap<String, String>(){
            {
                put("hostName", dto.getHostName());
            }
        };

        // SSH接続の実施
        // int sessionSeq = SshSessionHolder.connect(request.getIp(), request.getPort(), request.getHostName(), request.getPasswd());
        // SSHセッションIDの設定
        // model.addAttribute("sessionIdSeq", sessionSeq);

        LogUtil.endLog();
        return new ResponseEntity<Map>(body, HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginFromSelectForm(Model model) {
        LogUtil.startLog();
        // 入力フォームで取り扱うオブジェクトを取得
        LoginRequestSelectForm dto = (LoginRequestSelectForm)model.getAttribute(ModelAttributeName.LOGIN_REQUEST_SELECT_FORM);
        LogUtil.debug(dto.toString());
        Map<String, String> body = new HashMap<String, String>(){
            {
                put("hostName", dto.getHostName());
            }
        };

        // SSH接続の実施
        // int sessionSeq = SshSessionHolder.connect(request.getIp(), request.getPort(), request.getHostName(), request.getPasswd());
        // SSHセッションIDの設定
        // model.addAttribute("sessionIdSeq", sessionSeq);

        LogUtil.endLog();
        return new ResponseEntity<Map>(body, HttpStatus.OK);
    }
}
