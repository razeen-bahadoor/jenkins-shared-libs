package pipelinestep.build
import pipelinestep.basestep.Step


@groovy.transform.InheritConstructors
class WorkflowEnforcer extends Step {
    // The greatest enemy (bugs) will hide in the last place you would ever look ~ Julius Caesar

    WorkflowEnforcer(steps) {
        super(steps)
        this.config = config
    }

    void enforce(String env, String branch, String imageTag) {
        if (isValidDeployment(env, branch)) {
            this.steps.error("Pipeline aborted due to invalid deployment, the ${branch} may not be deployed to the ${env} environment")
        
        } else if(isValidPromotion(env, imageTag)) {
            this.steps.error("Pipeline aborted due to invalid promotion, Only build to PROD and UAT can be promoted by specifying a valid image tag")
        } else if(enforceConventionalCommitMessages()) {
            // TODO
        } 
    }


    Boolean isValidDeployment(String env, String branch) {
        //checks if the deployment configuration is valid absed on gitflow workflow
        //release,master & hotfix branches may only be deployed to SIT,UAT & PROD
        //develop branches may only be  deployed to develop & no other environment
        return !((env == "DEV" && branch != "DEV") || (env != "DEV" && branch == "DEV"))
    }

    Boolean isValidPromotion(String env,String imageTag) {
        Boolean result = isValidImageTag(imageTag)
        return (env == "UAT" || env == "PROD") && result || (env == "DEV" || env == "SIT") && !result
    }

    Boolean enforceConventionalCommitMessages() {
            return true
    }

    def isValidImageTag(String imageTag){
        def matcher = (name ==~ /^[\w\d\.]+\-\d+\-[a-z0-9]{7}\-(release|hotfix|master)$/)
        return matcher.matches()
    }
}


