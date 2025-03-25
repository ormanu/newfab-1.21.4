package ormanu.newfab.util;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import ormanu.newfab.NewFab;

public class ModTags {

    public static class Items {
        public static final TagKey<Item> AirSpeedCapable = createTag("airspeedcapable");
        public static final TagKey<Item> ExplosiveHitCapable = createTag("explosivehitcapable");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(NewFab.MOD_ID, name));
        }
    }
}
