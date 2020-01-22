package com.tree.STAdventures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ActivityUpdater implements MessageCreateListener {

    DiscordApi api;

    ArrayList<String> activities;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    int placeInActivites = 0;

    File activityFile;

    public ActivityUpdater(DiscordApi api){



       activityFile  = new File("activities.json");

        this.api = api;

        activities = new ArrayList<>();




        activities.addAll(Arrays.asList(Objects.requireNonNull(loadActivates(activityFile))));



        api.getThreadPool().getScheduler().scheduleAtFixedRate(()  ->     {
            System.err.println(activities.size());
            String currentActivity = activities.get(placeInActivites).replace("%servers",String.valueOf(api.getServers().size()));

            api.updateActivity(currentActivity);

            placeInActivites++;
            if(placeInActivites >= activities.size()){
                placeInActivites = 0;
            }


        },0,10, TimeUnit.SECONDS);


    }

    private void saveActivates(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(activities.toArray()));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] loadActivates(File file) {

        try {
          return gson.fromJson(new FileReader(file),String[].class);
        } catch (FileNotFoundException e) {
            saveActivates(file);
            return loadActivates(file);
        }


    }


    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        String messageContent = messageCreateEvent.getMessageContent().toLowerCase();


        if(messageCreateEvent.getMessageAuthor().isBotOwner()){



            if(messageContent.equals("list activities")){

               StringBuilder sb = new StringBuilder();
                for (int i = 0; i < activities.size(); i++) {
                    sb.append(i + " " + activities.get(i) + "\n");
                }

                messageCreateEvent.getChannel().sendMessage(sb.toString());
            }

            if(messageContent.startsWith("add activity")){
                String activityToAdd = messageContent.replaceFirst("add activity ", "").trim();

                activities.add(activityToAdd);

                saveActivates(activityFile);
            }

            if(messageContent.startsWith("remove activity")){
                int activityToRemove = Integer.parseInt(messageContent.replaceFirst("remove activity ", "").trim());

                activities.remove(activityToRemove);

                saveActivates(activityFile);

            }

        }
    }
}
