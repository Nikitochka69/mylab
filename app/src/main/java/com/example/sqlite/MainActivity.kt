package com.example.sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var searchText: SearchView
    private lateinit var buttonAdd: Button
    private lateinit var listText: RecyclerView

    private lateinit var adapter: RecyclerAdapter

    companion object {
        const val EXTRA_KEY = "EXTRA"
    }
    private val REQUEST_CODE = 1
    //val REQUEST_CODE = 2
    private val REQUEST_CODE2 = 3
        //дихелпер
    private val dbHelper = DBHelper(this)
    lateinit var list: MutableList<Contact>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchText = findViewById<SearchView>(R.id.searchText)
        buttonAdd = findViewById<Button>(R.id.buttonAdd)
        listText = findViewById<RecyclerView>(R.id.recyclerView)

        loadList()





        buttonAdd.setOnClickListener {
            val intent = Intent(this, ContactEditActivity::class.java)
            intent.putExtra(EXTRA_KEY, (-1).toString())
            startActivityForResult(intent, REQUEST_CODE)

        }

        searchText.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText.toString())
                return false
            }

        })
    }





    fun filter(text: String){
        if(text == ""){
            loadList()
        }else {
           // fun filter()
            var filteredList = mutableListOf<Contact>()
           // list.clear()
            for (contact in list) {
                if (contact.name.contains(text,true)||contact.surname.contains(text,true)) {
                    filteredList.add(contact)

                }
            }
            changeListFiltered(filteredList)
        }







    }

                           private fun loadListFiltered(list: List<Contact>) {
                          TODO("Not yet implemented")
    }

    fun changeListFiltered(filters: MutableList<Contact>){
        adapter = RecyclerAdapter(filters) {
            if(it != -1) {
                val intent = Intent(this, ContactActivity::class.java)
                intent.putExtra(EXTRA_KEY, it.toString())
                startActivityForResult(intent, REQUEST_CODE2)


                    //list = dbHelper.getContacts()
                //adapter = RecyclerAdapter(list) {
                    //if(it != -1) {
                       // val intent = Intent(this, ContactActivity::class.java)
                        //intent.putExtra(EXTRA_KEY, it.toString())
                       // startActivityForResult(intent, REQUEST_CODE2)


                    }

        }
        adapter.notifyItemInserted(filters.lastIndex)
        listText.layoutManager = LinearLayoutManager(this)
        listText.adapter = adapter
    }

    private fun loadList(){
        list = dbHelper.getContacts()
        adapter = RecyclerAdapter(list) {
            if(it != -1) {
                val intent = Intent(this, ContactActivity::class.java)
                intent.putExtra(EXTRA_KEY, it.toString())
                startActivityForResult(intent, REQUEST_CODE2)

            }





        }
        adapter.notifyItemInserted(list.lastIndex)
        listText.layoutManager = LinearLayoutManager(this)
        listText.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        loadList()
    }
}