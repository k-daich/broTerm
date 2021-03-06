package jp.daich.broterm.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {

    /**
     * Invalidate Construct
     */
    private LogUtil() {
    }

    private static final Logger logger = LogManager.getLogger();

    private static String indent = "";

    private static String incrementIndent() {
        // スペースを2つ足す
        return indent + "  ";
    }

    private static String decrementIndent() {
        // 先頭2桁削る
        return indent.substring(2);
    }

    public static void startLog(String... arguments) {
        StackTraceElement stackTrcEle = Thread.currentThread().getStackTrace()[2];
        logger.debug(
                indent + "[" + stackTrcEle.getFileName() + "@" + stackTrcEle.getMethodName() + "] >>>> start >>>>");
        logger.debug(indent + " Args > " + ArrayUtils.toString(arguments));
        // インデントを足す
        indent = incrementIndent();
    }

    public static void endLog() {
        StackTraceElement stackTrcEle = Thread.currentThread().getStackTrace()[2];
        // インデントを減らす
        indent = decrementIndent();
        logger.debug(indent + "[" + stackTrcEle.getFileName() + "@" + stackTrcEle.getMethodName() + "] <<<< end <<<<");
    }

    public static void debug(String message) {
        StackTraceElement stackTrcEle = Thread.currentThread().getStackTrace()[2];
        logger.debug(indent + "[" + stackTrcEle.getFileName() + "@" + stackTrcEle.getLineNumber() + "] " + message);
    }

    public static void printStackTrace(String message, Exception ex) {
        StackTraceElement stackTrcEle = Thread.currentThread().getStackTrace()[2];
        logger.debug(indent + "[" + stackTrcEle.getFileName() + "@" + stackTrcEle.getLineNumber() + "] " + message);
        for (StackTraceElement stEle : ex.getStackTrace()) {
            logger.debug(indent + "[cause]" + stEle.toString());
        }
    }

}
