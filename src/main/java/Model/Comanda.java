package Model;

/**
 * The `Comanda` class represents a order entity.
 */
public class Comanda {
    private Integer comanda_id;
    private Integer produs_id;
    private Integer client_id;
    private Integer cantitate;

    /**
     * Constructs a new `Comanda` object with the specified parameters.
     *
     * @param comanda_id  the order ID
     * @param produs_id   the product ID
     * @param client_id   the client ID
     * @param cantitate   the quantity of the product in the order
     */
    public Comanda(Integer comanda_id, Integer produs_id, Integer client_id, Integer cantitate) {
        super();
        this.comanda_id = comanda_id;
        this.produs_id = produs_id;
        this.client_id = client_id;
        this.cantitate = cantitate;
    }

    /**
     * Constructs a new `Comanda` object with default values.
     */
    public Comanda() {
    }

    /**
     * Gets the order ID.
     *
     * @return the order ID
     */
    public Integer getComanda_id() {
        return comanda_id;
    }

    /**
     * Gets the quantity of the product in the order.
     *
     * @return the quantity of the product
     */
    public Integer getCantitate() {
        return cantitate;
    }

    /**
     * Gets the product ID.
     *
     * @return the product ID
     */
    public Integer getProdus_id() {
        return produs_id;
    }

    /**
     * Gets the client ID.
     *
     * @return the client ID
     */
    public Integer getClient_id() {
        return client_id;
    }

    /**
     * Sets the order ID.
     *
     * @param comanda_id the order ID to set
     */
    public void setComanda_id(Integer comanda_id) {
        this.comanda_id = comanda_id;
    }

    /**
     * Sets the quantity of the product in the order.
     *
     * @param cantitate the quantity of the product to set
     */
    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    /**
     * Sets the product ID.
     *
     * @param produs_id the product ID to set
     */
    public void setProdus_id(Integer produs_id) {
        this.produs_id = produs_id;
    }

    /**
     * Sets the client ID.
     *
     * @param client_id the client ID to set
     */
    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }
}
