package com.zhengzhengyiyimc.network;

import com.zhengzhengyiyimc.Improved_item;
import com.zhengzhengyiyimc.entity.ThrowingAxeEntity;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ServerNetwork {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(
            MouseClickPacketPayload.ID,
            (payload, context) -> {
                World world = context.server().getOverworld();
                ThrowingAxeEntity throwingAxeEntity = new ThrowingAxeEntity(Improved_item.THROWING_AXE, world);
                throwingAxeEntity.setPosition(context.player().getPos().add(0, 1.4, 0));
                if (context.player().getMainHandStack().isIn(ItemTags.AXES)) throwingAxeEntity.setAxeStack(context.player().getMainHandStack()); else return;
                world.spawnEntity(throwingAxeEntity);
                throwingAxeEntity.addVelocity(context.player().getRotationVector().multiply(new Vec3d(1.5, 1.5, 1.5)));
            }
        );
    }
}
