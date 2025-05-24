// HomeFragment.kt
package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Data receive from arguments
        val name = arguments?.getString("name") ?: "N/A"
        val email = arguments?.getString("email") ?: "N/A"
        val option = arguments?.getString("option") ?: "N/A"

        // Set received data
        binding.textViewName.text = "Name: $name"
        binding.textViewEmail.text = "Email: $email"
        binding.textViewOption.text = "Option: $option"

        // Popup menu logic
        binding.imageMenu.setOnClickListener { view ->
            val popupMenu = PopupMenu(requireContext(), view)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setTitle("Edit Profile")
                        builder.setMessage("Do you want to edit your profile?")
                        builder.setPositiveButton("Yes") { _, _ ->
                            // Navigate back to input screen (MainActivity's input form)
                            requireActivity().supportFragmentManager.popBackStack()
                            Toast.makeText(requireContext(), "Edit clicked", Toast.LENGTH_SHORT).show()
                        }
                        builder.setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                        builder.show()
                        true
                    }
                    R.id.action_share -> {
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Check out my profile!\nName: ${binding.textViewName.text}\nEmail: ${binding.textViewEmail.text}"
                            )
                            type = "text/plain"
                        }
                        startActivity(Intent.createChooser(shareIntent, "Share via"))
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}