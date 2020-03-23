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
            int[] D6Rolls = rollD6s(d6s);



            //Building the embed to send
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setTitle(beginning);

            embedBuilder.addField("Results:", " Challenge Dice rolls: " + Arrays.toString(D6Rolls) + " " + stringBuilder.toString() +
                    "\n Challenge Dice Results:" + getD6Results(D6Rolls));

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
                 help.addField("Support:", "Please consider supporting the bot. https://www.paypal.me/TreeAB");
                 help.setFooter("Created by: Tree#7119  This project is not endorsed, sponsored or affiliated with CBS Studios Inc, Modiphius Entertainment or the Star Trek franchise. ");
                 event.getChannel().sendMessage(help);

             } else {


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


                 int[] D6Rolls = null;

                 //if there are any D6's roll them and set them to a string for latter use.
                 if (d6s > 0) {

                     D6Rolls = rollD6s(d6s);
                 }

                 //create the embed and set the title.
                 EmbedBuilder embedBuilder = new EmbedBuilder();
                 embedBuilder.setTitle(beginning);

                 //create the result embeied field detrmed by weather or not d6's were rolled.
                 if (D6Rolls != null) {
                     embedBuilder.addField("Results:", "D20's: " + resultOf20s +
                             "\n Challenge Dice rolls: " + Arrays.toString(D6Rolls) + " " + stringBuilder.toString() +
                             "\n Challenge Dice Results:" + getD6Results(D6Rolls));
                 } else {
                     embedBuilder.addField("Results:", "D20's: " + resultOf20s + stringBuilder.toString());
                 }

                 //send the message.
                 event.getChannel().sendMessage(embedBuilder);
             }
         }
    }

    private String getD6Results(int[] d6Rolls) {
        StringBuilder sb = new StringBuilder();
        int effects = 0;
        int total = 0;
        for (int i = 0; i < d6Rolls.length; i++) {
            switch (d6Rolls[i]){
                case 1:
                    total++;
                    break;
                case 2:
                    total += 2;
                    break;
                case 3:
                case 4:
                    break;
                case 5:
                case 6:
                    effects++;
                    total++;
                    break;
            }


        }
        sb.append( " " + total + " Hits,  " + effects + " Effects.");
        return sb.toString();
    }

    private int[] rollD6s(int d6s) {

        int[] rolls = new int[d6s];

        for (int i = 0; i < d6s; i++) {
            rolls[i] = ThreadLocalRandom.current().nextInt(1,7);

        }


        return rolls;
    }



    private String d20sRoll() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(1,21));
    }

}
