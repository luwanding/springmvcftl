package com.fiveone.log;


import com.fiveone.util.DateTimeUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;

import java.util.Date;

/**
 * 自动日志拦截器
 * @author : Luwanding
 */
public class LogAdvice implements MethodInterceptor {
    private final LogTrace log = new LogTrace(this.getClass());

    /**
     * 拦截方法
     * 
     * @param invocation
     *            拦截对象
     * @return 执行结果
     * @throws Throwable
     *             异常
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {

//        log.info(createLog(invocation, true, null), false);

        StopWatch clock = new StopWatch();
        clock.start(); // 计时开始
        Object result = invocation.proceed();
        clock.stop(); // 计时结束
        log.info(createLog(invocation, true, clock.getTime()), false);
        clock = null;
        return result;
    }

    /**
     * 获取日志信息
     * 
     * @param invocation
     *            拦截对象
     * @param startTask
     *            目前是否在方法访问前
     * @param taskTime
     *            执行时间
     * @return 日志信息
     */
    @SuppressWarnings("rawtypes")
    private String createLog(MethodInvocation invocation, Boolean startTask, Long taskTime) {

        Class[] params = invocation.getMethod().getParameterTypes();

        String[] simpleParams = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            simpleParams[i] = params[i].getSimpleName();

        }
        StringBuffer sb = new StringBuffer();
        if (startTask) {
            sb.append("*******HR LOG BEGIN*******  ");
            sb.append(invocation.getThis().getClass().getName());
            sb.append("*******************Arguments：");
            for (int i = 0;i < invocation.getArguments().length;i++)
            {
                Object[] objs = invocation.getArguments();
                sb.append(objs[i]);
            }
        } else {
            sb.append("*******HR LOG END*******  ");
            sb.append(invocation.getThis().getClass().getName());
        }
        sb.append(".");
        sb.append(invocation.getMethod().getName());
        sb.append("(");
        sb.append(StringUtils.join(simpleParams, ","));
        sb.append(")");
        sb.append("  Time :" + DateTimeUtil.getInstance().formatDateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        if (!startTask) {
            sb.append("  TaskTime :" + taskTime + "ms");
        }
        return sb.toString();
    }

}
