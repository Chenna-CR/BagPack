package com.cr7.bagpack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.cr7.bagpack.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Trip Planning Button (Existing)
        binding.btnTripPlanning.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_tripPlanningFragment)
        }

        // Calendar Button
        binding.btnCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_calendarFragment)
        }

        // Packing List Button
        binding.btnPackingList.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_packingListFragment)
        }

        // Expense Tracker Button
        binding.btnExpenseTracker.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_expenseTrackerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
