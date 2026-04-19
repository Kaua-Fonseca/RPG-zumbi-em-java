package jogo.front;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import jogo.Game.Game;
import jogo.Mapa.Mapa;
import jogo.Personagem.Jogador;
import jogo.Personagem.Npc;
import jogo.Tile.Tile;
import org.checkerframework.checker.units.qual.N;;



/**
 * Interface Gráfica do Usuário (GUI) para o RPG Roguelike.
 *
 * Esta classe aplica os princípios SOLID:
 * - Single Responsibility: Focada apenas na exibição e captura de eventos da interface.
 * - Open/Closed: Facilmente estendível para novos modos de input (ex: mouse).
 * - Dependency Inversion: Depende da classe Modelos.Game.Game para obter dados do estado do jogo.
 *
 * @author IA de Apoio (Aula Didática)
 */
public class Layout extends JFrame {

    // --- DEPENDÊNCIA DO JOGO ---
    private final Game jogo;

    // --- CONSTANTES DE ESTILO (Clean Code) ---
    private static final Color COR_FUNDO_JANELA = new Color(10, 10, 10);
    private static final Color COR_FUNDO_CAMPO = new Color(25, 25, 25);
    private static final Color COR_CHAT_ATIVO = new Color(45, 45, 55);
    private static final Color COR_BORDA_MOVIMENTO = new Color(0, 180, 0);
    private static final Color COR_BORDA_CHAT = new Color(0, 200, 255);
    private static final Color COR_TEXTO_MAPA = new Color(220, 220, 220);
    private static final Color COR_MAPA_NOME = new Color(255, 215, 0);

    private static final Font FONTE_MONOSPACED = new Font("Monospaced", Font.BOLD, 22);
    private static final Font FONTE_INTERFACE = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONTE_HEADER = new Font("Monospaced", Font.BOLD, 16);

    // --- COMPONENTES DA UI ---
    private JTextPane areaMapa;
    private JTextField campoInput;
    private JLabel labelNomeMapa;
    private JPanel painelTeclasHUD;
    private JTextArea areaChat;

    // Armazenamento das teclas para manipulação visual (HUD)
    private final JLabel[] teclasHUD = new JLabel[5]; // W, A, S, D, I
    private static final char[] NOMES_TECLAS = {'W', 'A', 'S', 'D', 'I'};

    // --- ESTADO DA INTERFACE ---
    private boolean modoChat = false;

    // indentificar qual npc estou em dialogo e salvar
    private Npc npcAtual = null;

    public Layout(Game jogo) {
        this.jogo = jogo;
        configurarJanela();
        inicializarComponentes();
        montarLayout();
        registrarEventosGlobais();

        // Atualização inicial da interface
        atualizarExibicaoMapa();
        definirNomeMapa(jogo.getMapa().getNome());

    }

    // --- MÉTODOS DE CONFIGURAÇÃO (Single Responsibility) ---

