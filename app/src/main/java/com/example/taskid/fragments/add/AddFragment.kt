package com.example.taskid.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.taskid.R
import com.example.taskid.data.models.Priority
import com.example.taskid.data.models.TaskData
import com.example.taskid.data.viewmodel.TaskViewModel
import com.example.taskid.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private val addTaskViewModel : TaskViewModel by viewModels()
    private var _binding: FragmentAddBinding?=null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentAddBinding.inflate(inflater,container,false)

        //fijamos el menú list_fragment_menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_task){
            insertDataToDb()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb(){
        val addTitle = binding.etTitle.text.toString()
        val addPriority = binding.spinnerPriority.selectedItem.toString()
        val addDescription = binding.etDescripcion.text.toString()

        val validator = verifyData(addTitle,addDescription)
        if (validator){
            val newData = TaskData(
                0,
                addTitle,
                parsePriority(addPriority),
                addDescription
            )
            addTaskViewModel.insertData(newData)
            Toast.makeText(requireContext(),"Nota añadida correctamente",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Rellena todos los campos",Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyData(title:String, description:String):Boolean{
        return if(TextUtils.isEmpty(title)||TextUtils.isEmpty(description)){
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