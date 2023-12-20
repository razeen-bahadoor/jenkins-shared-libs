import static builders.KanikoBuilder.build
import utilities.*

def call(String env, String awsRegion="eu-west-1") {
    String template = "nodejs"
    def renderer= new podTemplateRenderer()
    String renderedTemplate = renderer.render(template)
    // setup variables
    def containerRegistry = "${Constants.awsAccountIds[env]}.dkr.ecr.${awsRegion}.amazonaws.com"

    podTemplate(  podRetention: never(),
            idleMinutes: 1,
            yaml: renderedTemplate) {
     node(POD_LABEL) {
        try {

                stage('preBuildCheck') {
                    container('ubuntu') {
                        checkout scm
                        preBuildChecks()
                    }
                }

                stage('build') {
                    if (env == "DEV" || env == "SIT") {
                        container('Kaniko') {
                            withCredentials([usernamePassword(credentialsId: 'bitbucket-token', usernameVariable: 'BITBUCKET_USERNAME', passwordVariable: 'BITBUCKET_PASSWORD')]) {
                                build(this, true, containerRegistry, usernameVariable, passwordVariable)

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