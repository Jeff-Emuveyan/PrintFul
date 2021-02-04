package com.seamfix.printful

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException
import com.seamfix.printful.datasource.local.AppDatabase
import com.seamfix.printful.model.User
import org.junit.Test
import com.seamfix.printful.data.UserRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat

const val USER_ID = "USER_ID"


/**
 * This class tests the Room database of the app through the UserDAO.
 * NOTE: An ANDROID DEVICE must be connected to the IDE in other to run this instrumented tests.
 * **/
@RunWith(AndroidJUnit4::class)
class UserDAOInstrumentedTest {

    private lateinit var db: AppDatabase
    private lateinit var repo: UserRepository

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        repo = UserRepository()
        repo.database = db
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun can_save_a_user_to_database() = runBlocking{

        //create a user
        val user = User(id = USER_ID, firstName = "JEFF")

        //save the user
        repo.saveUser(user)

        //check that the user was saved
        val savedUser = repo.getUserLocally(USER_ID)

        assertThat(savedUser?.id, CoreMatchers.equalTo(USER_ID))
    }


    @Test
    @Throws(Exception::class)
    fun can_update_a_user() = runBlocking {

        //create a user
        val user = User(id = USER_ID, firstName = "JEFF")

        //save the user
        repo.saveUser(user)

        //update the user:
        val userUpdate = User(id = USER_ID, firstName = "JEFF", lastName = "Emuveyan")

        //save the user update
        repo.saveUser(userUpdate)

        //confirm that the update was done:
        val savedUser = repo.getUserLocally(USER_ID)

        assertThat(savedUser?.lastName, CoreMatchers.equalTo("Emuveyan"))
    }


    @Test
    @Throws(Exception::class)
    fun can_get_all_users() = runBlocking{

        //create a users
        val userA = User(id = "1", firstName = "JEFF A")
        val userB = User(id = "2", firstName = "JEFF B")
        val userC = User(id = "3", firstName = "JEFF C")

        //save the users:
        repo.saveUser(userA)
        repo.saveUser(userB)
        repo.saveUser(userC)

        //confirm that the users were saved:
        val savedUsers = repo.getUsersLocally()

        assertThat(savedUsers?.size, CoreMatchers.equalTo(3))
    }

}