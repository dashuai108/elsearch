package com.example.myelastisearch.utils;

import lombok.Getter;
import lombok.Setter;

public class Result<T> {

        @Setter
        @Getter
        private String code;
        @Setter@Getter
        private String msg;
        @Setter@Getter
        private T data;
        public Result(String code,String msg){
            this.code = code;
            this.msg = msg;
        }
        public Result(String code, String msg, T data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }
}
