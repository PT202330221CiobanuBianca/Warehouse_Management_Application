package DataAccess;

import Connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The AbstractDAO class provides generic database operations for CRUD (Create, Read, Update, Delete) operations.
 *
 * @param <T> The generic type parameter representing the entity class.
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * Constructs a new AbstractDAO object.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates the SELECT query for a specific field.
     *
     * @param field The field to filter the query by.
     * @return The SELECT query as a string.
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ").append(field).append(" = ?");
        return sb.toString();
    }

    /**
     * Creates the INSERT statement query for the entity.
     *
     * @return The INSERT statement query as a string.
     */
    private String insertStatementQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append("(");

        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(fields[i].getName());
        }

        sb.append(") VALUES (");

        for (int j = 0; j < fields.length; j++) {
            if (j > 0) {
                sb.append(", ");
            }
            sb.append("?");
        }

        sb.append(")");
        return sb.toString();
    }

    /**
     * Creates the UPDATE statement query for the entity.
     *
     * @return The UPDATE statement query as a string.
     */
    private String updateStatementQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");

        Field[] fields = type.getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            if (i > 1) {
                sb.append(", ");
            }
            sb.append(fields[i].getName());
            sb.append("=?");
        }

        sb.append(" WHERE ").append(fields[0].getName()).append(" = ?");

        return sb.toString();
    }

    /**
     * Creates the DELETE statement query for the entity.
     *
     * @return The DELETE statement query as a string.
     */
    private String deleteStatementQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ").append(type.getDeclaredFields()[0].getName()).append(" = ?");
        return sb.toString();
    }

    /**
     * Retrieves all entities from the database.
     *
     * @return A list of entities.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + type.getSimpleName());
            resultSet = statement.executeQuery();
            list = createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return list;
    }

    /**
     * Finds an entity by its ID.
     *
     * @param id The ID of the entity.
     * @return The found entity.
     */
    public Object findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(createSelectQuery("id"));
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<T> objects = createObjects(resultSet);
            if (!objects.isEmpty()) {
                return objects.get(0);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creates objects from the ResultSet.
     *
     * @param resultSet The ResultSet containing the data.
     * @return A list of created objects.
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor<?>[] ctors = type.getDeclaredConstructors();
        Constructor<?> ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0) {
                break;
            }
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SQLException | InvocationTargetException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Inserts an entity into the database.
     *
     * @param t The entity to be inserted.
     * @return The inserted entity.
     */
    public T insert(T t) {
        Connection connection = ConnectionFactory.getConnection();
        String query = insertStatementQuery();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            Field[] fields = type.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Object givenValue = fields[i].get(t);
                statement.setObject(i + 1, givenValue);
            }
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }

    /**
     * Updates an entity in the database.
     *
     * @param t The entity to be updated.
     * @return The updated entity.
     */
    public T update(T t) {
        Connection connection = ConnectionFactory.getConnection();
        String query = updateStatementQuery();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            Field[] fields = type.getDeclaredFields();
            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Object givenValue = fields[i].get(t);
                statement.setObject(i, givenValue);
            }
            fields[0].setAccessible(true);
            Object givenValue = fields[0].get(t);
            statement.setObject(fields.length, givenValue);
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }

    /**
     * Deletes an entity from the database.
     *
     * @param t The entity to be deleted.
     * @return The deleted entity.
     */
    public T delete(T t) {
        Connection connection = ConnectionFactory.getConnection();
        String query = deleteStatementQuery();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            Field[] fields = type.getDeclaredFields();
            fields[0].setAccessible(true);
            Object givenValue = fields[0].get(t);
            statement.setObject(1, givenValue);
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }
}
