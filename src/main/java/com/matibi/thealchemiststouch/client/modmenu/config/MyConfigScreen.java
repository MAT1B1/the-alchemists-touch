package com.matibi.thealchemiststouch.client.modmenu.config;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public final class MyConfigScreen extends Screen {
    private final Screen parent;

    // UI
    private TextFieldWidget search;
    private final List<Row> rows = new ArrayList<>();
    private int contentHeight;   // hauteur totale des lignes
    private int scroll;          // offset de scroll en px
    private int listTop, listBottom, listLeft, listRight;

    // Scrollbar (drag)
    private boolean draggingBar = false;
    private int dragOffsetY = 0;         // distance souris → top du thumb au moment du clic
    private int lastBarY = 0, lastBarH = 0;
    private boolean hasScrollbar = false;

    public MyConfigScreen(Screen parent) {
        super(Text.of("The Alchemist's Touch — Potions"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        listTop = 36;
        listBottom = this.height - 40;
        listLeft = 16;
        listRight = this.width - 16;   // pleine largeur

        search = new TextFieldWidget(this.textRenderer, listLeft, 10, this.width - 32, 16, Text.of("Search"));
        search.setPlaceholder(Text.of("Rechercher par nom ou id…"));
        search.setChangedListener(s -> rebuild());
        this.addSelectableChild(search);
        this.setInitialFocus(search);

        this.addDrawableChild(ButtonWidget.builder(Text.of("Done"), b -> this.close())
                .dimensions(this.width / 2 - 40, this.height - 28, 80, 20).build());

        rebuild();
    }

    /** Nom lisible/localisé à partir d'une entrée de registre. */
    private static String displayNameOf(RegistryEntry<Potion> entry) {
        ItemStack stack = PotionContentsComponent.createStack(Items.POTION, entry);
        return stack.getName().getString();
    }

    private void rebuild() {
        rows.clear();
        String q = search.getText().trim().toLowerCase(Locale.ROOT);

        List<RegistryEntry<Potion>> all = new ArrayList<>();
        Registries.POTION.getIndexedEntries().forEach(all::add);

        // Tri par nom lisible puis ID
        all.sort(Comparator
                .comparing((RegistryEntry<Potion> e) -> displayNameOf(e).toLowerCase(Locale.ROOT))
                .thenComparing(RegistryEntry::getIdAsString));


        int y = 0;
        for (RegistryEntry<Potion> entry : all) {
            Identifier id = Identifier.of(entry.getIdAsString());
            String idStr = id.toString();
            String name = displayNameOf(entry);
            String nameLower = name.toLowerCase(Locale.ROOT);

            // filtre par nom OU par id
            if (!q.isEmpty() && !(nameLower.contains(q) || idStr.contains(q))) continue;

            boolean checked = ModConfig.DISABLED_POTIONS.contains(id);
            rows.add(new Row(entry, id, idStr, name, nameLower, y, checked));
            y += 20;
        }
        contentHeight = y;
        clampScroll();
    }

    private void clampScroll() {
        int visible = listBottom - listTop;
        int max = Math.max(0, contentHeight - visible);
        if (scroll < 0) scroll = 0;
        if (scroll > max) scroll = max;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (mouseY >= listTop && mouseY <= listBottom && mouseX >= listLeft && mouseX <= listRight) {
            scroll -= (int) (verticalAmount * 20);
            clampScroll();
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // clic sur case à cocher
        if (button == 0 && mouseY >= listTop && mouseY <= listBottom &&
                mouseX >= listLeft && mouseX <= listRight) {
            int yStart = listTop - scroll;
            for (Row r : rows) {
                int y = yStart + r.y;
                int boxX = listLeft + 6, boxY = y + 2;
                if (mouseX >= boxX && mouseX < boxX + 12 && mouseY >= boxY && mouseY < boxY + 12) {
                    r.checked = !r.checked;
                    if (r.checked) ModConfig.DISABLED_POTIONS.add(r.id);
                    else ModConfig.DISABLED_POTIONS.remove(r.id);
                    ModConfig.save();
                    return true;
                }
            }
        }

        // clic sur la barre de scroll → activer le drag
        if (button == 0 && hasScrollbar) {
            int barX1 = listRight - 6, barX2 = listRight - 2;
            if (mouseX >= barX1 && mouseX <= barX2 && mouseY >= listTop && mouseY <= listBottom) {
                draggingBar = true;
                // si on clique dans le thumb, garder l'offset; sinon, centrer
                if (mouseY >= lastBarY && mouseY <= lastBarY + lastBarH) {
                    dragOffsetY = (int) (mouseY - lastBarY);
                } else {
                    dragOffsetY = lastBarH / 2;
                    // sauter directement à cette position
                    updateScrollFromMouse((int) mouseY);
                }
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        if (draggingBar) {
            updateScrollFromMouse((int) mouseY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dx, dy);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && draggingBar) {
            draggingBar = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private void updateScrollFromMouse(int mouseY) {
        int trackHeight = listBottom - listTop;
        int maxScroll = Math.max(0, contentHeight - trackHeight);
        if (maxScroll == 0) { scroll = 0; return; }

        int minY = listTop;
        int thumbH = Math.max(20, (int) (trackHeight * (trackHeight / (float) contentHeight)));
        int thumbY = mouseY - dragOffsetY;

        // clamp thumb dans la piste
        int maxY = minY + trackHeight - thumbH;
        if (thumbY < minY) thumbY = minY;
        if (thumbY > maxY) thumbY = maxY;

        float t = (thumbY - minY) / (float) (trackHeight - thumbH);
        scroll = (int) (t * maxScroll);
        clampScroll();
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // pas de renderBackground() → pas de double blur
        ctx.fill(0, 0, this.width, this.height, 0x90000000);

        // Titre + cadre liste
        ctx.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 2, 0xFFFFFF);
        ctx.fill(listLeft - 2, listTop - 2, listRight + 2, listBottom + 2, 0x40FFFFFF);
        ctx.fill(listLeft, listTop, listRight, listBottom, 0x80000000);

        super.render(ctx, mouseX, mouseY, delta);
        search.render(ctx, mouseX, mouseY, delta);

        // Liste
        ctx.enableScissor(listLeft, listTop, listRight, listBottom);
        int yStart = listTop - scroll;

        for (Row r : rows) {
            int y = yStart + r.y;
            if (y < listTop - 20 || y > listBottom) continue;

            int boxX = listLeft + 6;
            int boxY = y + 2;

            // survol
            if (mouseX >= listLeft && mouseX <= listRight && mouseY >= y && mouseY <= y + 18) {
                ctx.fill(listLeft + 1, y, listRight - 1, y + 18, 0x30FFFFFF);
            }

            // case à cocher
            ctx.fill(boxX, boxY, boxX + 12, boxY + 12, 0xFF202020);
            if (!r.checked) ctx.fill(boxX + 2, boxY + 2, boxX + 10, boxY + 10, 0xFF60C060);

            // Nom lisible + ID
            int textX = boxX + 16;
            ctx.drawText(this.textRenderer, r.name,  textX, y - 4,  0xFFFFFF, false);
            ctx.drawText(this.textRenderer, r.idStr, textX, y + 5, 0xFFAAAAAA, false);
        }
        ctx.disableScissor();

        // Scrollbar (avec drag)
        int visible = listBottom - listTop;
        hasScrollbar = contentHeight > visible;
        if (hasScrollbar) {
            float ratio = visible / (float) contentHeight;
            int barH = Math.max(20, (int) (ratio * visible));
            int maxScroll = contentHeight - visible;
            int barY = listTop + (int) ((scroll / (float) maxScroll) * (visible - barH));
            int barX1 = listRight - 6, barX2 = listRight - 2;

            // piste
            ctx.fill(barX1, listTop, barX2, listBottom, 0x40000000);
            // thumb
            ctx.fill(barX1, barY,   barX2, barY + barH, draggingBar ? 0xC0FFFFFF : 0x80FFFFFF);

            // mémoriser pour le drag
            lastBarY = barY;
            lastBarH = barH;
        } else {
            lastBarY = lastBarH = 0;
        }
    }

    @Override
    public void close() {
        if (this.client != null)
            this.client.setScreen(parent);
    }

    /** Une ligne de la liste (basée sur RegistryEntry<Potion>). */
    private static final class Row {
        final RegistryEntry<Potion> entry;
        final Identifier id;
        final String idStr;
        final String name;
        final String nameLower;
        final int y;
        boolean checked;

        Row(RegistryEntry<Potion> entry, Identifier id, String idStr,
            String name, String nameLower, int y, boolean checked) {
            this.entry = entry;
            this.id = id;
            this.idStr = idStr;
            this.name = name;
            this.nameLower = nameLower;
            this.y = y;
            this.checked = checked;
        }
    }
}