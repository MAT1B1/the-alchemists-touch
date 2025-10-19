package com.matibi.thealchemiststouch.client;

import com.matibi.thealchemiststouch.effect.ModEffects;
import com.matibi.thealchemiststouch.util.ModTags;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public final class OreESP {
    private static int R = 5;
    private static final int PERIOD_T   = 20 * 3; // 5s
    private static final int DURATION_T = 20 * 2; // 2s visibles
    private static final int FADE_T     = 10;     // ~0.5s de fondu

    private static List<BlockPos> cached = List.of();
    private static int tick, pulseStartTick;
    private static boolean pulseActive, hadEffect;
    private static float pulseAlpha;

    public static final RenderPipeline XRAY_PIPELINE = RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET)
            .withLocation("pipeline/xray_pos_color_no_depth")
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withDepthWrite(false)
            .withBlend(BlendFunction.TRANSLUCENT)
            .withCull(false)
            .build();

    private static final RenderLayer.MultiPhase XRAY = RenderLayer.of(
            "xray",
            1,
            false,
            true,
            XRAY_PIPELINE,
            RenderLayer.MultiPhaseParameters.builder()
                    .texture(RenderPhase.NO_TEXTURE)
                    .layering(RenderPhase.VIEW_OFFSET_Z_LAYERING)
                    .target(RenderPhase.ITEM_ENTITY_TARGET)
                    .build(false)
    );

    private OreESP() {}

    public static void init() {
        // PULSE + scan
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null || client.player == null) return;

            boolean has = client.player.hasStatusEffect(ModEffects.ORE_SENSE);
            if (!has) { cached = List.of(); pulseActive = false; hadEffect = false; pulseAlpha = 0f; return; }

            int t = ++tick;

            if (!hadEffect) {
                hadEffect = true;
                pulseStartTick = t;
                cached = scanOres(client.world, client.player.getBlockPos());
            }

            int since = t - pulseStartTick;
            if (since >= PERIOD_T) {
                pulseStartTick = t;
                since = 0;
                cached = scanOres(client.world, client.player.getBlockPos());
            }

            pulseActive = since < DURATION_T;

            if (pulseActive) {
                float in  = clamp01(since / (float) FADE_T);
                float out = clamp01((DURATION_T - since) / (float) FADE_T);
                pulseAlpha = smoothstep(in) * smoothstep(out); // 0→1→0
            } else {
                pulseAlpha = 0f;
            }
        });
    }

    public static void render(MatrixStack matrices, Camera camera) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.world == null || mc.player == null) return;
        if (!mc.player.hasStatusEffect(ModEffects.ORE_SENSE) || !pulseActive || cached.isEmpty()) return;
        if (pulseAlpha <= 0.01f) return;

        R = 5 + 5 * Objects.requireNonNull(mc.player.getStatusEffect(ModEffects.ORE_SENSE)).getAmplifier();

        Vec3d cam = camera.getPos();
        matrices.push();
        matrices.translate(-cam.x, -cam.y, -cam.z);

        VertexConsumerProvider.Immediate buf = mc.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer vc = buf.getBuffer(XRAY);

        float r = 253 / 255f, g = 255 / 255f, b = 112 / 255f, a = 0.35f * pulseAlpha;

        for (BlockPos pos : cached) {
            Box box = new Box(pos).expand(-0.03);
            fillBox(matrices, vc, box, r, g, b, a);
        }

        matrices.pop();
        buf.draw();
    }


    private static List<BlockPos> scanOres(ClientWorld w, BlockPos c) {
        List<BlockPos> out = new ArrayList<>();
        BlockPos.Mutable m = new BlockPos.Mutable();
        int cx = c.getX(), cy = c.getY(), cz = c.getZ();
        for (int x = -R; x <= R; x++)
            for (int y = -R; y <= R; y++)
                for (int z = -R; z <= R; z++) {
                    m.set(cx + x, cy + y, cz + z);
                    BlockState s = w.getBlockState(m);
                    if (s.isIn(ModTags.Blocks.DETECTABLE_ORES)) out.add(m.toImmutable());
                }
        return out;
    }

    // --------- dessin d'une box remplie (6 quads) en POSITION_COLOR ----------
    private static void fillBox(MatrixStack ms, VertexConsumer vc, Box b, float r, float g, float bl, float a) {
        Matrix4f m = ms.peek().getPositionMatrix();

        float x1 = (float)b.minX, y1 = (float)b.minY, z1 = (float)b.minZ;
        float x2 = (float)b.maxX, y2 = (float)b.maxY, z2 = (float)b.maxZ;

        // bas
        quad(vc, m, x1,y1,z1,  x2,y1,z1,  x2,y1,z2,  x1,y1,z2, r,g,bl,a);
        // haut
        quad(vc, m, x1,y2,z1,  x1,y2,z2,  x2,y2,z2,  x2,y2,z1, r,g,bl,a);
        // nord (-Z)
        quad(vc, m, x1,y1,z1,  x1,y2,z1,  x2,y2,z1,  x2,y1,z1, r,g,bl,a);
        // sud (+Z)
        quad(vc, m, x1,y1,z2,  x2,y1,z2,  x2,y2,z2,  x1,y2,z2, r,g,bl,a);
        // ouest (-X)
        quad(vc, m, x1,y1,z1,  x1,y1,z2,  x1,y2,z2,  x1,y2,z1, r,g,bl,a);
        // est (+X)
        quad(vc, m, x2,y1,z1,  x2,y2,z1,  x2,y2,z2,  x2,y1,z2, r,g,bl,a);
    }

    private static void quad(VertexConsumer vc, Matrix4f mat,
                             float x1,float y1,float z1, float x2,float y2,float z2,
                             float x3,float y3,float z3, float x4,float y4,float z4,
                             float r,float g,float b,float a) {
        vc.vertex(mat, x1,y1,z1).color(r,g,b,a);
        vc.vertex(mat, x2,y2,z2).color(r,g,b,a);
        vc.vertex(mat, x3,y3,z3).color(r,g,b,a);
        vc.vertex(mat, x4,y4,z4).color(r,g,b,a);
    }

    // --------- helpers ---------
    private static float clamp01(float v) { return v < 0f ? 0f : Math.min(v, 1f); }
    private static float smoothstep(float x) { x = clamp01(x); return x * x * (3f - 2f * x); }
}
