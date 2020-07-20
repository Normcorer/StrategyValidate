import domain.Result;
import enumeration.ResultEnum;
import org.junit.Test;
import strategy.Context;
import strategy.PasswordVerify;
import strategy.ValidateVerify;

import static org.junit.Assert.assertEquals;

public class ContextTest {

    @Test
    public void testSize() {
        // Arrange
        Context context = new Context();

        // Act
        int size = context.size();

        // Assert
        assertEquals(0, size);
    }

    @Test
    public void testAddStrategy_RemoveAll() {
        // Arrange
        Context context = new Context();
        context.addStrategy(new PasswordVerify("123456"));

        // Act
        int size = context.size();

        // Assert
        assertEquals(1, size);

        // Act
        context.removeAll();
        size = context.size();

        // Assert
        assertEquals(0, size);
    }

    @Test
    public void testContext() {
        // Case1: 密码不正确
        // Arrange
        Context context = new Context();
        context.addStrategy(new PasswordVerify("zyj"))
                .addStrategy(new ValidateVerify("123456"));

        // Act
        Result exec = context.exec();

        // Assert
        assertEquals(ResultEnum.PASSWORD_ERROR.getValue(), exec.getCode());

        // Case2：验证码不正确
        // Arrange
        context.removeAll();
        context.addStrategy(new PasswordVerify("123456"))
                .addStrategy(new ValidateVerify("zyj"));

        // Act
        exec = context.exec();

        // Assert
        assertEquals(ResultEnum.VALIDATE_ERROR.getValue(), exec.getCode());

        // Case3：全部不正确,取第一个不正确的
        // Arrange
        context.removeAll();
        context.addStrategy(new PasswordVerify("zyj"))
                .addStrategy(new ValidateVerify("zyj"));

        // Act
        exec = context.exec();

        // Assert
        assertEquals(ResultEnum.PASSWORD_ERROR.getValue(), exec.getCode());

        // Case4：全部正确
        // Arrange
        context.removeAll();
        context.addStrategy(new PasswordVerify("123456"))
                .addStrategy(new ValidateVerify("123456"));

        // Act
        exec = context.exec();

        // Assert
        assertEquals(ResultEnum.SUCCESS.getValue(), exec.getCode());
    }
}
