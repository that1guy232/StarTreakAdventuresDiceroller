package com.tree.STAdventures;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String token = args[0];

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();


        api.getServers().forEach(server -> System.err.println(server.getName() + "amount of users: " + server.getMemberCount()));

        api.addListener(new STAdventuresBot(api));
        api.addListener(new ActivityUpdater(api));

    }
}
