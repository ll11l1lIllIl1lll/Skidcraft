package wtf.kiddo.skidcraft.gui.ingame.clickgui;

import net.minecraft.client.Minecraft;
import wtf.kiddo.skidcraft.mod.impl.client.Colors;
import wtf.kiddo.skidcraft.util.RenderUtil;

import java.util.ArrayList;

/**
 * Author: zcy
 * Created: 2022/5/3
 */
public abstract class Panel {
    private Minecraft mc = Minecraft.getMinecraft();
    private final String label;
    private int angle;
    private int x;
    private int y;
    private int x2;
    private int y2;
    private int width;
    private int height;
    private boolean open;
    public boolean drag;
    private final ArrayList<Item> items = new ArrayList();

    public Panel(String label, int x, int y, boolean open) {
        this.label = label;
        this.x = x;
        this.y = y;
        this.angle = 180;
        this.width = 88;
        this.height = 18;
        this.open = open;
        this.setupItems();
    }

    public abstract void setupItems();

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drag(mouseX, mouseY);
        float totalItemHeight = this.open ? this.getTotalItemHeight() - 2.0f : 0.0f;
//        RenderMethods.drawGradientRect(this.x, (float)this.y - 1.5f, this.x + this.width, this.y + this.height - 6, -7829368, -6710887);
        RenderUtil.drawGradient(this.x, (float)this.y - 1.5f, this.x + this.width, this.y + this.height - 6, Colors.getClientColorCustomAlpha(77), Colors.getClientColorCustomAlpha(77));//0x77FB4242, 0x77FB4242);
        if (this.open) {
            RenderUtil.drawRect(this.x, (float)this.y + 12.5f, this.x + this.width, this.open ? (float)(this.y + this.height) + totalItemHeight : (float)(this.y + this.height - 1), 0x77000000);//1996488704
        }
        mc.fontRenderer.drawString(this.getLabel(), this.x + 3, this.y + 1/* - 4.0f*/, -1); //15592941
        //var5.f$L.f$E(this.f$E(), (double)((float)this.f$C + 3.0F), (double)((float)this.f$e + 1.5F), 15592941);

        if (this.open) {
            float y = (float)(this.getY() + this.getHeight()) - 3.0f;
            for (Item item : getItems()) {
                item.setLocation((float)this.x + 2.0f, y);
                item.setWidth(this.getWidth() - 4);
                item.drawScreen(mouseX, mouseY, partialTicks);
                y += (float)item.getHeight() + 1.5f;
            }
        }
    }

    private void drag(int mouseX, int mouseY) {
        if (!this.drag) {
            return;
        }
        this.x = this.x2 + mouseX;
        this.y = this.y2 + mouseY;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.x2 = this.x - mouseX;
            this.y2 = this.y - mouseY;
            GuiInstance.getInstance().getPanels().forEach(panel -> {
                if (panel.drag) {
                    panel.drag = false;
                }
            });
            this.drag = true;
            return;
        }
        if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
            this.open = !this.open;
//            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("random.click"), 1.0f));
            return;
        }
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.mouseClicked(mouseX, mouseY, mouseButton));
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
        if (releaseButton == 0) {
            this.drag = false;
        }
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.mouseReleased(mouseX, mouseY, releaseButton));
    }

    public final String getLabel() {
        return this.label;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean getOpen() {
        return this.open;
    }

    public final ArrayList<Item> getItems() {
        return this.items;
    }

    private boolean isHovering(int mouseX, int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight() - (this.open ? 2 : 0);
    }

    //added this method in, just to fix shit. It is from uz1 class in future
    public static float calculateRotation(float var0) {
        if ((var0 %= 360.0F) >= 180.0F) {
            var0 -= 360.0F;
        }

        if (var0 < -180.0F) {
            var0 += 360.0F;
        }

        return var0;
    }

    private float getTotalItemHeight() {
        float height = 0.0f;
        for (Item item : getItems()) {
            height += (float)item.getHeight() + 1.5f;
        }
        return height;
    }

    public void setX(int dragX) {
        this.x = dragX;
    }

    public void setY(int dragY) {
        this.y = dragY;
    }
}
