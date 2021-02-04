package com.seamfix.printful.ui.users.userdetail

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.seamfix.printful.data.UserRepository
import com.seamfix.printful.model.User
import javax.inject.Inject

class UserDetailViewModel @ViewModelInject constructor(var userRepository: UserRepository?)
    : ViewModel() {

    /***  Returns a user from either remote database or local database ***/
    suspend fun getUser(userID: String): User? {
        var user: User? = null

        if(userRepository != null){
            //get user from remote database:
            user = userRepository?.getUserFromRemote(userID)

            if(user != null){
                //if we were able to fetch user's from  the service, then we need to save these users
                // in the local database for offline purpose:
                saveUser(user)

            }else{//if no user was found, we fetch from local database:
                user = userRepository?.getUserLocally(userID)
            }
        }
        return user
    }

    /*** Saves a user to the database ***/
    suspend fun saveUser(user: User){
        userRepository?.saveUser(user)
    }
}