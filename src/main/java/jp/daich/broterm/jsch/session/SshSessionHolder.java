package jp.daich.broterm.jsch.session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import jp.daich.broterm.jsch.SshSession;

public class SshSessionHolder {
    private static AtomicInteger sessionSeq = new AtomicInteger(0);
    private static final Map<Integer, SshSession> sessionMap = new HashMap<Integer, SshSession>();

    public static int put(SshSession session) {
        int seq = sessionSeq.incrementAndGet();
        sessionMap.put(seq, session);
        return seq;
    }

    public static int connect(String hostName, int port, String loginUserName, String passwd) {
        int seq = sessionSeq.incrementAndGet();
        sessionMap.put(seq, new SshSession(hostName, port, loginUserName, passwd));
        return seq;
    }

    public static SshSession get(int seq) {
        return sessionMap.get(seq);
    }
}
