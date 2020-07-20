## 概述
  策略模式(Strategy Pattern)定义了算法族，分别封装起来，让它们之间可以相互替换，此模式让算法的变化独立于使用算法的用户。
  
  本文展示了用策略模式实现了简单的登录校验，我会在本文最后附上本次的测试代码，有需要的小伙伴可以运行代码中的单元测试来进行理解。
  
  为了校验登录的流程，我们往往需要判断用户名是否存在、密码是否正确、验证码是否正确等一系列操作，这就意味着我们的代码会有很多if-else没有扩展性，也没有复用性，如果我们想要增加一个邮箱的校验就需要，重新改if-else的代码，这意味着很容易影响到之前正确的功能。

## 策略模式结构
- 策略（Strategy）：策略是一个接口，该接口定义算法标识。
- 具体策略（ConcreteStrategy）：具体策略是实现策略接口的类。具体策略实现策略接口所定义的抽象方法，即给出算法标识的具体算法。
- 上下文（Context）：上下文是依赖于策略接口的类，即上下文包含有策略声明的变量。上下文中提供了一个方法，该方法委托策略变量调用具体策略所实现的策略接口中的方法。
![策略模式类图](https://molzhao-pic.oss-cn-beijing.aliyuncs.com/2020-07-20/%E7%AD%96%E7%95%A5%E6%A8%A1%E5%BC%8F%E7%B1%BB%E5%9B%BE.png)

## 适合策略模式的情景
- 一个类定义了多种行为，并且这些行为在这个类的方法中以多个条件语句的形式出现，那么可以使用策略模式在类中使用大量的条件语句。
- 程序不希望暴露复杂的、与算法有关的数据结构，可以使用策略模式来封装算法。

## 简单登录检验案例
由于是简单实现，所以一些校验我直接写死了，需要的话可以通过查询数据库来进行替换写死的代码。
### 策略接口
```java
public interface IVerify {
    Result exec();
}
```

### 具体策略
```java
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
```

### 策略上下文
```java
public class Context {
    private List<IVerify> verifyList;

    public Context() {
        verifyList = new LinkedList<>();
    }
	// 链式调用
    public Context addStrategy(IVerify verify) {
        verifyList.add(verify);
        return this;
    }

    public void removeAll() {
        verifyList.clear();
    }

    public int size() {
        return verifyList.size();
    }

    public Result exec() {
        return verifyList.stream().map(IVerify::exec).filter(x -> x.getCode() >= 400).findFirst().orElse(Result.sucess());
    }
}
```
【说明】这边我对策略的上下文进行了一些自定义，一般网上的策略都是单个校验，我这边通过`LinkedList<>()`存储策略，同时也保证了校验策略的顺序，通过java8新特性流式计算`stream().filter().findFirst()`方法来进行获取第一个检验失败的情况并返回相应的结果。

### 单元测试
```java
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
```

## 案例Demo
**[策略模式简单登录校验案例](https://github.com/Normcorer/StrategyValidate.git)**
