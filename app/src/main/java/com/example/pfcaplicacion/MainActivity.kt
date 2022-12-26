package com.example.pfcaplicacion

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class MainActivity : AppCompatActivity() {

    lateinit var pulso: LineGraphSeries<DataPoint?>
    private lateinit var database: DatabaseReference


    val CHANELID="Chanel_id_example"
    val CHANELNAME="ChannelNam"
    val notificationId = 1


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var ejeX = 1.0
        val bundle=intent.extras
        val nombre = bundle?.getString("nombre")
        val numeroPaciente = bundle?.getString("numeroPasiente")

        val TextView2=findViewById<TextView>(R.id.NombrePaciente)
        TextView2.text=nombre

        val TextView3=findViewById<TextView>(R.id.paciente)
        TextView3.text= "N° $numeroPaciente"


        val notification = NotificationCompat.Builder(this,CHANELID).also {
            it.setContentTitle("ALERTA!!!")
            it.setContentTitle("El paciente "+numeroPaciente+" se nos va ")
            it.setSmallIcon(R.drawable.ic_message)
            it.setPriority(NotificationCompat.PRIORITY_HIGH)
        }.build()

        val notificationManager = NotificationManagerCompat.from(this)

        database=FirebaseDatabase.getInstance().getReference("PFC").child(numeroPaciente.toString()).child("valor")
        database.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val dato = snapshot.getValue().toString()

                    initGraph(dato,ejeX)
                    ejeX+=1

                    if(dato.toDouble()>120)  {
                        notificationManager.notify(notificationId,notification)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun initGraph(rx: String,ejeX : Double) {
        //permitime controlar los ejes manualmente
        graph.viewport.isXAxisBoundsManual = false;
        graph.viewport.isYAxisBoundsManual = false;
        graph.viewport.setMinX(0.0);
        graph.viewport.setMaxX(60.0);
        graph.viewport.setMaxY(1024.0)
        graph.viewport.setMinY(0.0)

        //permite realizar zoom y ajustar posicion eje x
        graph.viewport.isScalable = true
        graph.viewport.setScalableY(true)

        pulso = LineGraphSeries()
        //draw points
        pulso.isDrawDataPoints = true;
        //draw below points
        pulso.isDrawBackground = true;
        //color series
        pulso.color = Color.RED


        rxReceived(rx, ejeX)
        val TextView=findViewById<TextView>(R.id.lecturaPulso)

        TextView.text=rx

        graph.addSeries(pulso);

    }
    private fun rxReceived(rx:String,ejeX : Double) {

        pulso.appendData(DataPoint(ejeX,rx.toDouble()),true,22)

    }

}




