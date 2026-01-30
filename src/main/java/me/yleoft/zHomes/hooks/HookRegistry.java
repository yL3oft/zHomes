package me.yleoft.zHomes.hooks;

public class HookRegistry extends me.yleoft.zAPI.hooks.HookRegistry {

    public static final HookPAPI PLUGIN_PAPI = new HookPAPI();
    public static final HookVault VAULT = new HookVault();
    public static final HookWorldGuard WORLDGUARD = new HookWorldGuard();
    public static final HookGriefPrevention GRIEF_PREVENTION = new HookGriefPrevention();

    public static void registerHooks() {
        registerHook(VAULT);
        registerHook(WORLDGUARD);
        registerHook(GRIEF_PREVENTION);
    }

}
