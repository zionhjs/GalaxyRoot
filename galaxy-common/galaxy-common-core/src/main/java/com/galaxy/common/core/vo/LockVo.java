package com.galaxy.common.core.vo;

/**
 * @Author chenjinming
 * @ClassName LockVo
 * @Description TOOD
 * @Date 2019/9/18 14:56
 **/
public class LockVo {
    private String name;
    private String value;

    public LockVo(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
