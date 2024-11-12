package org.mancheguet;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static void main(String[] args) {

        try {
            // Inicializa el bot
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MiBot());
            System.out.println("Bot iniciado con éxito.");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}