package tech.myrseth.akumamod;

import tech.myrseth.akumamod.enums.EnumModuleLanguages;
import tech.myrseth.akumamod.enums.EnumModuleTranslatorSources;
import tech.myrseth.akumamod.utils.TextElement;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.settings.elements.*;
import net.labymod.utils.Material;

import java.util.List;

public class Settings {

    private boolean enabled;
    private int lang;
    private String translation_icon;
    //private boolean translateAtHover;
    //private int delaySeconds;
    private String apiKey;
    private EnumModuleTranslatorSources translator_source;


    // Enabled
    private boolean pt_enabled;
    private boolean pd_enabled;
    private boolean history_enabled;
    private boolean chatflood_enabled;
    private boolean translate_enabled;
    private boolean acc_enabled;
    private boolean welcome_enabled;
    private boolean dupeip_enabled;
    private boolean autodupeip_enabled;

    // Icons
    private String pt_icon;
    private String history_icon;
    private String chatflood_icon;
    private String welcome_icon;
    private String dupeip_icon;

    // Messages
    private String welcome_msg;

    private final List<SettingsElement> subSettings;

    Settings(List<SettingsElement> subSettings) {
        this.subSettings = subSettings;

        loadConfig();
        init();
    }

    private final String ENABLED = "enabled";
    private final String SOURCE = "source";
    private final String LANG = "lang";
    private final String ICON = "icon";
    //private final String HOVER = "hover";
    //private final String DELAY = "delay";
    private final String APIKEY = "apiKey";

    private final String TRANSLATE_ENABLED = "translate_enabled";
    private final String PT_ENABLED = "pt_enabled";
    private final String HISTORY_ENABLED = "hist_enabled";
    private final String CHATFLOOD_ENABLED = "cf_enabled";
    private final String DUPEIP_ENABLED = "dupeip_enabled";
    private final String WELCOME_ENABLED = "welc_enabled";
    private final String ACC_ENABLED = "acc_enabled";
    private final String PD_ENABLED = "pd_enabled";
    private final String AUTODUPEIP_ENABLED = "autodupeip_enabled";

    private final String PT_ICON = "pt_icon";
    private final String HISTORY_ICON = "history_icon";
    private final String CHATFLOOD_ICON = "chatflood_icon";
    private final String WELCOME_ICON = "welcome_icon";
    private final String DUPEIP_ICON = "dupeip_icon";

    private final String WELCOME_MSG = "welcome_msg";

