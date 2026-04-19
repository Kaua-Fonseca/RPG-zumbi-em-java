package jogo.Gemini;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigLoader {
    public static String getApiKey() {
        Properties props = new Properties();
        String caminhoKeyApi = "src/jogo/config.properties";

            try (FileInputStream fis = new FileInputStream(caminhoKeyApi)) {
                props.load(fis);
                String key = props.getProperty("gemini.api.key");
                if (key != null) return key;
            } catch (IOException ignored) {
                // Tenta o próximo caminho se este falhar
            }

        System.err.println("Não foi possível localizar o arquivo config.properties!");
        return null;
    }
}