package com.example.musicapp.model.repository


import android.content.Context
import android.util.Log
import com.example.musicapp.model.api.TrackApi
import com.example.musicapp.model.dao.TrackDao
import com.example.musicapp.model.entity.Track
import com.example.musicapp.utils.AppResult
import com.example.musicapp.utils.NetworkManager.isOnline
import com.example.musicapp.utils.TAG
import com.example.musicapp.utils.Utils.handleApiError
import com.example.musicapp.utils.Utils.handleSuccess
import com.example.musicapp.utils.noNetworkConnectivityError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrackRepositoryImpl(
    private val api: TrackApi,
    private val context: Context,
    private val dao: TrackDao
) :
    TrackRepository {

    override suspend fun getAllTracks(): AppResult<List<Track>> {
        if (isOnline(context)) {
            return try {
                val response = api.getAllTracks()
                if (response.isSuccessful) {
                    //save the data
                    response.body()?.let {
                        withContext(Dispatchers.IO) { dao.add(it) }
                    }
                    handleSuccess(response)
                } else {
                    handleApiError(response)
                }
            } catch (e: Exception) {
                AppResult.Error(e)
            }
        } else {
            //check in db if the data exists
            val data = getTracksDataFromCache()
            return if (data.isNotEmpty()) {
                Log.d(TAG, "from db")
                AppResult.Success(data)
            } else
            //no network
              context.noNetworkConnectivityError()
        }
    }

    private suspend fun getTracksDataFromCache(): List<Track> {
        return withContext(Dispatchers.IO) {
            dao.findAll()
        }
    }

/*
This is another way of implementing where the source of data is db and api but we can always fetch from db
which will be updated with the latest data from api and also change findAll() return type to
LiveData<List<CountriesData>>
*/
    /* val data = dao.findAll()

     suspend fun getAllCountriesData() {
         withContext(Dispatchers.IO) {
             val response = api.getAllCountries()
             response.body()?.let {
                 withContext(Dispatchers.IO) { dao.add(it) }
             }
         }
     }*/

}