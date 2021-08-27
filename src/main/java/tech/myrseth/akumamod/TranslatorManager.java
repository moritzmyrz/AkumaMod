package tech.myrseth.akumamod;

import tech.myrseth.akumamod.api.DefaultTranslatorAPI;
import tech.myrseth.akumamod.api.GoogleTranslateAPI;
import tech.myrseth.akumamod.enums.EnumModuleTranslatorSources;

public class TranslatorManager {

    public static DefaultTranslatorAPI getTrangslator(EnumModuleTranslatorSources translatorSources){
        return new GoogleTranslateAPI();
    }
}
