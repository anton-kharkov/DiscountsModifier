package one.upup.clover.pricemodifier.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.clover.sdk.v1.Intents
import com.clover.sdk.v3.order.Discount
import com.clover.sdk.v3.order.Order
import com.clover.sdk.v3.order.OrderConnector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class OrderBroadcastReceiver : BroadcastReceiver(), KoinComponent {
    private val orderConnector: OrderConnector by inject()

    private val percent: Double = 0.10
    private var checkOrderId = ""
    private var newOrder = true

    private var coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intents.ACTION_LINE_ITEM_ADDED) {
            val orderId = intent.getStringExtra(Intents.EXTRA_CLOVER_ORDER_ID)
            coroutineScope.launch {
                addDiscount(orderConnector.getOrder(orderId))
            }

        }
    }

    private fun addDiscount(order: Order) {
        coroutineScope.launch {
            if (order.id == checkOrderId) {
                newOrder = false
                calculateDiscount(order)
            } else {
                newOrder = true
                calculateDiscount(order)
            }
        }
    }

    private fun calculateDiscount(order: Order) {
        if (order.total >= 1000) {
            if (newOrder) {
                checkOrderId = order.id
                order.lineItems.forEach {
                    val discount = Discount().apply {
                        name = "10% discount"
                        amount = -(it.price * percent).toLong()
                    }
                    orderConnector.addLineItemDiscount(order.id, it.id, discount)
                }
            } else {
                val item = order.lineItems.last()
                val discount = Discount().apply {
                    name = "10% discount"
                    amount = -(item.price * percent).toLong()
                }

                orderConnector.addLineItemDiscount(order.id, item.id, discount)
            }
        }
    }
}