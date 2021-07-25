package com.example.dindintest.data.services

import com.example.dindintest.data.apimgr.APIManager
import com.example.dindintest.BuildConfig

private const val API_URL = "https://demo9972855.mockable.io/"

val mockService = APIManager(API_URL, BuildConfig.DEBUG)