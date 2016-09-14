package model.strategy;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Food;
import model.Snake;

public class DefaultMoveStrategy implements SnakeStrategy {

    @Override
    public Direction nextMove(Snake snake, Arena arena) {
        Food food = arena.getFood().get(0);
        Coordinate head = snake.getHeadCoordinate();
        int min = Integer.MAX_VALUE;
        Direction result = Direction.WEST;
        for (Direction direction : Direction.values()) {
            Coordinate newHeadCoordinate = head.nextCoordinate(direction);
            int distance = newHeadCoordinate.minDistance(food.getCoordinate(), arena.getMaxCoordinate());
            if (distance < min && !arena.isOccupied(newHeadCoordinate)) {
                min = distance;
                result = direction;
            }
        }
        return result;
    }

}