    private void loadConfig(){
        this.enabled = !AkumaMod.getInstance().getConfig().has(ENABLED) || AkumaMod.getInstance().getConfig().get(ENABLED).getAsBoolean();
        this.translator_source = AkumaMod.getInstance().getConfig().has(SOURCE) ? EnumModuleTranslatorSources.fromString(AkumaMod.getInstance().getConfig().get(SOURCE).getAsString()) : EnumModuleTranslatorSources.DEFAULT;
        this.lang = AkumaMod.getInstance().getConfig().has(LANG) ? AkumaMod.getInstance().getConfig().get(LANG).getAsInt() : 0;
        //this.translateAtHover = TranslatorAddon.getInstance().getConfig().has(HOVER) ? TranslatorAddon.getInstance().getConfig().get(HOVER).getAsBoolean() : false;
        //this.delaySeconds = TranslatorAddon.getInstance().getConfig().has(DELAY) ? TranslatorAddon.getInstance().getConfig().get(DELAY).getAsInt() : 2;
        this.apiKey = AkumaMod.getInstance().getConfig().has(APIKEY) ? AkumaMod.getInstance().getConfig().get(APIKEY).getAsString() : "CHANGE_ME";

        this.pt_enabled = !AkumaMod.getInstance().getConfig().has(PT_ENABLED) || AkumaMod.getInstance().getConfig().get(PT_ENABLED).getAsBoolean();
        this.pt_icon = AkumaMod.getInstance().getConfig().has(PT_ICON) ? AkumaMod.getInstance().getConfig().get(PT_ICON).getAsString() : " &7[&ePT&7]";

        this.translate_enabled = !AkumaMod.getInstance().getConfig().has(TRANSLATE_ENABLED) || AkumaMod.getInstance().getConfig().get(TRANSLATE_ENABLED).getAsBoolean();
        this.translation_icon = AkumaMod.getInstance().getConfig().has(ICON) ? AkumaMod.getInstance().getConfig().get(ICON).getAsString() : " &7[&fT&7]";

        this.history_enabled = !AkumaMod.getInstance().getConfig().has(HISTORY_ENABLED) || AkumaMod.getInstance().getConfig().get(HISTORY_ENABLED).getAsBoolean();
        this.history_icon = AkumaMod.getInstance().getConfig().has(HISTORY_ICON) ? AkumaMod.getInstance().getConfig().get(HISTORY_ICON).getAsString() : " &7[&cH&7]";

        this.chatflood_enabled = !AkumaMod.getInstance().getConfig().has(CHATFLOOD_ENABLED) || AkumaMod.getInstance().getConfig().get(CHATFLOOD_ENABLED).getAsBoolean();
        this.chatflood_icon = AkumaMod.getInstance().getConfig().has(CHATFLOOD_ICON) ? AkumaMod.getInstance().getConfig().get(CHATFLOOD_ICON).getAsString() : " &7[&6CF&7]";

        this.dupeip_enabled = !AkumaMod.getInstance().getConfig().has(DUPEIP_ENABLED) || AkumaMod.getInstance().getConfig().get(DUPEIP_ENABLED).getAsBoolean();
        this.dupeip_icon = AkumaMod.getInstance().getConfig().has(DUPEIP_ICON) ? AkumaMod.getInstance().getConfig().get(DUPEIP_ICON).getAsString() : " &7[&9D&7]";

        this.welcome_enabled = !AkumaMod.getInstance().getConfig().has(WELCOME_ENABLED) || AkumaMod.getInstance().getConfig().get(WELCOME_ENABLED).getAsBoolean();
        this.welcome_icon = AkumaMod.getInstance().getConfig().has(WELCOME_ICON) ? AkumaMod.getInstance().getConfig().get(WELCOME_ICON).getAsString() : " &7[&aW&7]";
        this.welcome_msg = AkumaMod.getInstance().getConfig().has(WELCOME_MSG) ? AkumaMod.getInstance().getConfig().get(WELCOME_MSG).getAsString() : "Welcome %s!";

        this.acc_enabled = !AkumaMod.getInstance().getConfig().has(ACC_ENABLED) || AkumaMod.getInstance().getConfig().get(ACC_ENABLED).getAsBoolean();

        this.pd_enabled = !AkumaMod.getInstance().getConfig().has(PD_ENABLED) || AkumaMod.getInstance().getConfig().get(PD_ENABLED).getAsBoolean();

        this.autodupeip_enabled = !AkumaMod.getInstance().getConfig().has(AUTODUPEIP_ENABLED) || AkumaMod.getInstance().getConfig().get(AUTODUPEIP_ENABLED).getAsBoolean();
    }

