package dev.breadskin;

import dev.breadskin.network.ApplySkinPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BreadSkin implements ModInitializer {
    public static final String MOD_ID = "breadskin";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.serverboundPlay().register(
                ApplySkinPayload.TYPE,
                ApplySkinPayload.CODEC
        );
        LOGGER.info("BreadSkin client networking ready");
    }
}
