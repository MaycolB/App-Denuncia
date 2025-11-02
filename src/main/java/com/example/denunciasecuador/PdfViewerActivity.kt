package com.example.denunciasecuador

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView


class PdfViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Llama a la implementación del padre, es obligatorio.
        super.onCreate(savedInstanceState)

        // Llama a la función REAL del sistema para establecer la vista.
        setContentView(R.layout.activity_pdf_viewer)

        // Llama a la función REAL del sistema para encontrar el componente.
        val pdfView: PDFView = findViewById(R.id.pdfView)

        // Carga el PDF desde la carpeta 'assets'.
        // Asegúrate de que el nombre del archivo sea exacto.
        pdfView.fromAsset("Manual-de-usuario.pdf")
            .load()
    }

}