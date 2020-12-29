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

    /**
     * セッションコンストラクタ
     *
     * @param hostname
     * @param userid
     * @param passwd
     */
    public SshSession(String hostname, int port, String userid, String passwd) {
        jschSSHChannel = new JSch();
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
        model.addAttribute("command", command);
        Channel channel = null;
        StringBuilder sBuilder = new StringBuilder();
        try {
            channel = sesConnection.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            InputStream commandOutput = channel.getInputStream();
            channel.connect();
            int readByte = commandOutput.read();
            sBuilder.append(readByte);
            while (readByte != 0xffffffff) {
                sBuilder.append((char) readByte);
                readByte = commandOutput.read();
            }
        } catch (JSchException | IOException ex) {
            throw new RuntimeException("Errored send command by Jsch.", ex);
        } finally {
            LogUtil.debug(sBuilder.toString());
            LogUtil.endLog();
            if (channel != null) {
                channel.disconnect();
            }
        }
        model.addAttribute("commandResult", sBuilder.toString());
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