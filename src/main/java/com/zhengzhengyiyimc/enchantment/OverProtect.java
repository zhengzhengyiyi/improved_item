package com.zhengzhengyiyimc.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class OverProtect extends Enchantment {
    public OverProtect() {
        super(Rarity.UNCOMMON,
            EnchantmentTarget.ARMOR_CHEST,
            new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.CHEST});
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        attacker.addVelocity(attacker.getRotationVector().normalize().negate().multiply(level * 1.1));
        attacker.setOnFire(true);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
