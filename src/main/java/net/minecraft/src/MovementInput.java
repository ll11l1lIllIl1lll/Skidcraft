package net.minecraft.src;

public class MovementInput
{
    /**
     * The speed at which the player is strafing. Postive numbers to the left and negative to the right.
     */
    public static float moveStrafe = 0.0F;

    /**
     * The speed at which the player is moving forward. Negative numbers will move backwards.
     */
    public static float moveForward = 0.0F;
    public boolean jump = false;
    public boolean sneak = false;

    public void updatePlayerMoveState() {}
}
