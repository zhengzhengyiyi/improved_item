package com.zhengzhengyiyimc.network;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
// import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class MouseClickPacketPayload implements CustomPayload, FabricPacket {
    private static final Identifier MOUSE_CLICK_PACKET_ID = Identifier.of("zhengzhengyiyi", "mouse_click");
    public final int message;

    public static final Identifier ID = MOUSE_CLICK_PACKET_ID;

    public void write(PacketByteBuf buf) {
        buf.writeInt(message);
    }

    public MouseClickPacketPayload(int message) {
        this.message = message;
    }

    public MouseClickPacketPayload(PacketByteBuf buf) {
        this.message = buf.readInt();
    }

    @Override
    public Identifier id() {
        return MOUSE_CLICK_PACKET_ID;
    }

    @Override
    public PacketType<?> getType() {
        return PacketType.create(ID, MouseClickPacketPayload::new);
    }
}
