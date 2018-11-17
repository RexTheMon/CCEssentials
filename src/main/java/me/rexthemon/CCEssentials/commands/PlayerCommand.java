package me.rexthemon.CCEssentials.commands;

import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.rexthemon.lib.Core;

public abstract class PlayerCommand extends Command {

	private Player player;
	private String[] args;
	@Setter(value=AccessLevel.PROTECTED)
	private String prefix;

	protected PlayerCommand(String name) {
		super(name);
	}

	@Override
	public final boolean execute(CommandSender sender, String commandLabel, String[] args) {

		if (!(sender instanceof Player)) {
			Core.msg(sender, "&cYou must be a player to run this command.");

			return false;
		}

		final Player player = (Player) sender;
		this.player = player;
		this.args = args;

		try {
			run(player, args);

		} catch (final ReturnedCommandException e) {
			final String msgMessage = e.msgMessage;

			msg(msgMessage);
		}

		return false;
	}

	protected abstract void run(Player player, String[] args);

	protected void checkBoolean(boolean toCheck, String falseMsg) {

		if (!toCheck)
			returnMsg(falseMsg);
	}

	protected void checkNotNull(Object toCheck, String nullMsg) {

		if (toCheck == null)
			returnMsg(nullMsg);
	}

	protected void checkArgsStrict(int requiredLength, String message) {

		if (args.length != requiredLength)
			returnMsg(message);
	}

	protected int getNumber(int argsIndex, int from, int to, String errorMsg) {
		int age = 0;

		try {
			age = Integer.parseInt(args[argsIndex]);
			Validate.isTrue(age >= from && age <= to);

		} catch (final IllegalArgumentException e) {
			returnMsg(errorMsg.replace("{min}", from + "").replace("{max}", to + ""));
		}

		return age;
	}

	protected void returnMsg(String message) {
		throw new ReturnedCommandException(message);
	}

	protected void msg(String message) {
		Core.msg(player, (prefix != null ? prefix + " " : "") + message);
	}

	@RequiredArgsConstructor
	private final class ReturnedCommandException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		private final String msgMessage;
	}
}