package one.upup.clover.pricemodifier.di.module

import android.accounts.Account
import android.content.Context
import com.clover.sdk.util.CloverAccount
import com.clover.sdk.v3.order.OrderConnector
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val accountConnectorModule = module {
    single { provideCloverAccount(androidContext()) }
    single { provideOrderConnector(androidContext(), get()) }
}

private fun provideCloverAccount(context: Context) = CloverAccount.getAccount(context)

private fun provideOrderConnector(context: Context, cloverAccount: Account) =
    OrderConnector(context, cloverAccount, null)