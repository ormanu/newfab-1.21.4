package ormanu.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ormanu.newfab.NewFab;

public class ModItems {

    public static final Item TEST = registerItem("test", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(NewFab.MOD_ID, name), item);
    }
    public static void registerModItems() {
        NewFab.LOGGER.info("Registering Mod Items for " + NewFab.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(TEST);
        });
    }

}
