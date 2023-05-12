package one.upup.clover.pricemodifier.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.clover.sdk.v3.order.OrderConnector
import com.clover.sdk.v3.order.OrderV31Connector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import one.upup.clover.pricemodifier.discounts_control.DiscountsControl
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class OrderListenerService : Service(), OrderV31Connector.OnOrderUpdateListener2, KoinComponent {

    private val orderConnector: OrderConnector by inject()
    private val discountsControl: DiscountsControl by inject()

    private var coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        orderConnector.addOnOrderChangedListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        orderConnector.removeOnOrderChangedListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onOrderUpdated(orderId: String?, selfChange: Boolean) {
    }

    override fun onOrderCreated(orderId: String?) {}

    override fun onOrderDeleted(orderId: String?) {}

    override fun onOrderDiscountAdded(orderId: String?, discountId: String?) {}

    override fun onOrderDiscountsDeleted(orderId: String?, discountIds: MutableList<String>?) {}

    override fun onLineItemsAdded(orderId: String?, lineItemIds: MutableList<String>?) {
        coroutineScope.launch {
            discountsControl.addDiscount(orderConnector.getOrder(orderId))
        }
    }

    override fun onLineItemsUpdated(orderId: String?, lineItemIds: MutableList<String>?) {}

    override fun onLineItemsDeleted(orderId: String?, lineItemIds: MutableList<String>?) {
        coroutineScope.launch {
            discountsControl.removeDiscount(orderConnector.getOrder(orderId))
        }
    }

    override fun onLineItemDiscountsAdded(
        orderId: String?,
        lineItemIds: MutableList<String>?,
        discountIds: MutableList<String>?
    ) {
    }

    override fun onLineItemModificationsAdded(
        orderId: String?,
        lineItemIds: MutableList<String>?,
        modificationIds: MutableList<String>?
    ) {
    }

    override fun onLineItemExchanged(
        orderId: String?,
        oldLineItemId: String?,
        newLineItemId: String?
    ) {
    }

    override fun onPaymentProcessed(orderId: String?, paymentId: String?) {}

    override fun onRefundProcessed(orderId: String?, refundId: String?) {}

    override fun onCreditProcessed(orderId: String?, creditId: String?) {}
}