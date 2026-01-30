package me.yleoft.zHomes.utility;

import me.yleoft.zAPI.configuration.LanguageManager;
import me.yleoft.zAPI.configuration.Messages;
import me.yleoft.zAPI.configuration.YAMLBuilder;
import me.yleoft.zHomes.configuration.languages.*;
import me.yleoft.zHomes.zHomes;

import java.util.List;

public abstract class LanguageUtils {

    public static deYAML deYAML;
    public static enYAML enYAML;
    public static esYAML esYAML;
    public static frYAML frYAML;
    public static itYAML itYAML;
    public static nlYAML nlYAML;
    public static plYAML plYAML;
    public static ptbrYAML ptbrYAML;
    public static ruYAML ruYAML;
    public static zhcnYAML zhcnYAML;

    public static LanguageBuilder currentLanguageYAML;

    public static void loadLanguages() {
        deYAML = new deYAML();
        enYAML = new enYAML();
        esYAML = new esYAML();
        frYAML = new frYAML();
        itYAML = new itYAML();
        nlYAML = new nlYAML();
        plYAML = new plYAML();
        ptbrYAML = new ptbrYAML();
        ruYAML = new ruYAML();
        zhcnYAML = new zhcnYAML();
        loadCurrentLanguage();
    }

    public static void loadCurrentLanguage() {
        String currentLanguageCode = zHomes.getConfigYAML().getLanguageCode().toLowerCase();
        switch (currentLanguageCode.toLowerCase()) {
            case "de": currentLanguageYAML = deYAML; break;
            case "en": currentLanguageYAML = enYAML; break;
            case "es": currentLanguageYAML = esYAML; break;
            case "fr": currentLanguageYAML = frYAML; break;
            case "it": currentLanguageYAML = itYAML; break;
            case "nl": currentLanguageYAML = nlYAML; break;
            case "pl": currentLanguageYAML = plYAML; break;
            case "pt-br", "ptbr", "br": currentLanguageYAML = ptbrYAML; break;
            case "ru": currentLanguageYAML = ruYAML; break;
            case "zh-cn", "zhcn": currentLanguageYAML = zhcnYAML; break;
            default: {
                currentLanguageYAML = new LanguageBuilder(currentLanguageCode);
                break;
            }
        }
        loadzAPIMessages();
    }

    public static void loadzAPIMessages() {
        Messages.setCooldownMessage(currentLanguageYAML.getInCooldown());
        Messages.setNoPermissionDefault(currentLanguageYAML.getNoPermission());
        Messages.setOnlyPlayersDefault(currentLanguageYAML.getOnlyPlayers());
        Messages.setPluginPrefix(zHomes.getConfigYAML().getPrefix());
    }

    public static List<YAMLBuilder> getLanguageFiles() {
        return List.of(
                deYAML,
                enYAML,
                esYAML,
                frYAML,
                itYAML,
                nlYAML,
                plYAML,
                ptbrYAML,
                ruYAML,
                zhcnYAML
        );
    }
}
