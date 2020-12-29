package jp.daich.broterm.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginRequestDto implements Serializable {
    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 6929407741087512029L;

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

    public String getConsoleNickname() {
        return consoleNickname;
    }

    public void setConsoleNickname(String consoleNickname) {
        this.consoleNickname = consoleNickname;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
