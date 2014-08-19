package cc.joke.db;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cc.joke.debug.Logger;
import cc.joke.entity.T_App;

public class DBHelper extends SQLiteOpenHelper
{

    private static final int VERSION = 58;

    public DBHelper(Context context)
    {
        super(context, "diaoshi.db", null, VERSION);
    }

    public <T> List<T> query(Class<T> clazz, String where, String[] args, String orderBy)
    {
        return query(clazz, where, args, null, null, orderBy);
    }

    public synchronized <T> List<T> query(Class<T> clazz, String where, String[] args, String groupBy, String having,
            String orderBy)
    {
        List<T> list = new ArrayList<T>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = getReadableDatabase();
            Method[] methods = clazz.getDeclaredMethods();
            Field[] fields = clazz.getDeclaredFields();
            String[] columns = new String[fields.length];
            for (int i = 0; i < fields.length; i++)
            {
                columns[i] = fields[i].getName();
            }
            cursor = db.query(clazz.getSimpleName(), columns, where, args, groupBy, having, orderBy);
            while (cursor.moveToNext())
            {
                T t = clazz.newInstance();
                for (int i = 0; i < cursor.getColumnCount(); i++)
                {
                    String column = cursor.getColumnName(i);
                    Method method = null;
                    for (Method m : methods)
                    {
                        if (("set" + column).equalsIgnoreCase(m.getName()))
                        {
                            method = m;
                            break;
                        }
                    }
                    if (method != null)
                    {
                        for (Field f : fields)
                        {
                            if (f.getName().equalsIgnoreCase(column))
                            {
                                if (f.getType() == int.class || f.getType() == Integer.class)
                                {
                                    method.invoke(t, cursor.getInt(cursor.getColumnIndex(column)));
                                }
                                else if (f.getType() == double.class || f.getType() == Double.class)
                                {
                                    method.invoke(t, cursor.getDouble(cursor.getColumnIndex(column)));
                                }
                                else
                                {
                                    method.invoke(t, cursor.getString(cursor.getColumnIndex(column)));
                                }
                                break;
                            }
                        }

                    }
                }
                list.add(t);
            }
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
        return list;
    }

    public synchronized <T> boolean update(T t, String where, String[] args)
    {
        SQLiteDatabase db = null;
        try
        {
            db = getWritableDatabase();
            ContentValues values = new ContentValues();
            Class<?> clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            Method[] methods = clazz.getDeclaredMethods();
            if (fields != null && fields.length > 0)
            {
                for (Field f : fields)
                {
                    Method method = null;
                    for (Method m : methods)
                    {
                        if (m.getName().equalsIgnoreCase("get" + f.getName()))
                        {
                            method = m;
                            break;
                        }
                    }
                    if (method != null)
                    {
                        Object obj = method.invoke(t);
                        if (obj == null)
                        {
                            continue;
                        }
                        else if (obj instanceof Integer)
                        {
                            values.put(f.getName(), Integer.parseInt(obj.toString()));
                        }
                        else
                        {
                            values.put(f.getName(), obj.toString());
                        }
                    }
                }
            }

            long res = db.update(clazz.getSimpleName(), values, where, args);
            if (res != -1)
            {
                return true;
            }
            else
            {
                Logger.i("inert(" + clazz + ")", "修改数据失败(update " + clazz.getSimpleName() + " set [" + values
                        + "] where " + where + "[" + (args == null ? 0 : args.length) + "])");
            }
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }
        return false;
    }

    public <T> void clear(Class<T> clazz)
    {
        SQLiteDatabase db = getWritableDatabase();
        try
        {
            long res = db.delete(clazz.getSimpleName(), null, null);
            if (res != -1)
            {

            }
            else
            {
                Logger.i("inert(" + clazz + ")", "清除数据失败(delete " + clazz.getSimpleName() + "");
            }
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }
    }

    public synchronized <T> boolean delete(Class<T> clazz, String where, String[] args)
    {
        SQLiteDatabase db = null;
        try
        {
            db = getWritableDatabase();
            long res = db.delete(clazz.getSimpleName(), where, args);
            if (res != -1)
            {
                return true;
            }
            else
            {
                Logger.i("inert(" + clazz + ")", "删除数据失败(delete " + clazz.getSimpleName() + " where " + where + "["
                        + (args != null ? args.length : 0) + "])");
            }
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }
        return false;
    }

