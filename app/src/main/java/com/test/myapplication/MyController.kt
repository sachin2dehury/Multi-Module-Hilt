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
                .id("my_item${pair.first}")
                .data(pair.first)
                .onClick {
                    list[index + 1] = pair.copy(first = MyData.X())
                }
                .addTo(this)
            MyEpoxyModel_()
                .id("my_item_y${pair.second}")
                .data(pair.second)
                .onClick {
                    list[index + 1] = pair.copy(second = MyData.Y(index + 1))
                }
                .addTo(this)
        }
    }
}
