package com.zhengzhengyiyimc;

public enum Axes {
    WOODEN_AXE(0),
    STONE_AXE(1),
    IRON_AXE(2),
    DIAMOND_AXE(3),
    NETHERITE_AXE(4),
    GOLDEN_AXE(5);
    
    private int code;

    Axes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
