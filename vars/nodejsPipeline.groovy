import utilities.*

def call(String env) {
    String template = "nodejs"
    def renderer= new podTemplateRenderer()
    String renderedTemplate = renderer.render(template)
    podTemplate(  podRetention: never(),
            idleMinutes: 1,
            yaml: renderedTemplate) {
     node(POD_LABEL) {
        try {

                stage('preBuildCheck') {
                    container('ubuntu') {
                        preBuildChecks()
                    }
                }

                stage('build') {
                    if (env == "DEV" || env == "SIT") {
                        container('kaniko') {
                            withCredentials([usernamePassword(credentialsId: 'bitbucket-token', usernameVariable: 'BITBUCKET_USERNAME', passwordVariable: 'BITBUCKET_PASSWORD')]) {
                                String ecrRegistry = getECRRegistry(env)
                                echo ecrRegistry
//                            sh "/kaniko/executor --dockerfile `pwd`/Dockerfile --context `pwd` --destination=${CONTAINER_REGISTRY}/${SERVICE_NAME}:${IMAGENAME} --build-arg USERNAME=\$USERNAME --build-arg PASSWORD=\$PASSWORD"
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
                        preBuildChecks()
                    }
                }


                stage("promote") {
                    echo "promote"
                    // run only when env is uat + prod
                    // copy ecr
                }




        } catch (ex) {
            throw ex
        }
     }
    }
}