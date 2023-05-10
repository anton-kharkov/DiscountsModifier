package one.upup.clover.pricemodifier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import one.upup.clover.pricemodifier.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}