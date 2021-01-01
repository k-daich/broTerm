package jp.daich.broterm.form;

import lombok.Data;

@Data
public class LoginRequestTextForm {

    // 入力：コンソールのあだ名
    private String consoleNickname;
    // 入力：IP
    private String ip;
    // 入力：ポート番号
    private int port;
    // 入力：ホスト名
    private String hostName;
    // 入力：パスワード
    private String passwd;

}
