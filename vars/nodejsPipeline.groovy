import static utilities.GitUtilities.*
import static utilities.ImageUtilities.generateImageTag
import static pipelinestep.build.KanikoBuilder.build
import static pipelinestep.prebuild.WorkflowEnforcer.enforce
import static pipelinestep.helm.HelmManifest.update

import utilities.*

def call(BuildConfig buildConfig) {

    def renderer = new podTemplateRenderer()
    String renderedTemplate = renderer.render(buildConfig.appType)

    // setup variables
    def containerRegistry = "${buildConfig.awsAccountIds[buildConfig.env]}.dkr.ecr.${buildConfig.awsRegion}.amazonaws.com"
    String imageTag
    


    podTemplate(  podRetention: never(),
            idleMinutes: 1,
            yaml: renderedTemplate) {
     node(POD_LABEL) {
        try {

                stage('checkout scm') {
                    buildConfig.scmVars = checkout scm
                    sh "git config --global --add safe.directory '*'"
                }



                stage('PreBuildWorkflowEnforcement') {
                        String branchType = getBranchType(buildConfig.scmVars.GIT_BRANCH)
                        String shortCommit = getShortCommit(this)
                        String version = (readJSON(file: 'package.json')).version
                        enforce(this, buildConfig.env, branchType, buildConfig.imageToDeploy)
                        imageTag = buildConfig.env == "UAT" || buildConfig.env == "PROD" ? buildConfig.imageToDeploy : generateImageTag(version, $BUILD_NUMBER, shortCommit, branchType)
                }

                // What stands in the way becomes the way~Marcus Aurelius

                stage('build') {
                    if (buildConfig.env == "DEV" || buildConfig.env == "SIT") {
                        container('kaniko') {
                            withCredentials([usernamePassword(credentialsId: 'bitbucket-token', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
                                Map<String,String> kanikoConfig = [
                                    'destination': "${containerRegistry}/${buildConfig.appName}:${imageTag}",
                                    'extraArgs': " --build-arg USERNAME=${GIT_USERNAME} --build-arg PASSWORD=${GIT_PASSWORD} --no-push"
                                ]
                                build(this, kanikoConfig)
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


                stage("Promote") {
                    // promote image for uat & prod
                    if(buildConfig.env == "UAT" || buildConfig.env == "PROD") {
                        // promoteimage from SIT to uat ecr
                    }
                }

                stage("Update Helm Chart") {
                  
                    update(this, [
                        "helmChartRepoBaseURL": config.helmChartRepoBaseURL,
                        "helmChartRepo": config.helmChartRepo,
                        "helmChartValuesPath": config.helmChartValuesPath,
                        "imageToDeploy": imageTag,
                        "env": config.env,
                        "appName": config.appName
                    ])
                }


                stage("deploy apigw") {}

                stage("notifications") {
                    
                }

                stage("cleanup") {}
        } catch (ex) {
            throw ex
        }
     }
            }
}