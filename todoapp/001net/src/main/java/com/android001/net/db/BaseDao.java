package com.android001.net.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.android001.net.db.annotion.DbTable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class design：
 *
 * @author android001
 *         created at 2017/9/16 下午12:26
 *         <p>
 *         首先实现类Class<T>的field与数据库SQLiteDatabase column的匹配
 */

public abstract class BaseDao<T> implements IBaseDao<T> {

    protected SQLiteDatabase sqliteDatabase;
    private Class<T> entryClass;
    private boolean isInit = false;

    private String tableName;
    private Map<String, Field> cacheMap;//数据库中的columnName列名和类中的field成员变量匹配关系

    private ContentValues getContentValues(T entry) {
        return null;
    }

    @Override
    public Long insert(T entry) {
        return null;
    }

    @Override
    public int update(T entry, T where) {
        return 0;
    }

    @Override
    public int delete(T where) {
        return 0;
    }

    @Override
    public List<T> query(T where) {
        return null;
    }

    @Override
    public List<T> query(T where, String orderBy, Integer startIndex, Integer limit) {
        return null;
    }

    @Override
    public List<T> query(String sql) {
        return null;
    }

    protected synchronized boolean init(Class<T> entry, SQLiteDatabase sqliteDatabase) {
        if (!isInit) {
            //1.初始化sqLiteDatabase 和 EntryClass
            this.sqliteDatabase = sqliteDatabase;
            this.entryClass = entry;
            //2.确定表名称
            if (entry.getAnnotation(DbTable.class) == null) {//没有annotation
                tableName = entry.getClass().getSimpleName();
            } else {//有annotation
                tableName = entry.getAnnotation(DbTable.class).value();
            }
            //3.如果sqLiteDatabase尚未打开直接返回false
            if (!sqliteDatabase.isOpen()) return false;
            //4.创建表格:tableName
            if (!TextUtils.isEmpty(createTable())) {
                sqliteDatabase.execSQL(createTable());
            }
            //5.初始化columnName和Field匹配关系
            cacheMap = new HashMap<>();
            initCatchMap();
            //置为已初始化
            isInit = true;
        }
        return isInit;
    }

    /**
     * 维护映射关系
     */
    private void initCatchMap(){
        String sql = "select * from "+this.tableName+" limit 1,0";//得到表结构
        Cursor cursor = null;
        cursor = sqliteDatabase.rawQuery(sql,null);


    }

    public abstract String createTable();
}
