package com.zhengzhengyiyimc;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.component.type.ItemEnchantmentsComponent;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhengzhengyiyimc.effect.IgnoreLightningEffect;
import com.zhengzhengyiyimc.enchantment.OverProtect;
import com.zhengzhengyiyimc.enchantment.Thunder;
import com.zhengzhengyiyimc.entity.ThrowingAxeEntity;
import com.zhengzhengyiyimc.improvement.mob.Skeleton;
import com.zhengzhengyiyimc.improvement.mob.Zombie;
import com.zhengzhengyiyimc.network.MouseClickPacketPayload;
import com.zhengzhengyiyimc.network.ServerNetwork;

public class Improved_item implements ModInitializer {
	public static final String MOD_ID = "improved_item";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private int tickCounter = 0;
	public static final Enchantment THUNDER_ENCHANTMENT = new Thunder();
	public static final Enchantment OVERPROTECT_ENCHANTMENT = new OverProtect();
	public static final RegistryEntry<StatusEffect> IGNORE_LIGHTNING_EFFECT_ENTRY = Registry.registerReference(Registries.STATUS_EFFECT, new Identifier("improved_item", "ignore_lightningbolt"), new IgnoreLightningEffect());
	public static final List<Map<PlayerEntity, Boolean>> LOW_HEALTH_PLAYER = new ArrayList<>();
	public static final EntityType<ThrowingAxeEntity> THROWING_AXE =
        EntityType.Builder.<ThrowingAxeEntity>create(ThrowingAxeEntity::new, SpawnGroup.MISC)
			.dimensions(0.3F, 0.3F)
            .build("throwing_axe");

	@Override
	public void onInitialize() {
		Zombie.register();
		Skeleton.register();
		PayloadTypeRegistry.playC2S().register(MouseClickPacketPayload.ID, MouseClickPacketPayload.CODEC);
		ServerNetwork.register();
		Registry.register(Registries.ENCHANTMENT, new Identifier(MOD_ID, "thunder"), THUNDER_ENCHANTMENT);
		Registry.register(Registries.ENCHANTMENT, new Identifier(MOD_ID, "over_protect"), OVERPROTECT_ENCHANTMENT);
		Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier("improved_item", "throwing_axe"),
			THROWING_AXE
		);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(createEnchantedBook(THUNDER_ENCHANTMENT, 1));
            entries.add(createEnchantedBook(THUNDER_ENCHANTMENT, 2));
			entries.add(createEnchantedBook(OVERPROTECT_ENCHANTMENT, 1));
			entries.add(createEnchantedBook(OVERPROTECT_ENCHANTMENT, 2));
			entries.add(createEnchantedBook(OVERPROTECT_ENCHANTMENT, 3));
			entries.add(createEnchantedBook(OVERPROTECT_ENCHANTMENT, 4));
			entries.add(createEnchantedBook(OVERPROTECT_ENCHANTMENT, 5));
        });

		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
			ItemStack tool = player.getMainHandStack();
			if (tool.getDamage() > 0 && player.experienceLevel > 0) {
				tool.setDamage(tool.getDamage() - Math.round(world.random.nextFloat() > 0.5 ? 1 : 2));
				player.addExperienceLevels(Math.round(world.random.nextFloat() > 0.1 ? 1 : 0));
			}

			return ActionResult.PASS;
		});

		ServerTickEvents.END_WORLD_TICK.register(world -> {
			if (world.isThundering()) {
				tickCounter++;
				if (tickCounter % 40 == 0) {
					world.getPlayers().forEach(player -> {
						if (player.getMainHandStack().isIn(ItemTags.SWORDS)) {
							LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
							player.addStatusEffect(new StatusEffectInstance(IGNORE_LIGHTNING_EFFECT_ENTRY, 260, 1));
							lightning.setPosition(player.getPos());
							world.spawnEntity(lightning);
						} else if (player.getOffHandStack().isIn(ItemTags.SWORDS)) {
							LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
							player.addStatusEffect(new StatusEffectInstance(IGNORE_LIGHTNING_EFFECT_ENTRY, 260));
							lightning.setPosition(player.getPos());
							world.spawnEntity(lightning);
						} else {
							player.removeStatusEffect(IGNORE_LIGHTNING_EFFECT_ENTRY);
						}
					});
				}
			}
			world.getPlayers().forEach(player -> {
				if (!player.hasStatusEffect(IGNORE_LIGHTNING_EFFECT_ENTRY)) {
					player.setInvulnerable(false);
				}
			});
		});

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (player.getHealth() < 6) {
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200));
				HashMap<PlayerEntity, Boolean> playerMap = new HashMap<PlayerEntity, Boolean>();
				playerMap.put(player, true);
				LOW_HEALTH_PLAYER.add(playerMap);
			} else {
				for (Map<PlayerEntity, Boolean> playerMap : LOW_HEALTH_PLAYER) {
					if (playerMap.keySet().contains(player)) {
						playerMap.replace((PlayerEntity) playerMap.keySet().toArray()[0], false);
					}
				}
			}

			return ActionResult.PASS;
		});

		ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
			if (entity instanceof ZombieEntity zombieEntity) {
				int day = (int)(world.getTimeOfDay() / 24000L);
				if (day > 20) {
					zombieEntity.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));
				}
			}
		});
	}
	private static ItemStack createEnchantedBook(Enchantment enchantment, int level) {
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.set(book, ItemEnchantmentsComponent.DEFAULT);
        return book;
    }
}
