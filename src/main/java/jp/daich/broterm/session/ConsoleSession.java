package jp.daich.broterm.session;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConsoleSession implements Serializable {

    // 入力：コンソールのあだ名
    private String consoleNickname;
    // 入力：IP
    private String ip;
    // 入力：ポート番号
    private int port;
    // 入力：ホスト名
    private String hostName;
    // 入力：コマンド
    private String command;
    // 出力：結果出力
    private String output;

    public String getConsoleNickname() {
        return consoleNickname;
    }

    public void setConsoleNickname(String consoleNickname) {
        this.consoleNickname = consoleNickname;
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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

}
