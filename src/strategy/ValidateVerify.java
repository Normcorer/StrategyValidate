package strategy;

import domain.Result;
import enumeration.ResultEnum;

public class ValidateVerify implements IVerify {
    private static final String validateStr = "123456";
    private String validate;

    public ValidateVerify(String validate) {
        this.validate = validate;
    }

    @Override
    public Result exec() {
        return validate.equals(validateStr) ? Result.sucess() : new Result(ResultEnum.VALIDATE_ERROR);
    }
}
