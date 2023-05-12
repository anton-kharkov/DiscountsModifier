package one.upup.clover.pricemodifier.discounts_control

import com.clover.sdk.v3.order.Order

interface IDiscountsControl {
    fun addDiscount(order: Order)
    fun removeDiscount(order: Order)
}