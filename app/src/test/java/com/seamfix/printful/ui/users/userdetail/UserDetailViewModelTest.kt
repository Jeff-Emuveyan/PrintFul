package com.seamfix.printful.ui.users.userdetail

import com.nhaarman.mockitokotlin2.mock
import com.seamfix.printful.data.UserRepository
import com.seamfix.printful.model.User
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

const val USER_ID = "USER_ID"
class UserDetailViewModelTest {

    private lateinit var viewModel: UserDetailViewModel

    private lateinit var repository: UserRepository

    @Before
    fun init() {
        repository = mock()
    }

    @Test
    fun `UserDetailViewModel should return data from remote source`() = runBlocking {

        //Given
        val userResponse = User(id = USER_ID, firstName = "Jeff")

        //When
        `when`(repository.getUserFromRemote(USER_ID)).thenReturn(userResponse)
        `when`(repository.saveUser(userResponse)).thenReturn(Unit)

        viewModel = UserDetailViewModel(repository)

        val user = viewModel.getUser(USER_ID)

        assertTrue(user != null && user.id == USER_ID)

    }

    @Test
    fun `UserDetailViewModel should return data from local source when remote source returns null`()
        = runBlocking{

        //Given
        val userResponse = User(id = USER_ID, firstName = "Jeff")

        //When
        `when`(repository.getUserFromRemote(USER_ID)).thenReturn(null)
        `when`(repository.getUserLocally(USER_ID)).thenReturn(userResponse)
        `when`(repository.saveUser(userResponse)).thenReturn(Unit)

        viewModel = UserDetailViewModel(repository)

        val user = viewModel.getUser(USER_ID)

        assertTrue(user != null && user.id == USER_ID)
    }

}