package pipelinestep.helm
import static utilities.GitUtilities.*

class HelmManifest {

    static void update(steps,Map<String,String> config) {
        String helmChartRepoURL = getCloneURL(config.helmChartRepoBaseURL, config.helmChartRepo)
        steps.sh "pwd"
        steps.sh "cd ../ && pwd"
        steps.sh "pwd && ls -la"
        gitClone(steps, helmChartRepoURL)
        steps.sh "ls -a"
        steps.sh "cd ${config.helmChartRepo}"
        steps.sh "ls -a"
        updateImageTag(steps, config.helmChartValuesPath, config.imageToDeploy, config.env, config.appName)
    }

    static void updateImageTag(steps,String valuesFile, String imageToDeploy,String env, String appName) {
        steps.sh "sed -ri \'s/^\\s*tag\\s*:.*\$/    tag: \'${imageToDeploy}\'/\' ${valuesFile}/values.yaml"
        steps.sh "git config  user.name 'Jenkins User'"
        steps.sh "git config  user.email '<>'"
        stageCommit(steps, "Updates ${appName} image on ${env} to ${imageToDeploy}")
        gitPush(steps)
    }  
}