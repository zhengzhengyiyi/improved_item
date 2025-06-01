package com.zhengzhengyiyimc.mixin.mob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;

@Mixin(MobEntity.class)
public class MobEntityMixin {
    @Shadow
    protected GoalSelector goalSelector;
}
