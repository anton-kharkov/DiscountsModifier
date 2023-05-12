package one.upup.clover.pricemodifier.di.module

import one.upup.clover.pricemodifier.discounts_control.DiscountsControl
import one.upup.clover.pricemodifier.service.OrderListenerService
import org.koin.dsl.module

val appModule = module {
    single { OrderListenerService() }
    single { DiscountsControl() }
}