package com.example.archivosinterno

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {


    var contenido: EditText? = null
    var archivo: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var guardar = findViewById<ImageButton>(R.id.imageButtonSave)
        var borrar = findViewById<ImageButton>(R.id.imageButtonDelete)
        var buscar = findViewById<ImageButton>(R.id.imageButtonSearch)


        guardar.setOnClickListener {
        guardarArchivo()
        }
        buscar.setOnClickListener {
        buscarArchivo()
        }
        borrar.setOnClickListener {
        borrarArchivo()
        }
    }
    fun guardarArchivo() {
        var nombreArchivo = archivo?.text.toString()
        var contenido = contenido?.text.toString()
        try {
            var sd = Environment.getExternalStorageDirectory()
            var rutaArchivo = File(sd.path.toString(), nombreArchivo)
            var file = OutputStreamWriter(openFileOutput(nombreArchivo, Activity.MODE_PRIVATE))
            file.write(contenido)
            file.flush()
            file.close()
            Toast.makeText(this, "Archivo guardado", Toast.LENGTH_SHORT).show()
            archivo?.setText("")
        }
        catch (e: IOException) {
            Toast.makeText(this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show()
        }

    }

    fun existeArchivo(archivos: Array<String>, nombreArchivo: String): Boolean {
        archivos.forEach {
            if (nombreArchivo == it) {
                return true
            }
        }
        return false
    }

    fun buscarArchivo() {
        var nombreArchivo = archivo?.text.toString()
        try {
            var sd = Environment.getExternalStorageDirectory()
            var rutaArchivo = File(sd.path.toString(), nombreArchivo)
            var file = InputStreamReader(openFileInput(nombreArchivo))
            if (existeArchivo(fileList(), nombreArchivo)) {
                var contenido = ""
                var nombreArchivo = InputStreamReader(openFileInput(nombreArchivo))
                var buffer = BufferedReader(file)
                var line = buffer.readLine()
                while (line != null) {
                    contenido = contenido + line + "\n"
                    line = buffer?.readLine()
                }
                Toast.makeText(this, "Archivo encontrado", Toast.LENGTH_SHORT).show()
                file.close()
                buffer.close()

            } else {
                Toast.makeText(this, "El archivo no existe", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Error al leer el archivo", Toast.LENGTH_SHORT).show()
        }
    }

        fun borrarArchivo() {
        deleteFile("archivo.txt")
        contenido?.setText("")
    }



}