package com.zhengzhengyiyimc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class MouseClickPacketPayload implements CustomPayload {
    private static final Identifier MOUSE_CLICK_PACKET_ID = Identifier.of("zhengzhengyiyi", "mouse_click");
    public static final PacketCodec<ByteBuf, MouseClickPacketPayload> CODEC = 
    PacketCodec.of(
        (payload, buf) -> buf.writeInt(payload.message),
        buf -> new MouseClickPacketPayload(buf.readInt())
    );
    public final int message;

    public static final Id<MouseClickPacketPayload> ID = new Id<>(MOUSE_CLICK_PACKET_ID);

    public MouseClickPacketPayload(int message) {
        this.message = message;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return new Id<>(MOUSE_CLICK_PACKET_ID);
    }
}
