package one.upup.clover.pricemodifier.discounts_control

import android.util.Log
import com.clover.sdk.v3.order.Discount
import com.clover.sdk.v3.order.Order
import com.clover.sdk.v3.order.OrderConnector
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val DISCOUNT10 = "10% discount"

class DiscountsControl : IDiscountsControl, KoinComponent {

    private val orderConnector: OrderConnector by inject()

    private val discount = Discount().apply {
        name = DISCOUNT10
        percentage = 10
    }

    private var totalPrice: Long = 0

    override fun addDiscount(order: Order) {
        totalPrice = order.lineItems.sumOf { it.price }

        if (totalPrice >= 1000L) {
            val itemsList = order.lineItems
                .filter { item ->
                    item.discounts.isNullOrEmpty() ||
                            item.discounts?.any { it.name != DISCOUNT10 } ?: true

                }

            itemsList.forEach {
                orderConnector.addLineItemDiscount(order.id, it.id, discount)
            }
        }
    }

    override fun removeDiscount(order: Order) {
        totalPrice = order.lineItems.sumOf { it.price }

        if (totalPrice < 1000L) {
            val itemList = order.lineItems.filter { it.discounts != null }

            if (itemList.isNotEmpty()) {

                itemList.forEach { item ->
                    val discountIds = item.discounts
                        .filter { discount -> discount.name == DISCOUNT10 }
                        .map { it.id }
                    Log.d("testTest", discountIds.toString())
                    orderConnector.deleteLineItemDiscounts(order.id, item.id, discountIds)
                }

                addDiscount(order)
            }
        }
    }
}