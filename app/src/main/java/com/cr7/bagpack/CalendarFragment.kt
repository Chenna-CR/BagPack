package com.cr7.bagpack

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.cr7.bagpack.database.TripsDatabase
import com.cr7.bagpack.databinding.DialogAddTripDetailsBinding
import com.cr7.bagpack.databinding.FragmentCalendarBinding
import com.cr7.bagpack.dataclasses.TripDataClass
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalendarConfiguration
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment(), TripListAdapter.onClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentCalendarBinding? = null
    var simpleDateFormat = SimpleDateFormat("dd MMM yyyy")
    var database : TripsDatabase?= null
    var tripsDetails = arrayListOf<TripDataClass>()
    lateinit var tripListAdapter:TripListAdapter
    var selectedDate = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = TripsDatabase.getInstance(requireContext())
        val date = Date()
        date.time = System.currentTimeMillis()
        val startCal = Calendar.getInstance()

        val endCal = Calendar.getInstance()
        endCal.time = date
        endCal.add(Calendar.MONTH, 3)
        val configuration: RecyclerCalendarConfiguration =
            RecyclerCalendarConfiguration(
                calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.HORIZONTAL,
                calendarLocale = Locale.getDefault(),
                includeMonthHeader = true
            )
        configuration.weekStartOffset = RecyclerCalendarConfiguration.START_DAY_OF_WEEK.MONDAY


        val calendarAdapterHorizontal: HorizontalRecyclerCalendarAdapter =
            HorizontalRecyclerCalendarAdapter(
                startDate = startCal.time,
                endDate = endCal.time,
                configuration = configuration,
                selectedDate = date,
                dateSelectListener = object : HorizontalRecyclerCalendarAdapter.OnDateSelected {
                    override fun onDateSelected(date: Date) {
                        selectedDate = date
                    }
                }
            )

        binding?.calendarRecyclerView?.adapter = calendarAdapterHorizontal


        tripListAdapter=TripListAdapter(requireContext(),tripsDetails,this)
        binding?.recyclerView?.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding?.recyclerView?.adapter=tripListAdapter
        getTrips()
