package ormanu.newfab.items;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import ormanu.newfab.NewFab;

public class ModItems {

    public static final Item LongSword = registerItem("longsword", new LongSwordItem(ToolMaterial.NETHERITE,4.5f, -2.8f, new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(NewFab.MOD_ID, "longsword")))
            .attributeModifiers(LongSwordItem.createAttributeModifiers()).enchantable(15).rarity(Rarity.EPIC).fireproof().maxCount(1)
    ));
    public static final Item NewCrossbow = registerItem("newcrossbow", new NewCrossbowItem(new Item.Settings().maxCount(1).fireproof()
            .enchantable(15).rarity(Rarity.UNCOMMON)
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(NewFab.MOD_ID, "newcrossbow")))));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(NewFab.MOD_ID, name), item);
    }

    public static void initialize() {
        NewFab.LOGGER.info("NewFab Items Initialized");
    }

}
