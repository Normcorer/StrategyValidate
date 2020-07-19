package strategy;

import domain.Result;

import java.util.LinkedList;
import java.util.List;

public class Context {
    private List<IVerify> verifyList;

    public Context() {
        verifyList = new LinkedList<>();
    }

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
