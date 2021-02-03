package jp.daich.broterm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.daich.broterm.controller.constant.ModelAttributeName;
import jp.daich.broterm.form.LoginSelectForm;
import jp.daich.broterm.form.LoginTextForm;
import jp.daich.broterm.jsch.SshSession;
import jp.daich.broterm.jsch.session.SshSessionHolder;
import jp.daich.broterm.util.LogUtil;
import jp.daich.broterm.util.StringUtils;

@Controller
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
    public String loginFromTextForm(@RequestBody LoginTextForm loginTextForm) {
        // public ResponseEntity loginFromTextForm(@RequestBody LoginTextForm loginTextForm) {
            LogUtil.startLog();
        LogUtil.debug(loginTextForm.toString());

        // 入力フォームの内容チェック
        assertFormInfo(loginTextForm);

        // SSH接続の実施
        SshSession session = new SshSession(loginTextForm.getIp(), loginTextForm.getPort(), loginTextForm.getHostName(),
                loginTextForm.getPasswd());
        int sessionSeq = SshSessionHolder.put(session);

        LogUtil.endLog();
        return "console";
        // Map<String, String> resBody = new HashMap<String, String>() {
        //     {
        //         put("hostName", loginTextForm.getHostName());
        //         put("sessionId", Integer.toString(sessionSeq));
        //         put("sessionSeq", Integer.toString(session.getSessionSeq()));
        //         put("commandResult", session.getResult());
        //     }
        // };
        // return new ResponseEntity<Map>(resBody, HttpStatus.OK);
    }

    private void assertFormInfo(LoginTextForm loginTextForm) {
        // 空項目チェック
        if (StringUtils.isEmpty(loginTextForm.getIp()) || StringUtils.isEmpty(loginTextForm.getHostName())
                || StringUtils.isEmpty(loginTextForm.getPasswd())) {
            throw new RuntimeException("LoginForm empty Error. IP=[" + loginTextForm.getIp() + "], HostName=["
                    + loginTextForm.getHostName() + "], Passwd=[not print]");
        }

        // IPアドレスをドット区切りで配列化する
        String[] ipOctets = loginTextForm.getIp().split("\\.");
        if (ipOctets.length != 4) {
            throw new RuntimeException("Invalid Form @ IpAdress. ip=[" + loginTextForm.getIp() + "],ip octet length = ["
                    + ipOctets.length + "]");
        }

        for (String ipOctet : ipOctets) {
            try {
                // IPアドレスの各オクテットごとに０未満256以上である場合は異常値のためエラー
                if (Integer.parseInt(ipOctet) < 0 || Integer.parseInt(ipOctet) >= 256) {
                    throw new RuntimeException("Invalid Form @ IpAdress. ip=[" + loginTextForm.getIp() + "]");
                }
            } catch (NumberFormatException ex) {
                // IPアドレスの各オクテットが数字でない場合はエラー
                throw new RuntimeException("Invalid Form @ IpAdress. ip=[" + loginTextForm.getIp() + "]", ex);
            }
        }

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

        LogUtil.endLog();
        return new ResponseEntity<Map>(body, HttpStatus.OK);
    }
}
