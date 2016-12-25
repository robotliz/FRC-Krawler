package com.team2052.frckrawler.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "ROBOT".
 */
public class RobotDao extends AbstractDao<Robot, Long> {

    public static final String TABLENAME = "ROBOT";
    private DaoSession daoSession;
    private Query<Robot> game_RobotListQuery;
    private Query<Robot> team_RobotListQuery;
    private String selectDeep;

    public RobotDao(DaoConfig config) {
        super(config);
    }

    public RobotDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ROBOT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE ," + // 0: id
                "\"TEAM_ID\" INTEGER NOT NULL ," + // 1: team_id
                "\"GAME_ID\" INTEGER NOT NULL ," + // 2: game_id
                "\"DATA\" TEXT," + // 3: data
                "\"COMMENTS\" TEXT," + // 4: comments
                "\"LAST_UPDATED\" INTEGER);"); // 5: last_updated
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ROBOT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Robot entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getTeam_id());
        stmt.bindLong(3, entity.getGame_id());
 
        String data = entity.getData();
        if (data != null) {
            stmt.bindString(4, data);
        }
 
        String comments = entity.getComments();
        if (comments != null) {
            stmt.bindString(5, comments);
        }
 
        java.util.Date last_updated = entity.getLast_updated();
        if (last_updated != null) {
            stmt.bindLong(6, last_updated.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Robot entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getTeam_id());
        stmt.bindLong(3, entity.getGame_id());
 
        String data = entity.getData();
        if (data != null) {
            stmt.bindString(4, data);
        }
 
        String comments = entity.getComments();
        if (comments != null) {
            stmt.bindString(5, comments);
        }
 
        java.util.Date last_updated = entity.getLast_updated();
        if (last_updated != null) {
            stmt.bindLong(6, last_updated.getTime());
        }
    }

    @Override
    protected final void attachEntity(Robot entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Robot readEntity(Cursor cursor, int offset) {
        Robot entity = new Robot( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getLong(offset + 1), // team_id
                cursor.getLong(offset + 2), // game_id
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // data
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // comments
                cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)) // last_updated
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Robot entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTeam_id(cursor.getLong(offset + 1));
        entity.setGame_id(cursor.getLong(offset + 2));
        entity.setData(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setComments(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLast_updated(cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Robot entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Robot entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Robot entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

    /** Internal query to resolve the "robotList" to-many relationship of Game. */
    public List<Robot> _queryGame_RobotList(long game_id) {
        synchronized (this) {
            if (game_RobotListQuery == null) {
                QueryBuilder<Robot> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Game_id.eq(null));
                game_RobotListQuery = queryBuilder.build();
            }
        }
        Query<Robot> query = game_RobotListQuery.forCurrentThread();
        query.setParameter(0, game_id);
        return query.list();
    }

    /** Internal query to resolve the "robotList" to-many relationship of Team. */
    public List<Robot> _queryTeam_RobotList(long team_id) {
        synchronized (this) {
            if (team_RobotListQuery == null) {
                QueryBuilder<Robot> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Team_id.eq(null));
                team_RobotListQuery = queryBuilder.build();
            }
        }
        Query<Robot> query = team_RobotListQuery.forCurrentThread();
        query.setParameter(0, team_id);
        return query.list();
    }

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getGameDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getTeamDao().getAllColumns());
            builder.append(" FROM ROBOT T");
            builder.append(" LEFT JOIN GAME T0 ON T.\"GAME_ID\"=T0.\"_id\"");
            builder.append(" LEFT JOIN TEAM T1 ON T.\"TEAM_ID\"=T1.\"NUMBER\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected Robot loadCurrentDeep(Cursor cursor, boolean lock) {
        Robot entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Game game = loadCurrentOther(daoSession.getGameDao(), cursor, offset);
        if (game != null) {
            entity.setGame(game);
        }
        offset += daoSession.getGameDao().getAllColumns().length;

        Team team = loadCurrentOther(daoSession.getTeamDao(), cursor, offset);
        if (team != null) {
            entity.setTeam(team);
        }

        return entity;
    }
    
    public Robot loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();

        String[] keyArray = new String[]{key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);

        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }

    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Robot> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Robot> list = new ArrayList<Robot>(count);

        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Robot> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Robot> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
    
    /**
     * Properties of entity Robot.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Team_id = new Property(1, long.class, "team_id", false, "TEAM_ID");
        public final static Property Game_id = new Property(2, long.class, "game_id", false, "GAME_ID");
        public final static Property Data = new Property(3, String.class, "data", false, "DATA");
        public final static Property Comments = new Property(4, String.class, "comments", false, "COMMENTS");
        public final static Property Last_updated = new Property(5, java.util.Date.class, "last_updated", false, "LAST_UPDATED");
    }
 
}
