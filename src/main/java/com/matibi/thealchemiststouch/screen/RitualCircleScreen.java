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

    public RitualCircleScreen(RitualCircleScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 166;
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(
                RenderPipelines.GUI_TEXTURED,
                GUI_TEXTURE,
                x, y,
                0,0,
                backgroundWidth, backgroundHeight,
                256, 256
        );
    }
}
