package ormanu.newfab.items;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import ormanu.newfab.NewFab;

public class LongSwordItem extends SwordItem {

    public LongSwordItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 8.0, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.6, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(Identifier.ofVanilla("base_attack_range"), 0.75, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack longswordStack = user.getStackInHand(hand);

        // Check if the sword has the "airspeed" enchantment
        if (!hasAirSpeedEnchant(longswordStack)) {
            // Return a failure, meaning the action cannot proceed
            return ActionResult.FAIL;
        }

        if (!world.isClient) {
            // Check cooldown and apply effect if allowed
            if (!user.getItemCooldownManager().isCoolingDown(longswordStack)) {
                user.addStatusEffect(new StatusEffectInstance(
                        NewFab.FasterEffect,
                        100 // Duration: 5 seconds (100 ticks)
                ));

                // Set a cooldown of 6 seconds (120 ticks)
                user.getItemCooldownManager().set(longswordStack, 120);
            }
        }

        return super.use(world, user, hand); // Perform normal right-click behavior
    }



    // Helper function to check if the crossbow has the AirSpeedEnchant
    private boolean hasAirSpeedEnchant(ItemStack stack) {
        return stack.hasEnchantments() && stack.getEnchantments().toString().contains("airspeed"); // Simplistic check
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        return false;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return super.postHit(stack, target, attacker);
    }

}
