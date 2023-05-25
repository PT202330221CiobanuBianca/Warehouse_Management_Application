package Model;
/**
 * The `Clienti` class represents a client entity.
 */
public class Clienti {
    private int client_id;
    private String nume;
    private String email;
    private String adresa;

    /**
     * Constructs a new `Clienti` object with default values.
     */
    public Clienti() {
    }

    /**
     * Constructs a new `Clienti` object with the specified parameters.
     *
     * @param client_id the client ID
     * @param nume      the client's name
     * @param email     the client's email
     * @param adresa    the client's address
     */
    public Clienti(int client_id, String nume, String email, String adresa) {
        super();
        this.client_id = client_id;
        this.nume = nume;
        this.email = email;
        this.adresa = adresa;
    }

    /**
     * Gets the client's address.
     *
     * @return the client's address
     */
    public String getAdresa() {
        return adresa;
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
     * Gets the client's name.
     *
     * @return the client's name
     */
    public String getNume() {
        return nume;
    }

    /**
     * Gets the client's email.
     *
     * @return the client's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the client ID.
     *
     * @param client_id the client ID to set
     */
    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    /**
     * Sets the client's name.
     *
     * @param nume the client's name to set
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Sets the client's email.
     *
     * @param email the client's email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the client's address.
     *
     * @param adresa the client's address to set
     */
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
