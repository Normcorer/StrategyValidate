import domain.Result;
import enumeration.ResultEnum;
import org.junit.Test;
import strategy.PasswordVerify;

import static org.junit.Assert.assertEquals;

public class PasswordVerifyTest {
    @Test
    public void testPasswordVerify() {
        // Case1：密码正确
        // Arrange
        PasswordVerify passwordVerify = new PasswordVerify("123456");

        // Act
        Result exec = passwordVerify.exec();

        // Assert
        assertEquals(ResultEnum.SUCCESS.getValue(), exec.getCode());

        // Case2：密码不正确
        // Arrange
        passwordVerify = new PasswordVerify("zyj");

        // Act
        exec = passwordVerify.exec();

        // Assert
        assertEquals(ResultEnum.PASSWORD_ERROR.getValue(), exec.getCode());
    }
}
