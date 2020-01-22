package com.tree.STAdventures;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class STAdventuresBot implements MessageCreateListener {



    public STAdventuresBot( DiscordApi api) {

 }


    @Override
    public void onMessageCreate(MessageCreateEvent event) {


        if(event.getMessageAuthor().getId() == 145676409078022144L){
            if (event.getMessage().getContent().toLowerCase().equals("!sta test")){
                StringBuilder stringBuilder = new StringBuilder();

                event.getApi().getServers().forEach(server -> stringBuilder.append(server.getName() + " amount of users: " + server.getMemberCount() + "\n"));


                event.getChannel().sendMessage(stringBuilder.toString());
            }
        }


        String message = event.getMessage().getContent().toLowerCase();

         if(message.startsWith("!stac")){

            message = message.replaceFirst("!stac", "").trim();
            System.err.println(message);

          int d6s =   Integer.parseInt(message);

          StringBuilder stringBuilder = new StringBuilder();

          String beginning = event.getMessage().getAuthor().asUser().get().getName() + " Rolled "  + d6s + " Challenge Dice.\n";


             int[] d6srolls = test(d6s, stringBuilder);


             String d6sNumber = Arrays.toString(d6srolls);

          EmbedBuilder embedBuilder = new EmbedBuilder();

          embedBuilder.setTitle(beginning);

          embedBuilder.addField("Results:", " Challenge: Dice: " + d6sNumber + " " + stringBuilder.toString());

          event.getChannel().sendMessage(embedBuilder);

         }else if(message.startsWith("!sta")) {





            if (message.toLowerCase().contains("help")) {
                EmbedBuilder help = new EmbedBuilder();
                help.setTitle("STA help");
                help.addField("Main usage", "!sta [d20's] [d6's]");
                help.addField("Discord link: ", " https://discord.gg/CrrDRRd");

                help.setFooter("Created by: Tree#7119 ");
                event.getChannel().sendMessage(help);
            }


            message = message.replaceFirst("!sta", "").trim();
            String[] diceS = message.split(" ");


            diceS[0] = diceS[0].trim();


            int d20s = Integer.parseInt(diceS[0]);
            int d6s = 0;

            if (diceS.length > 1) {
                diceS[1] = diceS[1].trim();
                d6s = Integer.parseInt(diceS[1]);
            }


            StringBuilder stringBuilder = new StringBuilder();
            String beginning;
            if (d6s > 0) {
                beginning = event.getMessage().getAuthor().asUser().get().getName() + " Rolled " + d20s + "D20's & " + d6s + " Challenge Dice.\n";
            } else {
                beginning = event.getMessage().getAuthor().asUser().get().getName() + " Rolled " + d20s + "D20's.\n";
            }

            stringBuilder.append("[");

            for (int i = 0; i < d20s; i++) {
                stringBuilder.append(d20sRoll() + " ");
            }

            stringBuilder.append("] ");
            String resultOf20s = stringBuilder.toString();

            stringBuilder = new StringBuilder();

            String d6sNumber = "";

            if (d6s > 0){


                d6sNumber = Arrays.toString(test(d6s, stringBuilder));
        }
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(beginning);

            if(d6s > 0) {
                embedBuilder.addField("Results:", "D20's: " + resultOf20s + " Challenge: Dice: " + d6sNumber + " " + stringBuilder.toString());
            }else {
                embedBuilder.addField("Results:", "D20's: " + resultOf20s + stringBuilder.toString());
            }
            event.getChannel().sendMessage(embedBuilder);
        }
    }

    private int[] test(int d6s, StringBuilder stringBuilder) {
        stringBuilder.append("\nChallenge Dice: ");

        int[] d6srolls = new int[d6s];


        for (int i = 0; i < d6s; i++) {
          int x = ThreadLocalRandom.current().nextInt(1, 7);
          stringBuilder.append(d6sRoll(x) + " ");
          d6srolls[i] = x;

        }
        return d6srolls;
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
