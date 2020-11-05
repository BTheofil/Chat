package hu.nagyhazi.adapter

import hu.nagyhazi.model.User

interface AdapterListener {
    fun onClickItem(user: User)
}