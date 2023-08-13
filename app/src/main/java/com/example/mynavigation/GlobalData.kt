package com.example.mynavigation

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate

//import org.threeten.bp.LocalDate

data class DataModel(
    var athlet: Boolean = false, var selectedDate: LocalDate = LocalDate.now(),
//    var exerciseMap: MutableMap<String, MutableList<String>> = mutableMapOf()
    //Date ->[taskName,[taskDescription, taskComment]]
    var exerciseMap: MutableMap<LocalDate, MutableMap<String, MutableMap<String, String>>> = mutableMapOf()
)

class GlobalDataView : ViewModel() {
    private var globalData by mutableStateOf(DataModel())
    private var initializeCalled = false

    fun setAthlete(newAthlete: Boolean) {
        globalData = globalData.copy(athlet = newAthlete)
    }

    fun getAthlete(): Boolean {
        return globalData.athlet
    }

    fun setDate(newDate: LocalDate) {
        globalData = globalData.copy(selectedDate = newDate)
    }

    fun getDate(): LocalDate {
        return globalData.selectedDate
    }

    fun setExercise(key: LocalDate, value: MutableMap<String, MutableMap<String, String>>) {
        if (value.isEmpty()) return
        val newExerciseMap = getExercise()
        newExerciseMap[key] = value
        globalData = globalData.copy(exerciseMap = newExerciseMap)
    }

    fun setTask(key: String, value: MutableMap<String, String>) {
        val newExerciseMap = getExercise()
        newExerciseMap[globalData.selectedDate]?.set(key, value)
        globalData = globalData.copy(exerciseMap = newExerciseMap)
    }

    fun getExercise(): MutableMap<LocalDate, MutableMap<String, MutableMap<String, String>>> {
        return globalData.exerciseMap
    }

    fun initializeData() {
        if (initializeCalled) return
        initializeCalled = true
        val newTask: MutableMap<String, MutableMap<String, String>> = mutableMapOf()
        val taskDescription: MutableMap<String, String> = mutableMapOf()
        val localDate = LocalDate.now()

        taskDescription["Every 6 MIN x 5 sets"] = ""
        taskDescription["15/12 Calorie Row "] = ""
        taskDescription["10 Burpee Box Jump Over"] = ""
        taskDescription["12/10 Calorie Row"] = ""
        taskDescription["10 Burpee Box Jump Over"] = ""
        taskDescription["9/7 Calorie Row"] = ""

        newTask["Задание 1"] = taskDescription

        GlobalData.setExercise(key = localDate, value = newTask)

        val taskDescription2: MutableMap<String, String> = mutableMapOf()

        taskDescription2["For Total Reps in 24 minutes and 30 seconds"] = ""
        taskDescription2["Complete each movement for 40 seconds, then 20 seconds rest:"] = ""
        taskDescription2["Battle Ropes"] = ""
        taskDescription2["Sled Push (3x25 lb, 2x25 lb)"] = ""
        taskDescription2["Sledge Hammer on Tire"] = ""
        taskDescription2["Tire Flip"] = ""
        taskDescription2["Single-Unders"] = ""
        taskDescription2["Wall Ball Shots (20/14 lb)"] = ""
        taskDescription2["Weighted Sit-Ups (45/35 lb)"] = ""
        taskDescription2["Alternating Dumbbell Hang Snatches (50/35 lb)"] = ""
        taskDescription2["Ladders "] = ""
        taskDescription2["American Kettlebell Swings (50/35 lb)"] = ""

        newTask["Задание 2"] = taskDescription2

        GlobalData.setExercise(key = localDate, value = newTask)

        val taskDescription3: MutableMap<String, String> = mutableMapOf()

        taskDescription3["For Total Reps in 24 minutes and 30 seconds"] = ""
        taskDescription3["Complete each movement for 40 seconds, then 20 seconds rest:"] = ""
        taskDescription3["Battle Ropes"] = ""
        taskDescription3["Sled Push  3x25 lb, 2x25 lb "] = ""
        taskDescription3["Sledge Hammer on Tire"] = ""
        taskDescription3["Tire Flip"] = ""
        taskDescription3["Single-Unders"] = ""
        taskDescription3["Ladders "] = ""
        taskDescription3["American Kettlebell Swings  50/35 lb "] = ""

        newTask["Задание 3"] = taskDescription3

        GlobalData.setExercise(key = localDate, value = newTask)

        val taskDescription4: MutableMap<String, String> = mutableMapOf()

        taskDescription4["For Total Reps in 24 minutes and 30 seconds"] = ""
        taskDescription4["Complete each movement for 40 seconds, then 20 seconds rest:"] = ""
        taskDescription4["Battle Ropes"] = ""
        taskDescription4["Sled Push  3x25 lb, 2x25 lb "] = ""
        taskDescription4["Ladders "] = ""
        taskDescription4["American Kettlebell Swings  50/35 lb "] = ""

        newTask["Задание 4"] = taskDescription4

        GlobalData.setExercise(key = localDate, value = newTask)
    }
}


data class UserAuthData(
    var userAuthorization: Boolean = false,
    var userAuthentication: Boolean = false
)

class UserAuthView : ViewModel() {
    private var userAuthData by mutableStateOf(UserAuthData())

    fun setUserAuthorization(authorization: Boolean) {
        userAuthData = userAuthData.copy(userAuthorization = authorization)
    }

    fun setUserAuthentication(authentication: Boolean) {
        userAuthData = userAuthData.copy(userAuthentication = authentication)
    }

    fun isAuthorization(): Boolean {
        return userAuthData.userAuthorization
    }

    fun isAuthentication(): Boolean {
        return userAuthData.userAuthentication
    }

}

fun showState(message: String, context: android.content.Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

var GlobalData = GlobalDataView()
var UserAuth = UserAuthView()