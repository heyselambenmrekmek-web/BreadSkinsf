package dev.breadskin;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.chat.Component;
import com.mojang.brigadier.Command;
import dev.breadskin.network.ApplySkinPayload;

public final class BreadSkinClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommands.literal("bs")
                        .then(ClientCommands.literal("set")
                                .then(ClientCommands.argument("url", StringArgumentType.greedyString())
                                        .executes(context -> {
                                            String url = StringArgumentType.getString(context, "url").trim();
                                            if (!url.startsWith("https://") || url.length() > 2048) {
                                                context.getSource().sendFeedback(Component.literal(
                                                        "BreadSkin: HTTPS PNG linki gir. URL en fazla 2048 karakter olabilir."));
                                                return 0;
                                            }
                                            if (!ClientPlayNetworking.canSend(ApplySkinPayload.TYPE)) {
                                                context.getSource().sendFeedback(Component.literal(
                                                        "BreadSkin: Bu sunucuda BreadSkin sunucu eklentisi bulunmuyor."));
                                                return 0;
                                            }
                                            ClientPlayNetworking.send(new ApplySkinPayload(url));
                                            context.getSource().sendFeedback(Component.literal(
                                                    "BreadSkin: Skin isteği gönderildi. Geyser üzerinden bağlanan Bedrock oyuncuları da yeni görünümü görebilir."));
                                            return Command.SINGLE_SUCCESS;
                                        })))
                        .then(ClientCommands.literal("help")
                                .executes(context -> {
                                    context.getSource().sendFeedback(Component.literal(
                                            "Kullanım: /breadskin set <HTTPS PNG bağlantısı> veya /breadskin reset"));
                                    return Command.SINGLE_SUCCESS;
                                }))));
    }
}
