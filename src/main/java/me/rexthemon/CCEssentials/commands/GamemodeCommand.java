package me.rexthemon.CCEssentials.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import me.rexthemon.lib.Core;

public class GamemodeCommand extends PlayerCommand {

	// --------------------------------
	// Command initialization.
	// --------------------------------

	public GamemodeCommand() {
		super("gamemode");

		setAliases(Arrays.asList("gm"));
		setDescription("Change the gamemode of you or the specified target.");
	}

	// --------------------------------
	// Run on commmand send.
	// --------------------------------

	@Override
	protected void run(Player player, String[] args) {

		// Check permissions.

		if (!player.hasPermission("core.command.gamemode")) {
			Core.msg(player, "&cYou do not have the permission core.command.gamemode");

			return;
		}

		// Check if args are 0. If they are, send usage & return.

		if (args.length == 0) {
			Core.msg(player, "&cUsage: &7/<gamemode, gm> <mode> [player]");

			return;
		}

		// Check if args are 1.

		if (args.length == 1) {

			// Go through all the gamemodes.

			if (args[0].equalsIgnoreCase("creative")
					|| args[0].equalsIgnoreCase("1")
					|| args[0].equalsIgnoreCase("c")) {
				player.setGameMode(GameMode.CREATIVE);
				Core.msg(player, "&aYour gamemode has been set to creative.");

			} else if (args[0].equalsIgnoreCase("survival")
					|| args[0].equalsIgnoreCase("0")
					|| args[0].equalsIgnoreCase("s")) {
				player.setGameMode(GameMode.SURVIVAL);
				Core.msg(player, "&aYour gamemode has been set to survival.");

			} else if (args[0].equalsIgnoreCase("adventure")
					|| args[0].equalsIgnoreCase("2")
					|| args[0].equalsIgnoreCase("a")) {
				player.setGameMode(GameMode.ADVENTURE);
				Core.msg(player, "&aYour gamemode has been set to adventure.");

			} else if (args[0].equalsIgnoreCase("spectator")
					|| args[0].equalsIgnoreCase("3")
					|| args[0].equalsIgnoreCase("sp")) {
				player.setGameMode(GameMode.SPECTATOR);
				Core.msg(player, "&aYour gamemode has been set to spectator.");

			}
		}

		// Check if args are 2.

		else if (args.length == 2) {
			final Player target = Bukkit.getServer().getPlayer(args[1]);

			// If target is null, send error msg & return.

			if (target == null) {
				Core.msg(player, "&cThe player you specified could not be found.");

				return;

				// Else if through all the possible gamemodes.

			} else if (args[0].equalsIgnoreCase("creative")
					|| args[0].equalsIgnoreCase("1")
					|| args[0].equalsIgnoreCase("c")) {
				target.setGameMode(GameMode.CREATIVE);
				Core.msg(target, "&aYour gamemode has been set to creative.");
				Core.msg(player, "&aYou set " + target.getName() + "&a's gamemode to creative.");

			} else if (args[0].equalsIgnoreCase("survival")
					|| args[0].equalsIgnoreCase("0")
					|| args[0].equalsIgnoreCase("s")) {
				target.setGameMode(GameMode.SURVIVAL);
				Core.msg(target, "&aYour gamemode has been set to survival.");
				Core.msg(player, "&aYou set " + target.getName() + "&a's gamemode to survival");

			} else if (args[0].equalsIgnoreCase("adventure")
					|| args[0].equalsIgnoreCase("2")
					|| args[0].equalsIgnoreCase("a")) {
				target.setGameMode(GameMode.ADVENTURE);
				Core.msg(target, "&aYour gamemode has been set to adventure.");
				Core.msg(player, "&aYou set " + target.getName() + "&a's gamemode to adventure.");

			} else if (args[0].equalsIgnoreCase("spectator")
					|| args[0].equalsIgnoreCase("3")
					|| args[0].equalsIgnoreCase("sp")) {
				target.setGameMode(GameMode.SPECTATOR);
				Core.msg(target, "&aYour gamemode has been set to spectator.");
				Core.msg(player, "&aYou set " + target.getName() + "&a's gamemode to spectator.");

			}
		}
	}
}