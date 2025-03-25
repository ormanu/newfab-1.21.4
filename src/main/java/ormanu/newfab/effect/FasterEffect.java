package ormanu.newfab.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class FasterEffect extends StatusEffect {
    public FasterEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xFFFFF);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true; // Update effect every tick
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        entity.fallDistance = 0;
        return true;
    }

}
