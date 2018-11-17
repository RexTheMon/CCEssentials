package me.rexthemon.CCEssentials;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import lombok.Getter;
import me.rexthemon.CCEssentials.commands.GamemodeCommand;
import me.rexthemon.CCEssentials.commands.RandomTPCommand;
import me.rexthemon.CCEssentials.commands.TPAcceptCommand;
import me.rexthemon.CCEssentials.commands.TPRequestCommand;
import me.rexthemon.CCEssentials.commands.TeleportCommand;
import me.rexthemon.CCEssentials.commands.WeatherCommand;
import me.rexthemon.CCEssentials.listeners.PlayerJoinListener;
import me.rexthemon.lib.Core;

public class CCEssentials extends JavaPlugin {

	private static final Cache<UUID, PlayerCache> playerCache = CacheBuilder.newBuilder()
			.maximumSize(10_000)
			.expireAfterWrite(10, TimeUnit.MINUTES)
			.build();

	@Getter
	private static CCEssentials instance;

	@Override
	public void onEnable() {
		instance = this;
		Core.setInstance(this);

		registerCommands(
				new GamemodeCommand(),
				new TeleportCommand(),
				new TPAcceptCommand(),
				new TPRequestCommand(),
				new WeatherCommand(),
				new RandomTPCommand());

		registerEvents(
				new PlayerJoinListener());
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	public static PlayerCache getCache(UUID id) {
		PlayerCache cache = playerCache.getIfPresent(id);

		if (cache == null) {
			cache = new PlayerCache(id);

			playerCache.put(id, cache);
		}

		return cache;
	}

	private void registerCommands(Command... cmds) {

		for (final Command cmd : cmds)
			Core.registerCommand(cmd);
	}

	private void registerEvents(Listener... listeners) {
		final PluginManager pm = getServer().getPluginManager();

		for (final Listener l : listeners)
			pm.registerEvents(l, this);
	}
}