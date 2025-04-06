package ormanu.newfab.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import ormanu.newfab.NewFab;

public class ModEntities {
    public static final EntityType<NewTridentEntity> NEW_TRIDENT_ENTITY = register(
            "newtridententity",
            EntityType.Builder.<NewTridentEntity>create(NewTridentEntity::new, SpawnGroup.MISC)
                    .dropsNothing()
                    .dimensions(1F, 1F)
    );

    private static <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> key, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return register(keyOf(id), type);
    }

    private static RegistryKey<EntityType<?>> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(NewFab.MOD_ID, id));
    }

    public static void initialize() {
    }
}
