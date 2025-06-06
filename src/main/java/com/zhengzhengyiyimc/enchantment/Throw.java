package com.zhengzhengyiyimc.enchantment;

import java.util.Optional;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;

public class Throw extends Enchantment {
    public Throw() {
        super(new Properties(
            ItemTags.AXES,
            Optional.of(ItemTags.AXES),
            5,
            2,
            new Cost(0, 4),
            new Cost(1, 7),
            3,
            FeatureSet.of(FeatureFlags.VANILLA),
            new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
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
