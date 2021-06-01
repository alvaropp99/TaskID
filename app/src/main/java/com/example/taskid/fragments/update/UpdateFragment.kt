package com.example.taskid.fragments.update

import android.os.Bundle
import android.renderscript.RenderScript
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskid.R
import com.example.taskid.data.models.Priority
import com.example.taskid.data.models.TaskData
import com.example.taskid.data.viewmodel.SharedViewModel
import com.example.taskid.data.viewmodel.TaskViewModel
import com.example.taskid.databinding.FragmentAddBinding
import com.example.taskid.databinding.FragmentUpdateBinding
import com.google.android.material.snackbar.Snackbar

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel: SharedViewModel by viewModels()
    private val updateTaskViewModel: TaskViewModel by viewModels()

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.update_task){
            updateData()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateData(){
        val updateTitle = binding.etTitleUpdate.text.toString()
        val updatePriority = binding.spinnerPriorityUpdate.selectedItem.toString()
        val updateDescription = binding.etDescripcionUpdate.text.toString()

        val validator = verifyData(updateTitle, updateDescription)
        if(validator){
            val updatedTask = TaskData (
                args.currentItem.id,
                updateTitle,
                parsePriority(updatePriority),
                updateDescription
            )
            updateTaskViewModel.updateData(updatedTask)
            var snackbar= activity?.let { Snackbar.make(it.findViewById(R.id.updateFragmentFrag),"Nota actualizada correctamente", Snackbar.LENGTH_SHORT).show()}
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            var snackbar= activity?.let { Snackbar.make(it.findViewById(R.id.updateFragmentFrag),"Compruebe que todos los campos han sido correctamente rellenados", Snackbar.LENGTH_SHORT).show()}
        }
    }

    private fun verifyData(title:String , description:String):Boolean{
        return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            false
        }else !(title.isEmpty()||description.isEmpty())
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