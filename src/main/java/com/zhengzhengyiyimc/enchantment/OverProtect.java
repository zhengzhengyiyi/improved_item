package com.zhengzhengyiyimc.enchantment;

import java.util.Optional;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;

public class OverProtect extends Enchantment {
    public OverProtect() {
        super(new Properties(
            ItemTags.CHEST_ARMOR,
            Optional.of(ItemTags.CHEST_ARMOR),
            5,
            2,
            new Cost(0, 3),
            new Cost(1, 6),
            3,
            FeatureSet.of(FeatureFlags.VANILLA),
            new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.CHEST}));
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
}
