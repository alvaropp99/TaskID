package com.example.taskid.fragments.update

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.taskid.R

class UpdateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //fijamos el menú list_fragment_menu
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }
}