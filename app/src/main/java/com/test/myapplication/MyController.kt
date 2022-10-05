package com.test.myapplication

import com.airbnb.epoxy.AsyncEpoxyController

class MyController : AsyncEpoxyController() {

    private val list = (0..50).toList().map {
        val x = MyData.X()
        val y = MyData.Y(it)
        x to y
    }.toMutableList()

    override fun buildModels() {
        list.forEachIndexed { index, pair ->
            MyEpoxyModel_()
                .id("my_item_$index")
                .data(pair.first)
                .addTo(this)
            MyEpoxyModel_()
                .id("my_item_$index")
                .data(pair.first.copy(data = ABC.B("Sachin")))
                .addTo(this)
            MyEpoxyModel_()
                .id("my_item_y${pair.second}")
                .data(pair.second)
                .addTo(this)
        }
    }
}
