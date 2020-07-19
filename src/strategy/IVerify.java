package strategy;

import domain.Result;

/**
 * 使用策略模式实现一个校验框架
 */
public interface IVerify {
    Result exec();
}
