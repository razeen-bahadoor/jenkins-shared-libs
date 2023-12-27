package pipelinestep.build
import java.lang.StringBuilder

class KanikoBuilder {


    static void build(steps, Map<String, String> config) {
        steps.sh getScript(steps, config)
    }


    String getScript(steps, Map<String, String> config) {
        StringBuilder builder = new StringBuilder()
        builder.append('/kaniko/executor')
        .append(' --dockerfile `pwd`/Dockerfile')
        .append(' --context `pwd`')
        .append(' --destination=')
        .append(config.get("destination"))
        .append(" ")
        .append(config.getOrDefault("extraArgs",""))
        return builder.toString()
    }

}