package ormanu.newfab.entities.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Unique;
import ormanu.newfab.entities.NewTridentEntity;

@Environment(EnvType.CLIENT)
public class NewTridentEntityRenderer extends EntityRenderer<NewTridentEntity, NewTridentRenderState> {
    public NewTridentEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
    public void render(NewTridentRenderState ShovelRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        // Push the matrix stack for transformations
        matrixStack.push();

        float multiplier = 1.25F;

        matrixStack.translate(ShovelRenderState.entity.getRotationVec(0).multiply(multiplier, multiplier, -multiplier));

        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(ShovelRenderState.entity.getYaw() - 180.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(ShovelRenderState.entity.getPitch() - 105.0F));

        matrixStack.scale(1.5F, 1.5F, 1.5F);
        matrixStack.translate(-0.1, 0, -0.1);

        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(15));

        if (ShovelRenderState.entity instanceof NewTridentEntity entity) {
            ItemStack shovel = entity.shovelStack;
            shovel.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, entity.isEnchanted());

            // Use the ItemRenderer to render the trident item with a FIRST_PERSON_RIGHT_HAND transformation
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            itemRenderer.renderItem(shovel, ModelTransformationMode.FIRST_PERSON_RIGHT_HAND, light, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider, MinecraftClient.getInstance().world, 0);
        }
        // Pop the matrix stack to clean up transformations
        matrixStack.pop();
    }

    @Unique
    private Vec3d transformToWorld(Matrix4f matrix, Camera camera) {
        // Convert (0,0,0) in local item space to transformed coordinates
        Vector4f localPos = new Vector4f(0, 0, 0, 1);
        matrix.transform(localPos);

        // Convert view space to world space by adding the camera position
        Vec3d cameraPos = camera.getPos();
        return new Vec3d(cameraPos.x + localPos.x(), cameraPos.y + localPos.y(), cameraPos.z + localPos.z());
    }

    public NewTridentRenderState createRenderState() {
        return new NewTridentRenderState();
    }

    public void updateRenderState(NewTridentEntity myriadShovelEntity, NewTridentRenderState myriadShovelRenderState, float f) {
        super.updateRenderState(myriadShovelEntity, myriadShovelRenderState, f);
        myriadShovelRenderState.entity = myriadShovelEntity;
    }
}
