package ormanu.newfab.enchantments;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import ormanu.newfab.NewFab;
import ormanu.newfab.enchantments.custom.AirSpeedEffect;
import ormanu.newfab.enchantments.custom.ExplosiveHitEffect;

public class ModEnchantmentEffects {
    public static final RegistryKey<Enchantment> AIRSPEED = of("airspeed");
    public static final RegistryKey<Enchantment> EXPLOSIVEHIT = of("explosivehit");
    public static MapCodec<AirSpeedEffect> AIRSPEED_EFFECT = register("airspeedffect", AirSpeedEffect.CODEC);
    public static MapCodec<ExplosiveHitEffect> EXPLOSIVEHIT_EFFECT = register("explosivehiteffect", ExplosiveHitEffect.CODEC);

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = Identifier.of(NewFab.MOD_ID, path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(NewFab.MOD_ID, id), codec);
    }

    public static void registerModEnchantmentEffects() {
        NewFab.LOGGER.info("Registering EnchantmentEffects for" + NewFab.MOD_ID);
    }
}
