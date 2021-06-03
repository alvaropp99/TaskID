package com.example.taskid.fragments.add

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.taskid.R
import com.example.taskid.data.models.Priority
import com.example.taskid.data.models.TaskData
import com.example.taskid.data.viewmodel.SharedViewModel
import com.example.taskid.data.viewmodel.TaskViewModel
import com.example.taskid.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar

class AddFragment : Fragment() {

    private val addTaskViewModel : TaskViewModel by viewModels()
    private val mSharedViewModel : SharedViewModel by viewModels()
    private var _binding: FragmentAddBinding?=null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentAddBinding.inflate(inflater,container,false)

        //fijamos el menú list_fragment_menu
        setHasOptionsMenu(true)

        // Llamamos al método que se encarga que las prioridades
        // tengan su color en función de la urgencia
        binding.spinnerPriority.onItemSelectedListener=mSharedViewModel.listener


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    // Establece los botones y las opciones del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_task){
            insertDataToDb()
            hideKeyboard()
        }

        return super.onOptionsItemSelected(item)
    }

    // Añade a la tabla principal de la base de datos todos los datos relacionados con una nota
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
            // Uso de snackbars en vez de Toast para mostrar información al usuario
            activity?.let { Snackbar.make(it.findViewById(R.id.addFragmentFrag),"Nota añadida correctamente", Snackbar.LENGTH_SHORT).show()}
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            activity?.let { Snackbar.make(it.findViewById(R.id.addFragmentFrag),"Rellene todos los campos", Snackbar.LENGTH_SHORT).show()}

        }
    }

    // Comprueba que los campos de título y descripción no estén vacíos
    private fun verifyData(title:String, description:String):Boolean{
        return if(TextUtils.isEmpty(title)||TextUtils.isEmpty(description)){
            false
        }else !(title.isEmpty()||description.isEmpty())
    }

    // Transforma el Enum Priority en un string que indica el tipo de prioridad
    private fun parsePriority(priority: String):Priority{
        return when(priority){
            "Muy Urgente"->{Priority.HIGH}
            "Urgente"->{Priority.MEDIUM}
            "Poco Urgente"->{Priority.LOW}
            else->Priority.LOW
        }
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}