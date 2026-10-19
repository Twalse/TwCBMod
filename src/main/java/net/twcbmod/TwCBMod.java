package net.twcbmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwCBMod implements ModInitializer {
    public static final String MOD_ID = "twcbmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("TwCBMod initialized - Russian Command Translator active.");
    }
}
