package com.example.boostmode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.boostmode.database.AppDatabase
import com.example.boostmode.database.entity.DriverEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.concurrent.thread

class DriverPickerBottomSheet : BottomSheetDialogFragment() {

    companion object {
        private const val ARG_SELECTED = "selected_drivers"

        fun newInstance(
            selectedDrivers: List<String>,
            onDriverSelected: (String) -> Unit
        ): DriverPickerBottomSheet {
            val sheet = DriverPickerBottomSheet()
            sheet.onDriverSelected = onDriverSelected
            val args = Bundle()
            args.putStringArrayList(ARG_SELECTED, ArrayList(selectedDrivers))
            sheet.arguments = args
            return sheet
        }
    }

    var onDriverSelected: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_driver_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val displayHeight = resources.displayMetrics.heightPixels
        view.layoutParams = view.layoutParams.apply {
            height = displayHeight / 2
        }

        val selectedDrivers = arguments?.getStringArrayList(ARG_SELECTED) ?: emptyList<String>()

        thread {
            val db = AppDatabase.getInstance(requireContext())
            val drivers = db.driverDao().getAll()
            requireActivity().runOnUiThread {
                bindDrivers(view, drivers, selectedDrivers)
            }
        }
    }

    private fun bindDrivers(
        view: View,
        drivers: List<DriverEntity>,
        selectedDrivers: List<String>
    ) {
        val container = view.findViewById<LinearLayout>(R.id.container_driver_list)

        drivers.forEach { driver ->
            val fullName = "${driver.firstName} ${driver.lastName}"
            val isSelected = selectedDrivers.contains(fullName)

            val row = TextView(requireContext()).apply {
                text = fullName
                textSize = 16f
                typeface = resources.getFont(R.font.formula1_regular)
                setPadding(0, 24, 0, 24)
                setTextColor(
                    if (isSelected)
                        ContextCompat.getColor(requireContext(), R.color.text_disabled)
                    else
                        ContextCompat.getColor(requireContext(), R.color.text_primary)
                )
                isClickable = !isSelected
                isFocusable = !isSelected

                if (!isSelected) {
                    setOnClickListener {
                        onDriverSelected?.invoke(fullName)
                        dismiss()
                    }
                }
            }
            container.addView(row)
        }
    }
}