package com.example.taskid.fragments.update

import android.os.Bundle
import android.renderscript.RenderScript
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.taskid.R
import com.example.taskid.data.models.Priority
import com.example.taskid.databinding.FragmentAddBinding
import com.example.taskid.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //fijamos el menú list_fragment_menu
        setHasOptionsMenu(true)
        // se realiza el inflate para este fragment
        return inflater.inflate(R.layout.fragment_update, container, false)



        // MIRAR MAÑANA COMO HACER QUE EL UPDATE FRAGMENT RECIBA LOS DATOS DE LAS NOTAS CREADAS

        /*binding.etTitleUpdate.setText(args.currentItem.title)
        binding.etDescripcionUpdate.setText(args.currentItem.description)
        binding.spinnerPriorityUpdate.setIte*/
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