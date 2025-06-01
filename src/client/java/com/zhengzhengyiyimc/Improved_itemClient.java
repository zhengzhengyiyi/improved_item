package com.zhengzhengyiyimc;

import org.lwjgl.glfw.GLFW;

import com.zhengzhengyiyimc.network.ModPackets;
import com.zhengzhengyiyimc.renderer.ThrowingAxeRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class Improved_itemClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(Improved_item.THROWING_AXE, (ctx) -> {
			return new ThrowingAxeRenderer(ctx);
		});
        
        KeyBinding recallKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.throwingaxe.recall",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.throwingaxe"
        ));
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (recallKey.wasPressed()) {
                ModPackets.sendRecallPacket();
            }
        });
	}
}