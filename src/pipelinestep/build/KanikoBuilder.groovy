package pipelinestep.build
import pipelinestep.base.BaseStep

@groovy.transform.InheritConstructors
class KanikoBuilder extends BaseStep {

    KanikoBuilder(steps) {
        super(steps)
    }

    // static void build(
    // context, 
    // Boolean noPush = true,String destination,
    // String bitbucketUsername, String bitbucketPassword) {
    // context.sh "/kaniko/executor --dockerfile `pwd`/Dockerfile --context `pwd` --destination=${destination} --build-arg USERNAME=${bitbucketUsername} --build-arg PASSWORD=${bitbucketPassword} --no-push --tar-path image.tar"
    // }

    void build(
        Boolean noPush = true,String destination,
        String bitbucketUsername, String bitbucketPassword) {
        this.steps.sh "/kaniko/executor --dockerfile `pwd`/Dockerfile --context `pwd` --destination=${destination} --build-arg USERNAME=${bitbucketUsername} --build-arg PASSWORD=${bitbucketPassword} --no-push --tar-path image.tar"
    }

}