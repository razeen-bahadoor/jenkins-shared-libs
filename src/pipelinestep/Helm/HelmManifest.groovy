package pipelinestep.helm
import static utilities.GitUtilities.*

class HelmManifest {

    static void update(steps,Map<String,String> config) {
        String helmChartRepoURL = getCloneURL(config.helmChartRepoBaseURL, config.helmChartRepo)
        gitClone(steps, helmChartRepoURL)
        steps.sh "cd ${config.helmChartRepo}"
        updateImageTag(steps, config.helmChartValuesPath, config.imageToDeploy, config.env, config.appName)
    }

    static void updateImageTag(steps,String valuesFile, String imageToDeploy,String env, String appName) {
        steps.sh "sed -ri \'s/^\\s*tag\\s*:.*\$/    tag: \'${imageToDeploy}\'/\' ${valuesFile}"
        steps.sh "git config  user.name 'Jenkins User'"
        steps.sh "git config  user.email '<>'"
        stageCommit(steps, "Updates ${appName} image on ${env} to ${imageToDeploy}")
        gitPush(steps)
    }  
}