    private void configurarJanela() {
        setTitle("RPG - Projeto Mensal");
        setSize(1024, 768);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO_JANELA);
    }

    private void inicializarComponentes() {
        // Área Central do Mapa
        areaChat = new JTextArea();
        areaMapa = new JTextPane();
        areaMapa.setEditable(false);
        areaMapa.setFocusable(false);
        areaMapa.setBackground(COR_FUNDO_JANELA);
        areaMapa.setForeground(COR_TEXTO_MAPA);
        areaMapa.setFont(FONTE_MONOSPACED);
        areaMapa.setMargin(new Insets(20, 20, 20, 20));

        // Label do Nome do Mapa (Top Bar)
        labelNomeMapa = new JLabel("Carregando...");
        labelNomeMapa.setForeground(COR_MAPA_NOME);
        labelNomeMapa.setFont(FONTE_HEADER);
        labelNomeMapa.setHorizontalAlignment(SwingConstants.LEFT);
        labelNomeMapa.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 0));

        // Campo de Comandos
        campoInput = new JTextField();
        campoInput.setEditable(false);
        campoInput.setFocusable(false);
        campoInput.setBackground(COR_FUNDO_CAMPO);
        campoInput.setForeground(COR_BORDA_MOVIMENTO);
        campoInput.setCaretColor(Color.WHITE);
        campoInput.setFont(new Font("Monospaced", Font.PLAIN, 18));
        atualizarBordaInput(" MOVIMENTO ", COR_BORDA_MOVIMENTO);

        // HUD de Teclas (Painel à Direita)
        painelTeclasHUD = criarPainelTeclasHUD();
        areaChat.setLineWrap(true);
        areaChat.setWrapStyleWord(true);

        // estilo do chat
        areaChat.setBackground(new Color(20, 20, 20));
        areaChat.setForeground(new Color(200, 200, 200));
        areaChat.setFont(new Font("Consolas", Font.PLAIN, 18));
        areaChat.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        areaChat.setEditable(false);
        areaChat.setFocusable(false);
        areaChat.setEnabled(false);
    }

    private void montarLayout() {
        setLayout(new BorderLayout());

        JPanel containerIncial = new JPanel(new BorderLayout());
        containerIncial.setBackground(COR_FUNDO_JANELA);

        // TOPO
        containerIncial.add(labelNomeMapa, BorderLayout.NORTH);

        // MAPA (CENTRO)
        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setBackground(COR_FUNDO_JANELA);
        painelCentro.add(areaMapa, BorderLayout.CENTER);

        containerIncial.add(painelCentro, BorderLayout.CENTER);

        // CHAT (DIREITA FIXO)
        areaChat.setLineWrap(true);
        areaChat.setWrapStyleWord(true);

        JScrollPane scrollChat = new JScrollPane(areaChat);
        scrollChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollChat.setPreferredSize(new Dimension(270, 50));

        JPanel wrapperChat = new JPanel(new BorderLayout());
        wrapperChat.setBackground(COR_FUNDO_JANELA);
        wrapperChat.setPreferredSize(new Dimension(270, 50));
        wrapperChat.add(scrollChat, BorderLayout.CENTER);

        containerIncial.add(wrapperChat, BorderLayout.EAST);

        // CONTROLE (BAIXO)
        JPanel painelControle = new JPanel(new BorderLayout());
        painelControle.setBackground(COR_FUNDO_JANELA);
        painelControle.setPreferredSize(new Dimension(0, 100));

        painelControle.add(campoInput, BorderLayout.CENTER);
        painelControle.add(painelTeclasHUD, BorderLayout.EAST);

        JLabel labelDica = new JLabel(" [ENTER] Alternar Chat | WASD mover ");
        labelDica.setForeground(new Color(120, 120, 120));
        labelDica.setFont(FONTE_INTERFACE);
        painelControle.add(labelDica, BorderLayout.SOUTH);

        containerIncial.add(painelControle, BorderLayout.SOUTH);

        // FINAL
        setContentPane(containerIncial);
    }

    // --- LÓGICA DE EVENTOS (Key Bindings) ---

    private void registrarEventosGlobais() {
        JComponent root = getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        // Interceptador Global para o ENTER
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ENTER) {

                // Interceptador Global para o ENTER
                if (modoChat) {
                    return false;
                }

                // Interceptador Global para o ENTER
                alternarEstadoChat(true);
                return true;
            }
            return false;
        });

        // Ações de Movimentação (W, A, S, D, I)
        for (char tecla : NOMES_TECLAS) {
            String keyStr = String.valueOf(tecla);

            // Pressionar
            im.put(KeyStroke.getKeyStroke(keyStr), "press_" + keyStr);
            am.put("press_" + keyStr, new GameAction(tecla, true));

            // Soltar
            im.put(KeyStroke.getKeyStroke("released " + keyStr), "release_" + keyStr);
            am.put("release_" + keyStr, new GameAction(tecla, false));
        }

        // Action Listener no campo de texto para quando o usuário aperta ENTER para enviar
        campoInput.addActionListener(e -> processarEnvioMensagem());
    }

    // --- MÉTODOS DE AÇÃO (Encapsulamento) ---

    private void processarEnvioMensagem() {
        if (!modoChat) return;

        String msg = campoInput.getText().trim();
        if (msg.isEmpty()) return;

        // limpa chat visual SEMPRE
        areaChat.setText("");

        int hp = jogo.getPlayer().getHp();
        int nivel = jogo.getPlayer().getNivel();

        imprimirNoConsole("[STATUS: HP " + hp + " | LV: " + nivel + "]", Color.ORANGE);
        imprimirNoConsole("Player: " + msg, COR_BORDA_CHAT);

        String respostaFinal = "Não há ninguém por perto...";
        String nomeNpc = "";

        npcAtual = null;

        for (Npc npc : jogo.getNpcs()) {

            if (npc.getX() == jogo.getPlayer().getX()
                    && npc.getY() == jogo.getPlayer().getY()) {

                npcAtual = npc;
                break;
            }
        }

        if (npcAtual != null) {

            nomeNpc = npcAtual.getNome() + ": ";

            jogo.Gemini.GeminiServices gemini =
                    new jogo.Gemini.GeminiServices();

            String contexto = String.format(
                    "Você é um NPC de RPG chamado %s (%s).\n" +
                            "Você já conhece o jogador e lembra interações anteriores.\n" +
                            "Memória: %s\n\n" +
                            "Jogador diz: %s",
                    npcAtual.getNome(),
                    npcAtual.getPapel(),
                    npcAtual.getHistoricoConversa(),
                    msg
            );

            respostaFinal = gemini.conversar(contexto, msg);

            // salva memória do NPC
            npcAtual.adicioanarHistoricoConversa("Jogador: " + msg);
            npcAtual.adicioanarHistoricoConversa("NPC: " + respostaFinal);
        }

        imprimirNoConsole(nomeNpc + respostaFinal, COR_BORDA_CHAT);

        atualizarExibicaoMapa();
        alternarEstadoChat(false);
    }

    private void alternarEstadoChat(boolean ativar) {
        this.modoChat = ativar;

        if (ativar) {
            resetarVisualTeclasHUD();
            campoInput.setFocusable(true);
            campoInput.setEditable(true);
            campoInput.setBackground(COR_CHAT_ATIVO);
            campoInput.setForeground(COR_BORDA_CHAT);
            atualizarBordaInput(" CHAT ", COR_BORDA_CHAT);
            campoInput.requestFocusInWindow();
        } else {
            campoInput.setText("");
            campoInput.setEditable(false);
            campoInput.setFocusable(false);
            campoInput.setBackground(COR_FUNDO_CAMPO);
            campoInput.setForeground(COR_BORDA_MOVIMENTO);
            atualizarBordaInput(" MOVIMENTO ", COR_BORDA_MOVIMENTO);
            getRootPane().requestFocusInWindow();
        }
    }

    // --- AUXILIARES VISUAIS ---

    private JPanel criarPainelTeclasHUD() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(COR_FUNDO_JANELA);
        painel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        // Mapeamento e criação das teclas
        for (int i = 0; i < NOMES_TECLAS.length; i++) {
            teclasHUD[i] = criarEstiloTecla(String.valueOf(NOMES_TECLAS[i]));
        }

        // Posicionamento no Grid: W (Top Center), ASD (Bottom Row), I (Separado)
        gbc.gridx = 1; gbc.gridy = 0; painel.add(teclasHUD[0], gbc); // W
        gbc.gridx = 0; gbc.gridy = 1; painel.add(teclasHUD[1], gbc); // A
        gbc.gridx = 1; gbc.gridy = 1; painel.add(teclasHUD[2], gbc); // S
        gbc.gridx = 2; gbc.gridy = 1; painel.add(teclasHUD[3], gbc); // D

        // Espaçamento visual para separar ação de movimento
        gbc.gridx = 3; gbc.gridy = 0; gbc.insets = new Insets(0, 15, 0, 5);
        painel.add(new JLabel(""), gbc);
        gbc.gridx = 4; gbc.gridy = 1; gbc.insets = new Insets(3, 3, 3, 3);
        painel.add(teclasHUD[4], gbc); // I

        return painel;
    }

    private JLabel criarEstiloTecla(String txt) {
        JLabel lb = new JLabel(txt, SwingConstants.CENTER);
        lb.setPreferredSize(new Dimension(50, 50));
        lb.setOpaque(true);
        lb.setBackground(new Color(40, 40, 40));
        lb.setForeground(new Color(200, 200, 200));
        lb.setFont(new Font("Monospaced", Font.BOLD, 20));
        // Borda bevel mais sutil
        lb.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, new Color(70, 70, 70), new Color(20, 20, 20)));
        return lb;
    }

    private void atualizarBordaInput(String titulo, Color cor) {
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(cor, 2), " " + titulo + " ");
        tb.setTitleColor(cor);
        tb.setTitleFont(new Font("Monospaced", Font.BOLD, 14));
        campoInput.setBorder(tb);
    }

    private void destacarTeclaHUD(char tecla, boolean pressionada) {
        for (int i = 0; i < NOMES_TECLAS.length; i++) {
            if (NOMES_TECLAS[i] == tecla) {
                JLabel alvo = teclasHUD[i];
                if (pressionada) {
                    alvo.setBackground(new Color(200, 200, 200));
                    alvo.setForeground(Color.BLACK);
                    alvo.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                } else {
                    alvo.setBackground(new Color(40, 40, 40));
                    alvo.setForeground(new Color(200, 200, 200));
                    alvo.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, new Color(70, 70, 70), new Color(20, 20, 20)));
                }
                break;
            }
        }
    }

    private void resetarVisualTeclasHUD() {
        for (char t : NOMES_TECLAS) destacarTeclaHUD(t, false);
    }

    // --- MÉTODOS PÚBLICOS DE ATUALIZAÇÃO (API da Interface) ---

    public void atualizarExibicaoMapa() {
        SwingUtilities.invokeLater(() -> {
            StyledDocument doc = areaMapa.getStyledDocument();
            try {
                doc.remove(0, doc.getLength()); // Limpa o mapa
                Mapa mapa = jogo.getMapa();

                for (int y = 0; y < mapa.getAltura(); y++) {
                    for (int x = 0; x < mapa.getLargura(); x++) {
                        renderizarCelulaColorida(x, y, doc);
                    }
                    doc.insertString(doc.getLength(), "\n", null);
                }
                labelNomeMapa.setText(mapa.getNome().toUpperCase());
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }

    private void renderizarCelulaColorida(int x, int y, StyledDocument doc) throws BadLocationException {
        char c;
        Color cor;

        // Lógica de cores baseada na entidade ou tile
        if (x == jogo.getPlayer().getX() && y == jogo.getPlayer().getY()) {
            c = jogo.getPlayer().getSimbolo();
            cor = jogo.getPlayer().getCor();
        } else {
            Npc npcEncontrado = jogo.getNpcs().stream()
                    .filter(n -> n.getX() == x && n.getY() == y)
                    .findFirst().orElse(null);

            if (npcEncontrado != null) {
                c = npcEncontrado.getSimbolo();
                cor = npcEncontrado.getCor();
            } else {
                Tile t = jogo.getMapa().getTile(x, y);
                c = (t != null) ? t.getSimbolo() : ' ';
                cor = (t != null) ? t.getColor() : COR_TEXTO_MAPA;
            }
        }

        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, cor);
        doc.insertString(doc.getLength(), c + " ", style);
    }

    public void imprimirNoConsole(String texto, Color cor) {
        areaChat.setText("");
        areaChat.append(texto + "\n");
        areaChat.setCaretPosition(areaChat.getDocument().getLength());
    }

    public void definirNomeMapa(String nome) {
        SwingUtilities.invokeLater(() -> labelNomeMapa.setText(nome.toUpperCase()));
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
            getRootPane().requestFocusInWindow();
        });
    }

    // --- CLASSE INTERNA DE AÇÃO (Command Pattern) ---

    private class GameAction extends AbstractAction {
        private final char tecla;
        private final boolean pressionada;

        public GameAction(char tecla, boolean pressionada) {
            this.tecla = tecla;
            this.pressionada = pressionada;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (modoChat) return;

            destacarTeclaHUD(tecla, pressionada);

            if (pressionada) {
                if (tecla == 'I') {
                    String resultado = jogo.realizarInteracao();
                    imprimirNoConsole(resultado, Color.LIGHT_GRAY);
                    atualizarExibicaoMapa();
                } else {
                    jogo.moverPlayer(tecla);
                    atualizarExibicaoMapa();
                }
            }
        }
    }
}
