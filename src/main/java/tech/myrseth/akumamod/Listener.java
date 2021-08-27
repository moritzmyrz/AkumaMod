package tech.myrseth.akumamod;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import net.labymod.api.EventManager;
import net.labymod.api.events.MessageModifyChatEvent;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import tech.myrseth.akumamod.utils.MD5;

public class Listener {

    private final List<String> cfWords = Arrays.asList("twitch.tv", "youtube.com", "youtu.be", "twitch");
    private final List<String> bypassRanks = Arrays.asList("[Alert]", "[Owner]", "[Manager]","[Sr-Admin]","[Admin]","[Jr-Admin]","[Sr-Mod]","[Mod]","[Jr-Mod]","[Helper]","[Partner]","[Media-Owner]");
    private final List<String> chatSigns = Arrays.asList("»", ":");

    private String lastMessage = UUID.randomUUID().toString();
    private final HashMap<String, String> messages = new HashMap<>();
    public HashMap<String, String> translations = new HashMap<>();


//    private Countdown countdown;


    public Listener() throws NoSuchAlgorithmException {

        AkumaMod.getInstance().getApi().getEventManager().registerOnJoin(sd -> {
            // Welcome Message
            LabyMod.getInstance().displayMessageInChat("§8§m-----------------------\n§4Akuma§fMod §fv1.3 §7is §aenabled\n§8§m-----------------------");
        });

        EventManager eventManager = AkumaMod.getInstance().getApi().getEventManager();
        eventManager.register(this::sendMessage);
        eventManager.register((MessageModifyChatEvent) this::modifyChatMessage);
        eventManager.register(this::MessageReceiveEvent);
//        this.countdown = new Countdown();
//        AkumaMod.getInstance().getApi().registerModule(countdown);

        // ANTI CLEAR CHAT
        AkumaMod.getInstance().getApi().getEventManager().register((s, s1) -> {
            return AkumaMod.getSettings().isACCEnabled() && s1.trim().isEmpty();
        });
    }

    private boolean MessageReceiveEvent(String msg1, String msg2) {
        final String colorCodeRegex = "\\u00A7[0-9A-Fa-fK-Ok-oRr]";
        final String msg = msg2.replaceAll(colorCodeRegex, "");



        return false;
    }

    private String translationText(String s){
        return "§7Translation: §f" + s;
    }

