package com.fiveone.log;


import com.chyjr.platform.common.util.DateTimeUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日志封装类
 * @author : Luwanding
 */
public class LogTrace {

    private final Log logger;
    
    private String className;

    public LogTrace(Class<?> clazz) {
        this.logger = LogFactory.getLog(clazz);
        this.className = clazz.getName();
    }

    public LogTrace(String name) {
        this.logger = LogFactory.getLog(name);
        this.className = name;
    }

    /**
     * 输出debug日志
     * 
     * @param message
     * @param isSave
     */
    public void debug(String message, boolean isSave) {
        if (logger.isDebugEnabled()) {
            logger.debug(getLogContent(message));
        }
        if (isSave) {
            saveLog("Debug", message);
        }
    }

    /**
     * 输出debug日志
     * 
     * @param message
     * @param throwable
     * @param isSave
     */
    public void debug(String message, Throwable throwable, boolean isSave) {
        if (logger.isDebugEnabled()) {
            logger.debug(getLogContent(message), throwable);
        }
        if (isSave) {
            saveLog("Debug", message);
        }
    }

    /**
     * 输出debug日志，并保存到日志表
     * 
     * @param moudel
     *            模块名称
     * @param message
     *            日志内容
     * @param isSave
     */
    public void debug(String moudel, String message, boolean isSave) {
        if (logger.isDebugEnabled()) {
            logger.debug(getLogContent(moudel, message));
        }
        if (isSave) {
            saveLog(moudel, message);
        }
    }

    /**
     * 输出info日志
     * 
     * @param message
     * @param isSave
     */
    public void info(String message, boolean isSave) {
        if (logger.isInfoEnabled()) {
            logger.info(getLogContent(message));
        }
        if (isSave) {
            saveLog("Info", message);
        }
    }

    /**
     * 输出info日志
     * 
     * @param message
     * @param throwable
     * @param isSave
     */
    public void info(String message, Throwable throwable, boolean isSave) {
        if (logger.isInfoEnabled()) {
            logger.info(getLogContent(message), throwable);
        }
        if (isSave) {
            saveLog("Info", message);
        }
    }

    /**
     * 输出info日志，并保存到日志表
     * 
     * @param moudel
     *            模块名称
     * @param message
     *            日志内容
     * @param isSave
     */
    public void info(String moudel, String message, boolean isSave) {
        if (logger.isInfoEnabled()) {
            logger.info(getLogContent(moudel, message));
        }
        if (isSave) {
            saveLog(moudel, message);
        }
    }

    /**
     * 输出warn日志
     * 
     * @param message
     * @param isSave
     */
    public void warn(String message, boolean isSave) {
        if (logger.isWarnEnabled()) {
            logger.warn(getLogContent(message));
        }
        if (isSave) {
            saveLog("Warn", message);
        }
    }

    /**
     * 输出warn日志
     * 
     * @param message
     * @param throwable
     * @param isSave
     */
    public void warn(String message, Throwable throwable, boolean isSave) {
        if (logger.isWarnEnabled()) {
            logger.warn(getLogContent(message), throwable);
        }
        if (isSave) {
            saveLog("Warn", message);
        }
    }

    /**
     * 输出warn日志，并保存到日志表
     * 
     * @param moudel
     *            模块名称
     * @param message
     *            日志内容
     * @param isSave
     */
    public void warn(String moudel, String message, boolean isSave) {
        if (logger.isWarnEnabled()) {
            logger.warn(getLogContent(moudel, message));
        }
        if (isSave) {
            saveLog(moudel, message);
        }
    }

    /**
     * 输出error日志
     * 
     * @param message
     * @param isSave
     */
    public void error(String message, boolean isSave) {
        if (logger.isErrorEnabled()) {
            logger.error(getLogContent(message));
        }
        if (isSave) {
            saveLog("Error", message);
        }
    }

    /**
     * 输出error日志
     * 
     * @param message
     * @param throwable
     * @param isSave
     */
    public void error(String message, Throwable throwable, boolean isSave) {
        if (logger.isErrorEnabled()) {
            logger.error(getLogContent(message), throwable);
        }
        if (isSave) {
            saveLog("Error", message);
        }
    }

    /**
     * 输出error日志，并保存到日志表
     * 
     * @param moudel
     *            模块名称
     * @param message
     *            日志内容
     * @param isSave
     */
    public void error(String moudel, String message, boolean isSave) {
        if (logger.isErrorEnabled()) {
            logger.error(getLogContent(moudel, message));
        }
        if (isSave) {
            saveLog(moudel, message);
        }
    }

    /**
     * 获取log信息
     * 
     * @param moudel
     * @param message
     * @param
     * @return
     */
    private String getLogContent(String moudel, String message) {
    	
        StringBuffer sb = new StringBuffer();
        sb.append("模块:[" + moudel + "],");
        sb.append("信息:[" + message + "],");
        
//        if (LoginUserContainer.getCurrentUser() != null) {
//            UserEntity loginUser = LoginUserContainer.getCurrentUser().getUserInfo();
//            sb.append("操作人:[(" + loginUser.getUserId() + ") " + loginUser.getUserName() + "],");
//            sb.append("操作时间:[" + DateTimeUtil.getFormatTime(new Date()) + "]");
//        }else{
//        	 sb.append("操作人:[(未知 ) " + "未知" + "],");
            sb.append("操作时间:[" + DateTimeUtil.getInstance().getNowDate() + "]");
//        }
        return sb.toString();
    }

    /**
     * 获取log信息
     * 
     * @param message
     * @param
     * @return
     */
    private String getLogContent(String message) {
        StringBuffer sb = new StringBuffer();
//        if (LoginUserContainer.getCurrentUser() != null) {
//            UserEntity loginUser = LoginUserContainer.getCurrentUser().getUserInfo();
            sb.append("信息:[" + className + "----------->"  + message + "],");
//            sb.append("操作人:[(" + loginUser.getUserId() + ") " + loginUser.getUserName() + "],");
//            sb.append("操作时间:[" + DateTimeUtil.getFormatTime(new Date()) + "]");
//        } else {
//            sb.append("信息:[" + message + "],");
//            sb.append("操作人:[(未知 ) " + "未知" + "],");
            sb.append("操作时间:[" + DateTimeUtil.getInstance().getNowDate() + "]");
//        }
        return sb.toString();
    }

    /**
     * 持久化日志
     * 
     * @param moudel
     * @param message
     * @param
     */
    private void saveLog(String moudel, String message) {
//        LogMapper logMapper = (LogMapper) WebContextUtil.getBean("logMapper");
//        LogEntity info = new LogEntity();
//        info.setModule(moudel);
//        info.setContent( className + "----------->" + message);
//        info.setCreateBy(LoginUserContainer.getCurrentLoginUserId());
//        info.setCreateDate(new Date());
//        info.setUpdateBy(LoginUserContainer.getCurrentLoginUserId());
//        info.setUpdateDate(new Date());
//        logMapper.create(info);
    }
}
