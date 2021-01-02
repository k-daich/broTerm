package jp.daich.broterm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.daich.broterm.controller.constant.ModelAttributeName;
import jp.daich.broterm.form.LoginSelectForm;
import jp.daich.broterm.form.LoginTextForm;
import jp.daich.broterm.jsch.session.SshSessionHolder;
import jp.daich.broterm.util.LogUtil;

@Controller
@SessionAttributes(types = LoginTextForm.class)
public class MenuController {

    public MenuController() {
    }

    /*
     * ログインForm（テキスト方式）オブジェクトをHTTPセッションに追加する
     */
    @ModelAttribute(ModelAttributeName.LOGIN_TEXT_FORM)
    public LoginTextForm setUpLoginTextForm() {
        return new LoginTextForm();
    }

    /*
     * ログインForm（選択方式）オブジェクトをHTTPセッションに追加する
     */
    @ModelAttribute(ModelAttributeName.LOGIN_SELECT_FORM)
    public LoginSelectForm setUpLoginSelectForm() {
        return new LoginSelectForm();
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String init(Model model) {
        LogUtil.startLog();
        // 入力フォームで取り扱うオブジェクトを設定（テキスト方式）
        model.addAttribute(ModelAttributeName.LOGIN_TEXT_FORM, new LoginTextForm());
        // 入力フォームで取り扱うオブジェクトを設定（選択方式）
        model.addAttribute(ModelAttributeName.LOGIN_SELECT_FORM, new LoginSelectForm());
        LogUtil.endLog();
        return "menu";
    }

    @RequestMapping(value = "/loginFromTextForm", method = RequestMethod.POST)
    public ResponseEntity loginFromTextForm(@ModelAttribute(ModelAttributeName.LOGIN_TEXT_FORM) LoginTextForm loginTextForm,
            Model model) {
        LogUtil.startLog();
        LogUtil.debug(loginTextForm.toString());

        // SSH接続の実施
        int sessionSeq = SshSessionHolder.connect(loginTextForm.getIp(), loginTextForm.getPort(),
                loginTextForm.getHostName(), loginTextForm.getPasswd());

        Map<String, String> resBody = new HashMap<String, String>() {
            {
                put("hostName", loginTextForm.getHostName());
                put("sessionIdSeq", Integer.toString(sessionSeq));
            }
        };
        LogUtil.endLog();
        return new ResponseEntity<Map>(resBody, HttpStatus.OK);
    }

    @RequestMapping(value = "/loginFromSelectForm", method = RequestMethod.POST)
    public ResponseEntity loginFromSelectForm(Model model) {
        LogUtil.startLog();
        // 入力フォームで取り扱うオブジェクトを取得
        LoginSelectForm dto = (LoginSelectForm) model.getAttribute(ModelAttributeName.LOGIN_SELECT_FORM);
        LogUtil.debug(dto.toString());
        Map<String, String> body = new HashMap<String, String>() {
            {
                put("hostName", dto.getHostName());
            }
        };

        // SSH接続の実施
        // int sessionSeq = SshSessionHolder.connect(request.getIp(), request.getPort(),
        // request.getHostName(), request.getPasswd());
        // SSHセッションIDの設定
        // model.addAttribute("sessionIdSeq", sessionSeq);

        LogUtil.endLog();
        return new ResponseEntity<Map>(body, HttpStatus.OK);
    }
}
