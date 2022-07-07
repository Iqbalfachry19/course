package com.dicoding.courseschedule.ui.add


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.CardHomeView
import com.dicoding.courseschedule.ui.list.ListActivity
import com.dicoding.courseschedule.ui.list.ListViewModelFactory
import com.dicoding.courseschedule.util.DayName
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*


class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var viewModel: AddCourseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.add_course)
        val factory = ListViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)


    }

    fun showStartTimePicker(v: View) {
        val timePickerFragmentRepeat = TimePickerFragment()
        timePickerFragmentRepeat.show(supportFragmentManager, "startTimePicker")
    }

    fun showEndTimePicker(v: View) {
        val timePickerFragmentRepeat = TimePickerFragment()
        timePickerFragmentRepeat.show(supportFragmentManager, "endTimePicker")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val edCourse = findViewById<EditText>(R.id.add_ed_name)
                val spinner = findViewById<Spinner>(R.id.day)
                val lecturer = findViewById<EditText>(R.id.add_ed_lecturer).text.toString()
                val note = findViewById<EditText>(R.id.add_ed_note).text.toString()
                val course = edCourse.text.toString()
                val day = spinner.selectedItemPosition
                val startTime =findViewById<TextView>(R.id.tv_start_time)
                val endTime =findViewById<TextView>(R.id.tv_end_time)
                val start =
                    if (startTime.text.contains("\n")) startTime.text.toString().substring(11) else  ""
                val end =
                    if (endTime.text.contains("\n")) endTime.text.toString().substring(9) else ""
                if(start == "" || end == "" || course == "" ) {
                   Toast.makeText(this,getString(R.string.input_empty_message),Toast.LENGTH_SHORT).show()
                    false
                } else {
                    viewModel.insertCourse(course, day, start, end, lecturer, note)
                    val intent = Intent(this, ListActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

            }


            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        when (tag) {
            "startTimePicker" -> findViewById<TextView>(R.id.tv_start_time).text =
                StringBuilder().append("Start Time\n").append(dateFormat.format(calendar.time))

            "endTimePicker" -> findViewById<TextView>(R.id.tv_end_time).text =
                StringBuilder().append("End Time\n").append(dateFormat.format(calendar.time))

        }


    }
}