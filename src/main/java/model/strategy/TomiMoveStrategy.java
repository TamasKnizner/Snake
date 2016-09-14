package model.strategy;

import java.util.ArrayList;
import java.util.List;

import model.Arena;
import model.Coordinate;
import model.Direction;
import model.Snake;

public class TomiMoveStrategy implements SnakeStrategy {

    private ArenaProxy arenaProxy;
    private Snake snake;

    @Override
    public Direction nextMove(Snake snake, Arena arena) {
        initFieldsIfNecessary(snake, arena);
        return getBestDirection(getFreeCoordinates());
    }

    private Direction getBestDirection(List<CoordinateWithDirection> freeCoordinates) {
        Direction bestDirection = initBestDirection(freeCoordinates);
        int minDistance = arenaProxy.getMaxSize();
        for (CoordinateWithDirection coordinateWithDirection : freeCoordinates) {
            int actucalDistance = calculateActualDistance(coordinateWithDirection);
            if (actucalDistance < minDistance) {
                bestDirection = coordinateWithDirection.getDirection();
                minDistance = actucalDistance;
            }
        }
        return bestDirection;
    }

    private List<CoordinateWithDirection> getFreeCoordinates() {
        List<CoordinateWithDirection> freeCoordinates = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Coordinate nextCoordinate = calculateCoordinate(direction);
            if (!arenaProxy.isOccupied(nextCoordinate)) {
                freeCoordinates.add(new CoordinateWithDirection(nextCoordinate, direction));
            }
        }
        return freeCoordinates;
    }

    private int calculateActualDistance(CoordinateWithDirection coordinateWithDirection) {
        return arenaProxy.distanceFromFood(coordinateWithDirection.getCoordinate());
    }

    private Direction initBestDirection(List<CoordinateWithDirection> freeCoordinates) {
        return (freeCoordinates.isEmpty()) ? Direction.WEST : freeCoordinates.get(0).getDirection();
    }

    private Coordinate calculateCoordinate(Direction direction) {
        return snake.getHeadCoordinate().nextCoordinate(direction).truncLimits(arenaProxy.getMaxCoordinate());
    }

    private void initFieldsIfNecessary(Snake snake, Arena arena) {
        if (this.snake == null || this.arenaProxy == null) {
            this.snake = snake;
            this.arenaProxy = new ArenaProxy(arena);
        }
    }

    private class ArenaProxy {

        private Arena arena;

        ArenaProxy(Arena arena) {
            this.arena = arena;
        }

        private Coordinate getFoodCoordinate() {
            return arena.getFood().get(0).getCoordinate();
        }

        public int getMaxSize() {
            return arena.getMaxCoordinate().getX();
        }

        public Coordinate getMaxCoordinate() {
            return arena.getMaxCoordinate();
        }

        public boolean isOccupied(Coordinate coordinate) {
            return arena.isOccupied(coordinate);
        }

        public int distanceFromFood(Coordinate coordinate) {
            return getFoodCoordinate().minDistance(coordinate, getMaxCoordinate());
        }

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
