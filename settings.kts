import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2024.03"

project {

    subProject(ExampleTeamcity)
    subProject(ExampleTeamcityDz)
}


object ExampleTeamcity : Project({
    name = "Example Teamcity"

    vcsRoot(ExampleTeamcity_HttpsGithubComAragastmatbExampleTeamcityGitRefsHeadsMaster)

    buildType(ExampleTeamcity_Build)
})

object ExampleTeamcity_Build : BuildType({
    name = "Build"

    vcs {
        root(ExampleTeamcity_HttpsGithubComAragastmatbExampleTeamcityGitRefsHeadsMaster)
    }

    steps {
        maven {
            id = "Maven2"
            goals = "clean test"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object ExampleTeamcity_HttpsGithubComAragastmatbExampleTeamcityGitRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/aragastmatb/example-teamcity.git#refs/heads/master"
    url = "https://github.com/aragastmatb/example-teamcity.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "martishinid"
        password = "zxxfc3dd9cb8ca66c63775d03cbe80d301b"
    }
})


object ExampleTeamcityDz : Project({
    name = "Example Teamcity DZ"

    vcsRoot(ExampleTeamcityDz_HttpsGithubComMartishinidExampleTeamcityGitRefsHeadsMaster)

    buildType(ExampleTeamcityDz_Build)
})

object ExampleTeamcityDz_Build : BuildType({
    name = "Build"

    vcs {
        root(ExampleTeamcityDz_HttpsGithubComMartishinidExampleTeamcityGitRefsHeadsMaster)
    }

    steps {
        maven {
            id = "Maven2"

            conditions {
                contains("teamcity.build.branch", "master")
            }
            goals = "clean deploy"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            userSettingsSelection = "settings.xml"
        }
        maven {
            id = "Maven2_1"

            conditions {
                doesNotEqual("teamcity.build.branch", "master")
            }
            goals = "clean test"
            userSettingsSelection = "settings.xml"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object ExampleTeamcityDz_HttpsGithubComMartishinidExampleTeamcityGitRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/martishinid/example-teamcity.git#refs/heads/master"
    url = "https://github.com/martishinid/example-teamcity.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "martishinid"
        password = "zxxfc3dd9cb8ca66c63775d03cbe80d301b"
    }
})
