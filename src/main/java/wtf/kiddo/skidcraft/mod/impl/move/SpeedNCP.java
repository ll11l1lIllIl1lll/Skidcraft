package wtf.kiddo.skidcraft.mod.impl.move;

import me.bush.eventbus.annotation.EventListener;
import wtf.kiddo.skidcraft.event.LBUpdateEvent;
import wtf.kiddo.skidcraft.event.MotionEvent;
import wtf.kiddo.skidcraft.mod.Category;
import wtf.kiddo.skidcraft.mod.Mod;
import wtf.kiddo.skidcraft.utils.MoveUtils;

/**
 * Author: LiquidBounce
 */
public final class SpeedNCP extends Mod {

    public SpeedNCP() {

        super("SpeedNCP", Category.MOVE);
//        this.setEnabled(false);
        this.setKey(47);
    }

    @EventListener
    public void onUpdateEvent(final LBUpdateEvent event) {
        if (MoveUtils.isMoving()) {
            if (mc.thePlayer.onGround) {
                mc.thePlayer.motionY = 0.42;
                mc.thePlayer.motionX *= 1.01;
                mc.thePlayer.motionZ *= 1.01;
            }
            mc.thePlayer.motionY -= 0.00099999;
        } else {
            mc.thePlayer.motionX = 0.0;
            mc.thePlayer.motionZ = 0.0;
        }


    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
