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
    private lateinit var intentService: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intentService = Intent(this, OrderListenerService::class.java)

        startService(intentService)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(intentService)
        orderListenerService.onDestroy()
    }
}