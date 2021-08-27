package tech.myrseth.akumamod.modules;

import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleTextModule;
import net.labymod.settings.elements.ControlElement;
import tech.myrseth.akumamod.AkumaMod;

import java.util.ArrayList;
import java.util.List;

public class Countdown extends SimpleTextModule {
    private List<String> usernames = new ArrayList<String>();
    private List<String> timers = new ArrayList<String>();


    public void addUser(String username) {
        usernames.add(username);
        startCountDown();
    }

    @Override
    public String getControlName() {
        return getSettingName();
    }

    @Override
    public ControlElement.IconData getIconData() {
        return null;
    }

    @Override
    public void loadSettings() {

    }

    @Override
    public String getSettingName() {
        return "CF Countdown";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public ModuleCategory getCategory() {
        return AkumaMod.getInstance().category;
    }

    @Override
    public int getSortingId() {
        return 0;
    }


    @Override
    public String[] getValues() {
        String[] timersArray = new String[timers.size()];
        timersArray = timers.toArray(timersArray);
        return timersArray;
    }

    @Override
    public String[] getDefaultValues() {
        return new String[0];
    }

    @Override
    public String[] getKeys() {
        String[] usernameArray = new String[usernames.size()];
        usernameArray = usernames.toArray(usernameArray);
        return usernameArray;
    }

    @Override
    public String[] getDefaultKeys() {
        return new String[0];
    }

    // start countdown from 5 minutes with seconds
    private void startCountDown() {
        int seconds = 300;
        while (seconds > 0) {
            seconds--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.timers.add(convertSecondsToMinutesAndSeconds(seconds));
        }
    }

    // convert seconds to minutes and seconds
    private String convertSecondsToMinutesAndSeconds(int seconds) {
        int minutes = seconds / 60;
        int seconds2 = seconds % 60;
        return minutes + ":" + String.format("%02d", seconds2);
    }
}
