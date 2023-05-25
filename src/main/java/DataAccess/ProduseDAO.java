package DataAccess;

import Model.Produse;

import java.util.List;
/**
 * The `ProduseDAO` class is a data access object that provides CRUD operations
 * for the `Produse` entity.
 */
public class ProduseDAO extends AbstractDAO<Produse> {

    /**
     * Constructs a new `ProduseDAO` object.
     */
    public ProduseDAO() {
        super();
    }

    /**
     * Retrieves all `Produse` entities from the database.
     *
     * @return a list of all `Produse` entities
     */
    @Override
    public List<Produse> findAll() {
        return super.findAll();
    }

    /**
     * Retrieves a `Produse` entity from the database based on the specified ID.
     *
     * @param id the ID of the `Produse` entity
     * @return the `Produse` entity with the specified ID, or null if not found
     */
    @Override
    public Produse findById(int id) {
        return (Produse) super.findById(id);
    }

    /**
     * Inserts a new `Produse` entity into the database.
     *
     * @param produse the `Produse` entity to be inserted
     * @return the inserted `Produse` entity
     */
    @Override
    public Produse insert(Produse produse) {
        return super.insert(produse);
    }

    /**
     * Updates an existing `Produse` entity in the database.
     *
     * @param produse the `Produse` entity to be updated
     * @return the updated `Produse` entity
     */
    @Override
    public Produse update(Produse produse) {
        return super.update(produse);
    }

    /**
     * Deletes an existing `Produse` entity from the database.
     *
     * @param produse the `Produse` entity to be deleted
     * @return the deleted `Produse` entity
     */
    @Override
    public Produse delete(Produse produse) {
        return super.delete(produse);
    }
}
