package com.zhengzhengyiyimc;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import com.zhengzhengyiyimc.network.MouseClickPacketPayload;
import com.zhengzhengyiyimc.renderer.ThrowingAxeRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class Improved_itemClient implements ClientModInitializer {
	public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(new Identifier("zhengzhengyiyi", "throwing_axe"), "main");
	private static boolean isThrowing = false;
    private static int cooldownTicks = 0;

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(Improved_item.THROWING_AXE, ThrowingAxeRenderer::new);
		
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (cooldownTicks > 0) {
                cooldownTicks--;
                return;
            }

            boolean isRightMousePressed = client.mouse.wasRightButtonClicked();
            if (!isRightMousePressed || isThrowing) return;

			if (client.player == null) return;
			ItemStack stack = client.player.getMainHandStack();
			int code = 0;
			if (!stack.isIn(ItemTags.AXES)) return;
			if (stack.getItem() == Items.WOODEN_AXE) code = 0;
			if (stack.getItem() == Items.STONE_AXE) code = 1;
			if (stack.getItem() == Items.IRON_AXE) code = 2;
			if (stack.getItem() == Items.DIAMOND_AXE) code = 3;
			if (stack.getItem() == Items.NETHERITE_AXE) code = 4;
			if (stack.getItem() == Items.GOLDEN_AXE) code = 5;

			if (isRightMousePressed && !isThrowing) {
				if (client.player == null) return;
				if (stack.isIn(ItemTags.AXES) && hasSpecificEnchantment(stack, Improved_item.THROW_ENCHANTMENT)) ClientPlayNetworking.send(new MouseClickPacketPayload(code));
				cooldownTicks = 40;
			}
		});
	}

	public boolean hasSpecificEnchantment(ItemStack stack, Enchantment targetEnchant) {
        Set<RegistryEntry<Enchantment>> enchantments = stack.getEnchantments().getEnchantments();
        AtomicBoolean returnValue = new AtomicBoolean(false);

        enchantments.forEach(enchantment -> {
            if (enchantment.matchesId(new Identifier(Improved_item.MOD_ID, "throw"))) {
                returnValue.set(true);
            }
        });

        return returnValue.get();
    }
}
