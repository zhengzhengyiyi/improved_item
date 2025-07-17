package com.zhengzhengyiyimc.network;

import com.zhengzhengyiyimc.Axes;
import com.zhengzhengyiyimc.Improved_item;
import com.zhengzhengyiyimc.entity.ThrowingAxeEntity;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ServerNetwork {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(
            MouseClickPacketPayload.ID,
            (MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) -> {
                if (!Improved_item.modConfig.enableThrowingAxe) return;
                
                World world = player.getServer().getOverworld();
                int axe_type = buf.readInt();
                ThrowingAxeEntity throwingAxeEntity = new ThrowingAxeEntity(Improved_item.THROWING_AXE, world);
                Item axe = Items.IRON_AXE;
                if (axe_type == Axes.WOODEN_AXE.getCode()) axe = Items.WOODEN_AXE;
                if (axe_type == Axes.STONE_AXE.getCode()) axe = Items.STONE_AXE;
                if (axe_type == Axes.IRON_AXE.getCode()) axe = Items.IRON_AXE;
                if (axe_type == Axes.GOLDEN_AXE.getCode()) axe = Items.GOLDEN_AXE;
                if (axe_type == Axes.DIAMOND_AXE.getCode()) axe = Items.DIAMOND_AXE;
                if (axe_type == Axes.NETHERITE_AXE.getCode()) axe = Items.NETHERITE_AXE;

                throwingAxeEntity.setPosition(player.getPos().add(0, 1.4, 0));
                throwingAxeEntity.setAxeStack(new ItemStack(axe));

                if (!player.isCreative()) player.getMainHandStack().setDamage(1);

                world.spawnEntity(throwingAxeEntity);
                throwingAxeEntity.addVelocity(player.getRotationVector().multiply(new Vec3d(1.5, 1.5, 1.5)));
            }
        );
    }
}
