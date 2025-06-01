package com.zhengzhengyiyimc.network;

import java.util.function.Predicate;

import com.zhengzhengyiyimc.entity.ThrowingAxeEntity;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;

public class ModPackets {
    public static final Identifier RECALL_PACKET = new Identifier("throwingaxe", "recall");
    
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(Recall.ID, 
            (payload, context) -> {
                context.server().execute(() -> {
                    PlayerEntity player = context.player();
                    Predicate<ThrowingAxeEntity> safePredicate = axe -> 
                        axe != null &&
                        player != null &&
                        axe.getOwner() != null &&
                        axe.getOwner().getUuid().equals(player.getUuid());
                    for (Entity entity : player.getWorld().getEntitiesByClass(ThrowingAxeEntity.class, new Box(player.getPos().x - 8 * 5, -60, player.getPos().z - 8 * 5, player.getPos().x + 8 * 5, 256, player.getPos().z + 8 * 5), safePredicate)) {
                        if (entity instanceof ThrowingAxeEntity axe &&
                            axe.getOwner() == player) {
                            sendRecallPacket();
                        }
                    }
                });
            });
    }
    
    public static void sendRecallPacket() {
        ClientPlayNetworking.send(new Recall());
    }

    public static class Recall implements CustomPayload {
        public static CustomPayload.Id<Recall> ID = new CustomPayload.Id<>(RECALL_PACKET);

        public CustomPayload.Id<Recall> getId() {
            return ID;
        }
    }
}