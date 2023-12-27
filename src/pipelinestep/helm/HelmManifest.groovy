package pipelinestep.helm
import static utilities.GitUtilities.*

class HelmManifest {

    static void update(steps,Map<String,String> config) {
        String helmChartRepoURL = getCloneURL(config.helmChartRepoBaseURL, config.helmChartRepo)
        steps.dir('helm') {
            gitClone(steps, helmChartRepoURL)
            steps.dir(config.helmChartRepo) {
                steps.sh "pwd && ls -a"
                updateImageTag(steps, config.helmChartValuesPath, config.imageToDeploy)
                steps.sh "git config  user.name 'Jenkins User'"
                steps.sh "git config  user.email '<>'"
                stageCommit(steps, "Updates ${config.appName} image on ${config.env} to ${config.imageToDeploy}")
                gitPush(steps)
            }
        }
    }

    static void updateImageTag(steps,String valuesFile, String imageToDeploy) {
        steps.sh "sed -ri \'s/^\\s*tag\\s*:.*\$/    tag: \'${imageToDeploy}\'/\' ${valuesFile}/values.yaml"

    }  
}