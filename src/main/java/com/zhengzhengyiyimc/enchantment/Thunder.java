package com.zhengzhengyiyimc.enchantment;

import java.util.Optional;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;

public class Thunder extends Enchantment {
    public Thunder() {
        super(new Properties(
            ItemTags.SWORDS,
            Optional.of(ItemTags.SWORDS),
            5,
            2,
            new Cost(0, 3),
            new Cost(1, 6),
            3,
            FeatureSet.of(FeatureFlags.VANILLA),
            new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}));
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.isIn(ItemTags.SWORDS) 
        || stack.isIn(ItemTags.AXES)
        || stack.isOf(Items.TRIDENT);
    }

    @Override
    public void onAttack(LivingEntity attacker, Entity target, int level) {
        LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, attacker.getWorld());
        lightningEntity.setPosition(target.getPos());
        target.getWorld().spawnEntity(lightningEntity);
        target.damage(attacker.getDamageSources().inFire(), level * 4);
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
