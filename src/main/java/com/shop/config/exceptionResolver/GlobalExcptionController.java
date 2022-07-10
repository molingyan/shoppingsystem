package com.shop.config.exceptionResolver;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExcptionController {
    @ExceptionHandler(value = ArrayIndexOutOfBoundsException.class)
    public ModelAndView ArrayIndexOutOfBoundserrorHandler( ArrayIndexOutOfBoundsException e, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("error");
       return modelAndView;
    }

    @ResponseBody
    @ExceptionHandler(value = ArithmeticException.class)
    public Map ArithmeticExceptionerrorHandler(ArithmeticException e){
        HashMap map = new HashMap();
        map.put("code",101);
        map.put("msg","发生非0除数");
        return map;
    }
}
