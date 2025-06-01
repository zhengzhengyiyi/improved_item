package com.zhengzhengyiyimc.mixin.mob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.zhengzhengyiyimc.goal.FarAwayFromPlayerGoal;

import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.PathAwareEntity;

@Mixin(AbstractSkeletonEntity.class)
public class AbstractSkeletonEntityMixin extends MobEntityMixin {
    @Inject(at = @At("TAIL"), cancellable = true, method = "initGoals")
    private void initGoals(CallbackInfo ci) {
        this.goalSelector.add(5, new FarAwayFromPlayerGoal(((PathAwareEntity) (Object) this), 1.0));
    }
}
