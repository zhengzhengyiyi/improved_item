package com.zhengzhengyiyimc.goal;

import java.util.EnumSet;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FarAwayFromPlayerGoal extends Goal {
   protected final PathAwareEntity mob;
   private double targetX;
   private double targetY;
   private double targetZ;
   private final double speed;
   private final World world;

   public FarAwayFromPlayerGoal(PathAwareEntity mob, double speed) {
      this.mob = mob;
      this.speed = speed;
      this.world = mob.getWorld();
      this.setControls(EnumSet.of(Control.MOVE));
   }

   public boolean canStart() {
      if (this.mob.getTarget() != null) {
         return true;
      }

      boolean isPlayerNearby = this.mob.getWorld().getPlayers().stream()
         .anyMatch(player -> 
            this.mob.squaredDistanceTo(player) < 10 * 10
         );

      if (!isPlayerNearby) {
         return false;
      } else {
         return true;
      }
   }

   protected boolean targetShadedPos() {
      Vec3d vec3d = this.locateFarPos();
      if (vec3d == null) {
         return false;
      } else {
         this.targetX = vec3d.x;
         this.targetY = vec3d.y;
         this.targetZ = vec3d.z;
         return true;
      }
   }

   public boolean shouldContinue() {
      return !this.mob.getNavigation().isIdle();
   }

   public void start() {
      this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
   }

   @Nullable
   protected Vec3d locateFarPos() {
      Random random = this.mob.getRandom();
      PlayerEntity closestPlayer = this.world.getClosestPlayer(this.mob, 15);
      for(int i = 0; i < 10; ++i) {
         Vec3d postion = mob.getPos().add(
            random.nextInt(30) - 15,
            random.nextInt(6) - 3,
            random.nextInt(30) - 15
         );
         BlockPos targetPos = new BlockPos(Math.round((float)postion.x), Math.round((float)postion.y), Math.round((float)postion.z));
         boolean isFarFromPlayer = closestPlayer == null || targetPos.getSquaredDistance(closestPlayer.getBlockPos()) > 8 * 8;

         if (isFarFromPlayer) {
            return new Vec3d(targetPos.getX(), targetPos.getY(), targetPos.getZ());
         }
      }

      return null;
   }
}
