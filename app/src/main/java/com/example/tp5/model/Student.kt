package com.example.tp5.model

    data class Student(var name:String, var email:String){
        var id:Int = numero++
        companion object{
            var numero = 1
        }
    }
