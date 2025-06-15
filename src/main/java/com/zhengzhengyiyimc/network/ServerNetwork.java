package com.zhengzhengyiyimc.network;

import com.zhengzhengyiyimc.Axes;
import com.zhengzhengyiyimc.Improved_item;
import com.zhengzhengyiyimc.entity.ThrowingAxeEntity;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ServerNetwork {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(
            MouseClickPacketPayload.ID,
            (payload, context) -> {
                World world = context.server().getOverworld();
                ThrowingAxeEntity throwingAxeEntity = new ThrowingAxeEntity(Improved_item.THROWING_AXE, world);
                Item axe = Items.IRON_AXE;
                if (payload.message == Axes.WOODEN_AXE.getCode()) axe = Items.WOODEN_AXE;
                if (payload.message == Axes.STONE_AXE.getCode()) axe = Items.STONE_AXE;
                if (payload.message == Axes.IRON_AXE.getCode()) axe = Items.IRON_AXE;
                if (payload.message == Axes.GOLDEN_AXE.getCode()) axe = Items.GOLDEN_AXE;
                if (payload.message == Axes.DIAMOND_AXE.getCode()) axe = Items.DIAMOND_AXE;
                if (payload.message == Axes.NETHERITE_AXE.getCode()) axe = Items.NETHERITE_AXE;

                throwingAxeEntity.setPosition(context.player().getPos().add(0, 1.4, 0));
                throwingAxeEntity.setAxeStack(new ItemStack(axe));

                if (!context.player().isCreative()) context.player().getMainHandStack().setDamage(1);

                world.spawnEntity(throwingAxeEntity);
                throwingAxeEntity.addVelocity(context.player().getRotationVector().multiply(new Vec3d(1.5, 1.5, 1.5)));
            }
        );
    }
}