    public synchronized <T> boolean insert(T t)
    {
        SQLiteDatabase db = null;
        try
        {
            db = getWritableDatabase();
            ContentValues values = new ContentValues();
            Class<?> clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            Method[] methods = clazz.getDeclaredMethods();
            if (fields != null && fields.length > 0)
            {
                for (Field f : fields)
                {
                    Method method = null;
                    for (Method m : methods)
                    {
                        if (m.getName().equalsIgnoreCase("get" + f.getName()))
                        {
                            method = m;
                            break;
                        }
                    }
                    if (method != null)
                    {
                        Object obj = method.invoke(t);
                        if (obj == null)
                        {
                            values.putNull(f.getName());
                        }
                        else if (obj instanceof Integer)
                        {
                            values.put(f.getName(), Integer.parseInt(obj.toString()));
                        }
                        else if (obj instanceof Double)
                        {
                            values.put(f.getName(), Double.parseDouble(obj.toString()));
                        }
                        else
                        {
                            values.put(f.getName(), obj.toString());
                        }
                    }
                }
            }
            if (values.size() > 0)
            {
                long res = db.insert(clazz.getSimpleName(), null, values);
                if (res != -1)
                {
                    Method m = clazz.getDeclaredMethod("get_id");
                    Object obj = m.invoke(t);
                    // 如果是自增长(_id=null)返回主键ID
                    if (obj == null)
                    {
                        Cursor cursor = db.query(clazz.getSimpleName(), new String[] {"LAST_INSERT_ROWID()"}, null,
                                null, null, null, null);
                        cursor.moveToNext();
                        int _id = cursor.getInt(0);
                        // DsLog.i("",
                        // "insert table "+clazz.getSimpleName()+"("+_id+")");
                        m = clazz.getDeclaredMethod("set_id", Integer.class);
                        m.invoke(t, _id);

                    }
                    return true;
                }
                else
                {
                    Logger.i("inert(" + clazz + ")", "插入数据失败" + values);
                }
            }
            else
            {
                Logger.i("inert(" + clazz + ")", clazz.getSimpleName() + " 的属性字段为空！");
            }
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }
        return false;
    }

    public synchronized void updateApp(T_App app)
    {
        // SQLiteDatabase db = null;
        // try {
        // db = getWritableDatabase();
        // ContentValues values = new ContentValues();
        // values.put("existsDetail", 1);
        // values.put("productID", app.getProductID());
        // values.put("newVersionName", app.getNewVersionName());
        // values.put("newVersionCode", app.getNewVersionCode());
        // values.put("newSize", app.getNewSize());
        // values.put("BaseUrl", app.getBaseUrl());
        // values.put("FileName", app.getFileName());
        // values.put("IconUrl", app.getIconUrl());
        // db.update("T_APP", values, "PackageName = ?", new
        // String[]{app.getPackageName()});
        // } catch (Exception e) {
        // e.printStackTrace();
        // } finally {
        // if(db!=null){
        // db.close();
        // }
        // }
    }

