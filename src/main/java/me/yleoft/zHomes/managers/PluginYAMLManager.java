package me.yleoft.zHomes.managers;

import me.yleoft.zHomes.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;


public class PluginYAMLManager {

    private PluginDescriptionFile file;
    private List<Command> cmds = new ArrayList<>();
    private List<String> perms = new ArrayList<>();
    private Main main = Main.getInstance();

    public PluginYAMLManager() {
        this.file = main.getDescription();
    }

    public static final TabExecutor emptyExec = new TabExecutor() {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            return false;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            return null;
        }
    };


    public static void syncCommands() {
        try {
            Method syncCommandsMethod = Bukkit.getServer().getClass().getDeclaredMethod("syncCommands");
            syncCommandsMethod.setAccessible(true);
            syncCommandsMethod.invoke(Bukkit.getServer());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Could not invoke syncCommands method", e);
        }
    }



    public void unregisterCommands() {
        try {
            Field f = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            Map<String, Command> knownCommands = (Map<String, Command>) knownCommandsField.get(commandMap);
            HashMap<String, Command> commandsToCheck = new HashMap<String, Command>();

            for (Command c : cmds) {
                commandsToCheck.put(c.getLabel().toLowerCase(), c);
                commandsToCheck.put(c.getName().toLowerCase(), c);
                c.getAliases().forEach(a -> commandsToCheck.put(a.toLowerCase(), c));
            }

            for (Entry<String, Command> check : commandsToCheck.entrySet()) {
                Command mappedCommand = knownCommands.get(check.getKey());
                if (check.getValue().equals(mappedCommand)) {
                    mappedCommand.unregister(commandMap);
                    knownCommands.remove(check.getKey());
                } else if (check.getValue() instanceof PluginCommand) {
                    PluginCommand checkPCmd = (PluginCommand) check.getValue();
                    if (mappedCommand instanceof PluginCommand) {
                        PluginCommand mappedPCmd = (PluginCommand) mappedCommand;
                        CommandExecutor mappedExec = mappedPCmd.getExecutor();

                        if (mappedExec != null && mappedExec.equals(checkPCmd.getExecutor())) {
                            mappedPCmd.setExecutor(null);
                            mappedPCmd.setTabCompleter(null);
                        }
                    }
                    checkPCmd.setExecutor(emptyExec);
                    checkPCmd.setTabCompleter(emptyExec);
                }

            }
            knownCommandsField.setAccessible(false);
        } catch (Exception exception) {}
    }

    public void registerCommand(String command, CommandExecutor ce, String description, String... aliases){
        if(!file.getCommands().containsKey(command)) {
            try {
                Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
                Field f = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
                c.setAccessible(true);
                f.setAccessible(true);

                PluginCommand cmd = c.newInstance(command, main);
                cmd.setDescription(description);
                cmd.setExecutor(ce);

                List<String> aliasesList = new ArrayList<>();
                try {
                    aliasesList = new ArrayList<>(Arrays.asList(aliases));
                }catch (Exception e) {
                }
                cmd.setAliases(aliasesList);

                CommandMap commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
                commandMap.register(main.pluginName, cmd);
                cmds.add(cmd);

                main.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.coloredPluginName+"&aLoaded command &e/"+command
                ));
            }catch (Exception e) {
                main.getLogger().severe(main.coloredPluginName+"§4Couldn't load command §e"+command);
            }
        }else {
            main.getLogger().severe(main.coloredPluginName+"§4Couldn't load command §e"+command);
        }
    }

    public void registerCommand(String command, CommandExecutor ce, TabCompleter completer, String description, String... aliases){
        if(!file.getCommands().containsKey(command)) {
            try {
                Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
                Field f = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
                c.setAccessible(true);
                f.setAccessible(true);

                PluginCommand cmd = c.newInstance(command, main);
                cmd.setDescription(description);
                cmd.setExecutor(ce);
                cmd.setTabCompleter(completer);

                List<String> aliasesList = new ArrayList<>();
                try {
                    aliasesList = new ArrayList<>(Arrays.asList(aliases));
                }catch (Exception e) {
                }
                cmd.setAliases(aliasesList);

                CommandMap commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
                commandMap.register(main.pluginName, cmd);
                cmds.add(cmd);

                main.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.coloredPluginName+"&aLoaded command &e/"+command
                ));
            }catch (Exception e) {
                main.getLogger().severe(main.coloredPluginName+"§4Couldn't load command §e"+command);
            }
        }else {
            main.getLogger().severe(main.coloredPluginName+"§4Couldn't load command §e"+command);
        }
    }

    public void unregisterPermissions() {
        for(String perm : perms) {
            unregisterPermission(perm);
        }
        Bukkit.getPluginManager().getPermissions().forEach(permission -> {
            if(permission.getName().startsWith("zbase")) {
                unregisterPermission(permission);
            }
        });
    }
    public void unregisterPermission(String permission) {
        Bukkit.getPluginManager().removePermission(permission);
    }
    public void unregisterPermission(Permission permission) {
        Bukkit.getPluginManager().removePermission(permission);
    }

    public void registerPermission(String permission) {
        Bukkit.getPluginManager().addPermission(new Permission(permission));
        perms.add(permission);
    }
    public void registerPermission(String permission, String description) {
        Bukkit.getPluginManager().addPermission(new Permission(permission, description));
        perms.add(permission);
    }
    public void registerPermission(String permission, PermissionDefault def) {
        Bukkit.getPluginManager().addPermission(new Permission(permission, def));
        perms.add(permission);
    }
    public void registerPermission(String permission, String description, PermissionDefault def) {
        Bukkit.getPluginManager().addPermission(new Permission(permission, description, def));
        perms.add(permission);
    }
    public void registerPermission(String permission, String description, PermissionDefault def, Map<String, Boolean> children) {
        Bukkit.getPluginManager().addPermission(new Permission(permission, description, def, children));
        perms.add(permission);
    }

    public void registerTabCompleter(String command, TabCompleter tc){
        try {
            Objects.requireNonNull(main.getCommand(command)).setTabCompleter(tc);
        }catch (Exception e) {
        }
    }

    public void registerEvent(Listener l) {
        main.getServer().getPluginManager().registerEvents(l, main);
    }

}
