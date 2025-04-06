package ormanu.newfab.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ormanu.newfab.NewFab;

@Mixin(TridentEntity.class)
public abstract class TridentMixin extends PersistentProjectileEntity {

    @Shadow public abstract void tick();

    @Unique
    private int correctSlot = -1; // original slot

    protected TridentMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    // slot when trident thrown
    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
            at = @At("TAIL"))
    public void captureOriginalSlot(World world, LivingEntity owner, ItemStack stack, CallbackInfo ci) {
        if (owner instanceof PlayerEntity player) {
            // Find the slot that matches the tridentStack
            for (int i = 0; i < player.getInventory().size(); i++) {
                if (player.getInventory().getStack(i) == stack) {
                    this.correctSlot = i;
                    break;
                }
            }
        }
    }
    // inject at the beginning of the tick method
    @Inject(method = "tick", at = @At("HEAD"))
    private void injectLoyaltyOverride(CallbackInfo ci) {
        TridentEntity trident = (TridentEntity) (Object) this;

        // get the item stack of the trident
        ItemStack tridentStack = trident.getItemStack();

    }
    @Inject(method = "tryPickup", at = @At("HEAD"), cancellable = true)
    public void tryPickup(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        // Check if the player is the owner
        if (this.isOwner(player)) {
            // Check if the player's inventory is full
            boolean hasSpace = false;
            for (ItemStack stack : player.getInventory().main) {
                if (stack.isEmpty()) {
                    hasSpace = true;
                    break;
                }
            }

            // insert it into the player's inventory
            if (!player.isCreative() && !NewFab.CONFIG.goodTridentReturn && hasSpace) {
                player.getInventory().insertStack(this.asItemStack());
                cir.setReturnValue(true);
            }
            // if the trident is in no-clip mode and original slot is valid
            if (this.isNoClip() && this.correctSlot != -1) {
                // Place the trident in the original slot if it's empty
                if (player.getInventory().getStack(this.correctSlot).isEmpty()) {
                    player.getInventory().setStack(this.correctSlot, this.asItemStack());
                    cir.setReturnValue(true);
                }
            }

            if (!hasSpace && !player.isCreative()) {
                cir.setReturnValue(false);
            }
        }
    }
}
