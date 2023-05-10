package one.upup.clover.pricemodifier.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.clover.sdk.v3.order.OrderConnector
import com.clover.sdk.v3.order.OrderV31Connector
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class OrderListenerService : Service(), OrderV31Connector.OnOrderUpdateListener2, KoinComponent {

    private val orderConnector: OrderConnector by inject()

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
        TODO("Not yet implemented")
    }

    override fun onOrderCreated(orderId: String?) {
        TODO("Not yet implemented")
    }

    override fun onOrderDeleted(orderId: String?) {
        TODO("Not yet implemented")
    }

    override fun onOrderDiscountAdded(orderId: String?, discountId: String?) {
        TODO("Not yet implemented")
    }

    override fun onOrderDiscountsDeleted(orderId: String?, discountIds: MutableList<String>?) {
        TODO("Not yet implemented")
    }

    override fun onLineItemsAdded(orderId: String?, lineItemIds: MutableList<String>?) {
        TODO("Not yet implemented")
    }

    override fun onLineItemsUpdated(orderId: String?, lineItemIds: MutableList<String>?) {
        TODO("Not yet implemented")
    }

    override fun onLineItemsDeleted(orderId: String?, lineItemIds: MutableList<String>?) {
        TODO("Not yet implemented")
    }

    override fun onLineItemDiscountsAdded(orderId: String?, lineItemIds: MutableList<String>?, discountIds: MutableList<String>?) {
        TODO("Not yet implemented")
    }

    override fun onLineItemModificationsAdded(orderId: String?, lineItemIds: MutableList<String>?, modificationIds: MutableList<String>?) {
        TODO("Not yet implemented")
    }

    override fun onLineItemExchanged(orderId: String?, oldLineItemId: String?, newLineItemId: String?) {
        TODO("Not yet implemented")
    }

    override fun onPaymentProcessed(orderId: String?, paymentId: String?) {
        TODO("Not yet implemented")
    }

    override fun onRefundProcessed(orderId: String?, refundId: String?) {
        TODO("Not yet implemented")
    }

    override fun onCreditProcessed(orderId: String?, creditId: String?) {
        TODO("Not yet implemented")
    }
}