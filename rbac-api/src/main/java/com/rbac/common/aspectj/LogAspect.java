package com.rbac.common.aspectj;

import cn.dev33.satoken.stp.StpUtil;
import com.rbac.common.result.ResultCode;
import com.rbac.common.util.AddressUtils;
import com.rbac.common.util.ServletUtils;
import com.rbac.project.entity.SysAdmin;
import com.rbac.project.entity.SysOperatorLog;
import com.rbac.project.mapper.SysAdminMapper;
import com.rbac.project.mapper.SysOperatorLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * 操作日志记录处理
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Resource
    private SysAdminMapper sysAdminMapper;
    @Resource
    private SysOperatorLogMapper sysOperatorLogMapper;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.rbac.common.aspectj.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            com.rbac.common.aspectj.Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            int id = StpUtil.getLoginIdAsInt();
            SysAdmin sysAdmin = sysAdminMapper.selectById(id);
            InetAddress address = InetAddress.getLocalHost();
            String hostAddress = address.getHostAddress();
            SysOperatorLog sysOperatorLog = new SysOperatorLog();
            sysOperatorLog.setOperatorStatus(ResultCode.OPERATOR_SUCCESS.getCode());
            sysOperatorLog.setOperatorIp(String.valueOf(hostAddress));
            sysOperatorLog.setJsonResult(String.valueOf(jsonResult));
            sysOperatorLog.setOperatorUrl(ServletUtils.getRequest().getRequestURI());
            if (sysAdmin != null) {
                sysOperatorLog.setOperatorName(sysAdmin.getUsername());
            }
            if (e != null) {
                sysOperatorLog.setOperatorStatus(ResultCode.OPERATOR_FAIL.getCode());
                sysOperatorLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            sysOperatorLog.setMethod(className + "." + methodName + "()");
            sysOperatorLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            sysOperatorLog.setOperatorTime(new Date());
            sysOperatorLog.setOperatorLocation(AddressUtils.getRealAddressByIP(String.valueOf(hostAddress)));
            getControllerMethodDescription(joinPoint, controllerLog, sysOperatorLog);
            sysOperatorLogMapper.insert(sysOperatorLog);
        } catch (Exception exp) {
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperatorLog sysOperatorLog) throws Exception {
        sysOperatorLog.setBusinessType(log.businessType().ordinal());
        sysOperatorLog.setTitle(log.title());
        if (log.isSaveRequestData()) {
            setRequestValue(joinPoint, sysOperatorLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperatorLog sysOperatorLog) throws Exception {
        String requestMethod = sysOperatorLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            sysOperatorLog.setOperatorParam(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            sysOperatorLog.setOperatorParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!isFilterObject(paramsArray[i])) {
                    Object jsonObj = paramsArray[i];
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     */
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Iterator iter = collection.iterator(); iter.hasNext(); ) {
                return iter.next() instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Iterator iter = map.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iter.next();
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }
}
