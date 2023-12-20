import pipelinestep.build.*
import utilities.*










def call(BuildConfig buildConfig) {
    String template = "nodejs"
    def renderer = new podTemplateRenderer()
    String renderedTemplate = renderer.render(template)

    // setup variables
    def containerRegistry = "${buildConfig.awsAccountIds[buildConfig.env]}.dkr.ecr.${buildConfig.awsRegion}.amazonaws.com"
    String imageTag = "imagetag"
    
    
    podTemplate(  podRetention: never(),
            idleMinutes: 1,
            yaml: renderedTemplate) {
     node(POD_LABEL) {
        try {

                stage('preBuildCheck') {
                    container('ubuntu') {
                        checkout scm
                    // preBuildChecks()
                    }
                }



                stage('build') {
                    if (buildConfig.env == "DEV" || buildConfig.env == "SIT") {
                        container('kaniko') {
                            withCredentials([usernamePassword(credentialsId: 'bitbucket-token', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
                                Map<String,String> kanikoConfig = [
                                    'destination': "${containerRegistry}/${buildConfig.appName}:${imageTag}",
                                    'extraArgs': " --build-arg USERNAME=${GIT_USERNAME} --build-arg PASSWORD=${GIT_PASSWORD} --no-push"
                                ]
                                KanikoBuilder builder = new KanikoBuilder(this, kanikoConfig)
                                builder.build()
                            }
                        }
                    }
                }

                stage('test') {
                    container('ubuntu') {
                        echo "test"
                    }
                }


                stage('security checks') {
                    // kick off security pipeline
                    container('ubuntu') {
                       echo "security checks"
                    }
                }

                stage('push') {
                    // only push if security checls passed
                    container('ubuntu') {
                        sh "ls -a"
                    }
                }

                stage("promote") {
                    echo "promote"
                // run only when env is uat + prod
                // copy ecr
                }


                stage("notifications") {
                    
                }

                stage("cleanup") {}
        } catch (ex) {
            throw ex
        }
     }
            }
}