import jogo.Game.Game;
import jogo.front.Layout;

public class Main {
    public static void main (String[] args){
        Game game = new Game();
        Layout view = new Layout(game);
        view.start();
    }
}