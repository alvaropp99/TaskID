package com.example.taskid.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskid.R
import com.example.taskid.databinding.FragmentListBinding

class ListFragment : Fragment() {


    private var _binding:  FragmentListBinding?=null
    private val binding get()=_binding!!

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

        //fijamos el menú list_fragment_menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }
}