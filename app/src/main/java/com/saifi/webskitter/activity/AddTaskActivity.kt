package com.saifi.webskitter.activity

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.saifi.webskitter.R
import com.saifi.webskitter.roomClasses.DatabaseClient
import com.saifi.webskitter.roomClasses.UserModel
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        button_save.setOnClickListener {
            saveTask()
        }
    }

    private fun saveTask() {
        val naem = editTextName.text.toString().trim { it <= ' ' }
        val emal = editTextEmail.text.toString().trim { it <= ' ' }
        val phon = editTextPhone.text.toString().trim { it <= ' ' }
        val adrs = editTextAddress.text.toString().trim { it <= ' ' }
        if (naem.isEmpty()) {
            editTextName.error = "Name required"
            editTextName.requestFocus()
            return
        }
        if (emal.isEmpty()) {
            editTextEmail.error = "Email required"
            editTextEmail.requestFocus()
            return
        }
        if (phon.isEmpty()) {
            editTextPhone.error = "Phone no. required"
            editTextPhone.requestFocus()
            return
        }
        if (adrs.isEmpty()) {
            editTextAddress.error = "Address required"
            editTextAddress.requestFocus()
            return
        }
        class SaveTask : AsyncTask<Void?, Void?, Void?>() {


            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                onBackPressed()
                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
            }

            override fun doInBackground(vararg params: Void?): Void? {


                //creating a task
                val task = UserModel()
                task.name = naem
                task.email = emal
                task.phone = phon
                task.address = adrs


                //adding to database
                DatabaseClient.getInstance(applicationContext).appDatabase
                    .taskDao()
                    .insert(task)
                return null
            }
        }

        val st = SaveTask()
        st.execute()
    }

}