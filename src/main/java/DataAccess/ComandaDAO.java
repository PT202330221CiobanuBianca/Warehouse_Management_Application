package DataAccess;

import Model.Comanda;

import java.util.List;

/**
 * The `ComandaDAO` class is a data access object that provides CRUD operations
 * for the `Comanda` entity.
 */
public class ComandaDAO extends AbstractDAO<Comanda> {

    /**
     * Constructs a new `ComandaDAO` object.
     */
    public ComandaDAO() {
        super();
    }

    /**
     * Retrieves all `Comanda` entities from the database.
     *
     * @return a list of all `Comanda` entities
     */
    @Override
    public List<Comanda> findAll() {
        return super.findAll();
    }

    /**
     * Retrieves a `Comanda` entity from the database based on the specified ID.
     *
     * @param id the ID of the `Comanda` entity
     * @return the `Comanda` entity with the specified ID, or null if not found
     */
    @Override
    public Object findById(int id) {
        return super.findById(id);
    }

    /**
     * Inserts a new `Comanda` entity into the database.
     *
     * @param comanda the `Comanda` entity to be inserted
     * @return the inserted `Comanda` entity
     */
    @Override
    public Comanda insert(Comanda comanda) {
        return super.insert(comanda);
    }
}
