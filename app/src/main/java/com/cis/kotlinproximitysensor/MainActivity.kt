package com.cis.kotlinproximitysensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

// cm 단위로 측정 된다고는 하지만 정확도가 떨어진다.
// 물체가 근접 했는지 아닌지만 측정할 수 있는 정도임
class MainActivity : AppCompatActivity() {
    var manager : SensorManager? = null
    var listener : SensorListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        listener = SensorListener()

        startBtn.setOnClickListener {
            val sensor = manager?.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            val chk = manager?.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)

            if (chk == false) {
                tv.text = "근접 센서를 지원하지 않습니다."
            }
        }
    }

    inner class SensorListener : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
                tv.text = "물체와의 거리 : ${event.values[0]} cm"
            }
        }

    }
}
