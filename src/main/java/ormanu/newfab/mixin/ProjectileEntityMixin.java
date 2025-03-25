package ormanu.newfab.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ormanu.newfab.NewFab;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin {
    @Inject(method = "onCollision", at = @At("HEAD"))
    private void onCollision(HitResult hitResult, CallbackInfo ci) {
        // Safe cast to ProjectileEntity
        ProjectileEntity projectile = (ProjectileEntity) (Object) this;

        // Ensure the world exists and is on the server side
        World world = projectile.getWorld();
        if (!(world instanceof ServerWorld serverWorld)) return;

        // Check for valid owner (LivingEntity shooter)
        if (!(projectile.getOwner() instanceof LivingEntity shooter)) return;

        // Check if the shooter has the ExplosiveEffect
        if (!shooter.hasStatusEffect(NewFab.ExplosiveEffect)) return;

        // Determine the hit position
        Vec3d hitPosition = (hitResult instanceof EntityHitResult eHitResult)
                ? eHitResult.getEntity().getPos()
                : hitResult.getPos();

        // Trigger a non-destructive explosion
        serverWorld.createExplosion(null, hitPosition.x, hitPosition.y, hitPosition.z, 1.5F, World.ExplosionSourceType.NONE);
    }
}