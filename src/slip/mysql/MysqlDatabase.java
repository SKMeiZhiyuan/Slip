package slip.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 管理在指定数据库连接下的对数据库的操作
 */
public class MysqlDatabase {
    private String databaseName = null;
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    /**
     * 指定连接的数据库
     */
    public MysqlDatabase(Connection connection, String databaseName) {
        this.connection = connection;
        this.databaseName = databaseName;
        try {
            preparedStatement = connection.prepareStatement("use " + this.databaseName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * 对数据库进行操作
     */
    public MysqlDatabase(Connection connection) {
        this.connection = connection;
    }

    /**
     * 连接数据库下指定的表
     */
    public MysqlTable getTable(String tableName) {
        return new MysqlTable(this.connection, tableName);
    }

    /**
     * 根据实体类的class对象将表和实体类关联起来
     */
    public MysqlTable getTable(Class bean) {
        return new MysqlTable(this.connection, bean);
    }

    /**
     * 连接数据库下所有的表
     */
    public MysqlTable getTables() {
        return new MysqlTable(this.connection);
    }

    private void close() {
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
