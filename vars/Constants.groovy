class Constants {
    private static final Map<String,String> awsAccountIds = [
            "DEV": "xyz",
            "SIT": "xyz",
            "UAT": "xyz",
            "PROD": "xyz"
    ]


    private final String awsCrossAccountDeploymentRole = "Bounded-jenkins-crossaccount-deployment-role"

    public static String getECRRegistry(String env, String awsRegion) {
        return "${awsAccountIds[env]}.dkr.ecr.${awsRegion}.amazonaws.com" as String
    }
}