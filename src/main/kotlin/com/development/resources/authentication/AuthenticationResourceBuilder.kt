package com.development.resources.authentication

import com.development.resources.YAMLResourceBuilder
import com.development.resources.newsChannel.NewsChannelsResource

class AuthenticationResourceBuilder : YAMLResourceBuilder<AuthenticationResource> {
    override val resourceFile: String = "authentication.yml"
    override fun getResourceClassModel(): Class<AuthenticationResource> = AuthenticationResource::class.java
}
