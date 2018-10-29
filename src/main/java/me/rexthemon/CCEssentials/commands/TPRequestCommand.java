package me.rexthemon.CCEssentials.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.rexthemon.lib.Core;

public class TPRequestCommand extends PlayerCommand {

	// --------------------------------
	// Command initialization.
	// --------------------------------

	public TPRequestCommand() {
		super("tpa");

		setDescription("Send a teleport request to the specified player.");
	}

	protected static HashMap<String, String> requests = new HashMap<>();

	// --------------------------------
	// Run on commmand send.
	// --------------------------------

	@Override
	protected void run(Player player, String[] args) {

		// Check permissions.

		if (!player.hasPermission("core.command.tprequest")) {
			Core.msg(player, "&cYou do not have the permission core.command.tprequest");

			return;
		}

		// Check if args are 0. If they are, send usage & return.

		if (args.length == 0) {
			Core.msg(player, "&cUsage: &7/tpa <player>");

			return;
		}

		// If target is null, send error msg & return.

		final Player target = Bukkit.getServer().getPlayer(args[0]);

		if (target == null) {
			Core.msg(player, "&cThe player you specified could not be found.");

			return;
		}

		// Send request to target.

		requests.put(target.getName(), player.getName());

		Core.msg(player, "&aYou have requested to teleport to " + target.getName());
		Core.msg(target,
				"&a" + player.getDisplayName() + " has requested to teleport to you.",
				"&7to accept the request do /tpaccept");
	}
}