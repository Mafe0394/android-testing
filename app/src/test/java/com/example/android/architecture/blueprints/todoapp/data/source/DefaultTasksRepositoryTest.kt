package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Result.Success
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultTasksRepositoryTest {

    // Variables to represent the data in the fake data source
    private val task1 = Task("Title1", "Description")
    private val task2 = Task("Title2", "Description")
    private val task3 = Task("Title3", "Description")
    private val remoteTasks = listOf(task1,task2,task3).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    private lateinit var tasksRemoteDataSource:FakeDataSource
    private lateinit var tasksLocalDataSource:FakeDataSource

    // Class under test
    private lateinit var tasksRepository:DefaultTasksRepository

    /** initialization for test using the test double [FakeDataSource]*/

    @Before
    fun createRepository(){
        tasksRemoteDataSource= FakeDataSource(remoteTasks.toMutableList())
        tasksLocalDataSource= FakeDataSource(localTasks.toMutableList())
        // Get a reference to the class under test
        tasksRepository=DefaultTasksRepository(
            // TODO Dispatchers.Unconfined should be replaced with Dispatchers.Main
            tasksRemoteDataSource = tasksRemoteDataSource,
            tasksLocalDataSource = tasksLocalDataSource,
            ioDispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSourse()= runBlockingTest{
        // When tasks are loaded from the tasks repository
        val tasks=tasksRepository.getTasks(true) as Success

        // Then tasks are loaded from the remote data source
        assertThat(tasks.data, IsEqual(remoteTasks))
    }
}