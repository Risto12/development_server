package com.development.resources


/***
* Blueprint for Yaml Resource factory to find the resource file and how to create data class from it
* @property resourceFile file name that is located in resources folder. Example alien_news.yml
*/
interface YAMLResourceBuilder<T> {
    val resourceFile: String

    /**
     * @return [String] full path to resource file
     */
    fun getResourcePath(): String = "$RESOURCE_FOLDER_ROOT$resourceFile"

    /**
     * Yaml resource factory will populate this class with the fetched properties.
     * @return Class <[T]> that represents the class that will be populated with the fetched resources. Preferably
     * data class
     */
    fun getResourceClassModel(): Class<T>
}
