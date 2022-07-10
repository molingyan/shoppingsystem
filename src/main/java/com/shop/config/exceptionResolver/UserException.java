package com.shop.config.exceptionResolver;

public class UserException extends BaseException{
    private static  final long seriaVersionUID = 1L;
    public UserException(String code,Object[] args){
        super("user",code,args,null);
    }
}
