package org.mancheguet;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.Set;

public class MiBot extends TelegramLongPollingBot {

    //Declartamos primero el bot token para acceder al bot
    //Recuerda dar de alta antes en tu sistema la variable de entorno con el token key TELEGRAM_BOT_TOKEN
    // para dar de alta en windows en cmd: setx TELEGRAM_BOT_TOKEN "tokenAqui"
    private static final String BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
    // para dar de alta en windows en cmd: setx TELEGRAM_BOT_NAME "nombreAqui"
    private static final String BOT_USERNAME = System.getenv("TELEGRAM_BOT_NAME");

    // Lista de IDs de usuarios autorizados
    private static final Set<Long> USUARIOS_AUTORIZADOS = new HashSet<>();

    // Inicializa los usuarios autorizados
    // DESDE LA VARIABLE DE ENTORNO
    // para dar de alta en windows, en cmd : setx USUARIOS_BOTTELE "IDEUSUARIO1, IDUSUARUIO2"

    static {

        String usuariosEnv = System.getenv("USUARIOS_BOTTELE");

        if (usuariosEnv != null) {

            for (String id : usuariosEnv.split(",")) {
                //System.out.println(Long.parseLong(id.trim())); //para ver la id de usuario
                USUARIOS_AUTORIZADOS.add(Long.parseLong(id.trim()));

            }
        }
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            long userId = update.getMessage().getFrom().getId();

            // Verifica si el usuario está autorizado
            if (USUARIOS_AUTORIZADOS.contains(userId)) {

                String messageText = update.getMessage().getText();
                String responseText;

                switch (messageText) {
                    case "/help":
                        responseText = "Estos son los comandos disponibles :\n" +
                                "/bPatrones : Ejecutar copia de seguridad de patrones.\n" +
                                "/bDUsers : Ejecuta copia de seguiridad Diaria de Users.\n" +
                                "/bSUsers : Ejecuta copia de seguridad Semanal de Users.\n" +
                                "/bEUsers : Ejecutar copia de seguridad Externa de Users.\n" +
                                "/Ureporte : Mostrar el último reporte de las copias de seguridad\n" +
                                "Que comando quieres usar?";
                        break;
                    case "/bPatrones":
                        responseText = "Ejecutando copia de seguridad de Patrones...";
                        break;
                    case "/bDUsers":
                        responseText = "Ejecutando copia de seguridad Diaria de Users...";
                        break;
                    case "/bSUsers":
                        responseText = "Ejecutando copia de seguridad Semanal de Users";
                        break;
                    case "/bEUsers":
                        responseText = "Ejecutando copia de seguridad Externa de Users";
                        break;
                    case "/Ureporte":
                        responseText = "Aquí tienes el último reporte:\n";
                        break;
                    default:
                        responseText = "Comando no reconocido.";
                }

                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(update.getMessage().getChatId()));
                message.setText(responseText);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {

                // Respuesta para usuarios no autorizados
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(update.getMessage().getChatId()));
                message.setText("Acceso denegado. Este bot es privado.");

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
