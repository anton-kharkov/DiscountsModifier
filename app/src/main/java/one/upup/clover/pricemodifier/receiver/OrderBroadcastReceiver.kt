package one.upup.clover.pricemodifier.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.clover.sdk.v1.Intents
import one.upup.clover.pricemodifier.service.OrderListenerService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class OrderBroadcastReceiver : BroadcastReceiver(), KoinComponent {

    private val orderListenerService: OrderListenerService by inject()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intents.ACTION_LINE_ITEM_ADDED) {
            val orderId = intent.getStringExtra(Intents.EXTRA_CLOVER_ORDER_ID)
            orderListenerService.onLineItemsAdded(orderId, mutableListOf())
        }
    }
}