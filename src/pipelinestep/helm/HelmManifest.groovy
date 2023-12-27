package pipelinestep.helm
import static utilities.GitUtilities.*

class HelmManifest {

    static void update(steps,Map<String,String> config) {
        String helmChartRepoURL = getCloneURL(config.helmChartRepoBaseURL, config.helmChartRepo)
        steps.dir('helm') {
            gitClone(steps, helmChartRepoURL)
            steps.dir(config.helmChartRepo) {
                steps.sh "pwd && ls -a"
                updateImageTag(steps, config.helmChartValuesPath, config.imageToDeploy, config.env, config.appName)
                steps.sh "git config  user.name 'Jenkins User'"
                steps.sh "git config  user.email '<>'"
                stageCommit(steps, "Updates ${appName} image on ${env} to ${imageToDeploy}")
                gitPush(steps)
            }
        }
    }

    static void updateImageTag(steps,String valuesFile, String imageToDeploy,String env, String appName) {
        steps.sh "sed -ri \'s/^\\s*tag\\s*:.*\$/    tag: \'${imageToDeploy}\'/\' ${valuesFile}/values.yaml"

    }  
}