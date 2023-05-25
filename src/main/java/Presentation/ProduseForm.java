package Presentation;

import BusinessLogic.TableReflection;
import Connection.ConnectionFactory;
import DataAccess.ProduseDAO;
import Model.Produse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The `ProduseForm` class represents a GUI form for managing products.
 */
public class ProduseForm extends JDialog {
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JLabel Pret;
    private JPanel panel1;

    Connection connection = ConnectionFactory.getConnection();

    /**
     * Constructs a new `ProduseForm` object.
     *
     * @param parent the parent JFrame
     */
    public ProduseForm(JFrame parent) {
        super(parent);
        setTitle("Produse");
        setContentPane(panel1);
        setMinimumSize(new Dimension(490, 470));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Integer rc = rowCount();
        if (rc != 0)
            this.afisare();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProduseDAO DAO = new ProduseDAO();
                String nume = textField1.getText();
                Float pret = Float.valueOf(textField2.getText());
                Integer stoc = Integer.valueOf(textField3.getText());
                Produse produs = new Produse(0, nume, pret, stoc);
                produs = DAO.insert(produs);
                if (produs.getPret() == 0 || produs.getNume().length() == 0 || produs.getStoc() == 0) {
                    JOptionPane.showMessageDialog(ProduseForm.this, "NU POTI");
                } else {
                    JOptionPane.showMessageDialog(ProduseForm.this, "Bravo!!");
                    setVisible(false);
                    dispose();
                    ProduseForm p = new ProduseForm(null);
                    ComenziForm c = new ComenziForm(null);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProduseDAO DAO = new ProduseDAO();
                int id;
                if (textField4.getText().length() == 0)
                    id = 0;
                else
                    id = Integer.parseInt(textField4.getText());

                Produse produs = new Produse(id, null, null, null);
                produs = DAO.delete(produs);
                if (produs.getProdus_id() <= 0) {
                    JOptionPane.showMessageDialog(ProduseForm.this, "ID INCORECT");
                } else {
                    JOptionPane.showMessageDialog(ProduseForm.this, "Bravo!!");
                    setVisible(false);
                    dispose();
                    ProduseForm p = new ProduseForm(null);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProduseDAO DAO = new ProduseDAO();
                String nume = textField1.getText();
                Float pret = Float.valueOf(textField2.getText());
                Integer stoc = Integer.valueOf(textField3.getText());
                int id;
                if (textField4.getText().length() == 0)
                    id = 0;
                else
                    id = Integer.parseInt(textField4.getText());

                Produse produs = new Produse(id, nume, pret, stoc);
                produs = DAO.update(produs);
                if (produs.getProdus_id() <= 0 || produs.getProdus_id() > rc) {
                    JOptionPane.showMessageDialog(ProduseForm.this, "ID INCORECT");
                } else {
                    JOptionPane.showMessageDialog(ProduseForm.this, "Bravo!!");
                    setVisible(false);
                    dispose();
                    ProduseForm p = new ProduseForm(null);
                }
            }
        });

        setVisible(true);
    }

    /**
     * Displays the product data in a table.
     */
    public void afisare() {
        TableReflection tr = new TableReflection<Produse>();
        List<Produse> list = new ArrayList<>();
        ProduseDAO pd = new ProduseDAO();
        list = pd.findAll();
        JTable table = tr.createTable(list);
        table1.setModel(table.getModel());
        table1.setDefaultEditor(Object.class, null);
    }

    /**
     * Retrieves the row count of the "Produse" table.
     *
     * @return the row count
     */
    public Integer rowCount() {
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
}
