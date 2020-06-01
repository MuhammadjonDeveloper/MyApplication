package net.city.myapplication.ui

import android.annotation.SuppressLint
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
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val clicklistneer: View.OnClickListener = View.OnClickListener {
            //TODO Do something on snackbar click
        }


        view.findViewById<Button>(R.id.button_first).setOnClickListener {

            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG_LOG", "First onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG_LOG", "First onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG_LOG", "First onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG_LOG", "First onResume")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG_LOG", "First onDestroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG_LOG", "First onCreate")
    }
}