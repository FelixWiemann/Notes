package com.nepumuk.notizen.core.utils.db_access

data class TextNote (
        val id:String,
        val message:String
        )

data class TaskNote (
        val id:String,
        val message:String
)

data class Task(
        val id:String
)