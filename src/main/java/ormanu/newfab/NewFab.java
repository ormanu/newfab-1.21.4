package ormanu.newfab;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ormanu.newfab.effect.ExplosiveEffect;
import ormanu.newfab.effect.FasterEffect;
import ormanu.newfab.enchantments.ModEnchantmentEffects;
import ormanu.newfab.items.ModItems;

public class NewFab implements ModInitializer {

	public static final String MOD_ID = "newfab";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModEnchantmentEffects.registerModEnchantmentEffects();

		LOGGER.info("coaie merge?");
		//registering the group
		Registry.register(Registries.ITEM_GROUP, NEWFAB_GROUP_KEY, NEWFAB_GROUP);
		addItems();


	}
		public static final RegistryKey<ItemGroup> NEWFAB_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "newfab_group"));
		public static final ItemGroup NEWFAB_GROUP = FabricItemGroup.builder()
				.icon(() -> new ItemStack(ModItems.LongSword))
				.displayName(Text.translatable("itemGroup.newfab.newfabs").withColor(0xBE75E2))
				.build();

		private void addItems(){
			ItemGroupEvents.modifyEntriesEvent(NEWFAB_GROUP_KEY).register(itemGroup -> {
				itemGroup.add(ModItems.LongSword);
				itemGroup.add(ModItems.NewCrossbow);
			});
		}

	public static final RegistryEntry<StatusEffect> FasterEffect;
	static {
		FasterEffect = registerEffect("faster", new FasterEffect().addAttributeModifier(EntityAttributes.STEP_HEIGHT, Identifier.ofVanilla("effect.step_height"), 1, EntityAttributeModifier.Operation.ADD_VALUE));
		}
	public static final RegistryEntry<StatusEffect> ExplosiveEffect;
	static {
		ExplosiveEffect = registerEffect("explosive", new ExplosiveEffect());
	}
	private static RegistryEntry<StatusEffect> registerEffect(String id, StatusEffect statusEffect) {
		return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, id), statusEffect);
	}

}