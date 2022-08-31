package com.test.myapplication

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.test.myapplication.databinding.MyItemBinding

@EpoxyModelClass
abstract class MyEpoxyModel : EpoxyModelWithHolder<MyEpoxyModel.Holder>() {

    @EpoxyAttribute
    var data: MyData? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onClick: (() -> Unit)? = null

    inner class Holder : EpoxyHolder() {
        lateinit var binding: MyItemBinding
        override fun bindView(itemView: View) {
            binding = MyItemBinding.bind(itemView)
        }
    }

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.binding.run {
            root.setOnClickListener { onClick?.invoke() }
            data?.let {
                textView.text = it.toString()
            }
        }
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.binding.root.setOnClickListener(null)
    }

    override fun getDefaultLayout() = R.layout.my_item
}
