package org.mancheguet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SynchUtilities {

    //funcion para ejecutar copias de seguridad
    public void ejecutarBackup(String rutaPerfil) throws IOException {

        //ejecutar cmd para synch pueda hacer copias pasando por parametro el nombre del perfil
        //SynchredibleCmd.exe /RUN "<ruta del perfil>"

        ProcessBuilder builder = new ProcessBuilder("SynchredibleCmd.exe", "/RUN", rutaPerfil);
        builder.start();

    }

    //función para consultar el último log de copia de seguridad
    public String leerUltimoRegistro(String rutaLog) throws IOException {
        StringBuilder logContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaLog))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logContent.append(line).append("\n");
            }
        }
        return logContent.toString();
    }

}
