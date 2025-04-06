package ormanu.newfab.mixin;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.numeric.NumericProperties;
import net.minecraft.client.render.item.property.numeric.NumericProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ormanu.newfab.NewFab;
import ormanu.newfab.util.IntegerComponentProperty;

@Mixin(NumericProperties.class)
public class NumericPropertyMixin {
    @Shadow @Final public static Codecs.IdMapper<Identifier, MapCodec<? extends NumericProperty>> ID_MAPPER;

    @Inject(method = "bootstrap", at = @At("HEAD"))
    private static void bootstrap(CallbackInfo ci) {
        System.out.println("Registered new properties");
        ID_MAPPER.put(Identifier.of(NewFab.MOD_ID, "int_property"), IntegerComponentProperty.CODEC);
    }
}
