package com.zhengzhengyiyimc.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class IgnoreLightningEffect extends StatusEffect {
	public IgnoreLightningEffect() {
		super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
		// entity.isInvulnerableTo(entity.getWorld().getDamageSources().lightningBolt());
		entity.setInvulnerable(true);
		return true;
	}
}
