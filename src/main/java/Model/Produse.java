package Model;

/**
 * The `Produse` class represents a product entity.
 */
public class Produse {
    private Integer produs_id;
    private String nume;
    private Float pret;
    private Integer stoc;

    /**
     * Constructs a new `Produse` object with default values.
     */
    public Produse() {
    }

    /**
     * Constructs a new `Produse` object with the specified parameters.
     *
     * @param produs_id the product ID
     * @param nume      the product name
     * @param pret      the product price
     * @param stoc      the product stock quantity
     */
    public Produse(Integer produs_id, String nume, Float pret, Integer stoc) {
        super();
        this.produs_id = produs_id;
        this.nume = nume;
        this.pret = pret;
        this.stoc = stoc;
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
     * Gets the product name.
     *
     * @return the product name
     */
    public String getNume() {
        return nume;
    }

    /**
     * Gets the product price.
     *
     * @return the product price
     */
    public Float getPret() {
        return pret;
    }

    /**
     * Gets the product stock quantity.
     *
     * @return the product stock quantity
     */
    public Integer getStoc() {
        return stoc;
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
     * Sets the product name.
     *
     * @param nume the product name to set
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Sets the product price.
     *
     * @param pret the product price to set
     */
    public void setPret(Float pret) {
        this.pret = pret;
    }

    /**
     * Sets the product stock quantity.
     *
     * @param stoc the product stock quantity to set
     */
    public void setStoc(Integer stoc) {
        this.stoc = stoc;
    }
}
