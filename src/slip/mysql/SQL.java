package slip.mysql;

import java.util.Map;
/**
 * 一个可以根据外部传入的条件，自动生成对应SQL语句的类,
 * 所有的类方法都是静态方法，可以直接调用。
 * @author Linsu
 * */
public class SQL {
    /**
     * @param tableName 表名
     * @param info 存入的信息
     * @return 插入指定内容的SQL语句
     * */
    public static String insert(String tableName, Map info) {
        StringBuilder fields = new StringBuilder("(");
        StringBuilder values = new StringBuilder(" values(");
        for (Object object : info.entrySet()) {
            Map.Entry entry = (Map.Entry) object;
            fields.append(entry.getKey()).append(",");
            if (entry.getValue() instanceof String) {
                values.append("'").append(entry.getValue()).append("'").append(",");
            } else {
                values.append(entry.getValue()).append(",");
            }
        }
        fields.deleteCharAt(fields.length() - 1);
        fields.append(")");
        values.deleteCharAt(values.length() - 1);
        values.append(")");
        StringBuilder sqlTemp = new StringBuilder("insert into ").append(tableName).append(fields).append(values);
        return new String(sqlTemp);
    }
    /**
     * @param tableName 表名
     * @param conditions 删除条件
     * @return 删除指定内容的SQL语句
     * */
    public static String delete(String tableName, Map conditions) {
        StringBuilder sqlTemp = new StringBuilder("delete from ").append(tableName).append(" where 1=1 ");
        for (Object object : conditions.entrySet()) {
            Map.Entry entry = (Map.Entry) object;
            sqlTemp.append(" and ");
            if (entry.getValue() instanceof String) {
                sqlTemp.append(entry.getKey()).append("=").append("'").append(entry.getValue()).append("'");
            } else {
                sqlTemp.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return new String(sqlTemp);
    }
    /**
     * @param tableName 表名
     * @param fields 新数据
     * @param conditions 旧数据
     * */
    public static String update(String tableName, Map fields, Map conditions) {
        StringBuilder sets = new StringBuilder(" set ");
        StringBuilder wheres = new StringBuilder(" where 1=1 ");
        for (Object object : fields.entrySet()) {
            Map.Entry entry = (Map.Entry) object;
            if (entry.getValue() instanceof String) {
                sets.append(entry.getKey()).append("=").append("'").append(entry.getValue()).append("'").append(",");
            } else {
                sets.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
            }
        }
        sets.deleteCharAt(sets.length() - 1);
        for (Object object : conditions.entrySet()) {
            Map.Entry entry = (Map.Entry) object;
            wheres.append(" and ");
            if (entry.getValue() instanceof String) {
                wheres.append(entry.getKey()).append("=").append("'").append(entry.getValue()).append("'");
            } else {
                wheres.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        StringBuilder sqlTemp = new StringBuilder("update ").append(tableName).append(sets).append(wheres);
        return new String(sqlTemp);
    }
    /**
     * @param tableName 表名
     * @param conditions 查询条件
     * @return 查询指定条件的SQL语句
     * */
    public static String select(String tableName, Map conditions) {
        StringBuilder sqlTemp = new StringBuilder("select * from ").append(tableName).append(" where 1=1  ");
        for (Object object : conditions.entrySet()) {
            Map.Entry entry = (Map.Entry) object;
            sqlTemp.append(" and ");
            if (entry.getValue() instanceof String) {
                sqlTemp.append(entry.getKey()).append("=").append("'").append(entry.getValue()).append("'");
            } else {
                sqlTemp.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return new String(sqlTemp);
    }
    /**
     * @param tableName 表名
     * @return 查询表内所有数据的SQL语句
     * */
    public static String select(String tableName) {
        StringBuilder sqlTemp = new StringBuilder("select * from ").append(tableName).append(" where 1=1  ");
        return new String(sqlTemp);
    }
}
