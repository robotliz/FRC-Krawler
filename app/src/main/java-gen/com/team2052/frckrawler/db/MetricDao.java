package com.team2052.frckrawler.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table METRIC.
 */
public class MetricDao extends AbstractDao<Metric, Long> {

    public static final String TABLENAME = "METRIC";
    private DaoSession daoSession;

    ;
    private Query<Metric> game_MetricListQuery;

    public MetricDao(DaoConfig config) {
        super(config);
    }

    public MetricDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'METRIC' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE ," + // 0: id
                "'NAME' TEXT," + // 1: name
                "'CATEGORY' INTEGER," + // 2: category
                "'TYPE' INTEGER," + // 3: type
                "'DATA' TEXT," + // 4: data
                "'GAME_ID' INTEGER NOT NULL );"); // 5: game_id
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'METRIC'";
        db.execSQL(sql);
    }

    @Override
    protected void attachEntity(Metric entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /**
     * @inheritdoc
     */
    @Override
    public Metric readEntity(Cursor cursor, int offset) {
        Metric entity = new Metric( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
                cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // category
                cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // type
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // data
                cursor.getLong(offset + 5) // game_id
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, Metric entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCategory(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setType(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setData(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setGame_id(cursor.getLong(offset + 5));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, Metric entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }

        Integer category = entity.getCategory();
        if (category != null) {
            stmt.bindLong(3, category);
        }

        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(4, type);
        }

        String data = entity.getData();
        if (data != null) {
            stmt.bindString(5, data);
        }
        stmt.bindLong(6, entity.getGame_id());
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(Metric entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(Metric entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

    /**
     * Internal query to resolve the "metricList" to-many relationship of Game.
     */
    public List<Metric> _queryGame_MetricList(long game_id) {
        synchronized (this) {
            if (game_MetricListQuery == null) {
                QueryBuilder<Metric> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Game_id.eq(null));
                game_MetricListQuery = queryBuilder.build();
            }
        }
        Query<Metric> query = game_MetricListQuery.forCurrentThread();
        query.setParameter(0, game_id);
        return query.list();
    }

    /**
     * Properties of entity Metric.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Category = new Property(2, Integer.class, "category", false, "CATEGORY");
        public final static Property Type = new Property(3, Integer.class, "type", false, "TYPE");
        public final static Property Data = new Property(4, String.class, "data", false, "DATA");
        public final static Property Game_id = new Property(5, long.class, "game_id", false, "GAME_ID");
    }

}