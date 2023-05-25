package DataAccess;

import Model.Clienti;

import java.util.List;

/**
 * The ClientiDAO class extends the AbstractDAO class and provides specific data access operations for the Clienti class.
 */
public class ClientiDAO extends AbstractDAO<Clienti> {

    /**
     * Retrieves all the clienti records from the database.
     *
     * @return a list of clienti objects
     */
    @Override
    public List<Clienti> findAll() {
        return super.findAll();
    }

    /**
     * Retrieves a clienti record from the database based on the specified ID.
     *
     * @param id the ID of the clienti record to retrieve
     * @return the clienti object with the specified ID, or null if not found
     */
    @Override
    public Clienti findById(int id) {
        return (Clienti) super.findById(id);
    }

    /**
     * Inserts a new clienti record into the database.
     *
     * @param clienti the clienti object to insert
     * @return the inserted clienti object
     */
    @Override
    public Clienti insert(Clienti clienti) {
        return super.insert(clienti);
    }

    /**
     * Constructs a new ClientiDAO object.
     */
    public ClientiDAO() {
        super();
    }

    /**
     * Updates an existing clienti record in the database.
     *
     * @param clienti the clienti object to update
     * @return the updated clienti object
     */
    @Override
    public Clienti update(Clienti clienti) {
        return super.update(clienti);
    }
}
