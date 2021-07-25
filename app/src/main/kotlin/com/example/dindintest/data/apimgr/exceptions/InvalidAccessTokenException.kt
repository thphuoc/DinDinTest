package com.example.dindintest.data.apimgr.exceptions

class InvalidAccessTokenException : RemoteException(code = 401, "Invalid token")