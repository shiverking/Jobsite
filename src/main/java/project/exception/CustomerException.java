package project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import project.model.RespBean;

import java.util.HashMap;
import java.util.List;
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

    /**
     * 参数校验异常
     * @param e
     * @return
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespBean parameterExceptionHandler(MethodArgumentNotValidException e) {
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return RespBean.error(fieldError.getDefaultMessage());
            }
        }
        return RespBean.error("请求参数错误");
    }



}
