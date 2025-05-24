// SettingsFragment.kt
package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val notificationSwitch: Switch = view.findViewById(R.id.switch_notifications)
        val themeSpinner: Spinner = view.findViewById(R.id.spinner_theme)
        val submitButton: Button = view.findViewById(R.id.button_submit)

        // Set up the spinner with theme options
        val themes = arrayOf("Light", "Dark")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, themes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        themeSpinner.adapter = adapter

        // Handle switch toggle
        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(
                requireContext(),
                if (isChecked) "Notifications enabled" else "Notifications disabled",
                Toast.LENGTH_SHORT
            ).show()
            // Add logic to enable/disable notifications (e.g., update SharedPreferences)
        }

        // Handle theme selection
        themeSpinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>, view: View?, position: Int, id: Long) {
                when (parent.getItemAtPosition(position).toString()) {
                    "Light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    "Dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }

        // Handle submit button click with scale animation
        submitButton.setOnClickListener {
            val scaleAnimation = ScaleAnimation(
                1f, 0.9f, // Start and end scale X
                1f, 0.9f, // Start and end scale Y
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot X
                Animation.RELATIVE_TO_SELF, 0.5f // Pivot Y
            ).apply {
                duration = 100
                repeatCount = 1
                repeatMode = Animation.REVERSE
            }
            it.startAnimation(scaleAnimation)
            Toast.makeText(requireContext(), "Settings saved", Toast.LENGTH_SHORT).show()
            // Add logic to save settings (e.g., to SharedPreferences)
        }

        return view
    }
}