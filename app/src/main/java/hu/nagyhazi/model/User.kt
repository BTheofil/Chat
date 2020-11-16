package hu.nagyhazi.model

data class User(
    val name: String,
    val profilePicture: String?
) {
    constructor(): this("", null)
}