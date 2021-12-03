package io.unthrottled.intellijpowerplugin.services

import com.intellij.openapi.project.Project
import io.unthrottled.intellijpowerplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
