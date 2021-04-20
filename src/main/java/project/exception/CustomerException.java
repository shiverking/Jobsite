package project.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import project.model.RespBean;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class CustomerException {
    /*
    自定义异常
     */

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public RespBean handlerMyException(ServiceException ex) {
        return RespBean.error(ex.getMessage());
    }

}
