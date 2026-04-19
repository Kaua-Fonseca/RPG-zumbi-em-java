package jogo.Gemini;

import com.google.genai.Client;

public class GeminiServices {

    private Client client;

    private final String modeloGemini = "gemini-robotics-er-1.5-preview";

    private final String promptSistema =
            "Você é o mestre de um jogo de RPG de sobrevivência zumbi. " +
                    "Sua tarefa é interpretar o personagem com quem o jogador está interagindo. " +
                    "Regras: seja fiel ao personagem, nunca saia do papel."+
                    "não precisa ser perfeito, tente ser bem resumida para não travar o jogo e mantendo a criatividade";

    public GeminiServices() {
        String apiKey = ConfigLoader.getApiKey();

        if (apiKey == null || apiKey.isBlank()) {
            throw new RuntimeException("API KEY DO GEMINI NÃO CARREGADA!");
        }

        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
    }

    public String conversar(String infoNpc, String mensagemJogador) {

        try {
            String contextoFinal = promptSistema +
                    "\n\nNPC: " + infoNpc +
                    "\nJogador: " + mensagemJogador;

            var response = client.models.generateContent(
                    modeloGemini,
                    contextoFinal,
                    null
            );

            return response.text();

        } catch (Exception e) {
            System.err.println("ERRO GEMINI:");
            e.printStackTrace();

            return "O personagem apenas te observa em silêncio...";
        }
    }
}