    public void init(){
        subSettings.clear();

        subSettings.add(new HeaderElement("General"));
        subSettings.add(new BooleanElement("Enabled", new ControlElement.IconData(Material.LEVER), aBoolean -> {
            enabled = aBoolean;
            saveConfig();
        }, enabled));
        subSettings.add(new HeaderElement("AkumaMod Icons"));
        subSettings.add(new BooleanElement("Translate", new ControlElement.IconData(Material.LEVER), aBoolean -> {
            translate_enabled = aBoolean;
            saveConfig();
        }, translate_enabled));
        subSettings.add(new BooleanElement("Playtime", new ControlElement.IconData(Material.LEVER), aBoolean -> {
            pt_enabled = aBoolean;
            saveConfig();
        }, pt_enabled));
        subSettings.add(new BooleanElement("History", new ControlElement.IconData(Material.LEVER), aBoolean -> {
            history_enabled = aBoolean;
            saveConfig();
        }, history_enabled));
        subSettings.add(new BooleanElement("Welcome", new ControlElement.IconData(Material.LEVER), aBoolean -> {
            welcome_enabled = aBoolean;
            saveConfig();
        }, welcome_enabled));
        subSettings.add(new BooleanElement("Chat Flood", new ControlElement.IconData(Material.LEVER), aBoolean -> {
            chatflood_enabled = aBoolean;
            saveConfig();
        }, chatflood_enabled));
        subSettings.add(new BooleanElement("DupeIP", new ControlElement.IconData(Material.LEVER), aBoolean -> {
            dupeip_enabled = aBoolean;
            saveConfig();
        }, dupeip_enabled));
        subSettings.add(new HeaderElement("AkumaMod Features"));
        subSettings.add(new BooleanElement("Anti Clear Chat", new ControlElement.IconData(Material.BARRIER), aBoolean -> {
            acc_enabled = aBoolean;
            saveConfig();
        }, acc_enabled));
        subSettings.add(new BooleanElement("Punishment Detector", new ControlElement.IconData(Material.IRON_AXE), aBoolean -> {
            pd_enabled = aBoolean;
            saveConfig();
        }, pd_enabled));
        subSettings.add(new HeaderElement("AkumaMod Automation"));
        subSettings.add(new BooleanElement("DupeIP", new ControlElement.IconData(Material.REDSTONE), aBoolean -> {
            autodupeip_enabled = aBoolean;
            saveConfig();
        }, autodupeip_enabled));

        DropDownMenu<EnumModuleTranslatorSources> alignmentDropDownMenu = new DropDownMenu<EnumModuleTranslatorSources>("Translator", 0, 0, 0, 0 ).fill(EnumModuleTranslatorSources.values());
        DropDownElement<EnumModuleTranslatorSources> alignmentDropDown = new DropDownElement<>("Translator", alignmentDropDownMenu );
        alignmentDropDownMenu.setSelected(translator_source);
        alignmentDropDown.setChangeListener(alignment -> {
            translator_source = alignment;
            saveConfig();
        });
        subSettings.add(alignmentDropDown);

        String[] help;

        DropDownMenu<EnumModuleLanguages> alignmentDropDownMenu2 = new DropDownMenu<EnumModuleLanguages>("Translate to", 0, 0, 0, 0).fill(EnumModuleLanguages.values());
        DropDownElement<EnumModuleLanguages> alignmentDropDown2 = new DropDownElement<>("Translate to", alignmentDropDownMenu2);
        alignmentDropDownMenu2.setSelected(EnumModuleLanguages.fromInt(lang));
        alignmentDropDown2.setChangeListener(alignment -> {
            lang = EnumModuleLanguages.fromEnum(alignment);
            saveConfig();
        });
        subSettings.add(alignmentDropDown2);

        subSettings.add(new HeaderElement("Action Icons"));
        StringElement icon = new StringElement("Translate", new ControlElement.IconData(Material.PAPER), this.getTranslation_icon(), s -> {
            translation_icon = s;
            saveConfig();
        });
        subSettings.add(icon);
        StringElement playtime_icon = new StringElement("Playtime", new ControlElement.IconData(Material.PAPER), this.getPlaytimeIcon(), s -> {
            pt_icon = s;
            saveConfig();
        });
        subSettings.add(playtime_icon);
        StringElement hist_icon = new StringElement("History", new ControlElement.IconData(Material.PAPER), this.getHistoryIcon(), s -> {
            history_icon = s;
            saveConfig();
        });
        subSettings.add(hist_icon);
        StringElement cf_icon = new StringElement("Chat Flood", new ControlElement.IconData(Material.PAPER), this.getChatfloodIcon(), s -> {
            chatflood_icon = s;
            saveConfig();
        });
        subSettings.add(cf_icon);
        StringElement dip_msg = new StringElement("DupeIP", new ControlElement.IconData(Material.PAPER), this.getDupeIPIcon(), s -> {
            dupeip_icon = s;
            saveConfig();
        });
        subSettings.add(dip_msg);

        StringElement welc_icon = new StringElement("Welcome", new ControlElement.IconData(Material.PAPER), this.getWelcomeIcon(), s -> {
            welcome_icon = s;
            saveConfig();
        });
        subSettings.add(welc_icon);

        subSettings.add(new HeaderElement("Welcome Message"));
        help = new String[] {
                "You can add a §a%s§r to the",
                "welcome message which will be",
                "automatically replaced with the",
                "users username, or §e%n§r for",
                "the users join number.",
                ""
        };
        for(String s : help)
            subSettings.add(new TextElement(s,5, 10));

        StringElement w_msg = new StringElement("Welcome Message", new ControlElement.IconData(Material.PAPER), this.getWelcomeMsg(), s -> {
            welcome_msg = s;
            saveConfig();
        });
        subSettings.add(w_msg);
    }

