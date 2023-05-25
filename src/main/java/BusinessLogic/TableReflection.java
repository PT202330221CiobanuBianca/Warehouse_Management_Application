package BusinessLogic;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a generic class that generates a table from a list of objects.
 *
 * @param <T> The generic parameter that can be any class.
 */
public class TableReflection<T> {

    /**
     * Creates a JTable from a list of objects.
     *
     * @param list The list of objects used to populate the table.
     * @return The generated JTable.
     */
    public JTable createTable(List<T> list) {
        List<String> columnNames = new ArrayList<String>();
        int iter = 0;
        for (Field field : list.get(0).getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                columnNames.add(field.getName());
                iter++;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        Object[][] tableData = new Object[list.size()][iter];
        int i = 0;
        for (T obj : list) {
            int j = 0;
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    tableData[i][j++] = field.get(obj);
                } catch (IllegalAccessException exc) {
                    System.out.println("Access error");
                }
            }
            i++;
        }

        return new JTable(tableData, columnNames.toArray());
    }}
