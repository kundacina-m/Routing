package sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.topnews.screens.Article
import com.example.topnews.screens.Constants

class ArticlesDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun readAllArticles(): ArrayList<Article> {
        val articles by lazy { ArrayList<Article>() }
        val db = readableDatabase
        val cursor = db.rawQuery("select * from " + DBContract.ArticleEntry.TABLE_NAME, null)

        cursor?.let {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                articles.add(createObjFromRow(cursor))
                cursor.moveToNext()
            }
        } ?: db.execSQL(SQL_CREATE_ENTRIES) ?: return ArrayList()
        cursor.close()
        return articles
    }

    fun insertArticle(article: Article?): Boolean {

        var db = readableDatabase
        if (checkIfRowAlreadyExists(db, article.hashCode())) return false

        db = writableDatabase
        db.insert(DBContract.ArticleEntry.TABLE_NAME, null, getRowValues(article))

        return true
    }

    private fun removeArticle(id: Int) {
        val db = writableDatabase

        val queryToDelete =
            "delete from " + DBContract.ArticleEntry.TABLE_NAME + " where " + DBContract.ArticleEntry.COLUMN_ID + " = " + id

        db.execSQL(queryToDelete)
        db.close()
    }

    private fun checkIfRowAlreadyExists(db: SQLiteDatabase, id: Int): Boolean {
        val queryToFind =
            "select * from " + DBContract.ArticleEntry.TABLE_NAME + " where " + DBContract.ArticleEntry.COLUMN_ID + " = " + id

        val cursor = db.rawQuery(queryToFind, null)
        if (cursor.count > 0) {
            removeArticle(id)
            cursor.close()
            return true
        }
        cursor.close()
        return false
    }

    private fun getRowValues(article: Article?): ContentValues {
        val cont = article?.content ?: "No content"
        val auth = article?.author ?: "No author"

        val values = ContentValues()
        values.put(DBContract.ArticleEntry.COLUMN_ID, article.hashCode())
        values.put(DBContract.ArticleEntry.COLUMN_SOURCE, article?.source?.getValue(Constants.MAP_SOURCE_KEY_NAME))
        values.put(DBContract.ArticleEntry.COLUMN_AUTHOR, auth)
        values.put(DBContract.ArticleEntry.COLUMN_TITLE, article?.title)
        values.put(DBContract.ArticleEntry.COLUMN_DESCRIPTION, article?.description)
        values.put(DBContract.ArticleEntry.COLUMN_URL, article?.url)
        values.put(DBContract.ArticleEntry.COLUMN_URL_TO_IMAGE, article?.urlToImage)
        values.put(DBContract.ArticleEntry.COLUMN_PUBLISHED_AT, article?.publishedAt)
        values.put(DBContract.ArticleEntry.COLUMN_CONTENT, cont)

        return values
    }

    private fun createObjFromRow(cursor: Cursor): Article {
        return Article(
            hashMapOf(Constants.MAP_SOURCE_KEY_NAME to cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_SOURCE))),
            cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_AUTHOR)),
            cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_TITLE)),
            cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_DESCRIPTION)),
            cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_URL)),
            cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_URL_TO_IMAGE)),
            cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_PUBLISHED_AT)),
            cursor.getString(cursor.getColumnIndex(DBContract.ArticleEntry.COLUMN_CONTENT))
        )
    }


    companion object {
        var DATABASE_VERSION = 1
        const val DATABASE_NAME = "ArticlesSQL.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.ArticleEntry.TABLE_NAME + " (" +
                    DBContract.ArticleEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                    DBContract.ArticleEntry.COLUMN_SOURCE + " TEXT," +
                    DBContract.ArticleEntry.COLUMN_AUTHOR + " TEXT," +
                    DBContract.ArticleEntry.COLUMN_TITLE + " TEXT," +
                    DBContract.ArticleEntry.COLUMN_DESCRIPTION + " TEXT," +
                    DBContract.ArticleEntry.COLUMN_URL + " TEXT," +
                    DBContract.ArticleEntry.COLUMN_URL_TO_IMAGE + " TEXT," +
                    DBContract.ArticleEntry.COLUMN_PUBLISHED_AT + " TEXT," +
                    DBContract.ArticleEntry.COLUMN_CONTENT + " TEXT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.ArticleEntry.TABLE_NAME
    }
}