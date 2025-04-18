package ormanu.newfab.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public record TransformData(
        Identifier item,
        List<Float> scale,
        List<Float> rotation,
        List<Float> translation,
        ModelTransformationMode mode,
        Float sway,
        Map<String, SubTransformData> componentTransforms, // Map of int -> TransformData
        SecondaryTransformData secondaryTransforms,
        TertiaryTransformData tertiaryTransforms
) {
    public static final Codec<TransformData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("item").forGetter(TransformData::item),
            Codec.FLOAT.listOf().fieldOf("scale").orElseGet(() -> List.of(1.0f, 1.0f, 1.0f)).forGetter(TransformData::scale),
            Codec.FLOAT.listOf().fieldOf("rotation").orElseGet(() -> List.of(0.0f, 0.0f, 0.0f)).forGetter(TransformData::rotation),
            Codec.FLOAT.listOf().fieldOf("translation").orElseGet(() -> List.of(0.0f, 0.0f, 0.0f)).forGetter(TransformData::translation),
            Codec.STRING.fieldOf("mode").orElse("FIXED")
                    .xmap(ModelTransformationMode::valueOf, ModelTransformationMode::name)
                    .forGetter(TransformData::mode),
            Codec.FLOAT.fieldOf("sway").orElse(1.0F).forGetter(TransformData::sway),
            Codec.unboundedMap(Codec.STRING, SubTransformData.CODEC) // Map<Integer, SubTransformData>
                    .fieldOf("componentTransforms").orElse(Map.of())
                    .forGetter(TransformData::componentTransforms),
            SecondaryTransformData.CODEC.fieldOf("secondary").orElse(new SecondaryTransformData(
                            Identifier.of("null"),
                            List.of(1.0f, 1.0f, 1.0f),
                            List.of(0.0f, 0.0f, 0.0f),
                            List.of(0.0f, 0.0f, 0.0f),
                            ModelTransformationMode.NONE
                    ))
                    .forGetter(TransformData::secondaryTransforms),
            TertiaryTransformData.CODEC.fieldOf("tertiary").orElse(new TertiaryTransformData(
                            Identifier.of("null"),
                            List.of(1.0f, 1.0f, 1.0f),
                            List.of(0.0f, 0.0f, 0.0f),
                            List.of(0.0f, 0.0f, 0.0f),
                            ModelTransformationMode.NONE
                    ))
                    .forGetter(TransformData::tertiaryTransforms)
    ).apply(instance, TransformData::new));

    // Sub-class to store transformations per component value
    public record SubTransformData(
            List<Float> scale,
            List<Float> rotation,
            List<Float> translation,
            ModelTransformationMode mode,
            Float sway,
            SecondaryTransformData secondaryTransforms,
            TertiaryTransformData tertiaryTransforms
    ) {
        public static final Codec<SubTransformData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.FLOAT.listOf().fieldOf("scale").orElseGet(() -> List.of(1.0f, 1.0f, 1.0f)).forGetter(SubTransformData::scale),
                Codec.FLOAT.listOf().fieldOf("rotation").orElseGet(() -> List.of(0.0f, 0.0f, 0.0f)).forGetter(SubTransformData::rotation),
                Codec.FLOAT.listOf().fieldOf("translation").orElseGet(() -> List.of(0.0f, 0.0f, 0.0f)).forGetter(SubTransformData::translation),
                Codec.STRING.fieldOf("mode").orElse("FIXED")
                        .xmap(ModelTransformationMode::valueOf, ModelTransformationMode::name)
                        .forGetter(SubTransformData::mode),
                Codec.FLOAT.fieldOf("sway").orElse(1.0F).forGetter(SubTransformData::sway),
                SecondaryTransformData.CODEC.fieldOf("secondary")
                        .forGetter(SubTransformData::secondaryTransforms),
                TertiaryTransformData.CODEC.fieldOf("tertiary")
                        .forGetter(SubTransformData::tertiaryTransforms)
        ).apply(instance, SubTransformData::new));
    }

    public record SecondaryTransformData(
            Identifier item,
            List<Float> scale,
            List<Float> rotation,
            List<Float> translation,
            ModelTransformationMode mode
    ) {
        public static final Codec<SecondaryTransformData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf("model").forGetter(SecondaryTransformData::item),
                Codec.FLOAT.listOf().fieldOf("scale").orElseGet(() -> List.of(1.0f, 1.0f, 1.0f)).forGetter(SecondaryTransformData::scale),
                Codec.FLOAT.listOf().fieldOf("rotation").orElseGet(() -> List.of(0.0f, 0.0f, 0.0f)).forGetter(SecondaryTransformData::rotation),
                Codec.FLOAT.listOf().fieldOf("translation").orElseGet(() -> List.of(0.0f, 0.0f, 0.0f)).forGetter(SecondaryTransformData::translation),
                Codec.STRING.fieldOf("mode").orElse("FIXED")
                        .xmap(ModelTransformationMode::valueOf, ModelTransformationMode::name)
                        .forGetter(SecondaryTransformData::mode)
        ).apply(instance, SecondaryTransformData::new));
    }

    public record TertiaryTransformData(
            Identifier item,
            List<Float> scale,
            List<Float> rotation,
            List<Float> translation,
            ModelTransformationMode mode
    ) {
        public static final Codec<TertiaryTransformData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf("model").forGetter(TertiaryTransformData::item),
                Codec.FLOAT.listOf().fieldOf("scale").orElseGet(() -> List.of(1.0f, 1.0f, 1.0f)).forGetter(TertiaryTransformData::scale),
                Codec.FLOAT.listOf().fieldOf("rotation").orElseGet(() -> List.of(0.0f, 0.0f, 0.0f)).forGetter(TertiaryTransformData::rotation),
                Codec.FLOAT.listOf().fieldOf("translation").orElseGet(() -> List.of(0.0f, 0.0f, 0.0f)).forGetter(TertiaryTransformData::translation),
                Codec.STRING.fieldOf("mode").orElse("FIXED")
                        .xmap(ModelTransformationMode::valueOf, ModelTransformationMode::name)
                        .forGetter(TertiaryTransformData::mode)
        ).apply(instance, TertiaryTransformData::new));
    }
}