    private void saveConfig(){
        AkumaMod.getInstance().getConfig().addProperty(ENABLED, this.enabled);
        AkumaMod.getInstance().getConfig().addProperty(SOURCE, this.translator_source.toString());
        AkumaMod.getInstance().getConfig().addProperty(LANG, this.lang);
        //TranslatorAddon.getInstance().getConfig().addProperty(HOVER, this.translateAtHover);
        //TranslatorAddon.getInstance().getConfig().addProperty(DELAY, this.delaySeconds);
        AkumaMod.getInstance().getConfig().addProperty(APIKEY, this.apiKey);

        AkumaMod.getInstance().getConfig().addProperty(TRANSLATE_ENABLED, this.translate_enabled);
        AkumaMod.getInstance().getConfig().addProperty(ICON, this.translation_icon);

        AkumaMod.getInstance().getConfig().addProperty(PT_ENABLED, this.pt_enabled);
        AkumaMod.getInstance().getConfig().addProperty(PT_ICON, this.pt_icon);

        AkumaMod.getInstance().getConfig().addProperty(HISTORY_ENABLED, this.history_enabled);
        AkumaMod.getInstance().getConfig().addProperty(HISTORY_ICON, this.history_icon);

        AkumaMod.getInstance().getConfig().addProperty(CHATFLOOD_ICON, this.chatflood_icon);
        AkumaMod.getInstance().getConfig().addProperty(CHATFLOOD_ENABLED, this.chatflood_enabled);

        AkumaMod.getInstance().getConfig().addProperty(DUPEIP_ICON, this.dupeip_icon);
        AkumaMod.getInstance().getConfig().addProperty(DUPEIP_ENABLED, this.dupeip_enabled);

        AkumaMod.getInstance().getConfig().addProperty(WELCOME_ICON, this.welcome_icon);
        AkumaMod.getInstance().getConfig().addProperty(WELCOME_ENABLED, this.welcome_enabled);
        AkumaMod.getInstance().getConfig().addProperty(WELCOME_MSG, this.welcome_msg);

        AkumaMod.getInstance().getConfig().addProperty(ACC_ENABLED, this.acc_enabled);

        AkumaMod.getInstance().getConfig().addProperty(PD_ENABLED, this.pd_enabled);

        AkumaMod.getInstance().getConfig().addProperty(AUTODUPEIP_ENABLED, this.autodupeip_enabled);

        AkumaMod.getInstance().resetTranslator();
        AkumaMod.getListener().translations.clear();

        init();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public EnumModuleTranslatorSources getTranslatorSource(){
        return translator_source;
    }

    public int getLanguageAsInt(){
        return lang;
    }

    public String getWelcomeMsg() {return welcome_msg;}

    public boolean isCfEnabled() {return chatflood_enabled; }
    public boolean isPtEnabled () {return pt_enabled; }
    public boolean isACCEnabled() {return acc_enabled; }
    public boolean isDupeIPEnabled () {return dupeip_enabled; }
    public boolean isHistoryEnabled () {return history_enabled; }
    public boolean isWelcomeEnabled() {return welcome_enabled; }
    public boolean isTranslateEnabled () {return translate_enabled; }
    public boolean isPdEnabled () { return pd_enabled; }
    public boolean isAutoDupeipEnabled() { return autodupeip_enabled; }

    public String getTranslation_icon() {
        return translation_icon;
    }
    public String getDupeIPIcon() { return dupeip_icon; }
    public String getChatfloodIcon() { return chatflood_icon; }
    public String getWelcomeIcon() { return welcome_icon; }
    public String getPlaytimeIcon() {
        return pt_icon;
    }
    public String getHistoryIcon() {
        return history_icon;
    }

    public boolean isTranslateAtHover() {
        return false;
    }

    public int getDelaySeconds() {
        return 0;
    }

    public String getApiKey(){
        return apiKey;
    }
}
