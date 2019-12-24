package com.example.myelastisearch.utils;

public class ResultUtil {

    public static Result success(Object obj){

        return new Result("200","success",obj);
    }

    public  static Result success(){
        return new Result("200","success");
    }

    public static Result error(String code,String msg){
        return new Result(code,msg);
    }


}

