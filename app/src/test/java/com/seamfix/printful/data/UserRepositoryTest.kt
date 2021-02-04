package com.seamfix.printful.data

import com.nhaarman.mockitokotlin2.mock
import com.seamfix.printful.datasource.local.AppDatabase
import com.seamfix.printful.datasource.remote.Service
import com.seamfix.printful.model.User
import com.seamfix.printful.model.UserResponse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import retrofit2.Response


const val DUMMY_ID = "aDummyId"


class UserRepositoryTest {

    private lateinit var service: Service
    private lateinit var database: AppDatabase

    @Before
    fun init() {
        service = mock()
        database = mock()
    }

    @Test
    fun `If network response code is 200, a user should be returned when getUserFromRemote(ID) is called`() =
        runBlocking {

            //Given
            val userA = User(id = DUMMY_ID, firstName = "JEFF")

            //When
            `when`(service.fetchUser(DUMMY_ID)).thenReturn(Response.success(userA))
            val repo = UserRepository()
            repo.service = service

            val userResponse = repo.getUserFromRemote(DUMMY_ID)

            //Then
            assertTrue(userResponse?.id == userA.id)
        }


    @Test
    fun `If network response code is NOT 200, null should be returned when getUserFromRemote(ID) is called`() =
        runBlocking {


            `when`(service.fetchUser(DUMMY_ID)).thenReturn(Response.error(400, FakeResponseBody()))
            val repo = UserRepository()
            repo.service = service

            val userResponse = repo.getUserFromRemote(DUMMY_ID)

            assertTrue(userResponse == null)
        }


    @Test
    fun `If network response code is 200, a list of users should be returned when getUsersFromRemote() is called`() =
        runBlocking {

            //Given
            val userA = User(id = DUMMY_ID, firstName = "JEFF")
            val userB = User(id = DUMMY_ID + "B", firstName = "JAMES")
            val userC = User(id = DUMMY_ID + "C", firstName = "JERRY")

            val userResponse = UserResponse()
            userResponse.data = arrayOf(userA, userB, userC)

            //When
            `when`(service.fetchUsers()).thenReturn(Response.success(userResponse))
            val repo = UserRepository()
            repo.service = service

            val result = repo.getUsersFromRemote()

            //Then
            assertTrue(result != null && result.size == 3)
        }


    @Test
    fun `If network response code is NOT 200, null should be returned when getUsersFromRemote() is called`() =
        runBlocking {

            //When
            `when`(service.fetchUsers()).thenReturn(Response.error(400, FakeResponseBody()))
            val repo = UserRepository()
            repo.service = service

            val result = repo.getUsersFromRemote()

            //Then
            assertTrue(result == null)
        }
}