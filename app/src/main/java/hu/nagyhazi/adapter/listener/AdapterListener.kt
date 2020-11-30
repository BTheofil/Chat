package hu.nagyhazi.adapter.listener

import hu.nagyhazi.model.User

interface AdapterListener {
    fun onClickItem(user: User)
}