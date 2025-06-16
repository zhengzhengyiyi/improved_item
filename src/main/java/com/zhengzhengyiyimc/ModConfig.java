package com.zhengzhengyiyimc;

import java.io.Serializable;

public class ModConfig implements Serializable {
    public float movingHitDamage = 8.0f;

    // throwing axe
    public boolean enableThrowingAxe = true;
    public float throwingAxeDamage = 12.0f;

    // general
    public int additionMovementDamage = 0;
}
