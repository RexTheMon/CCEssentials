package me.rexthemon.CCEssentials.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.rexthemon.lib.Core;

public class TeleportCommand extends PlayerCommand {

	// --------------------------------
	// Command initialization.
	// --------------------------------

	public TeleportCommand() {
		super("teleport");

		setAliases(Arrays.asList("tp"));
		setDescription("Teleport the the specified player.");
	}

	// --------------------------------
	// Run on command send.
	// --------------------------------

	@Override
	protected void run(Player player, String[] args) {

		// Check permissions.

		if (!player.hasPermission("core.command.teleport")) {
			Core.msg(player, "&cYou do not have the permission core.command.teleport");

			return;
		}

		// Check if args are 0. If they are, send usage & return;

		if (args.length == 0)
			Core.msg(player, "&cUsage: &7/<teleport, tp> <player>");

		// If target is null, send error msg & return.

		final Player target = Bukkit.getServer().getPlayer(args[0]);

		if (target == null) {
			Core.msg(player, "&cThe player you specified could not be found.");

			return;
		}

		// Teleport player.

		player.teleport(target.getLocation());

		Core.msg(player, "&aTeleported to " + target.getName());
		Core.msg(target, "&a" + player.getName() + " has teleported to you.");

		return;
	}
}
