package com.example.tp5.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tp5.model.Student

class SQLiteHelper ( context : Context) : SQLiteOpenHelper(context , DATABASE_NAME , null  , DATABASE_VERSION) {


    companion object {
        private const val DATABASE_NAME = "etudentskotlinSQLITETP.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_STUDENT = "student"
        private const val ID = "id"
        private const val NAME = "nom"
        private const val EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(("CREATE TABLE $TABLE_STUDENT ("+

                "$ID INT PRIMARY KEY,"+
                "$NAME TEXT,"+
                "$EMAIL TEXT)"
                ))
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENT")
        this.onCreate(db)

    }

    fun insertStudent(student: Student) : Long{
        val database = this.writableDatabase
        val values = ContentValues()
        values.put(ID , student.id)
        values.put(NAME , student.name)
        values.put(EMAIL , student.email)
        val success = database.insert(TABLE_STUDENT, null , values)
        return success
    }


    fun getAllStudents(): ArrayList<Student> {
        val studentsList: ArrayList<Student> = ArrayList()
        val query = "SELECT * FROM $TABLE_STUDENT"
        val database = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = database.rawQuery(query, null)

            var id: Int
            var name: String
            var email: String
            var index: Int

            if (cursor.moveToFirst()) {
                do {
                    index = cursor.getColumnIndex("id")
                    id = cursor.getInt(index)
                    index = cursor.getColumnIndex("nom")
                    name = cursor.getString(index)
                    index = cursor.getColumnIndex("email")
                    email = cursor.getString(index)

                    val student = Student(name, email)
                    student.id = id
                    studentsList.add(student)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }

        return studentsList
    }

    fun updateStudent(student : Student) : Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID, student.id)
        values.put(NAME, student.name)
        values.put(EMAIL, student.email)
        val success = db.update(
            TABLE_STUDENT, values, "id = ${student.id}", null
        )
        db.close()
        return success


    }



    fun deleteStudentById(id : Int) : Int{
        val db = this.writableDatabase
        val success = db.delete(TABLE_STUDENT, "id=$id"

            , null)

        db.close()
        return success
    }




}