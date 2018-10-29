package me.rexthemon.CCEssentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rexthemon.lib.Core;

public abstract class PlayerCommand extends Command {

	protected PlayerCommand(String name) {
		super(name);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {

		if (!(sender instanceof Player)) {
			Core.msg(sender, "&4You may only use this command in-game.");

			return false;
		}

		run((Player) sender, args);
		return true;
	}

	protected abstract void run(Player player, String[] args);
}
