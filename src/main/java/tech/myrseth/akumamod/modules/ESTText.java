package tech.myrseth.akumamod.modules;

import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import tech.myrseth.akumamod.AkumaMod;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ESTText extends SimpleModule {
    @Override
    public ControlElement.IconData getIconData() {
        return null;
    }


    @Override
    public void loadSettings() {}

    @Override
    public String getControlName() {
        return getSettingName();
    }

    @Override
    public String getSettingName() {
        return "EST Time";
    }

    @Override
    public String getDescription() {
        return "Display the current EST time.";
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public String getDisplayName() {
        return "EST Time";
    }

    @Override
    public String getDisplayValue() {
        return getTime();
    }

    @Override
    public String getDefaultValue() {
        return getTime();
    }

    @Override
    public ModuleCategory getCategory() {
        return AkumaMod.getInstance().category;
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date d = new Date();
        return sdf.format(d);
    }
}
