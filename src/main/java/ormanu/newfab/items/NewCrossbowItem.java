package ormanu.newfab.items;


import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import ormanu.newfab.NewFab;

public class NewCrossbowItem extends net.minecraft.item.CrossbowItem {
    public NewCrossbowItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack crossbowStack = user.getStackInHand(hand);
        boolean sneaking = user.isSneaking();
        if (user.isSneaking()) {
            // Check if the crossbow has the "explosivehit" enchantment
            if (!hasExplosiveHitEnchant(crossbowStack)) {
                // Return a failure, meaning the action cannot proceed
                return ActionResult.FAIL;
            }

            if (!world.isClient) {
                // Check cooldown and apply effect if allowed
                if (!user.getItemCooldownManager().isCoolingDown(crossbowStack)) {
                    user.addStatusEffect(new StatusEffectInstance(
                            NewFab.ExplosiveEffect,
                            50 // Duration: 5 seconds (100 ticks)
                    ));

                    // Set a cooldown of 6 seconds (120 ticks)
                    user.getItemCooldownManager().set(crossbowStack, 100);
                }
            }

            return super.use(world, user, hand); // Perform normal right-click behavior
        }

        return super.use(world, user, hand); // Perform normal right-click behavior
    }

    // Helper function to check if the crossbow has the ExplosiveHitEnchant
    private boolean hasExplosiveHitEnchant(ItemStack stack) {
        return stack.hasEnchantments() && stack.getEnchantments().toString().contains("explosivehit"); // Simplistic check
    }
}
