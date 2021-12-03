package com.github.unthrottled.intellijpowerplugin.services

import com.intellij.openapi.project.Project
import com.github.unthrottled.intellijpowerplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
