package pipelinestep.build
import pipelinestep.basestep.Step
import java.lang.StringBuilder

@groovy.transform.InheritConstructors
class KanikoBuilder extends Step {

    Map<String, String> config

    KanikoBuilder(steps, Map<String, String> config) {
        super(steps)
        this.config = config
    }

    void build() {
        this.steps.sh getScript()
    }


    String getScript() {
        StringBuilder builder = new StringBuilder()
        builder.append('/kaniko/executor')
        .append(' --dockerfile `pwd`/Dockerfile')
        .append(' --context `pwd`')
        .append(' --destination=')
        .append(this.config.get("destination"))
        .append(" ")
        .append(this.config.getOrDefault("extraArgs",""))
        return builder.toString()
    }

}