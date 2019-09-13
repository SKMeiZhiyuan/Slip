package slip.mysql;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 管理指定数据库下对表的操作
 */
public class MysqlTable {
    private Class bean = null;
    private String tableName = null;
    private Connection connection = null;

    /**
     * 根据表名连接到指定表
     */
    public MysqlTable(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }

    /**
     * 将实体类和表进行关联
     */
    public MysqlTable(Connection connection, Class bean) {
        this.bean = bean;
        this.connection = connection;
        this.tableName = bean.getSimpleName().toLowerCase();
    }

    /**
     * 连接所有表
     */
    public MysqlTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * 获取指定表下的所有数据
     */
    public List selectList() {
        List<Map> resultsMap = Execute.selectExecute(this.connection, SQL.select(tableName));
        List results = new LinkedList();
        if (bean != null) {
            for (Map map : resultsMap) {
                results.add(FormatData.toBean(map, this.bean));
            }
            return results;
        } else {
            return resultsMap;
        }
    }

    /**
     * 根据实例查询数据
     */
    public <T> List select(T bean) {
        List<Map> resultsMap = Execute.selectExecute(this.connection, SQL.select(tableName, FormatData.toMap(bean)));
        List results = new LinkedList();
        if (resultsMap.size() != 0) {
            for (Map map : resultsMap) {
                results.add(FormatData.toBean(map, this.bean));
            }
        }
        return results;
    }

    /**
     * 根据一个或多个条件进行查询
     */
    public List select(Map condition) {
        List<Map> resultsMap = Execute.selectExecute(this.connection, SQL.select(tableName, condition));
        List results = new LinkedList();
        if (bean != null) {
            for (Map map : resultsMap) {
                results.add(FormatData.toBean(map, this.bean));
            }
            return results;
        } else {
            return resultsMap;
        }
    }

    /**
     * 将一个实例插入到数据库
     */
    public <T> int insert(T bean) {
        return Execute.otherExecute(this.connection, SQL.insert(this.tableName, FormatData.toMap(bean)));
    }

    /**
     * 将多个实例插入数据库
     */
    public <T> int insertMany(List<T> beans) {
        int flag = 0;
        for (T bean : beans) {
            flag += Execute.otherExecute(this.connection, SQL.insert(this.tableName, FormatData.toMap(bean)));
        }
        return flag;
    }

    /**
     * 根据一个实例删除对应数据
     */
    public <T> int delete(T bean) {
        return Execute.otherExecute(this.connection, SQL.delete(this.tableName, FormatData.toMap(bean)));
    }

    /**
     * 根据多个实例删除对应数据
     */
    public <T> int deleteMany(List<T> beans) {
        int flag = 0;
        for (T bean : beans) {
            flag += Execute.otherExecute(this.connection, SQL.delete(this.tableName, FormatData.toMap(bean)));
        }
        return flag;
    }

    /**
     * 用一个新的实例替换旧的实例数据
     */
    public <T> int update(T oldBean, T newBean) {
        return Execute.otherExecute(this.connection, SQL.update(this.tableName, FormatData.toMap(newBean), FormatData.toMap(oldBean)));
    }
}
