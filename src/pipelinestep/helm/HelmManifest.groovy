package pipelinestep.helm
import static utilities.GitUtilities.*

class HelmManifest {

    static void update(steps,Map<String,String> config) {
 
        updateImageTag(steps, config.helmChartValuesPath, config.imageToDeploy, config.env, config.appName)
    }

    static void updateImageTag(steps,String valuesFile, String imageToDeploy,String env, String appName) {
        steps.sh "sed -ri \'s/^\\s*tag\\s*:.*\$/    tag: \'${imageToDeploy}\'/\' ${valuesFile}/values.yaml"
    }  
}