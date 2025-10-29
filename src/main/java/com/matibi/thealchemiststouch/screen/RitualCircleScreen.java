package com.matibi.thealchemiststouch.screen;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RitualCircleScreen extends HandledScreen<RitualCircleScreenHandler> {
    public static final Identifier GUI_TEXTURE =
            Identifier.of(TheAlchemistsTouch.MOD_ID, "textures/gui/ritual_circle/ritual_circle_gui.png");

    private static final Identifier BLOOD_FILL =
            Identifier.of(TheAlchemistsTouch.MOD_ID, "textures/gui/ritual_circle/blood_bar_fill.png");
    private static final Identifier BLOOD_FRAME =
            Identifier.of(TheAlchemistsTouch.MOD_ID, "textures/gui/ritual_circle/blood_bar_frame.png");

    private static final int BAR_W = 60;
    private static final int BAR_H = 60;

    private int barX, barY;

    public RitualCircleScreen(RitualCircleScreenHandler handler, PlayerInventory inv, Text title) {
        super(handler, inv, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 166;
    }

    @Override
    protected void drawBackground(DrawContext ctx, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        ctx.drawTexture(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y,
                0, 0, backgroundWidth, backgroundHeight, 256, 256);

        barX = x + 90;
        barY = y + 14;

        int blood = handler.getBlood();
        int max   = Math.max(1, handler.getMaxBlood());

        float percent = Math.min(1f, blood / (float) max);
        int filled = (blood > 0) ? Math.max(2, Math.round(percent * BAR_H)) : 0;
        ctx.drawTexture(RenderPipelines.GUI_TEXTURED, BLOOD_FRAME,
                barX, barY, 0, 0, BAR_W, BAR_H, BAR_W, BAR_H);
        if (filled > 0) {
            int vStart = BAR_H - filled;
            int drawY = barY + vStart;
            ctx.drawTexture(
                    RenderPipelines.GUI_TEXTURED,
                    BLOOD_FILL,
                    barX, drawY,
                    0, vStart,
                    BAR_W, filled,
                    BAR_W, BAR_H
            );
        }
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        super.render(ctx, mouseX, mouseY, delta);

        if (isPointWithinBar(mouseX, mouseY)) {
            String txt = handler.getBlood() + " / " + handler.getMaxBlood();
            ctx.drawTooltip(textRenderer, Text.literal(txt), mouseX, mouseY);
        }
    }

    private boolean isPointWithinBar(int mouseX, int mouseY) {
        return mouseX >= barX && mouseX < barX + BAR_W
                && mouseY >= barY && mouseY < barY + BAR_H;
    }
}