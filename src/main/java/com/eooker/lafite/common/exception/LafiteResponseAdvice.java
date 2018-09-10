package com.eooker.lafite.common.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eooker.lafite.common.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author xiyatu
 * @date 2018/6/18 8:57
 * Description
 */
@ControllerAdvice
public class LafiteResponseAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(LafiteResponseAdvice.class);

    private static final String ERROR_PAGE = "error/lafiteError";

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Object exceptionHandle(Throwable throwable, HttpServletRequest request) {
        BaseCode code = CommonCode.SYSTEM_ERROR;
        //统一编辑message
        String message = code.getDesc();
        if (throwable instanceof MethodArgumentNotValidException) {
            //参数不合法，通过@Valid验证抛出
            code = CommonCode.PARAM_INVALID;
            message = getParamResult(throwable);
        } else if (throwable instanceof MissingServletRequestParameterException) {
            //缺少参数
            LOGGER.error("Requried Arguments: {} \r\n stackTrace:{}", throwable.getMessage(), getStackTrackFromThrowable(throwable));
            code = CommonCode.PARAM_LACK;
            message = code.getDesc();
        } else if (throwable instanceof HttpRequestMethodNotSupportedException) {
            //HTTP请求方式不支持
            LOGGER.error("Http Method Error:{} \r\n stackTrace:{}", throwable.getMessage(), getStackTrackFromThrowable(throwable));
            code = CommonCode.HTTP_METHOD_UNSUPPORTED;
            message = code.getDesc();
        } else if (throwable instanceof LafiteException) {
            //自定义异常
            LafiteException lafiteException = (LafiteException) throwable;
            LOGGER.error("Lafite Error:{} \r\n stackTrace:{}", lafiteException.getMsg(), getStackTrackFromException(lafiteException));
            code = lafiteException.getBaseCode();
            message = lafiteException.getMsg();
        } else {
            //未知的异常
            LOGGER.error("System Error:{}  \r\n stackTrace:{}", throwable.getMessage(), getStackTrackFromThrowable(throwable));
        }
        if(!request.getRequestURI().contains("interface")){
            //返回页面错误
            return responseModelAndView(code,throwable,message);
        }
        //返回接口json错误
//        return responseApiJson(code,message);
//        System.out.println("test");
        return Result.makeFailResult(message);
    }

    /**
     * 参数不合法，获取不合法的参数
     * @param throwable
     * @return
     */
    private String  getParamResult(Throwable throwable){
        MethodArgumentNotValidException e = (MethodArgumentNotValidException) throwable;
        LOGGER.warn("Invalid arguments {}", e.getMessage());
        StringBuilder result = new StringBuilder();
        result.append(e.getBindingResult().getErrorCount()).append(" error(s): ");
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                result.append("[").append(fieldError.getField()).append("=").append(fieldError.getRejectedValue())
                        .append(",").append(error.getDefaultMessage()).append("]");
            } else {
                result.append("[").append(error.getDefaultMessage()).append("]");
            }
        }
        return result.toString();
    }


    /**
     * 获取Exception 的stackTrace
     * @param e
     * @return
     */
    private String getStackTrackFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e2) {
            return "getStackTrackFromException error";
        }
    }

    /**
     * 获取Throwable的stackTrace
     * @param throwable
     * @return
     */
    private String getStackTrackFromThrowable(Throwable throwable) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e2) {
            return "getStackTrackFromThrowable error";
        }
    }

    private ModelAndView responseModelAndView(BaseCode code,Throwable throwable,String message){
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setException((Exception) throwable);
        exceptionMessage.setCode(code.getCode());
        exceptionMessage.setMsg(message);
        ModelAndView mv = new ModelAndView();
        mv.setViewName(ERROR_PAGE);
        mv.addObject("exceptionMessage", exceptionMessage);
        return mv;
    }

    private CommonResponse responseApiJson(BaseCode code,String message){
       return new CommonResponse<>(code.getCode(),message,null);
    }
}