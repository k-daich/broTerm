package jp.daich.broterm.form;

import lombok.Data;

@Data
public class ConsoleRequestForm {
    // 入力：コマンド
    private String commandLines;

    // 出力：コマンド結果出力
    private int sessionSeq;
}
