package com.example.taskid.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.taskid.R
import com.example.taskid.data.viewmodel.SharedViewModel
import com.example.taskid.data.viewmodel.TaskViewModel
import com.example.taskid.databinding.FragmentListBinding
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment() {

    private val mTaskViewModel: TaskViewModel by viewModels()
    private val mSharedViewModel : SharedViewModel by viewModels()
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

        mTaskViewModel.getData.observe(viewLifecycleOwner,{data ->
            //mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    private fun setRecyclerView(){
        val recyclerView = binding.listaNotas
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
    }
}