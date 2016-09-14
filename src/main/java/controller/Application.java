package controller;

import model.ModifiableArena;
import model.ModifiableSnake;
import model.strategy.DefaultMoveStrategy;
import model.strategy.TomiMoveStrategy;

public final class Application {

    private Application() {
    }

    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager());

        ModifiableArena arena = new ModifiableArena();
        ModifiableSnake snake1 = new ModifiableSnake(arena, new TomiMoveStrategy(), "Tomi");
        ModifiableSnake snake2 = new ModifiableSnake(arena, new DefaultMoveStrategy(), "Default");
        arena.addSnake(snake1);
        arena.addSnake(snake2);
        new SnakeController(arena).start();
    }

}
