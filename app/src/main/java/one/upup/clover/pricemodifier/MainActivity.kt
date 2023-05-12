package one.upup.clover.pricemodifier

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import one.upup.clover.pricemodifier.databinding.ActivityMainBinding
import one.upup.clover.pricemodifier.service.OrderListenerService
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private val orderListenerService: OrderListenerService by inject()

    private lateinit var binding: ActivityMainBinding
    private val intentService = Intent(this, OrderListenerService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startService(intentService)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(intentService)
        orderListenerService.onDestroy()
    }
}