package me.rexthemon.CCEssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.rexthemon.lib.Core;

public class TPAcceptCommand extends PlayerCommand {

	// --------------------------------
	// Command initialization.
	// --------------------------------

	public TPAcceptCommand() {
		super("tpaccept");

		setDescription("Accept the last incoming teleport request.");
	}

	// --------------------------------
	// Run on commmand send.
	// --------------------------------

	@Override
	protected void run(Player player, String[] args) {

		// Check permissions.

		if (!player.hasPermission("core.command.tpaccept")) {
			Core.msg(player, "&cYou do not have the permission core.command.tpaccept");

			return;
		}

		// If hashmap contains the requester, teleport requester,
		// send msg & remove player from hashmap.

		if (TPRequestCommand.requests.containsKey(player.getName())) {
			Bukkit.getPlayer(TPRequestCommand.requests.get(player.getName())).teleport(player);

			Core.msg(player, "&aYou have been teleported.");

			TPRequestCommand.requests.remove(player.getName());
		}
	}
}