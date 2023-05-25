package Presentation;

import BusinessLogic.TableReflection;
import Connection.ConnectionFactory;
import DataAccess.ClientiDAO;
import DataAccess.ComandaDAO;
import DataAccess.ProduseDAO;
import Model.Clienti;
import Model.Comanda;
import Model.Produse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The `ComenziForm` class represents a GUI form for managing orders.
 */
public class ComenziForm extends JDialog {
    private JButton button1;
    private JTable table1;
    private JTable table2;
    private JTable table3;
    private JPanel panel2;
    private JTextField textField1;

    Connection connection = ConnectionFactory.getConnection();

    /**
     * Constructs a new `ComenziForm` object.
     *
     * @param parent the parent JFrame
     */
    public ComenziForm(JFrame parent) {
        super(parent);
        setTitle("Comenzi");
        setContentPane(panel2);
        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Integer rcC = rowCountC();
        Integer rcP = rowCountP();
        Integer rcCz = rowCountCz();
        if (rcCz > 0)
            this.afisareComenzi();
        if (rcC > 0)
            this.afisareClienti();
        if (rcP > 0)
            this.afisareProdus();

        setVisible(true);

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = table2.getSelectedRow();
                int selectedRow2 = table3.getSelectedRow();
                int stoc = 0;
                // Retrieve data from the table model based on the selected row
                Integer produs_id = (Integer) table3.getModel().getValueAt(selectedRow2, 0);
                Integer client_id = (Integer) table2.getModel().getValueAt(selectedRow, 0);
                Integer cantitate = Integer.valueOf(textField1.getText());
                ComandaDAO cd = new ComandaDAO();
                Comanda comanda = new Comanda(null, produs_id, client_id, cantitate);
                comanda = cd.insert(comanda);
                String query = "SELECT stoc FROM Produse WHERE produs_id=" + produs_id;
                PreparedStatement statement = null;
                try {
                    statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        stoc = resultSet.getInt(1);
                        System.out.println(stoc);
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (textField1.getText().length() == 0) {
                    JOptionPane.showMessageDialog(ComenziForm.this, "Camp necompletat");
                } else {
                    JOptionPane.showMessageDialog(ComenziForm.this, "Bravo!!");
                    String query1 = "UPDATE Produse SET stoc=? WHERE produs_id=" + produs_id;
                    PreparedStatement statement1 = null;
                    try {
                        statement1 = connection.prepareStatement(query1);
                        int newstoc = stoc - cantitate;
                        statement1.setInt(1, newstoc);
                        statement1.executeUpdate();
                        System.out.println(newstoc);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    setVisible(false);
                    dispose();
                    ComenziForm c = new ComenziForm(null);
                }
            }
        });
    }

    /**
     * Retrieves the row count of the "Comanda" table.
     *
     * @return the row count
     */
    public Integer rowCountCz() {
        Integer c;
        String query = "SELECT COUNT(*) FROM Comanda";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                c = resultSet.getInt(1);
                return c;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    /**
     * Retrieves the row count of the "Produse" table.
     *
     * @return the row count
     */
    public Integer rowCountP() {
        Integer c;
        String query = "SELECT COUNT(*) FROM Produse";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                c = resultSet.getInt(1);
                return c;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    /**
     * Retrieves the row count of the "Clienti" table.
     *
     * @return the row count
     */
    public Integer rowCountC() {
        Integer c;
        String query = "SELECT COUNT(*) FROM Clienti";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                c = resultSet.getInt(1);
                return c;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    /**
     * Displays the order data in a table.
     */
    public void afisareComenzi() {
        TableReflection tr3 = new TableReflection<Comanda>();
        List<Comanda> list3 = new ArrayList<>();
        ComandaDAO zd = new ComandaDAO();
        list3 = zd.findAll();
        JTable tableZ = tr3.createTable(list3);
        table1.setModel(tableZ.getModel());
        table1.setDefaultEditor(Object.class, null);
    }

    /**
     * Displays the client data in a table.
     */
    public void afisareClienti() {
        TableReflection tr = new TableReflection<Clienti>();
        List<Clienti> list = new ArrayList<>();
        ClientiDAO cd = new ClientiDAO();
        list = cd.findAll();
        JTable table = tr.createTable(list);
        table2.setModel(table.getModel());
        table2.setDefaultEditor(Object.class, null);
    }

    /**
     * Displays the product data in a table.
     */
    public void afisareProdus() {
        TableReflection tr = new TableReflection<Produse>();
        List<Produse> list = new ArrayList<>();
        ProduseDAO pd = new ProduseDAO();
        list = pd.findAll();
        JTable table = tr.createTable(list);
        table3.setModel(table.getModel());
        table3.setDefaultEditor(Object.class, null);
    }
}
