package jogo.Gemini;

import com.google.genai.Client;

import java.util.List;

public class GeminiServices {

    private Client client;
    private int indiceModeloAtual = 0;

    private final List<String> modelosGemini = List.of(
            "gemini-robotics-er-1.6-preview",
            "gemini-3.1-flash-lite-preview",
            "gemini-2.5-flash",
            "gemini-2.5-flash-lite",
            "gemini-2.5-pro"
    );

    private final String promptSistema =
            "Você é o mestre de um jogo de RPG de sobrevivência zumbi. " +
                    "Sua tarefa é interpretar o personagem com quem o jogador está interagindo. " +
                    "Regras: seja fiel ao personagem, nunca saia do papel."+
                    "não precisa ser perfeito, tente ser bem resumida para não travar o jogo e mantendo a criatividade" +
                    "use no maximo umas 5 linhas para as falas, porque o chat fica muito extenso e poluido";

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

        String contextoFinal = promptSistema +
                "\n\nNPC: " + infoNpc +
                "\nJogador: " + mensagemJogador;

        int total = modelosGemini.size();

        // tenta todos os modelos começando do último que funcionou
        for (int i = 0; i < total; i++) {

            int index = (indiceModeloAtual + i) % total;
            String modelo = modelosGemini.get(index);

            try {
                var response = client.models.generateContent(
                        modelo,
                        contextoFinal,
                        null
                );

                if (response != null && response.text() != null) {

                    // guarda o modelo que funcionou
                    indiceModeloAtual = index;

                    return response.text();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "O personagem apenas te observa em silêncio...";
    }
}