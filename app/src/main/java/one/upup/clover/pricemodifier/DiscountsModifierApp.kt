package one.upup.clover.pricemodifier

import android.app.Application
import android.content.Intent
import one.upup.clover.pricemodifier.di.module.accountConnectorModule
import one.upup.clover.pricemodifier.di.module.appModule
import one.upup.clover.pricemodifier.service.OrderListenerService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DiscountsModifierApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DiscountsModifierApp)
            modules(listOf(accountConnectorModule, appModule))
        }

        startService(Intent(this, OrderListenerService::class.java))
    }
}