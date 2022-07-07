package com.dicoding.courseschedule.ui.home

import androidx.lifecycle.*
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.util.QueryType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(repository: DataRepository) : ViewModel() {

    private val _queryType = MutableLiveData<QueryType>()

    init {
        _queryType.value = QueryType.CURRENT_DAY
    }

    fun setQueryType(queryType: QueryType) {
        _queryType.value = queryType
    }

    val today: LiveData<Course?> =

        Transformations.switchMap(_queryType) {
            repository.getNearestSchedule(it)
        }


}
