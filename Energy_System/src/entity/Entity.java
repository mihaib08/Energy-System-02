package entity;

/**
 * Defines the operations that can be done on any entity
 */
public abstract class Entity {
    private final int id;

    /** Constructor */

    public Entity(final int entityId) {
        this.id = entityId;
    }

    /** Getters + Setters */

    public int getId() {
        return id;
    }
}
