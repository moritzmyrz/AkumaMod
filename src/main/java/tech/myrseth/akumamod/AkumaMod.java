package tech.myrseth.akumamod;

import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;
import tech.myrseth.akumamod.api.DefaultTranslatorAPI;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;
import tech.myrseth.akumamod.modules.ESTText;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class AkumaMod extends LabyModAddon {

    private static AkumaMod translatorAddon;
    private static Settings settings;
    private static DefaultTranslatorAPI translator;
    private static Listener listener;

    public ModuleCategory category = new ModuleCategory("AkumaMod", true, new ControlElement.IconData(Material.IRON_AXE));

    @Override
    public void onEnable() {
        System.out.println("============================================");
        System.out.println(" Activate AkumaMod Addon for LabyMod ");
        System.out.println("============================================");

        translatorAddon = this;
        try {
            listener = new Listener();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.getApi().registerModule(new ESTText());

        ModuleCategoryRegistry.loadCategory(category);

        System.out.println("============================================");
        System.out.println(" Enabled AkumaMod Addon for LabyMod  ");
        System.out.println("============================================");
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        settings = new Settings(list);
        resetTranslator();
    }

    public void resetTranslator(){
        translator = TranslatorManager.getTrangslator(getSettings().getTranslatorSource());
        translator.setAPIKey(settings.getApiKey());
        translator.setOutputLanguage(settings.getLanguageAsInt());
    }

    public static AkumaMod getInstance(){
        return translatorAddon;
    }

    public static Settings getSettings(){
        return settings;
    }

    public static DefaultTranslatorAPI getTranslator(){
        return translator;
    }

    public static Listener getListener() {
        return listener;
    }
}
