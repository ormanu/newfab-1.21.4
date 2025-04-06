package ormanu.newfab.util;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ormanu.newfab.NewFab;

public class ModComponents {
    public static final ComponentType<Integer> INTEGER_PROPERTY = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(NewFab.MOD_ID, "integer_property"),
            ComponentType.<Integer>builder()
                    .codec(Codec.INT)
                    .build()
    );
    public static final ComponentType<Boolean> BOOLEAN_PROPERTY = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(NewFab.MOD_ID, "boolean_property"),
            ComponentType.<Boolean>builder()
                    .codec(Codec.BOOL)
                    .build()
    );

    public static void initialize() {}
}
