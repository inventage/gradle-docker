/*
 * (c) Copyright 2020 Palantir Technologies Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.palantir.gradle.docker

import groovy.util.logging.Slf4j
import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

@Slf4j
class DockerComposeUp extends DefaultTask {
    @Internal
    Configuration configuration

    DockerComposeUp() {
        this.group = 'Docker'
    }

    @TaskAction
    void run() {
        GradleExecUtils.execWithErrorMessage(project) {
            it.executable "docker"
            it.args "compose", "-f", getDockerComposeFile(), "up", "-d"
        }
    }

    @Internal
    @Override
    String getDescription() {
        def defaultDescription = "Executes `docker-compose` using ${dockerComposeFile.name}"
        return super.description ?: defaultDescription
    }

    @InputFiles
    File getDockerComposeFile() {
        return dockerComposeExtension.dockerComposeFile
    }

    @Internal
    DockerComposeExtension getDockerComposeExtension() {
        return project.extensions.findByType(DockerComposeExtension)
    }
}
