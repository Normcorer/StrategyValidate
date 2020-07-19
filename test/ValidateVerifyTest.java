import domain.Result;
import enumeration.ResultEnum;
import org.junit.Test;
import strategy.ValidateVerify;

import static org.junit.Assert.assertEquals;

public class ValidateVerifyTest {
    @Test
    public void testValidateVerify() {
        // Case1：验证码正确
        // Arrange
        ValidateVerify validateVerify = new ValidateVerify("123456");

        // Act
        Result exec = validateVerify.exec();

        // Assert
        assertEquals(ResultEnum.SUCCESS.getValue(), exec.getCode());

        // Case2：验证码不正确
        // Arrange
        validateVerify = new ValidateVerify("zyj");

        // Act
        exec = validateVerify.exec();

        // Assert
        assertEquals(ResultEnum.VALIDATE_ERROR.getValue(), exec.getCode());
    }
}
