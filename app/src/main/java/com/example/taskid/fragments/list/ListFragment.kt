package com.example.taskid.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.taskid.R
import com.example.taskid.data.viewmodel.TaskViewModel
import com.example.taskid.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment() {

    private val addTaskViewModel: TaskViewModel by viewModels()
    private var _binding:  FragmentListBinding?=null
    private val binding get()=_binding!!
    private val adapter : ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // mediante el binding se enlaza la clase de kotlin con su respectivo layout y atributos
        _binding= FragmentListBinding.inflate(inflater, container, false)

        //cuando se pulsa el floating button que abra el fragment que añade una nota
        binding.addTaskButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //te lleva al fragment que permite editar una nota
        binding.listLayout.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

        setRecyclerView()


        //fijamos el menú list_fragment_menu
        setHasOptionsMenu(true)

        addTaskViewModel.getData.observe(viewLifecycleOwner,{data ->
            addTaskViewModel.checkIfDbEmpty(data)
            adapter.setData(data)
        })

        addTaskViewModel.emptyDb.observe(viewLifecycleOwner, Observer {
            ifDbIsEmpty(it)
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_button){
            confirmFullDelete()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setRecyclerView(){
        val recyclerView = binding.listaNotas
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
    }

    private fun confirmFullDelete(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Borrar todas las notas")
        builder.setMessage("¿Estás seguro/a de que quieres eliminar todas las notas?")
        builder.setNegativeButton("No"){_,_ ->}
        builder.setPositiveButton("Sí"){_,_ ->
            addTaskViewModel.deleteAllTasks()
            activity?.let { Snackbar.make(it.findViewById(R.id.listLayout),"Todas las notas han sido borradas", Snackbar.LENGTH_LONG).show()}
        }
        builder.create().show()
    }

    private fun ifDbIsEmpty(emptyDb:Boolean){
        if(emptyDb){
            binding.noTaskImage.visibility = View.VISIBLE
            binding.noTaskText.visibility = View.VISIBLE
        }else{
            binding.noTaskImage.visibility = View.INVISIBLE
            binding.noTaskText.visibility = View.INVISIBLE
        }
    }
}