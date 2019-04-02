package com.tree.STAdventures;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Main {
    public static void main(String[] args) {
        String token = args[0];
        System.out.println(token);
        try {
            DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
            api.addListener(new STAdventuresBot());
        }catch (IllegalStateException e){

        }

    }
}
