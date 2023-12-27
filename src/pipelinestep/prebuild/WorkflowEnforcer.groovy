package pipelinestep.prebuild

import static utilities.ImageUtilities.isValidReleaseImageTag


class WorkflowEnforcer  {
    // The greatest enemy (bugs) will hide in the last place you would ever look ~ Julius Caesar



    static void enforce(steps, String env, String branchType, String imageTag) {
        if (!isValidDeployment(env, branchType)) {
            steps.error("Pipeline aborted due to invalid deployment, ${branchType} branch may not be deployed to the ${env} environment")
        
        } else if(!isValidPromotion(env, imageTag)) {
            steps.error("Pipeline aborted due to invalid promotion, Only build to PROD and UAT can be promoted by specifying a valid image tag")
        } else if(enforceConventionalCommitMessages()) {
        // TODO
        } 
    }


    static Boolean isValidDeployment(String env, String branchType) {
        //checks if the deployment configuration is valid absed on gitflow workflow
        //release,master & hotfix branches may only be deployed to SIT,UAT & PROD
        //develop branches may only be  deployed to develop & no other environment
        return (env == "DEV" && branchType == "development") || ( env != "DEV" && branchType != "development")
    }

    static Boolean isValidPromotion(String env,String imageTag) {
        Boolean result = isValidReleaseImageTag(imageTag)
        return (env == "UAT" || env == "PROD") && result || (env == "DEV" || env == "SIT") && imageTag == ""
    }



    static Boolean enforceConventionalCommitMessages() {
            return true
    }





}


