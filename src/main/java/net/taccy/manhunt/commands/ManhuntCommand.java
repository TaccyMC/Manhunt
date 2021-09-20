package net.taccy.manhunt.commands;

import net.taccy.manhunt.Manhunt;
import net.taccy.manhunt.commands.subcommands.PlaySub;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManhuntCommand implements CommandExecutor, TabCompleter {

    private Manhunt pl;

    public ManhuntCommand(Manhunt pl) {
        this.pl = pl;

        subcommands.add(new PlaySub(pl));
    }

    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // if they dont give a subcommand
        if (args.length == 0) {
            sender.sendMessage("Please specify a subcommand!");
            return true;
        }

        // get the subcommand they used
        SubCommand cmd = getSubCommandFromString(args[0]);

        if (cmd == null) {
            sender.sendMessage("That subcommand does not exist!");
            return true;
        }

        // checks for permission from command
        if (cmd.getPermission() != null) {
            if (!sender.hasPermission(cmd.getPermission())) {
                return true;
            }
        }

        // convert the args into arraylist and remove first argument
        // this converts "/sp gmc 1berry1" arguments to simply "1berry1"
        ArrayList betterArgs = new ArrayList<String>(Arrays.asList(args));
        betterArgs.remove(0);

        // now pass it over to the proper subcomand to handle
        cmd.handle(sender, betterArgs);

        return false;

    }

    public SubCommand getSubCommandFromString(String s) {
        for (SubCommand cmd : subcommands) {
            if (cmd.getName().equalsIgnoreCase(s)) {
                return cmd;
            }
        }
        return null;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> suggestions = new ArrayList<>();

        if (!sender.hasPermission("manhunt.admin")) {
            suggestions.add("play");
            return suggestions;
        }

        if (args.length == 1) {
            for (SubCommand cmd : subcommands) {
                suggestions.add(cmd.getName());
            }
        }

        SubCommand subCommand = getSubCommandFromString(args[0]);

        if (subCommand == null) {
            return suggestions;
        }

        if (args.length == 2) {
            List<String> subcommands = subCommand.getSubCommands();
            if (subcommands != null) {
                for (String subcommand : getSubCommandFromString(args[0]).getSubCommands()) {
                    suggestions.add(subcommand);
                }
            }
        }

        return suggestions;
    }

}

