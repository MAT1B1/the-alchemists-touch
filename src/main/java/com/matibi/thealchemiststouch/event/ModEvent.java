package com.matibi.thealchemiststouch.event;

import com.matibi.thealchemiststouch.TheAlchemistsTouch;

public class ModEvent {
    public static void register() {
        TheAlchemistsTouch.LOGGER.info("Register event for " + TheAlchemistsTouch.MOD_ID);

        LightningStrikeHandler.register();
        CopperCleanHandler.register();
    }
}
