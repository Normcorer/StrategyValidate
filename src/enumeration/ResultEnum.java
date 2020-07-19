package enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ResultEnum {
    SUCCESS(0, "成功"),
    FAILED(400, "失败"),
    PASSWORD_ERROR(401, "密码错误"),
    VALIDATE_ERROR(402, "验证码错误");

    private int value;
    private String description;

    ResultEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionByValue(int value) {
        return Arrays.stream(values())
                .filter(x -> x.getValue() == value).findFirst().map(ResultEnum::getDescription).orElse("");
    }

    public List<ResultEnum> getList() {
        return new ArrayList<>(Arrays.asList(values()));
    }
}
