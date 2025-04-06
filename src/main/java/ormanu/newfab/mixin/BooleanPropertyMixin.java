package ormanu.newfab.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ormanu.newfab.NewFab;
import ormanu.newfab.util.BooleanComponentProperty;

@Mixin(BooleanProperties.class)
public class BooleanPropertyMixin {
    @Shadow @Final public static Codecs.IdMapper<Identifier, MapCodec<? extends BooleanProperty>> ID_MAPPER;

    @Inject(method = "bootstrap", at = @At("HEAD"))
    private static void bootstrap(CallbackInfo ci) {
        ID_MAPPER.put(Identifier.of(NewFab.MOD_ID, "bool_property"), BooleanComponentProperty.CODEC);
    }
}
