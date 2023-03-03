package com.subha.myusergithubapp.data.models

data class SearchUserRes(
    val incomplete_results: Boolean,
    val items: ArrayList<Item>,
    val total_count: Int
)