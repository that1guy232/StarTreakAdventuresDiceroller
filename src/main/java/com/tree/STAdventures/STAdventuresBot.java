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

        //For my use only get the amount guilds & users and prints them (this will break in the the future)
        if(event.getMessageAuthor().getId() == 145676409078022144L){
            if (event.getMessage().getContent().toLowerCase().equals("!sta rollD6s")){
                StringBuilder stringBuilder = new StringBuilder();

                event.getApi().getServers().forEach(server -> stringBuilder.append(server.getName() + " amount of users: " + server.getMemberCount() + "\n"));


                event.getChannel().sendMessage(stringBuilder.toString());
            }
        }


        //Converts the message to lowercase so it's easier to work with.
        String message = event.getMessage().getContent().toLowerCase();


        //if the user just wants to roll challenge dice.
         if(message.startsWith("!stac")){

             //cleans the message for better use.
            message = message.replaceFirst("!stac", "").trim();
            System.err.println(message);

            //gets the amount of d6's
            int d6s =   Integer.parseInt(message);

            StringBuilder stringBuilder = new StringBuilder();
             //start of the embed (user rolled x challenge dice)
            String beginning = event.getMessage().getAuthor().asUser().get().getName() + " Rolled "  + d6s + " Challenge Dice.\n";

            //gets the rolls.
            int[] d6srolls = rollD6s(d6s, stringBuilder);

            //creates a string of all the rolls
            String d6sNumber = Arrays.toString(d6srolls);

            //Building the embed to send
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setTitle(beginning);

            embedBuilder.addField("Results:", " Challenge: Dice results: " + d6sNumber + " " + stringBuilder.toString());

            //sending the embed to the channel
            event.getChannel().sendMessage(embedBuilder);

         }else if(message.startsWith("!sta")) {




            //the help command
            if (message.toLowerCase().contains("help")) {
                EmbedBuilder help = new EmbedBuilder();
                help.setTitle("STA help");
                help.addField("Main usage", "!sta [d20's] [d6's]");
                help.addField("You can also do:", "!stac [d6's] to exclusively roll challenge dice or !sta [d20's] to just roll d20's!");
                help.addField("Discord link: ", " https://discord.gg/CrrDRRd");

                help.setFooter("Created by: Tree#7119 ");
                event.getChannel().sendMessage(help);
            }


            //cleaning the message.
            message = message.replaceFirst("!sta", "").trim();
            String[] diceS = message.split(" ");
            diceS[0] = diceS[0].trim();

            //getting the d20's
            int d20s = Integer.parseInt(diceS[0]);
            //set the value of d6s to avoid errors latter
            int d6s = 0;

            //get the d6's if there are any.
            if (diceS.length > 1) {
                diceS[1] = diceS[1].trim();
                d6s = Integer.parseInt(diceS[1]);
            }


            StringBuilder stringBuilder = new StringBuilder();
            String beginning;

            //set up the name of the embed depending on weather or not the user rolled any d6's
            if (d6s > 0) {
                beginning = event.getMessage().getAuthor().asUser().get().getName() + " Rolled " + d20s + "D20's & " + d6s + " Challenge Dice.\n";
            } else {
                beginning = event.getMessage().getAuthor().asUser().get().getName() + " Rolled " + d20s + "D20's.\n";
            }


            //start of the d20's
            stringBuilder.append("[");


            //roll the d20's and append the to the string builder.
            for (int i = 0; i < d20s; i++) {
                stringBuilder.append(d20sRoll() + " ");
            }

            //end of the d20's and set it to string for latter use.
            stringBuilder.append("] ");
            String resultOf20s = stringBuilder.toString();

            //new
            stringBuilder = new StringBuilder();


            String resultOfD6s = "";

            //if there are any D6's roll them and set them to a string for latter use.
            if (d6s > 0){
                resultOfD6s = Arrays.toString(rollD6s(d6s, stringBuilder));
            }

            //create the embed and set the title.
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(beginning);

            //create the result embeied field detrmed by weather or not d6's were rolled.
            if(d6s > 0) {
                embedBuilder.addField("Results:", "D20's: " + resultOf20s + " Challenge: Dice results: " + resultOfD6s + " " + stringBuilder.toString());
            }else {
                embedBuilder.addField("Results:", "D20's: " + resultOf20s + stringBuilder.toString());
            }

            //send the message.
            event.getChannel().sendMessage(embedBuilder);
        }
    }

    private int[] rollD6s(int d6s, StringBuilder stringBuilder) {
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
            return "<:FWbadge:562544700012363776>\"";
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
        return "This should not happen contact Tree!";
    }
}
