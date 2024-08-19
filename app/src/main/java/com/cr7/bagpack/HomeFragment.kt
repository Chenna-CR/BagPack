package com.cr7.bagpack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.cr7.bagpack.database.TripsDatabase
import com.cr7.bagpack.databinding.FragmentHomeBinding
import com.cr7.bagpack.dataclasses.TripDataClass

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var tripId = 0
    lateinit var mainActivity: MainActivity
    lateinit var tripsDatabase: TripsDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        tripsDatabase = TripsDatabase.getInstance(mainActivity)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            tripId = it.getInt("tripId")
            getTripDetails()
        }

        // Trip Planning Button (Existing)
        binding.btnTripPlanning.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_tripPlanningFragment, bundleOf("tripId" to tripId))
        }

        // Calendar Button
        binding.btnCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_calendarFragment, bundleOf("tripId" to tripId))
        }

        // Packing List Button
        binding.btnPackingList.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_packingListFragment, bundleOf("tripId" to tripId))
        }

        // Expense Tracker Button
        binding.btnExpenseTracker.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_expenseTrackerFragment, bundleOf("tripId" to tripId))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getTripDetails(){
        mainActivity.tripDataClass = tripsDatabase.tripsDaoInterface().getSingleTrip(tripId)
    }
}
