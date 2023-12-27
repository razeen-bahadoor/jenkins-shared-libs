class BuildConfig {
    Map<String,String> awsAccountIds;
    String awsCrossAccountDeploymentRole
    String awsRegion
    String env
    String appName
    String appType
    String imageToDeploy = ""
    String helmChartRepo
    String helmChartValuesPath
    String helmChartRepoBaseURL
    def scmVars
}
     