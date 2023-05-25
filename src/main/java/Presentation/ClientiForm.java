package Presentation;

import BusinessLogic.TableReflection;
import DataAccess.ClientiDAO;
import Connection.ConnectionFactory;
import Model.Clienti;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The `ClientiForm` class represents a GUI form for managing client data.
 */
public class ClientiForm extends JDialog {
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField textField4;
    private JPanel panel1;

    Connection connection = ConnectionFactory.getConnection();

    /**
     * Constructs a new `ClientiForm` object.
     *
     * @param parent the parent JFrame
     */
    public ClientiForm(JFrame parent) {
        super(parent);
        setTitle("Clienti");
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
                ClientiDAO DAO = new ClientiDAO();
                String nume = textField1.getText();
                String email = textField2.getText();
                String adresa = textField3.getText();
                Clienti client = new Clienti(0, nume, email, adresa);
                client = DAO.insert(client);
                if (client.getAdresa().length() == 0 || client.getNume().length() == 0 || client.getEmail().length() == 0) {
                    JOptionPane.showMessageDialog(ClientiForm.this, "NU POTI");
                } else {
                    JOptionPane.showMessageDialog(ClientiForm.this, "Bravo!!");
                    setVisible(false);
                    dispose();
                    ClientiForm c = new ClientiForm(null);
                }

            }

        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientiDAO DAO = new ClientiDAO();
                int id;
                if (textField4.getText().length() == 0)
                    id = 0;
                else
                    id = Integer.parseInt(textField4.getText());

                Clienti client = new Clienti(id, null, null, null);
                client = DAO.delete(client);

                if (client.getClient_id() <= 0) {
                    JOptionPane.showMessageDialog(ClientiForm.this, "ID INCORECT");
                } else {
                    JOptionPane.showMessageDialog(ClientiForm.this, "Bravo!!");
                    setVisible(false);
                    dispose();
                    ClientiForm c = new ClientiForm(null);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientiDAO DAO = new ClientiDAO();
                String nume = textField1.getText();
                String email = textField2.getText();
                String adresa = textField3.getText();
                int id;
                if (textField4.getText().length() == 0)
                    id = 0;
                else
                    id = Integer.parseInt(textField4.getText());

                Clienti client = new Clienti(id, nume, email, adresa);
                client = DAO.update(client);
                if (client.getClient_id() <= 0 || client.getClient_id() > rc) {
                    JOptionPane.showMessageDialog(ClientiForm.this, "ID INCORECT");
                } else {
                    JOptionPane.showMessageDialog(ClientiForm.this, "Bravo!!");
                    setVisible(false);
                    dispose();
                    ClientiForm c = new ClientiForm(null);
                }
            }
        });
        setVisible(true);
    }

    /**
     * Displays the client data in a table.
     */
    public void afisare() {
        TableReflection tr = new TableReflection<Clienti>();
        List<Clienti> list = new ArrayList<>();
        ClientiDAO cd = new ClientiDAO();
        list = cd.findAll();
        JTable table = tr.createTable(list);
        table1.setModel(table.getModel());
        table1.setDefaultEditor(Object.class, null);
    }

    /**
     * Retrieves the row count of the "Clienti" table.
     *
     * @return the row count
     */
    public Integer rowCount() {
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
}
