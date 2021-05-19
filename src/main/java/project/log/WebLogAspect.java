package project.log;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import project.model.User;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class WebLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * project.controller..*.*(..)) && !execution(public * project.controller.OrderController.OrderDetail*(..))")
    public void webLog() {

    }

    /**
     * 在切点进去之前进入
     *
     * @param joinPoint
     * @throws Throwable
     */

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 打印请求相关参数
        logger.info("========================================== Start ==========================================");
        // 打印请求 url
        logger.info("URL            : {}", request.getRequestURL().toString());
        // 打印 Http method
        logger.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        logger.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        logger.info("IP             : {}", request.getRemoteAddr());
        // 打印请求入参
        logger.info("Request Args   : {}", joinPoint.getArgs());
    }

    /**
     * 在切点之后进入
     *
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() throws Throwable {
        logger.info("=========================================== End ===========================================");
    }

    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参 若是返回RespBean可以将其Json化，方便查看

        if (result.getClass() == project.model.RespBean.class) {
            logger.info("Response Args  : {}", new Gson().toJson(result));
        } else if (result.getClass() == ModelAndView.class) {
            //给每个返回的ModelAndView添加user属性
            ModelAndView modelAndView = (ModelAndView) result;
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal.getClass() == project.model.User.class) {
                User user = (User) principal;
                modelAndView.addObject("user", user);
                logger.info("Response Args  : {}", result);
            }else {
                logger.info("Response Args  : {}", result);
            }
        } else {
            logger.info("Response Args  : {}", result);
        }
        // 执行耗时
        logger.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
        // 每个请求之间空一行
        logger.info("");
        return result;
    }

}
