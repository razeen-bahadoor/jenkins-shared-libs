import utilities.*




def call(String env, String awsRegion="eu-west-1") {
    String template = "nodejs"
    def renderer= new podTemplateRenderer()
    String renderedTemplate = renderer.render(template)

    // setup variables

    def ecrRegistry = "${Constants.awsAccountIds[env]}.dkr.ecr.${awsRegion}.amazonaws.com"



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
//                            sh '''/kaniko/executor --dockerfile `pwd`/Dockerfile --context `pwd` --destination=${ecrRegistry} --build-arg USERNAME="$USERNAME" --build-arg PASSWORD="$PASSWORD" --no-push --tar-path image.tar'''
                                sh '''
                                    set +x
                                    echo ${ecrRegistry}
                                    echo "$USERNAME"
                                '''
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
                    echo "ls -a"
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