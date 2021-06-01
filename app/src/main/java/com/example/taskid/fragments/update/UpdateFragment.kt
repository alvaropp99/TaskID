package com.example.taskid.fragments.update

import android.os.Bundle
import android.renderscript.RenderScript
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.taskid.R
import com.example.taskid.data.models.Priority
import com.example.taskid.data.viewmodel.SharedViewModel
import com.example.taskid.data.viewmodel.TaskViewModel
import com.example.taskid.databinding.FragmentAddBinding
import com.example.taskid.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mTaskViewModel: TaskViewModel by viewModels()

    private var _binding: FragmentUpdateBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Realizamos el binding para el updateFragment con su respectivo layout
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args

        //fijamos el menú list_fragment_menu
        setHasOptionsMenu(true)

        binding.spinnerPriorityUpdate.onItemSelectedListener = mSharedViewModel.listener

        return binding.root


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    private fun parsePriority(priority: String):Priority{
        return when(priority){
            "Muy Urgente"->{Priority.HIGH}
            "Urgente"->{Priority.MEDIUM}
            "Poco Urgente"->{Priority.LOW}
            else->Priority.LOW
        }
    }
}