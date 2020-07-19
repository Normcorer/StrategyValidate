package domain;

import enumeration.ResultEnum;

public class Result {
    private int code;
    private String msg;

    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.getValue();
        this.msg = resultEnum.getDescription();
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static Result sucess() {
        return new Result(ResultEnum.SUCCESS);
    }
}
