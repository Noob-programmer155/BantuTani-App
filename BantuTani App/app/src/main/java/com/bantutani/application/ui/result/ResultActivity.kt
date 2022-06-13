package com.bantutani.application.ui.result

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bantutani.application.data.entitiy.predict.DataPredict
import com.bantutani.application.databinding.ActivityResultBinding
import com.bantutani.application.ui.scan.ScanViewModel


class ResultActivity : AppCompatActivity() {
    private lateinit var resultBinding: ActivityResultBinding
    private lateinit var scanModel: ScanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(resultBinding.root)

        resultBinding.kembali.setOnClickListener {
            super.onBackPressed()
        }
        SetupLiveData()
    }

    private fun SetupLiveData() {
        val Data = intent.getParcelableExtra<DataPredict>(EXTRA_DATA) as DataPredict
        resultBinding.apply {
            titleName.text = Data.name
            otherName.text = Data.otherNames.toString()
            textResult.text = Data.description.toString()
            textTips.text = Data.plantsCares.toString()?: "Belum ada tips"
//            Glide.with(this@ResultActivity)
//                .load(it.image)
//                .circleCrop()
//                .into(imageResult)
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}