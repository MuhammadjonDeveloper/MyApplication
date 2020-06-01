package net.city.myapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import net.city.myapplication.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG_LOG","Second onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG_LOG","Second onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG_LOG","Second onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG_LOG","Second onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG_LOG","Second onDestroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG_LOG","Second onCreate")
    }
}
