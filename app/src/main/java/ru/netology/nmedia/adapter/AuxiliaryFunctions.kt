package ru.netology.nmedia.adapter

    fun showNumbers(x: Int): String {
        return when (x) {
            in 0..999 -> x.toString()
            in 1000..10000 -> (x / 1000).toString() + "." + (x % 1000 / 100).toString() + "K"
            in 10000..999999 -> (x / 1000).toString() + "K"
            else -> (x / 1000000).toString() + "." + (x % 1000000 / 100000).toString() + "M"
        }
    }