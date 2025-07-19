package com.example.s13_techpoint

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.gms.common.api.ApiException // Importar ApiException

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    // Variables para las vistas de la UI
    private lateinit var etTechComponent: EditText
    private lateinit var btnSearch: Button

    // Variables para Google Maps y Ubicación
    private var googleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lastKnownLocation: Location? = null

    // Cliente de Google Places API
    private lateinit var placesClient: PlacesClient

    // Launcher para solicitar permisos de ubicación
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            if (fineLocationGranted || coarseLocationGranted) {
                // Permisos concedidos, obtener la ubicación y configurar el mapa
                getDeviceLocation()
                updateLocationUI()
            } else {
                // Permisos denegados, mostrar un mensaje al usuario
                Toast.makeText(this,
                    "Permisos de ubicación denegados. No se puede mostrar tu ubicación actual ni buscar establecimientos cercanos.",
                    Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        etTechComponent = findViewById(R.id.et_tech_component)
        btnSearch = findViewById(R.id.btn_search)

        // Inicializar el cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Inicializar Google Places API
        // Asegúrate de reemplazar "TU_CLAVE_DE_API_DE_GOOGLE_MAPS_AQUI" con tu clave real.
        // También puedes obtener la clave desde AndroidManifest.xml si la configuraste allí.
        if (!Places.isInitialized()) {
            // Es crucial que esta clave sea la misma que la configurada en AndroidManifest.xml
            // o una clave válida para Places API.
            Places.initialize(applicationContext, "TU API KEY AQUI");
        }
        placesClient = Places.createClient(this)

        // Obtener el SupportMapFragment y notificar cuando el mapa esté listo
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        // Configurar el listener para el botón de búsqueda
        btnSearch.setOnClickListener {
            val component = etTechComponent.text.toString().trim()
            if (component.isNotEmpty()) {
                // Iniciar la búsqueda de establecimientos
                searchForEstablishments(component)
            } else {
                Toast.makeText(this, "Por favor, ingresa un componente tech para buscar.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Se llama cuando el mapa está listo para ser utilizado.
     */
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.uiSettings?.isZoomControlsEnabled = true // Habilitar controles de zoom
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true // Habilitar botón de mi ubicación

        // Solicitar permisos y obtener la ubicación
        getLocationPermission()
    }

    /**
     * Solicita los permisos de ubicación necesarios.
     */
    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permiso ya concedido
            getDeviceLocation()
            updateLocationUI()
        } else {
            // Solicitar permiso
            requestPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }

    /**
     * Obtiene la última ubicación conocida del dispositivo y mueve la cámara del mapa.
     */
    private fun getDeviceLocation() {
        try {
            if (ContextCompat.checkSelfPermission(this.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                val locationResult = fusedLocationClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Se encontró la última ubicación conocida
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            val currentLatLng = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
                            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                            // Opcional: Añadir un marcador para la ubicación del usuario
                            // googleMap?.addMarker(MarkerOptions().position(currentLatLng).title("Tu Ubicación"))
                        } else {
                            Log.d("MainActivity", "Ubicación actual es nula. Usando valores por defecto (Lima, Perú).")
                            // Si la ubicación es nula, mover la cámara a una ubicación por defecto (ej. Lima, Perú)
                            val defaultLocation = LatLng(-12.046374, -77.042793) // Coordenadas de Lima
                            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
                            Toast.makeText(this, "No se pudo obtener tu ubicación actual. Mostrando ubicación por defecto.", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Log.e("MainActivity", "Excepción al obtener ubicación: %s", task.exception)
                        // Si falla, mover la cámara a una ubicación por defecto
                        val defaultLocation = LatLng(-12.046374, -77.042793) // Coordenadas de Lima
                        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
                        Toast.makeText(this, "Error al obtener tu ubicación. Mostrando ubicación por defecto.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    /**
     * Habilita la capa de "Mi Ubicación" en el mapa si los permisos están concedidos.
     */
    private fun updateLocationUI() {
        if (googleMap == null) {
            return
        }
        try {
            if (ContextCompat.checkSelfPermission(this.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                googleMap?.isMyLocationEnabled = true
                googleMap?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                googleMap?.isMyLocationEnabled = false
                googleMap?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    /**
     * Busca establecimientos que vendan el componente especificado utilizando Google Places API.
     * Los resultados se muestran como marcadores en el mapa.
     */
    private fun searchForEstablishments(component: String) {
        // Asegúrate de tener una ubicación conocida antes de buscar
        if (lastKnownLocation == null) {
            Toast.makeText(this, "No se pudo obtener tu ubicación. Intenta de nuevo o habilita los permisos.", Toast.LENGTH_LONG).show()
            getDeviceLocation() // Intentar obtener la ubicación de nuevo
            return
        }

        val latitude = lastKnownLocation!!.latitude
        val longitude = lastKnownLocation!!.longitude

        // Limpiar marcadores anteriores del mapa
        googleMap?.clear()

        // Añadir de nuevo el marcador de la ubicación actual del usuario
        val userLocation = LatLng(latitude, longitude)
        googleMap?.addMarker(MarkerOptions().position(userLocation).title("Tu Ubicación Actual"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f)) // Centrar en la ubicación del usuario

        Log.d("MainActivity", "Buscando '$component' cerca de Lat: $latitude, Lng: $longitude")
        Toast.makeText(this, "Buscando '$component'...", Toast.LENGTH_SHORT).show()

        // Definir un área de búsqueda alrededor de la ubicación actual del usuario
        // Ajusta los valores 0.1 para cambiar el radio de búsqueda (aprox. 11km por grado de lat/lon en el ecuador)
        val locationBias = RectangularBounds.newInstance(
            LatLng(latitude - 0.1, longitude - 0.1), // Suroeste
            LatLng(latitude + 0.1, longitude + 0.1)  // Noreste
        )

        // Construir la consulta para buscar predicciones de autocompletado.
        // Se busca el componente seguido de "tienda de electrónica" o "tienda de computadoras"
        val query = "$component tienda de electrónica" // Puedes probar con "$component tienda de computadoras" o ambos

        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .setLocationBias(locationBias)
            .build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            if (response.autocompletePredictions.isEmpty()) {
                Toast.makeText(this, "No se encontraron establecimientos para '$component'.", Toast.LENGTH_LONG).show()
                return@addOnSuccessListener
            }

            var placesFound = 0
            for (prediction in response.autocompletePredictions) {
                val placeId = prediction.placeId
                // Campos que queremos obtener para cada lugar
                val placeFields = listOf(Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.TYPES)
                val fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build()

                placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener { fetchResponse ->
                    val place = fetchResponse.place
                    val placeLatLng = place.latLng
                    val placeName = place.name
                    val placeAddress = place.address
                    val placeTypes = place.types // Lista de tipos de lugar (ej: electronics_store)

                    if (placeLatLng != null && placeName != null) {
                        // Opcional: Filtrar por tipo de tienda si es necesario, aunque la consulta ya lo sugiere
                        // if (placeTypes?.contains(Place.Type.ELECTRONICS_STORE) == true ||
                        //     placeTypes?.contains(Place.Type.HOME_GOODS_STORE) == true) {
                        googleMap?.addMarker(MarkerOptions()
                            .position(placeLatLng)
                            .title(placeName)
                            .snippet(placeAddress)) // Muestra la dirección como un subtítulo
                        placesFound++
                        // }
                    }
                    // Si es el último resultado, mostrar un mensaje final
                    if (placesFound > 0 && prediction == response.autocompletePredictions.last()) {
                        Toast.makeText(this, "Se encontraron $placesFound establecimientos para '$component'.", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener { exception ->
                    Log.e("PlacesAPI", "Error al obtener detalles del lugar: ${exception.message}")
                    // No mostrar Toast aquí para cada error individual, solo un mensaje general de fallo.
                }
            }
        }.addOnFailureListener { exception ->
            // Manejo de errores más específico para la API de Places
            if (exception is ApiException) {
                val statusCode = exception.statusCode
                val statusMessage = exception.message
                Log.e("PlacesAPI", "Error de API de Places (Código: $statusCode): $statusMessage")
                when (statusCode) {
                    // Código 9011 es el error de facturación
                    9011 -> Toast.makeText(this, "Error de API: Facturación no habilitada en Google Cloud. Código: $statusCode", Toast.LENGTH_LONG).show()
                    // Otros códigos de error comunes
                    7 -> Toast.makeText(this, "Error de API: Problema de red o tiempo de espera. Código: $statusCode", Toast.LENGTH_LONG).show()
                    else -> Toast.makeText(this, "Error al buscar establecimientos: ${statusMessage ?: "Error desconocido"}. Código: $statusCode", Toast.LENGTH_LONG).show()
                }
            } else {
                // Para otros tipos de excepciones (ej. problemas de red genéricos)
                Log.e("PlacesAPI", "Error desconocido al buscar predicciones: ${exception.message}")
                Toast.makeText(this, "Error al buscar establecimientos. Asegúrate de tener conexión a internet.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
