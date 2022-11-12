package app;

import characters.Captain;
import characterz.Freaker;

public class App {
    public static void main(String... argz) {
        Captain cap = new Captain();
        Freaker freak = new Freaker();
        cap.payRespects();
        freak.freakOut();
    }
}