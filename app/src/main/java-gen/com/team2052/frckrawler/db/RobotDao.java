package com.team2052.frckrawler.db;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.team2052.frckrawler.db.Robot;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ROBOT.
*/
public class RobotDao extends AbstractDao<Robot, Long> {

    public static final String TABLENAME = "ROBOT";

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
    };

    private DaoSession daoSession;

    private Query<Robot> game_RobotListQuery;
    private Query<Robot> team_RobotListQuery;

    public RobotDao(DaoConfig config) {
        super(config);
    }
    
    public RobotDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ROBOT' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE ," + // 0: id
                "'TEAM_ID' INTEGER NOT NULL ," + // 1: team_id
                "'GAME_ID' INTEGER NOT NULL ," + // 2: game_id
                "'DATA' TEXT," + // 3: data
                "'COMMENTS' TEXT);"); // 4: comments
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ROBOT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Robot entity) {
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
    }

    @Override
    protected void attachEntity(Robot entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Robot readEntity(Cursor cursor, int offset) {
        Robot entity = new Robot( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // team_id
            cursor.getLong(offset + 2), // game_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // data
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // comments
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Robot entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTeam_id(cursor.getLong(offset + 1));
        entity.setGame_id(cursor.getLong(offset + 2));
        entity.setData(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setComments(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Robot entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Robot entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
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

}
