package com.dicoding.storyapp.presentation.ui.dashboard.maps

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.databinding.FragmentMapsBinding
import com.dicoding.storyapp.presentation.ui.story_detail.StoryDetailActivity
import com.dicoding.storyapp.utils.toLocaleFormat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber


class MapsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<MapsViewModel>()

    private val markersMap = HashMap<Marker, Story>()
    private val dicodingSpace = LatLng(-6.8957643, 107.6338462)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, s: Bundle?,
    ): View {
        // Bind view
        _binding = FragmentMapsBinding.inflate(inflater, container, false)

        // Setup map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Return view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.storiesLiveData.observe(viewLifecycleOwner) { response ->
            if (response is ApiResponse.Success) {
                Timber.d("Sizes of stories: ${response.data.size}")
            }
        }
    }

    override fun onMapReady(mMap: GoogleMap) {
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dicodingSpace, 8f))

        mMap.setOnInfoWindowClickListener {marker ->
            markersMap[marker]?.let { story ->
                val intent = Intent(activity, StoryDetailActivity::class.java)
                intent.putExtra(StoryDetailActivity.EXTRA_STORY, story)
                startActivity(intent)
            }
        }

        viewModel.storiesLiveData.observe(viewLifecycleOwner) { response ->
            if (response is ApiResponse.Success) {
                response.data.map { story ->
                    if (story.latitude == null || story.longitude == null) return@map
                    val pos = LatLng(story.latitude, story.longitude)
                    val markerOptions = MarkerOptions().position(pos).title(story.name)
                        .snippet(story.createdAt.toLocaleFormat())
                    mMap.addMarker(markerOptions)?.let { marker -> markersMap.put(marker, story) }
                }
            }
        }
    }
}