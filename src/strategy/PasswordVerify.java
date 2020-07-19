package strategy;

import domain.Result;
import enumeration.ResultEnum;

/**
 * 密码校验策略：这边简易校验一下密码
 */
public class PasswordVerify implements IVerify {
    private static final String access = "123456";
    private String passWord;

    public PasswordVerify(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public Result exec() {
        return passWord.equals(access) ? Result.sucess() : new Result(ResultEnum.PASSWORD_ERROR);
    }
}
