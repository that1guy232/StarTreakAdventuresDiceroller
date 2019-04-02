package com.tree.STAdventures;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class STAdventuresBot implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String message = event.getMessage().getContent();
        if(message.startsWith("!sta")) {
            message = message.replaceFirst("!sta","").trim();
            String[] diceS = message.split(" ");
            diceS[0] = diceS[0].trim();
            diceS[1] = diceS[1].trim();

            int d20s = Integer.parseInt(diceS[0]);
            int d6s  = Integer.parseInt(diceS[1]);

            d20s += 2;



            StringBuilder stringBuilder = new StringBuilder();
            String beginning = event.getMessage().getAuthor().asUser().get().getMentionTag() + " Rolled " + d20s+"D20's & " + d6s + " Challenge Dice.\n";

            stringBuilder.append("[");
            for (int i = 0; i < d20s; i++) {
                stringBuilder.append(d20sRoll() + " ");
            }
            stringBuilder.append("] ");
            String resultOf20s = stringBuilder.toString();

            stringBuilder = new StringBuilder();
            stringBuilder.append("\nChallenge Dice: ");

            int[] d6srolls = new int[d6s];

            for (int i = 0; i < d6s; i++) {
                int x = ThreadLocalRandom.current().nextInt(1,7);
                stringBuilder.append(d6sRoll(x) + " ");
                d6srolls[i] = x;

            }

            String d6sNumber = Arrays.toString(d6srolls);

            event.getChannel().sendMessage(beginning + "D20's: "+ resultOf20s + " Challenge: Dice: " + d6sNumber + " " + stringBuilder.toString());
        }
    }

    private String d20sRoll() {
        int x = ThreadLocalRandom.current().nextInt(1,21);
        if(x == 20){
            return "<:Fbadge:562524448872202240>";
        }else {
            return String.valueOf(x);
        }

    }
    /*
    1 = 1 star
2 = 2 star
3 = blank
4= Blank
5 = symbol
6 = symbol
     */
    private String d6sRoll(int x) {
        switch (x){
            case 1:
                return "<:1:562544700062695427>";
            case 2:
                return "<:2:562544700058370048> ";
            case 3:
            case 4:
                return "<:sblank:562544699861106701>";
            case 5:
            case 6:
                return "<:FWbadge:562544700012363776>";
        }
        return "This should not happen contact Tree#1111";
    }
}