//        tripListAdapter.notifyDataSetChanged()
        binding?.fab?.setOnClickListener {
            var dialogBinding = DialogAddTripDetailsBinding.inflate(layoutInflater)

            var dialog=Dialog(requireContext()).apply {
                setContentView(dialogBinding.root)
                window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                show()


            }
            var startDate = Calendar.getInstance()
            var endDate = Calendar.getInstance()
            dialogBinding.etStartDate.setOnClickListener {
               var startDateDialog =  DatePickerDialog(
                    requireContext(), { _, year, month, date ->
                        startDate.set(year, month, date)
                        dialogBinding.etStartDate?.setText(simpleDateFormat.format(startDate.time))
                    },
                    startDate.get(Calendar.YEAR),
                    startDate.get(Calendar.MONTH),
                    startDate.get(Calendar.DAY_OF_MONTH)
                )
                startDateDialog.datePicker.setMinDate(startDate.timeInMillis)
                startDateDialog.show()
            }
            dialogBinding.etEndDate.setOnClickListener {
                var endDateDialog = DatePickerDialog(
                    requireContext(), { _, year, month, date ->
                        endDate.set(year, month, date)
                        dialogBinding.etEndDate.setText(simpleDateFormat.format(endDate.time))
                    },
                    endDate.get(Calendar.YEAR),
                    endDate.get(Calendar.MONTH),
                    endDate.get(Calendar.DAY_OF_MONTH)
                )
                endDateDialog.datePicker.minDate = startDate.timeInMillis
                endDateDialog.show()
            }
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etName.text.toString().isNullOrEmpty()) {
                    dialogBinding.etName.error = resources.getString(R.string.add_name)
                } else if (dialogBinding.etStartDate.text.toString().isNullOrEmpty()) {
                    dialogBinding.etName.error = resources.getString(R.string.enter_start_date)
                } else if (dialogBinding.etEndDate.text.toString().isNullOrEmpty()) {
                    dialogBinding.etName.error = resources.getString(R.string.enter_end_date)
                } else{
                    var tripDataClass = TripDataClass(name = dialogBinding.etName.text.toString(), startingDate = startDate.time, endingDate = endDate.time)
                    database?.tripsDaoInterface()?.insertTripsDetails(tripDataClass)
                    dialog.dismiss()
                    getTrips()
                }
            }
        }

    }

    fun getTrips(){
        tripsDetails.clear()
        tripsDetails.addAll(database?.tripsDaoInterface()?.getTripsList()?: arrayListOf())
        tripListAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalendarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        class CalendarFragment : Fragment() {

            override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
            ): View? {
                return inflater.inflate(R.layout.fragment_calendar, container, false)
            }
        }
    }

    override fun delete(position: Int) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(resources.getString(R.string.delete_trip_details))
            setMessage(resources.getString(R.string.delete_trip_details_msg))
            setPositiveButton(resources.getString(R.string.yes)){_,_->
                database?.tripsDaoInterface()?.deleteTripDetails(tripsDetails[position])
                getTrips()
            }
            setPositiveButton(resources.getString(R.string.no)){_,_->}
            show()
        }
    }

    override fun update(position: Int) {
        var dialogBinding = DialogAddTripDetailsBinding.inflate(layoutInflater)
        var dialog=Dialog(requireContext()).apply {
            setContentView(dialogBinding.root)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            show()
        }
        var startDate = Calendar.getInstance()
        var endDate = Calendar.getInstance()
        startDate.time = tripsDetails[position].startingDate
        endDate.time = tripsDetails[position].endingDate
        dialogBinding.etName.setText(tripsDetails[position].name)
        dialogBinding.etStartDate.setOnClickListener {
            var startDateDialog =  DatePickerDialog(
                requireContext(), { _, year, month, date ->
                    startDate.set(year, month, date)
                    dialogBinding.etStartDate?.setText(simpleDateFormat.format(startDate.time))
                },
                startDate.get(Calendar.YEAR),
                startDate.get(Calendar.MONTH),
                startDate.get(Calendar.DAY_OF_MONTH)
            )
            startDateDialog.datePicker.setMinDate(startDate.timeInMillis)
            startDateDialog.show()
        }
        dialogBinding.etEndDate.setOnClickListener {
            var endDateDialog = DatePickerDialog(
                requireContext(), { _, year, month, date ->
                    endDate.set(year, month, date)
                    dialogBinding.etEndDate.setText(simpleDateFormat.format(endDate.time))
                },
                endDate.get(Calendar.YEAR),
                endDate.get(Calendar.MONTH),
                endDate.get(Calendar.DAY_OF_MONTH)
            )
            endDateDialog.datePicker.minDate = startDate.timeInMillis
            endDateDialog.show()
        }

        dialogBinding.btnAdd.setOnClickListener {
            if (dialogBinding.etName.text.toString().isNullOrEmpty()) {
                dialogBinding.etName.error = resources.getString(R.string.add_name)
            } else if (dialogBinding.etStartDate.text.toString().isNullOrEmpty()) {
                dialogBinding.etName.error = resources.getString(R.string.enter_start_date)
            } else if (dialogBinding.etEndDate.text.toString().isNullOrEmpty()) {
                dialogBinding.etName.error = resources.getString(R.string.enter_end_date)
            } else{
                var tripDataClass = TripDataClass(id = tripsDetails[position].id,
                    name = dialogBinding.etName.text.toString(),
                    startingDate = startDate.time,
                    endingDate = endDate.time)
                database?.tripsDaoInterface()?.updateTripDetailsEntity(tripDataClass)
                dialog.dismiss()
                getTrips()
            }
        }
    }
}