package one.upup.clover.pricemodifier.discounts_control

import com.clover.sdk.v3.order.Discount
import com.clover.sdk.v3.order.LineItem
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
    private var itemList: List<LineItem> = listOf()

    override fun addDiscount(order: Order) {
        itemList = if (order.lineItems == null) {
            listOf()
        } else {
            order.lineItems
        }

        totalPrice = itemList.sumOf { it.price }

        if (totalPrice >= 1000L) {
            val itemListWithoutDiscount = itemList.filter { item ->
                item.discounts.isNullOrEmpty() ||
                        item.discounts?.any { it.name != DISCOUNT10 } ?: true
            }

            itemListWithoutDiscount.forEach {
                orderConnector.addLineItemDiscount(order.id, it.id, discount)
            }
        }
    }

    override fun removeDiscount(order: Order) {
        itemList = if (order.lineItems == null) {
            listOf()
        } else {
            order.lineItems
        }

        totalPrice = itemList.sumOf { it.price }

        if (totalPrice < 1000L) {
            val itemListWithDiscount = itemList.filter { it.discounts != null }

            if (itemListWithDiscount.isNotEmpty()) {

                itemListWithDiscount.forEach { item ->
                    val discountIds = item.discounts
                        .filter { discount -> discount.name == DISCOUNT10 }
                        .map { it.id }

                    orderConnector.deleteLineItemDiscounts(order.id, item.id, discountIds)
                }

                addDiscount(order)
            }
        }
    }
}