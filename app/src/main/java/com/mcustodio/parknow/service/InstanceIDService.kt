package com.mcustodio.parknow.service

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.mcustodio.parknow.debug

class InstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        debug(FirebaseInstanceId.getInstance().token)
    }
}