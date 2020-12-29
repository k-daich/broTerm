package jp.daich.broterm.dto;

import lombok.Data;

@Data
public class ConsoleRequestDto {
    private String commandLines;

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
