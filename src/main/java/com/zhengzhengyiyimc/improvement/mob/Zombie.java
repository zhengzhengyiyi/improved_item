package com.zhengzhengyiyimc.improvement.mob;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class Zombie {
    public static void register() {
        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (entity instanceof ZombieEntity && world.random.nextFloat() <= 0.08F) {
                for (int i = 0; i < 4; i++) {
                    ZombieEntity zombie = new ZombieEntity(EntityType.ZOMBIE, world);
                    zombie.setHealth(zombie.getMaxHealth());
                    zombie.refreshPositionAndAngles(
                        entity.getX(),
                        entity.getY(),
                        entity.getZ(),
                        entity.getYaw(),
                        entity.getPitch()
                    );
                    zombie.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
                    world.spawnEntity(zombie);
                }
            }
        });
    }
}
