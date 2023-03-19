package com.saifurrijaal.ecommerce.presentation.location

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.saifurrijaal.ecommerce.R
import com.saifurrijaal.ecommerce.databinding.ActivityLocationBinding
import com.saifurrijaal.ecommerce.databinding.BottomSheetChooseLocationBinding
import com.saifurrijaal.ecommerce.utils.*

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val REQUEST_CODE_MAP_PERMISSIONS = 1000
        const val REQUEST_CODE_LOCATION = 2000
        const val DEFAULT_ZOOM = 18F
    }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityLocationBinding
    private lateinit var bindingBottomSheetLocation: BottomSheetChooseLocationBinding
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationManager: LocationManager
    private lateinit var locationSettingsRequest: LocationSettingsRequest
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var task: Task<LocationSettingsResponse>
    private var isRequestingUpdateLocation = false
    private var lastKnownLocation: LatLng? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    // callback
    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val location = locationResult.lastLocation
            val lat = location.latitude
            val lon = location.longitude
            val position = LatLng(lat, lon)

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, DEFAULT_ZOOM), 2000, null)

            isRequestingUpdateLocation = true
            hideLoadingSearchLocation()

            fusedLocationProviderClient?.removeLocationUpdates(this)
        }
    }

    private val mapPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingBottomSheetLocation = binding.bottomSheetChooseLocation

        initLayoutMap()
        initMap()
        initLocation()
        openBottomSheetLocation()
        onAction()
    }

    private fun onAction() {
        bindingBottomSheetLocation.fabCurrentLocationChooseLocation.setOnClickListener { requestLocation() }

        bindingBottomSheetLocation.fabBackChooseLocation.setOnClickListener { onBackPressed() }
    }

    private fun collapsedBottomSheetLocation() {
        bottomSheetBehavior = BottomSheetBehavior.from(bindingBottomSheetLocation.bottomSheetChooseLocation)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun openBottomSheetLocation() {
        bottomSheetBehavior = BottomSheetBehavior.from(bindingBottomSheetLocation.bottomSheetChooseLocation)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun hideLoadingSearchLocation() {
        bindingBottomSheetLocation.pbBottomSheet.gone()
        bindingBottomSheetLocation.btnChooseLocation.visible()
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initLayoutMap() {
        val matchParent = ConstraintLayout.LayoutParams.MATCH_PARENT
        val layoutParams = ConstraintLayout.LayoutParams(matchParent, getHeightScreen() + 400)
        binding.map.layoutParams = layoutParams

        val constraint = findViewById<ConstraintLayout>(R.id.container_map)
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraint)
        constraintSet.connect(R.id.map, ConstraintSet.BOTTOM, R.id.container_map, ConstraintSet.BOTTOM)
        constraintSet.applyTo(binding.containerMap)
    }

    private fun initLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.create().apply {
            interval = 1000 * 5.toLong()
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest
            .Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(this)
        task = client.checkLocationSettings(builder.build())

        locationSettingsRequest = builder.build()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_MAP_PERMISSIONS -> {
                if (grantResults.isNotEmpty()) {
                    var isHasPermission = false
                    val permissionNotGrantedMessage = StringBuilder()

                    for (i in permissions.indices) {
                        isHasPermission = grantResults[i] == PackageManager.PERMISSION_GRANTED

                        if (!isHasPermission) {
                            permissionNotGrantedMessage.append("${permissions[i]}\n")
                        }
                    }

                    if (isHasPermission) {
                        requestUpdateLocation()
                    } else {
                        val message = permissionNotGrantedMessage.toString() + "\n" + getString(R.string.not_granted)
                        showDialogError(this, message)
                    }
                }
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val defaultLocation = LatLng(-7.9544106880785765, 112.61488706484423)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM))

        mMap.setOnCameraMoveListener {
            collapsedBottomSheetLocation()
        }

        mMap.setOnCameraIdleListener {
            // error nya saat nyoba buat dapetin lokasinya
            lastKnownLocation = mMap.cameraPosition.target
            val address = lastKnownLocation?.convertToAddress(this)
            if (address != null) {
                bindingBottomSheetLocation.tvAddressChooseLocation.text = address
            }
            openBottomSheetLocation()
        }

        requestLocation()
    }

    // Location
    private fun requestLocation() {
        if (checkMapPermission()) {
            if (isLocationEnabled()) {
                requestUpdateLocation()
            } else {
                goTurnOnGps()
            }
        } else {
            setRequestMapPermission()
        }
    }

    private fun setRequestMapPermission() {
        requestPermissions(mapPermissions, REQUEST_CODE_MAP_PERMISSIONS)
    }

    private fun goTurnOnGps() {
        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(this, REQUEST_CODE_LOCATION)
                } catch (sendEx: IntentSender.SendIntentException) {
                    sendEx.printStackTrace()
                }
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return true
        }
        return false
    }

    private fun requestUpdateLocation() {
        if (checkMapPermission()) {
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = false

            bindingBottomSheetLocation.tvAddressChooseLocation.text = getString(R.string.search_your_location)

            fusedLocationProviderClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun checkMapPermission(): Boolean {
        var isMapPermission = false
        for (permission in mapPermissions) {
            isMapPermission = ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
        return isMapPermission
    }
}