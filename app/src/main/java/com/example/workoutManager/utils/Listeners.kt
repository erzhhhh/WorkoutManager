package com.example.workoutManager.utils


interface OnItemClickListener<Item> {
    fun onItemClick(item: Item)
}

interface OnRetryClickListener {
    fun onButtonClick()
}