package hu.nagyhazi.model

data class User(
    val name: String,
    val email: String,
    val conversation: ArrayList<String>
)