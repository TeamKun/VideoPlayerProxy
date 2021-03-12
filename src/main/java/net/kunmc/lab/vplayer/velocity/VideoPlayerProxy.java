package net.kunmc.lab.vplayer.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;

@Plugin(id = "vplayer",
        name = "VideoPlayerProxy",
        version = "${project.version}",
        description = "Support VideoPlayer packet with Velocity",
        authors = {"Kamesuta"}
)
public class VideoPlayerProxy {
    private final ProxyServer proxy;
    private final Logger logger;

    private MinecraftChannelIdentifier INCOMING;
    private MinecraftChannelIdentifier OUTGOING;

    @Inject
    public VideoPlayerProxy(ProxyServer proxy, Logger logger) {
        this.proxy = proxy;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        proxy.getChannelRegistrar().register(INCOMING = OUTGOING = MinecraftChannelIdentifier.create("vplayer", "patch"));
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (event.getIdentifier().equals(INCOMING)) {
            event.setResult(PluginMessageEvent.ForwardResult.forward());
        }
    }
}