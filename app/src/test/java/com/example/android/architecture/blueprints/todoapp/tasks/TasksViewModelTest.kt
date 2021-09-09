package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailFragmentArgs
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    /* For testing this ViewModel we need to simulate an Android Framework class
    * to get the Application for the constructor. We need to follow these steps:
    * Add the AndroidX Test core and ext dependencies
    * Add the Robolectric Testing library dependency
    * Annotate the class with the AndroidJunit4 test runner
    * Write AndroidTX Test code
    * If our view model doesn't need an Application context, we don't need additional libraries*/

    private lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setupViewModel(){
        /** Given a fresh [TasksViewModel]*/
        tasksViewModel=TasksViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun addNewTask_setsNewTaskEvent() {

        // When adding a new task
        tasksViewModel.addNewTask()

        // Then the new task event is triggered
        // Getting the LiveData value
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()
        // Assert that the value is not null
        assertThat(value.getContentIfNotHandled(), (not(nullValue())))
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        // Check that if we've set our filter type to show all tasks, that the
        // Add task button is visible

        /** When [TasksFilterType.ALL_TASKS]*/
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        /** Then the [TasksViewModel.tasksAddViewVisible] is true*/
        val value=tasksViewModel.tasksAddViewVisible.getOrAwaitValue()
        assertThat(value,`is`(true))
    }

    /* Without LiveDataTestUtil
    @Test
    fun addNewTask_setsNewTaskEvent1() {

        // Given a fresh ViewModel
        val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())


        // Create observer - no need for it to do anything!
        val observer = androidx.lifecycle.Observer<Event<Unit>> {}
        try {

            // Observe the LiveData forever
            tasksViewModel.newTaskEvent.observeForever(observer)

            // When adding a new task
            tasksViewModel.addNewTask()

            // Then the new task event is triggered
            val value = tasksViewModel.newTaskEvent.value
            assertThat(value?.getContentIfNotHandled(), (not(nullValue())))

        } finally {
            // Whatever happens, don't forget to remove the observer!
            tasksViewModel.newTaskEvent.removeObserver(observer)
        }
    }*/
}