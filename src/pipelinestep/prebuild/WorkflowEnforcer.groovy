package pipelinestep.prebuild
import pipelinestep.basestep.Step


@groovy.transform.InheritConstructors
class WorkflowEnforcer extends Step {
    // The greatest enemy (bugs) will hide in the last place you would ever look ~ Julius Caesar

    WorkflowEnforcer(steps) {
        super(steps)
    }

    void enforce(String env, String branchType, String imageTag) {
        if (!isValidDeployment(env, branchType)) {
            this.steps.error("Pipeline aborted due to invalid deployment, ${branchType} branch may not be deployed to the ${env} environment")
        
        } else if(!isValidPromotion(env, imageTag)) {
            this.steps.error("Pipeline aborted due to invalid image tag, A valid IMAGE_TO_DEPLOY parameter is required for ${env}. See parameter description)")
        } else if(!isValidBuild(env, imageTag)) {
                this.steps.error("Pipeline aborted due to invalid image tag, no image tag required for ${env})")
        } else if(enforceConventionalCommitMessages()) {
            // TODO
        } 
    }


    Boolean isValidDeployment(String env, String branchType) {
        //checks if the deployment configuration is valid absed on gitflow workflow
        //release,master & hotfix branches may only be deployed to SIT,UAT & PROD
        //develop branches may only be  deployed to develop & no other environment
        return (env == "DEV" && branchType == "development") || ( env != "DEV" && branchType != "development")
    }

    Boolean isValidPromotion(String env,String imageTag) {
        return (env == "UAT" || env == "PROD") && isValidReleaseImageTag(imageTag) 
    }


    Boolean isValidBuild(String env,String imageTag) {
        // checks if env is dev or sit & no image tage specified
        // in groovy an empty string is consider falsey
        return (env == "DEV" || env == "SIT") && imageTag 
    
    }

    Boolean enforceConventionalCommitMessages() {
            return true
    }


    def isValidReleaseImageTag(String imageTag){
        return (imageTag ==~ /^[\w\d\.]+\-\d+\-[a-z0-9]{7}\-(release|hotfix|master)$/)
    }



}


