package hu.nagyhazi.adapter

import hu.nagyhazi.Room.User

interface AdapterListener {
    fun onClickItem(user: User)
}