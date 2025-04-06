package ormanu.newfab;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ormanu.newfab.config.ModConfig;
import ormanu.newfab.effect.ExplosiveEffect;
import ormanu.newfab.effect.FasterEffect;
import ormanu.newfab.enchantments.ModEnchantmentEffects;
import ormanu.newfab.entities.ModEntities;
import ormanu.newfab.items.ModItems;
import ormanu.newfab.networking.*;
import ormanu.newfab.sounds.ModSounds;
import ormanu.newfab.util.ItemSlotSoundHandler;
import ormanu.newfab.util.ModComponents;
import ormanu.newfab.util.TickDelayScheduler;
import ormanu.newfab.util.TransformResourceReloadListener;

public class NewFab implements ModInitializer {

	public static final String MOD_ID = "newfab";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ModConfig CONFIG = new ModConfig();

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModEnchantmentEffects.registerModEnchantmentEffects();
		ModComponents.initialize();
		ModEntities.initialize();

		LOGGER.info("coaie merge?");
		//registering the group
		Registry.register(Registries.ITEM_GROUP, NEWFAB_GROUP_KEY, NEWFAB_GROUP);
		addItems();
		Registries.ITEM.forEach(item -> {
			if (item instanceof ItemSlotSoundHandler soundItem) {
				if (soundItem instanceof SwordItem || soundItem instanceof AxeItem) {
					soundItem.newfab$getUnsheatheSound(ModSounds.SWORD_UNSHEATH);
				}
			}
		});

		ServerTickEvents.END_SERVER_TICK.register(server -> TickDelayScheduler.tick());

		//json
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new TransformResourceReloadListener());

		PayloadTypeRegistry.playC2S().register(BackSlotPacketPayload.ID, BackSlotPacketPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(BackSlotInventoryPacketPayload.BACKSLOT_INVENTORY_PACKET_ID, BackSlotInventoryPacketPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(BackSlotCreativeClientPacketPayload.BACKSLOT_CREATIVE_CLIENT_PACKET_ID, BackSlotCreativeClientPacketPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(BackSlotClientPacketPayload.BACKSLOT_CLIENT_PACKET_ID, BackSlotClientPacketPayload.CODEC);

		BackSlotInventoryPacketReceiver.registerServerPacket();
		BackSlotServerPacket.registerServerPacket();
		BackSlotCreativeClientPacket.registerClientPacket();

		//config
		AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
	}
	// END OF ON INITIALIZE

	//item group
		public static final RegistryKey<ItemGroup> NEWFAB_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(MOD_ID, "newfab_group"));
		public static final ItemGroup NEWFAB_GROUP = FabricItemGroup.builder()
				.icon(() -> new ItemStack(ModItems.LongSword))
				.displayName(Text.translatable("itemGroup.newfab.newfabs").withColor(0xBE75E2))
				.build();

		private void addItems(){
			ItemGroupEvents.modifyEntriesEvent(NEWFAB_GROUP_KEY).register(itemGroup -> {
				itemGroup.add(ModItems.LongSword);
				itemGroup.add(ModItems.NewCrossbow);
				itemGroup.add(ModItems.NewTrident);
				itemGroup.add(ModItems.Scythe);
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