    // get string between parentathis and endparentathis
    private String getStringBetween(String s, String start, String end){
        Pattern pattern = Pattern.compile(start + "(.*?)" + end);
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()){
            return matcher.group(1);
        }
        return "";
    }


    // count amount of repeated characters in a row (ignoring whitespaces) in a string
    private int countRepeatedChars(String s){
        int count = 0;
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == ' ' || c == '\n' || c == '\t') continue;
            if(i > 0 && s.charAt(i-1) == c) count++;
        }
        return count+1;
    }

    // count uppercase words in a message
    private int countUppercaseWords(String s){
        return (int) Stream.of(s.split("\\s+")).filter(word -> word.matches("[A-Z]+")).count();
    }

    private boolean sendMessage(String s){

        if(s.startsWith("/+translate ")){
        
            String message = s.replace("/+translate ", "");

            String MD5String = MD5.getMD5(message);
            messages.put(MD5String, message);

            s = "/+translateNow " + MD5String;
        }

        if(s.startsWith("/+translateNow ")){
            String hash = s.replace("/+translateNow ", "");

            if(!messages.containsKey(hash)){
                lastMessage = "[Translate] An error has occurred - 001";
                LabyMod.getInstance().displayMessageInChat("[Translate] An error has occurred - 001");
                return true;
            }

            final Thread translationThread = new Thread(() -> {
                String translation;
                if(translations.containsKey(hash)){
                    translation = translationText(translations.get(hash));
                }else {
                    String text = messages.get(hash).replace("\n", " ");
                    translation = AkumaMod.getTranslator().translate(text);
                    translations.put(hash, translation);
                    translation = translationText(translation);
                }

                lastMessage = translation;
                LabyMod.getInstance().displayMessageInChat(translation);
            });

            final Thread inspectThread = new Thread(() -> {
                int sleepSeconds = 10;
                try {
                    Thread.sleep(sleepSeconds * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(translationThread.isAlive()) {
                    translationThread.suspend();

                    String a = "[Translate] API did not reply within "+sleepSeconds+" seconds..";
                    lastMessage = a;
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(a));
                }
            });

            translationThread.start();
            inspectThread.start();
            return true;
        }
        return false;
    }

    private Object modifyChatMessage(Object o){
        if(!AkumaMod.getSettings().isEnabled())
            return o;

        try {
            IChatComponent cct = (IChatComponent) o;
            String original = cct.getUnformattedText();

            if(original.equals(lastMessage))
                return o;
            lastMessage = cct.getUnformattedText();
            if(original.length() == 0)
                return o;
            String withOut = original.replace("\t", "").replace("\n", "").replace("\r", "").replace(" ", "");
            if(withOut.length() == 0)
                return o;

            for(IChatComponent cc : cct.getSiblings()){
                if(cc.getChatStyle() != null && cc.getChatStyle().getChatHoverEvent() != null &&
                        cc.getChatStyle().getChatHoverEvent().getValue() != null && cc.getChatStyle().getChatHoverEvent().getValue().getUnformattedText() != null &&
                        cc.getChatStyle().getChatHoverEvent().getValue().getUnformattedText().startsWith(translationText(""))){
                    return o;
                }
            }

            final String regex = "\\[.*?\\]\\s+(\\w+)";
            final String colorCodeRegex = "\\u00A7[0-9A-Fa-fK-Ok-oRr]";
            final String realUnf = original.replaceAll(colorCodeRegex, "");

            String chatmsg = realUnf.substring(realUnf.indexOf("»")+2);
            String MD5String = MD5.getMD5(original);
            messages.put(MD5String, original);

            if ( !realUnf.contains("[Report]")&& !realUnf.contains("[Request]") && (realUnf.contains("[S]") || realUnf.contains("[SC]"))) return o;

            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(realUnf);

            int chars = countRepeatedChars(chatmsg);
            int wordsInCaps = countUppercaseWords(chatmsg);

            Settings settings = AkumaMod.getSettings();

            if(!settings.isTranslateAtHover()) {

                // TRANSLATE
                if (settings.isTranslateEnabled()&& !realUnf.contains("» » Welcome")) {
                    IChatComponent comp = new ChatComponentText(settings.getTranslation_icon().replace("&", "§"));
                    comp = addHoverAndCommand(comp, "Click here to translate", "/+translateNow " + MD5String);
                    cct.appendSibling(comp);
                }

                if (containsAKeyword(realUnf, bypassRanks) || realUnf.contains("AkumaMC » Punishment")) {
                    lastMessage = cct.getUnformattedText();
                    return cct;
                }

                if (matcher.find() && containsAKeyword(realUnf.toLowerCase(), chatSigns)){
                    final String username = matcher.group(1);

                    if (Objects.equals(username, LabyMod.getInstance().getPlayerName())) {
                        lastMessage = cct.getUnformattedText();
                        return cct;
                    }

                    // WELCOME
                    if (settings.isWelcomeEnabled() && realUnf.contains("» » Welcome")) {
                        String memberNumber = getStringBetween(realUnf, "\\(", "\\)");
                        IChatComponent comp = new ChatComponentText(settings.getWelcomeIcon().replace("&", "§"));
                        comp = addHoverAndCommand(comp, "§aClick here to welcome " + username + "!", AkumaMod.getSettings().getWelcomeMsg().replace("%s", username).replace("%n", memberNumber));
                        cct.appendSibling(comp);
                    }
                    // DUPEIP
                    if (realUnf.contains("» » Welcome")) {
                        if (settings.isAutoDupeipEnabled()) {
                            Minecraft.getMinecraft().thePlayer.sendChatMessage("/dupeip " + username);
                        } else if (settings.isDupeIPEnabled()) {
                            IChatComponent comp = new ChatComponentText(settings.getDupeIPIcon().replace("&", "§"));
                            comp = addHoverAndCommand(comp, "§9Click here to dupeip " + username, "/dupeip " + username);
                            cct.appendSibling(comp);
                        }
                    }

                    if (realUnf.contains("» » Welcome")) {
                        lastMessage = cct.getUnformattedText();
                        return cct;
                    }

                    if (settings.isPtEnabled()) {
                        IChatComponent ptcomp = new ChatComponentText(settings.getPlaytimeIcon().replace("&", "§"));
                        ptcomp = addHoverAndCommand(ptcomp, "§eClick here to see " + username + "'s playtime", "/pt " + username);
                        cct.appendSibling(ptcomp);
                    }

                    if (settings.isHistoryEnabled()) {
                        IChatComponent histcomp = new ChatComponentText(settings.getHistoryIcon().replace("&", "§"));
                        histcomp = addHoverAndCommand(histcomp, "§cClick here to see " + username + "'s history", "/history " + username);
                        cct.appendSibling(histcomp);
                    }
                    if (settings.isCfEnabled() && containsAKeyword(chatmsg.toLowerCase(), cfWords)) {
                        Date date = new Date();
                        IChatComponent ptcomp = new ChatComponentText(settings.getChatfloodIcon().replace("&", "§"));
                        ptcomp = addHoverAndCommand(ptcomp, "§6Click here to log " + username + "'s link", "/sc " + username + " " + toTimeStr(date), true, username);
                        cct.appendSibling(ptcomp);
                    }
                    if (settings.isPdEnabled()){
                        String pdPrefix = "§6[PD]§r";
                        if (chars >= 12) {
                            LabyMod.getInstance().displayMessageInChat(pdPrefix + " §6" + username + " §eexcessive chars detected: §6" + chars + "§e chars.");
                        }
                        if (wordsInCaps >= 4 && !realUnf.contains("AUGUST")) {
                            LabyMod.getInstance().displayMessageInChat(pdPrefix + " §6" + username + " §eexcessive caps detected: §6" + wordsInCaps + "§e words.");
                        }
                    }
                }

                lastMessage = cct.getUnformattedText();
                return cct;
            }
            return cct;
        }catch (Exception e){
            e.printStackTrace();
            return o;
        }
    }

    public boolean containsAKeyword(String myString, List<String> keywords){
        for(String keyword : keywords){
            if(myString.contains(keyword)){
                return true;
            }
        }
        return false; // Never found match.
    }

    public static String toTimeStr(Date date) {
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);
        int min = calendar.get(Calendar.MINUTE);
        StringBuilder sb = new StringBuilder();
        sb.append(":");
        if (min < 10) {
            sb.append(0).append(min);
        } else {
            sb.append(min);
        }
        return sb.toString();
    }

    private IChatComponent dontShow(){
        lastMessage = " ";

        return new ChatComponentText(" ");
    }

    private IChatComponent addHoverAndCommand(IChatComponent comp, String hover, String command, boolean cf, String username){
//        if (cf) {
//            countdown.addUser(username);
//        }
        return getiChatComponent(comp, hover, command);
    }

    private IChatComponent addHoverAndCommand(IChatComponent comp, String hover, String command){
        return getiChatComponent(comp, hover, command);
    }

    private IChatComponent getiChatComponent(IChatComponent comp, String hover, String command) {
        ChatStyle style = new ChatStyle().setChatClickEvent(
                        new ClickEvent(ClickEvent.Action.RUN_COMMAND, command) {
                            @Override
                            public Action getAction() {
                                return Action.RUN_COMMAND;
                            }
                        })
                .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(hover)));
        comp.setChatStyle(style);
        return comp;
    }
}
