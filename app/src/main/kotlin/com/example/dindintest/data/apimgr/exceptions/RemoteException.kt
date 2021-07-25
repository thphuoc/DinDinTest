package com.example.dindintest.data.apimgr.exceptions

import java.lang.Exception

open class RemoteException(val code: Int, message: String) : Exception(message)