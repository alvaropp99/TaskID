package com.example.taskid.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.taskid.R
import com.example.taskid.data.viewmodel.TaskViewModel
import com.example.taskid.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

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

        setRecyclerView()

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

        val search = menu.findItem(R.id.search_button)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_button){
            confirmFullDelete()
        }

        when (item.itemId){
            R.id.priority_high -> addTaskViewModel.sortByHigh.observe(viewLifecycleOwner,{adapter.setData(it)})
            R.id.priority_low -> addTaskViewModel.sortByLow.observe(viewLifecycleOwner,{adapter.setData(it)})
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null){
            searchInDb(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query!=null){
            searchInDb(query)
        }
        return true
    }

    private fun searchInDb(query: String){
        val searchQuery = "%$query%"

        addTaskViewModel.searchDb(searchQuery).observeOnce(viewLifecycleOwner, Observer { list ->
            list?.let {
                Log.d("ListFragment","searchInDb")
                adapter.setData(it)
            }
        })
    }

    private fun setRecyclerView(){
        val recyclerView = binding.listaNotas
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        // Estudiar funcionamiento de las animaciones
        /*recyclerView.itemAnimator = LandingAnimator().apply { moveDuration=300 }*/

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


    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
        observe(lifecycleOwner, object : Observer<T>{
            override fun onChanged(t: T) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }
}