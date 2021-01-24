package jp.daich.broterm.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import org.springframework.ui.Model;

import jp.daich.broterm.util.LogUtil;

public class SshSession {

    private JSch jschSSHChannel;
    private Session sesConnection;
    private String commandResult;
    private int sessionSeq = 0;

    /**
     * セッションコンストラクタ
     *
     * @param hostname
     * @param userid
     * @param passwd
     */
    public SshSession(String hostname, int port, String userid, String passwd) {
        jschSSHChannel = new JSch();
        StringBuilder sBuilder = new StringBuilder();
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        // knownHostsがあるなら使う
        // try {
        // jschSSHChannel.setKnownHosts(knownHostsFileName);
        // } catch (JSchException jschX) {
        // logError(jschX.getMessage());
        // }

        try {
            sesConnection = jschSSHChannel.getSession(userid, hostname, port);
            sesConnection.setConfig(config);
            sesConnection.setPassword(passwd);
            sesConnection.connect(6000);
            // TODO: できればログイン時の標準出力を出したい
            // commandResult = readOutput(sesConnection);
        } catch (JSchException ex) {
            throw new RuntimeException("Errored connect by Jsch.", ex);
        }
    }

    /**
     * SFTP接続を行う
     *
     * @param command
     */
    public void sendCommand(String command, Model model) {
        LogUtil.startLog(command);
        sessionSeq++;
        model.addAttribute("command", command);
        Channel channel = null;
        String commandResult = null;
        try {
            channel = sesConnection.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.connect();
            commandResult = readOutput(channel);
        } catch (JSchException | IOException ex) {
            throw new RuntimeException("Errored send command by Jsch.", ex);
        } finally {
            LogUtil.debug(commandResult);
            LogUtil.endLog();
            if (channel != null) {
                channel.disconnect();
            }
        }
        model.addAttribute("commandResult", commandResult);
    }

    /**
     * 
     * @return
     */
    public static String readOutput(Channel channel) throws IOException {
        StringBuilder sBuilder = new StringBuilder();
        InputStream commandOutput = channel.getInputStream();
        int readByte = commandOutput.read();
        sBuilder.append(readByte);
        while (readByte != 0xffffffff) {
            sBuilder.append((char) readByte);
            readByte = commandOutput.read();
        }
        return sBuilder.toString();
    }

    public String getResult(){
        return this.commandResult;
    }

    public int getSessionSeq() {
        return this.sessionSeq;
    }
    /**
     * セッションのコンフィグを設定する
     *
     * @param session
     */
    // private static Properties getCustomConfig() {
    // Properties config = new Properties();
    // // 未信用ホストの場合の信用するかの確認メッセージを抑止する
    // config.put("StrictHostKeyChecking", "no");
    // return config;
    // }

}