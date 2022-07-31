package com.example.app1.utilities

import android.net.Uri
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

class linkUtilities {

    companion object {

        fun generateContentLink(url: String, domain: String): Uri {

            val baseUrl = Uri.parse(url)

            val link = FirebaseDynamicLinks.getInstance()
                .createDynamicLink()
                .setLink(baseUrl)
                .setDomainUriPrefix(domain)
                .setAndroidParameters(DynamicLink.AndroidParameters.Builder("com.example.app1").build())
                .buildDynamicLink()

            return link.uri

        }
    }
}