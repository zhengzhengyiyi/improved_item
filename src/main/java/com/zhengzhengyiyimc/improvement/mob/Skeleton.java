package com.zhengzhengyiyimc.improvement.mob;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class Skeleton {
    public static void register() {
        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (entity instanceof SkeletonEntity && world.random.nextFloat() <= 0.08F) {
                for (int i = 0; i < 3; i++) {
                    ZombieEntity skeleton = new ZombieEntity(EntityType.ZOMBIE, world);
                    skeleton.setHealth(skeleton.getMaxHealth());
                    skeleton.refreshPositionAndAngles(
                        entity.getX(),
                        entity.getY(),
                        entity.getZ(),
                        entity.getYaw(),
                        entity.getPitch()
                    );
                    skeleton.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                    world.spawnEntity(skeleton);
                }
            }
        });
    }
}
