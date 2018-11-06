package me.rexthemon.CCEssentials.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.rexthemon.lib.Core;

public class WeatherCommand extends PlayerCommand {

	// --------------------------------
	// Command initialization.
	// --------------------------------

	public WeatherCommand() {
		super("weather");

		setAliases(Arrays.asList("ccweather"));
		setDescription("Change the time and look up the weather.");
	}

	// --------------------------------
	// Run on commmand send.
	// --------------------------------

	@Override
	protected void run(Player player, String[] args) {

		// Check permissions.

		if (!player.hasPermission("core.command.weather")) {
			Core.msg(player, "&cYou do not have the permission core.command.weather");

			return;
		}

		// Check if args are 0. If they are, send help, usage & return.

		if (args.length == 0) {
			Core.msg(player,
					"&f&l---- &b&lWeather Command Help &f&l----",
					"&7arg = info, sun, & storm",
					"&cUsage: &7/<weather> <arg>",
					"&f&l----------------------------");

			return;
		}

		// Check if args are 1.

		if (args.length == 1) {

			final World world = Bukkit.getWorld("world");

			// Arg info.

			if (args[0].equalsIgnoreCase("info")) {
				Core.msg(player,
						"&f&l---- &b&lWeather Info &f&l----",
						"&7Storming -&a " + world.hasStorm(),
						"&7Current weather duration in ticks -&a " + world.getWeatherDuration(),
						"&7Thundering -&a " + world.isThundering(),
						"&7Current thunder duration in ticks -&a " + world.getThunderDuration(),
						"&f&l--------------------");

				return;

				// Arg sun.

			} else if (args[0].equalsIgnoreCase("sun")) {
				world.setStorm(false);

				Core.msg(player, "&aChanging to sunny weather.");

				return;

			} else if (args[0].equalsIgnoreCase("storm")) {
				world.setStorm(true);

				Core.msg(player, "&aChanging to stormy weather.");

				return;
			}

			return;
		}
	}
}