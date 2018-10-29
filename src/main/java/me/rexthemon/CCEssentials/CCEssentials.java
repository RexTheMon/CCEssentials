package me.rexthemon.CCEssentials;

import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.rexthemon.CCEssentials.commands.GamemodeCommand;
import me.rexthemon.CCEssentials.commands.TPAcceptCommand;
import me.rexthemon.CCEssentials.commands.TPRequestCommand;
import me.rexthemon.CCEssentials.commands.TeleportCommand;
import me.rexthemon.CCEssentials.listeners.PlayerJoinListener;
import me.rexthemon.lib.Core;

public class CCEssentials extends JavaPlugin {

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
				new TPRequestCommand());

		registerEvents(
				new PlayerJoinListener());
	}

	@Override
	public void onDisable() {
		instance = null;
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