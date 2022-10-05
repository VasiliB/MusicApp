package com.example.musicapp.app.di

import com.example.musicapp.view_model.TrackViewModel
import org.koin.dsl.module

//val viewModelModule = module {
//    single { TrackViewModel(get()) }
//}


//val apiModule = module {
//    fun provideTrackApi(retrofit: Retrofit): TrackApi {
//        return retrofit.create(TrackApi::class.java)
//    }
//
//    single { provideTrackApi(get()) }
//}

//val networkModule = module {
//    fun provideCache(application: Application): Cache {
//        val cacheSize = 10 * 1024 * 1024
//        return Cache(application.cacheDir, cacheSize.toLong())
//    }
//
//    fun provideHttpClient(cache: Cache): OkHttpClient {
//        val okHttpClientBuilder = OkHttpClient.Builder()
//            .cache(cache)
//
//        return okHttpClientBuilder.build()
//    }
//
//    fun provideGson(): Gson {
//        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
//    }
//
//
//    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("https://raw.githubusercontent.com/VasiliB/RSShool2021-Android-task6-Music-App/main/data/")
//            .addConverterFactory(GsonConverterFactory.create(factory))
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .client(client)
//            .build()
//    }
//
//    single { provideCache(androidApplication()) }
//    single { provideHttpClient(get()) }
//    single { provideGson() }
//    single { provideRetrofit(get(), get()) }
//
//}

//val databaseModule = module {
//
//    fun provideDatabase(application: Application): AppDatabase {
//        return Room.databaseBuilder(application, AppDatabase::class.java, "eds.database")
//            .fallbackToDestructiveMigration()
//            .allowMainThreadQueries()
//            .build()
//    }
//
//
//    fun provideDao(database: AppDatabase): TrackDao {
//        return database.trackDao
//    }
//
//    single { provideDatabase(androidApplication()) }
//    single { provideDao(get()) }
//}

//val repositoryModule = module {
//    fun provideUserRepository(api: TrackApi, dao: TrackDao): TrackRepository {
//        return TrackRepository(api, dao)
//    }
//
//    single { provideUserRepository(get(), get()) }
//}