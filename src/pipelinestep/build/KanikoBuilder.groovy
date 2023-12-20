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

    // static void build(
    // context, 
    // Boolean noPush = true,String destination,
    // String bitbucketUsername, String bitbucketPassword) {
    // context.sh "/kaniko/executor --dockerfile `pwd`/Dockerfile --context `pwd` --destination=${destination} --build-arg USERNAME=${bitbucketUsername} --build-arg PASSWORD=${bitbucketPassword} --no-push --tar-path image.tar"
    // }

    void build() {
        steps.echo getScript()
        // this.steps.sh "/kaniko/executor --dockerfile `pwd`/Dockerfile --context `pwd` --destination=${destination} --build-arg USERNAME=${bitbucketUsername} --build-arg PASSWORD=${bitbucketPassword} --no-push --tar-path image.tar"
    }


    String getScript() {
        StringBuilder builder = new StringBuilder()
        builder.append("test")
        return builder.toString()
    }

}