    /*
     * T_INDEX _id 值范围 0 横条广告第1张 1 横条广告第2张 2 横条广告第3张 3 横条广告第4张 4 今日精选 5 小图标1 6
     * 小图标2 7 小图标3 8 小图标4 9 小图标5 10 小图标6 11 小图标7
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {

        // 登陆信息
        db.execSQL("create table T_LOGIN(_id integer primary key,status integer,userid integer,username varchar(50),usericon varchar(150),openid varchar(100),token varchar(100))");
        // //笑话列表配置表
        db.execSQL("create table T_JOKEINFO(_id integer primary key,id integer,title varchar(50),type varchar(50),iconUrl varchar(50),imgUrl varchar(50),description varchar(200),dspImages varchar(100),talknum integer,highPraise integer,badPraise integer,source integer)");
        // 消息表
        db.execSQL("create table T_MARKET_MESSAGE(_id integer primary key,msg_name varchar(100),msg_type integer,msg_network integer,msg_title varchar(100),msg_content varchar(255),cmd_target varchar(50),msg_pic varchar(50),used_time integer,unused_time integer,sort integer,createtime integer,modifytime integer)");
        // 头条表
        db.execSQL("create table T_HEADLINES(_id integer primary key,title varchar(100),description varchar(100),imgUrl varchar(50),htmlUrl varchar(50),source integer)");
        // 两性表
        db.execSQL("create table T_SEXY(_id integer primary key,title varchar(100),description varchar(100),imgUrl varchar(50),htmlUrl varchar(50),ischarge integer,price integer,source integer)");
        // 评论表
        db.execSQL("create table T_COMMENT(_id INTEGER primary key,pid integer,userid integer,username varchar(20),content varchar(100),createDate varchar(30),model varchar(20),usericon varchar(150))");

        db.execSQL("create table T_PAY(_id INTEGER primary key,pay integer)");

        /** 本地管理及APP更新程序维护表 **/
        StringBuffer buffer = new StringBuffer();
        buffer.append("CREATE TABLE T_APP(").append("_id INTEGER PRIMARY KEY AUTOINCREMENT,").append(
                "name VARCHAR(30),").append("packageName VARCHAR(100) UNIQUE,").append("size INTEGER,").append(
                "versionName VARCHAR(15),").append("versionCode INTEGER,").append("newVersionName VARCHAR(15),").append(
                "newVersionCode INTEGER,").append("newSize INTEGER,").append("location INTEGER,").append(
                "drawableID VARCHAR(20),").append("existsDetail INTEGER,").append("productID INTEGER,").append(
                "py VARCHAR(1),").append("Status INTEGER,").append("BaseUrl VARCHAR(100),").append(
                "FileName VARCHAR(20),").append("IconUrl VARCHAR(50),").append("lastDateTime VARCHAR(20)").append(")");
        db.execSQL(buffer.toString());

        /** 新闻维护表 **/
        buffer.setLength(0);
        buffer.append("CREATE TABLE IF NOT EXISTS T_News").append("(").append("_id INTEGER PRIMARY KEY AUTOINCREMENT,").append(
                "newsID INTEGER UNIQUE,").append("title VARCHAR(20),").append("content TEXT,").append(
                "iconUrl VARCHAR(80),").append("existsDetail INTEGER,").append("detailContent TEXT,").append(
                "createTime VARCHAR(20)").append(")");
        db.execSQL(buffer.toString());

        /** 好差评维护表 **/
        buffer.setLength(0);
        buffer.append("CREATE TABLE IF NOT EXISTS T_PRAISE").append("(").append(
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,").append("ProductID INTEGER UNIQUE,").append(
                "PraiseType INTEGER").append(")");
        db.execSQL(buffer.toString());

        /** 货架产品表 **/
        db.execSQL("create table T_PRODUCT(_id integer primary key, pid integer,shelfID INTEGER, title varchar(20),version varchar(20),type varchar(20),iconUrl varchar(50),star integer,language integer,size integer,downLoadCount integer,downloadUrl varchar(50))");

        /** 激活表 **/
        db.execSQL("CREATE TABLE IF NOT EXISTS T_ACTIVATION(_id INTEGER PRIMARY KEY, status INTEGER)");

        Logger.i("", "数据库创建完毕");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS T_LOGIN");
        db.execSQL("DROP TABLE IF EXISTS T_JOKEINFO");
        db.execSQL("DROP TABLE IF EXISTS T_HEADLINES");
        db.execSQL("DROP TABLE IF EXISTS T_SEXY");
        db.execSQL("DROP TABLE IF EXISTS T_PAY");

        db.execSQL("DROP TABLE IF EXISTS T_DOWNLOAD");
        db.execSQL("DROP TABLE IF EXISTS T_DOWNLOAD_DETAIL");
        db.execSQL("drop table if exists T_MARKET_MESSAGE");
        db.execSQL("drop table if exists T_PRODUCTINFO");
        db.execSQL("drop table if exists T_COMMENT");
        db.execSQL("DROP TABLE if exists T_APP");
        db.execSQL("DROP TABLE IF EXISTS T_News");
        db.execSQL("DROP TABLE IF EXISTS T_PRAISE");
        db.execSQL("DROP TABLE IF EXISTS T_PRODUCT");
        db.execSQL("DROP TABLE IF EXISTS T_ACTIVATION");
        Logger.i("", "数据库更新完毕");
        onCreate(db);
    }

}
