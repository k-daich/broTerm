package jp.daich.broterm.form;

import lombok.Data;

@Data
public class ConsoleRequestForm {
    // 入力：コマンド
    private String commandLines;

    // 出力：コマンド結果出力
    private int sessionSeq;

    public String getCommandLines() {
        return commandLines;
    }

    public void setCommandLines(String commandLines) {
        this.commandLines = commandLines;
    }

    public int getSessionSeq() {
        return sessionSeq;
    }

    public void setSessionSeq(int sessionSeq) {
        this.sessionSeq = sessionSeq;
    }
}
