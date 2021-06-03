package com.example.taskid.fragments.update

import android.app.AlertDialog
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


    // Crea el menú del updateFragment
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    // Establece los botones y las opciones del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.update_task){
            updateData()
        }else if(item.itemId == R.id.delete_task){
            deleteTask()
        }

        return super.onOptionsItemSelected(item)
    }

    // Actualiza los datos modificados de la nota
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
            activity?.let { Snackbar.make(it.findViewById(R.id.updateFragmentFrag),"Nota actualizada", Snackbar.LENGTH_SHORT).show()}
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            activity?.let { Snackbar.make(it.findViewById(R.id.updateFragmentFrag),"Compruebe que todos los campos han sido correctamente rellenados", Snackbar.LENGTH_SHORT).show()}
        }
    }

    // Comprueba que los campos de título y descripción no estén vacíos
    private fun verifyData(title:String , description:String):Boolean{
        return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            false
        }else !(title.isEmpty()||description.isEmpty())
    }

    // Transforma el Enum priority en un string que indica el tipo de prioridad
    private fun parsePriority(priority: String):Priority{
        return when(priority){
            "Muy Urgente"->{Priority.HIGH}
            "Urgente"->{Priority.MEDIUM}
            "Poco Urgente"->{Priority.LOW}
            else->Priority.LOW
        }
    }

    // Muestra un dialog con la opción de borrar o no la nota a la que se haya accedido
    private fun deleteTask(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar nota ${args.currentItem.title}")
        builder.setMessage("¿Estás seguro/a de que quieres eliminar la nota ${args.currentItem.title}?")
        builder.setNegativeButton("No"){_,_ ->}
        builder.setPositiveButton("Sí"){_,_ ->
            updateTaskViewModel.deleteData(args.currentItem)
            activity?.let { Snackbar.make(it.findViewById(R.id.updateFragmentFrag),"Nota Borrada", Snackbar.LENGTH_SHORT).show()}
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.create().show()
    }
}