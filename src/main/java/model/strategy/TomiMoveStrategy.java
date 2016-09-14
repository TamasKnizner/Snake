package model.strategy;

import java.util.ArrayList;
import java.util.List;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Snake;

public class TomiMoveStrategy implements SnakeStrategy {

    @Override
    public Direction nextMove(Snake snake, Arena arena) {
        List<CoordinateWithDirection> freeCoordinates = getFreeCoordinates(snake, arena);
        return getBestDirection(freeCoordinates, snake, arena);
    }

    private Direction getBestDirection(List<CoordinateWithDirection> freeCoordinates, Snake snake, Arena arena) {
        Direction bestDirection = (freeCoordinates.isEmpty()) ? Direction.WEST : freeCoordinates.get(0).getDirection();
        Coordinate foodCoordinate = arena.getFood().get(0).getCoordinate();
        int minDistance = arena.getMaxCoordinate().getX();
        for (CoordinateWithDirection coordinateWithDirection : freeCoordinates) {
            int actucalDistance = foodCoordinate.minDistance(coordinateWithDirection.getCoordinate(), arena.getMaxCoordinate());
            if (actucalDistance < minDistance) {
                bestDirection = coordinateWithDirection.getDirection();
                minDistance = actucalDistance;
            }
        }
        return bestDirection;
    }

    private List<CoordinateWithDirection> getFreeCoordinates(Snake snake, Arena arena) {
        List<CoordinateWithDirection> freeCoordinates = new ArrayList<>();
        Coordinate currentHeadPosition = snake.getHeadCoordinate();
        for (Direction direction : Direction.values()) {
            Coordinate nextCoordinate = currentHeadPosition.nextCoordinate(direction).truncLimits(arena.getMaxCoordinate());
            if (!arena.isOccupied(nextCoordinate)) {
                freeCoordinates.add(new CoordinateWithDirection(nextCoordinate, direction));
            }
        }
        return freeCoordinates;
    }

    private class CoordinateWithDirection {
        private Coordinate coordinate;
        private Direction direction;

        CoordinateWithDirection(Coordinate coordinate, Direction direction) {
            this.coordinate = coordinate;
            this.direction = direction;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public Direction getDirection() {
            return direction;
        }

    }

}
