// class Constants {

//     public static final Map<String,String> awsAccountIds = [
//             'DEV': 'xyz',
//             'SIT': 'xyz',
//             'UAT': 'xyz',
//             'PROD': 'xyz'
//     ]

//     public final String awsCrossAccountDeploymentRole = 'Bounded-jenkins-crossaccount-deployment-role'

// }
     

class BuildConfig {
    Map<String,String> awsAccountIds;
    String awsCrossAccountDeploymentRole
    String awsRegion
    String env

    // BuildConfig(Map<String, String> awsAccountIds, String awsCrossAccountDeploymentRole, String awsRegion, String env) {
    //         this.awsAccountIds = awsAccountIds
    //         this.awsCrossAccountDeploymentRole = awsCrossAccountDeploymentRole
    //         this.env = env
    //         this.awsRegion = awsRegion
    // }
}
     