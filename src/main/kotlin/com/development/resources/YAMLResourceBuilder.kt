package com.development.resources

// TODO improve documentation
/***
 * Instruction for Resource Factory how to build resource from yml file
 * resource file = filename with the file type ending. Example alien_news.yml
 * getResourceClassModel = Data class representation of the yml data transformed to java class
 */
interface YAMLResourceBuilder<T> {
    val resourceFile: String
    fun getResourcePath(): String = "$RESOURCE_FOLDER_ROOT$resourceFile"
    fun getResourceClassModel(): Class<T>
}
